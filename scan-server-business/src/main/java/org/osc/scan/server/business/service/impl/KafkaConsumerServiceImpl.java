package org.osc.scan.server.business.service.impl;

import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.osc.common.util.JsonUtil;
import org.osc.scan.server.business.dao.*;
import org.osc.scan.server.business.metadata.EParseCallType;
import org.osc.scan.server.business.metadata.ESubtaskStatus;
import org.osc.scan.server.business.metadata.ParserResultStatus;
import org.osc.scan.server.business.metadata.StartType;
import org.osc.scan.server.business.pojo.bo.*;
import org.osc.scan.server.business.pojo.dto.ModuleDto;
import org.osc.scan.server.business.pojo.dto.SubTaskDto;
import org.osc.scan.server.business.pojo.dto.TaskDto;
import org.osc.scan.server.business.pojo.dto.ToolDto;
import org.osc.scan.server.business.pojo.kafka.*;
import org.osc.scan.server.business.producer.KafkaProducer;
import org.osc.scan.server.business.service.KafkaConsumerService;
import org.osc.scan.server.business.service.ScannerService;
import org.osc.scan.server.business.service.TaskService;
import org.osc.scan.server.business.util.Base64Util;
import org.osc.scan.server.business.util.ScanPathUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

/**
 * @author : zbz
 * @date : 2021/4/18
 */
@Slf4j
@Service
public class KafkaConsumerServiceImpl implements KafkaConsumerService {

    @Autowired
    private DataSourceTransactionManager dataSourceTransactionManager;

    @Autowired
    private TransactionDefinition transactionDefinition;

    @Autowired
    private ModuleDao moduleDao;

    @Autowired
    private ToolDao toolDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private SubTaskDao subTaskDao;

    @Autowired
    private TaskDao taskDao;

    @Autowired
    private ScannerService scannerService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private LangToolDao langToolDao;

    @Override
    public void scanServerSendTopic(ScanServerSend scanServerSend) {
        Integer subTaskId = scanServerSend.getSubTaskId();
        TransactionStatus transaction = dataSourceTransactionManager.getTransaction(transactionDefinition);
        log.info("open transaction, deal sub_task with id={}", subTaskId);
        SubTaskDto subTaskDto;
        try {
            subTaskDto = subTaskDao.finForUpdate(subTaskId);
            if (null == subTaskDto) {
                log.info("subTask cant start, no subtask found. subtask id={}", subTaskId);
                return;
            }
            if (!ESubtaskStatus.UN_DEAL.getCode().equals(subTaskDto.getStatus())) {
                log.info("subTask cant start, status is non-supported. subtask id={}, now status={}", subTaskId,
                    subTaskDto.getStatus());
                return;
            }
            log.info("update subtask status.");
            subTaskDao.updateStatusBySubTaskId(subTaskId, ESubtaskStatus.RUN.getCode());
        } catch (Exception e) {
            log.error("subTask for update error", e);
            dataSourceTransactionManager.rollback(transaction);
            return;
        } finally {
            if (!transaction.isCompleted()) {
                dataSourceTransactionManager.commit(transaction);
            }
        }
        // deal job
        sendJenkisJob(scanServerSend, subTaskDto.getTaskId(), subTaskDto.getSubTaskId());
    }

    /**
     * 启动Jenkins 更新规则集
     * 
     * @param scanServerSend
     * @param taskId
     */
    private void sendJenkisJob(ScanServerSend scanServerSend, Integer taskId, Integer subTaskId) {
        log.info("building job... params: task_id={} sub_task_id={} topic_msg={}", taskId, subTaskId,
            JsonUtil.obj2String(scanServerSend));
        boolean dealFlag = false;
        boolean isUpdateStatus = true;
        try {
            // step1: 构建scanner启动的参数
            StartScannerParamsBo startScannerParamsBo = buildJobParams(scanServerSend);
            if(StartType.I.key.equals(startScannerParamsBo.getJenkinsJobBuildParamsBo().getStartType())){
                if(StringUtils.isEmpty(startScannerParamsBo.getJenkinsJobBuildParamsBo().getScanPath())){
                    ParserResolveComplete resolveComplete = new ParserResolveComplete();
                    resolveComplete.setErrDesc("不触发jenkins");
                    resolveComplete.setErrDetail("不触发jenkins");
                    resolveComplete.setStatus(ESubtaskStatus.SUCCESS.getCode());
                    resolveComplete.setType(EParseCallType.SUB_PARSE.getCode());
                    resolveComplete.setTaskId(Integer.parseInt(startScannerParamsBo.getJenkinsJobBuildParamsBo().getTaskId()));
                    resolveComplete.setSubTaskId(Integer.parseInt(startScannerParamsBo.getJenkinsJobBuildParamsBo().getSubTaskId()));
                    parserResolveComplete(resolveComplete);
                    //回调portal
                    PortalReceiveScanResult portalReceiveScanResult = new PortalReceiveScanResult();
                    portalReceiveScanResult.setJobId(Integer.parseInt(startScannerParamsBo.getJenkinsJobBuildParamsBo().getSubTaskId()));
                    portalReceiveScanResult.setStatus(true);
                    portalReceiveScanResult.setScanType(startScannerParamsBo.getScanTypeId());
                    kafkaProducer.scanTaskCallbackTopic(portalReceiveScanResult);

                    dealFlag = true;
                    isUpdateStatus = false;
                    return;
                }
            }
            // step2: 启动scanner
            log.info("step2: start jenkins job,startScannerParamsBo:{}", JsonUtil.obj2String(startScannerParamsBo));
            dealFlag = scannerService.startScanner(startScannerParamsBo);
            if (dealFlag) {
                // step3: scanner启动成功，更新task、subTask
                log.info("step3: job started. Update task rule...");
                String moduleId = startScannerParamsBo.getJenkinsJobBuildParamsBo().getModuleId();
                taskService.updateRuleSet(taskId, StringUtils.isNotBlank(moduleId) ? Integer.valueOf(moduleId) : null);
            } else {
                log.error("step3: job error. dealFlag:{}", dealFlag);
            }
        } catch (Exception e) {
            dealFlag = false;
            log.error("scanServerSendTopic deal error", e);
        } finally {
            log.info("step4: send message to portal...");
                SubTaskDto subTaskDto = subTaskDao.find(subTaskId);
                if (dealFlag) {
                ScanTaskUpdateSend scanTaskUpdateSend = new ScanTaskUpdateSend();
                scanTaskUpdateSend.setTaskId(taskId);
                scanTaskUpdateSend.setScanType(subTaskDto.getScanTypeId());
                if(isUpdateStatus){
                    scanTaskUpdateSend.setStatus(ESubtaskStatus.RUN.getCode());
                }
                kafkaProducer.scanTaskUpdate(scanTaskUpdateSend);
            } else {
                subTaskDao.updateStatusBySubTaskId(subTaskId, ESubtaskStatus.FAIL.getCode());
                PortalReceiveScanResult portalReceiveScanResult = new PortalReceiveScanResult();
                portalReceiveScanResult.setJobId(subTaskId);
                portalReceiveScanResult.setStatus(false);
                portalReceiveScanResult.setScanType(subTaskDto.getScanTypeId());
                kafkaProducer.scanTaskCallbackTopic(portalReceiveScanResult);
            }
        }
    }

    @Override
    public void parserResolveComplete(ParserResolveComplete request) {
        // 根据消息类型判断 0=子任务消息 1=合并任务消息
        if (EParseCallType.SUB_PARSE.compareWithCode(request.getType())) {
            // 查询当前任务信息 重组请求参数信息
            Integer subTaskId = request.getSubTaskId();
            SubTaskDto subTaskDto = subTaskDao.find(subTaskId);
            if (null == subTaskDto) {
                log.info("subtask cant start, no subtask found. subtask id={}", subTaskId);
                return;
            }
            dealSubtask(new SubTaskBo(subTaskDto.getTaskId(), subTaskId, request.getStatus(), request.getErrDesc(),
                request.getErrDetail(), subTaskDto.getScanTypeId()));
        }
        if (EParseCallType.MERGE_PARSE.compareWithCode(request.getType())) {
            dealMerge(request);
        }
    }

    /**
     * 构建调用Jenkins Job参数
     *
     * @param scanServerSend
     *            scanServerSend
     * 
     * @return StartScannerParamsBo
     */
    private StartScannerParamsBo buildJobParams(ScanServerSend scanServerSend) {
        log.info("step1: build params for jenkins job... param={}", JsonUtil.obj2String(scanServerSend));
        Integer subTaskId = scanServerSend.getSubTaskId();
        // 1. subtask info
        SubTaskDto subTaskDto = subTaskDao.find(subTaskId);
        // 2. task info
        TaskDto taskDto = taskDao.find(subTaskDto.getTaskId());
        if (null == taskDao) {
            log.error("no task found. build params error.");
            throw new RuntimeException("no task found.");
        }
        // build subTaskBo
        SubTaskBo subTaskBo = new SubTaskBo(subTaskDto);
        subTaskBo.setTaskBo(new TaskBo(taskDto));
        // taskBo
        TaskBo taskBo = subTaskBo.getTaskBo();
        Integer taskId = taskBo.getTaskId();
        log.info("building jenkins job params. task id={} subtask id={}", taskId, subTaskId);
        // 3.check tool
        LinkedList<Integer> tools = new LinkedList<>();
        tools.add(subTaskBo.getToolId());
        checkTool(subTaskBo.getLangId(), tools);
        // 4.module info
        ModuleDto moduleDto = moduleDao.find(taskBo.getModuleId());
        // 启动方式 3=手工提交
        Integer startWay = taskBo.getStartWay();
        if (null != startWay && startWay != 3 && null == moduleDto) {
            log.error("no module found. build params error.");
            throw new RuntimeException("no module found. build params error.");
        }
        ModuleBo moduleBo = new ModuleBo(moduleDto);
        if (null != moduleDto) {
            Integer getcodeType = moduleDto.getGetcodeType();
            moduleBo.setGetCodeType(null == getcodeType ? StringUtils.EMPTY : String.valueOf(getcodeType));
            Integer creator = moduleDto.getCreator();
            moduleBo.setCreator(null == creator ? StringUtils.EMPTY : String.valueOf(creator));
        }

        // 5.build params
        StartScannerParamsBo startScannerParamsBo = new StartScannerParamsBo();
        startScannerParamsBo.setScanTypeId(subTaskDto.getScanTypeId());
        startScannerParamsBo.setJenkinsJobBuildParamsBo(
            setBuildParams(subTaskBo, moduleBo, scanServerSend.getCodePath(), scanServerSend.getScanFileUri()));
        // jobName
        if (null != startWay && startWay == 3) {
            startScannerParamsBo.setJobName("manual_job");
            startScannerParamsBo
                .setMCreator(null != taskBo.getTaskOwner() ? String.valueOf(taskBo.getTaskOwner()) : StringUtils.EMPTY);
        } else {
            String jobName = StringUtils.EMPTY;
            if (StringUtils.isNotBlank(moduleBo.getModuleName())) {
                jobName = moduleBo.getModuleName().replaceAll("/", "_");
            }
            startScannerParamsBo.setJobName(moduleBo.getModuleId() + "_" + jobName);
            startScannerParamsBo.setMCiServer(moduleBo.getCiServer());
            startScannerParamsBo.setMCreator(moduleBo.getCreator());
            startScannerParamsBo.setMAssignedNode(moduleBo.getAgentName());
        }
        return startScannerParamsBo;
    }

    /**
     * 组织任务信息到构建参数
     * 
     * @param subTaskBo
     * @param moduleBo
     * @param codePath
     * @return
     */
    private JenkinsJobBuildParamsBo setBuildParams(SubTaskBo subTaskBo, ModuleBo moduleBo, String codePath,
        String scanFileUri) {
        JenkinsJobBuildParamsBo paramsBo = new JenkinsJobBuildParamsBo();
        // task sub_task
        TaskBo taskBo = subTaskBo.getTaskBo();
        // user_name
        String userName = userDao.getNameById(taskBo.getTaskOwner());
        // main params
        paramsBo.setScConfig("");
        // sub_task_id
        paramsBo.setSubTaskId(String.valueOf(subTaskBo.getSubTaskId()));
        // lang.lang_id
        paramsBo.setCodeType(null == subTaskBo.getLangId() ? StringUtils.EMPTY : String.valueOf(subTaskBo.getLangId()));

        // 启动方式：0平台启动，1 CI触发 2 API接入 3手工提交\n4 本地提交
        paramsBo.setStartType(taskBo.getType());
        // 启动方式：0平台启动，1 CI触发 2 API接入 3手工提交\n4 本地提交
        paramsBo.setStartWay(null == taskBo.getStartWay() ? StringUtils.EMPTY : String.valueOf(taskBo.getStartWay()));
        // 目标分支，PR环节才会有
        // TODO: 2021/4/30 target_branch
        paramsBo.setTargetBranch(null);
        // Git临时评审版本号
        paramsBo.setCommitIdRef(taskBo.getCommitIdRef());
        // commit_id
        paramsBo.setCommitId(taskBo.getCommitId());
        // 不用了
        paramsBo.setProductPath("");
        // 分支名
        paramsBo.setBranchName(taskBo.getBranchName());
        // 指定的扫描路径，多路径逗号间隔
        paramsBo.setScanPath(StartType.I.key.equals(taskBo.getType())?dealScanPath(subTaskBo):taskBo.getScanPath());
        log.info("paramsBo------------>scanpath:{}",paramsBo.getScanPath());
        // 需要过滤的文件，以,分割
        paramsBo.setFilterPath(taskBo.getFilterPath());
        // 1、2、3分别对应高中低扫描级别
        paramsBo.setBugPriority(null == taskBo.getBugPriority() ? "2" : String.valueOf(taskBo.getBugPriority()));
        // 扫描工具代码
        paramsBo.setToolType(null == subTaskBo.getToolId() ? StringUtils.EMPTY : String.valueOf(subTaskBo.getToolId()));
        // 基准commit_id
        paramsBo.setBaseCommitId(
            null == taskBo.getBaseCommitId() ? StringUtils.EMPTY : String.valueOf(taskBo.getBaseCommitId()));
        // task_id
        paramsBo.setTaskId(null == subTaskBo.getTaskId() ? StringUtils.EMPTY : String.valueOf(subTaskBo.getTaskId()));
        // ""/"eagle"/"fullcasn"
        paramsBo.setThirdPartyName(
            null == taskBo.getThirdPartyId() ? StringUtils.EMPTY : String.valueOf(taskBo.getThirdPartyId()));
        // need cpd_check?
        paramsBo.setCpdCheck(
            null == subTaskBo.getCpdChecked() ? StringUtils.EMPTY : String.valueOf(subTaskBo.getCpdChecked()));
        // 扫描文件类型，源码or编译产物
        paramsBo.setTargetType("0");
        // 不用了
        paramsBo.setPatch("");
        // static_check, 统计分析报告，report_infos
        Integer stChecked = subTaskBo.getStChecked();
        paramsBo.setStCheck(null == stChecked ? StringUtils.EMPTY : String.valueOf(stChecked));
        // 代码重复度分析，cpd_metrics
        Integer miChecked = subTaskBo.getMiChecked();
        paramsBo.setMiCheck(null == miChecked ? StringUtils.EMPTY : String.valueOf(miChecked));

        // module info is not null
        if (null != moduleBo) {
            // userName
            userName = (StringUtils.isNotBlank(moduleBo.getCodeUser()) ? moduleBo.getCodeUser() : userName);
            // 账户
            paramsBo.setCodeUser(userName);
            // 密码
            paramsBo.setCodePwd(Base64Util.encode(moduleBo.getCodePwd()));
            // 代码地址
            paramsBo.setCodePath(moduleBo.getCodePath());
            // == module.getcode_type 1/2/3/4
            paramsBo.setJobPattern(moduleBo.getGetCodeType());
            // 代码工程编译命令 module.complie_cmd
            paramsBo.setCompileCmd(moduleBo.getCompileCmd());
            // jenkins job name
            paramsBo.setProjectKey(moduleBo.getJobName());
            // un_need params
            paramsBo.setModuleId(
                null == moduleBo.getModuleId() ? StringUtils.EMPTY : String.valueOf(moduleBo.getModuleId()));
            // codeUser
            if ("3".equals(moduleBo.getGetCodeType())) {
                paramsBo.setCodeUser(moduleBo.getCodeUser());
            }
        }
        // 手工調用：手动调用会传入codePath
        if (StringUtils.isNotBlank(codePath)) {
            // 密码
            paramsBo.setCodePwd(Base64Util.encode(StringUtils.EMPTY));
            // 代码工程编译命令 module.complie_cmd
            paramsBo.setCompileCmd(StringUtils.EMPTY);
            // jenkins job name
            paramsBo.setProjectKey(StringUtils.EMPTY);
            // un_need params
            paramsBo.setModuleId(StringUtils.EMPTY);

            // 账户
            paramsBo.setCodeUser(userName);
            // 指定的扫描路径，多路径逗号间隔
            paramsBo.setCodePath(codePath);
            // 默認：4=wget
            paramsBo.setJobPattern("4");
        }
        paramsBo.setScanFileUri(scanFileUri);
        return paramsBo;
    }

    /**
     * 扫描工具校验
     *
     * @param langId
     *            langId
     * @param tools
     *            tools
     */
    private void checkTool(Long langId, LinkedList<Integer> tools) {
        // 根据langId，查询工具集
        List<ToolDto> toolDtos = toolDao.findToolsByLangId(langId);
        Set<Integer> rightTools =
            toolDtos.stream().collect(HashSet::new, (s, dto) -> s.add(dto.getToolId()), AbstractCollection::addAll);
        // Android可用java
        if (langId == 4L) {
            rightTools.add(2);
        }
        // 记录正确工具数
        int size = rightTools.size();
        // 加入正在使用的工具
        rightTools.addAll(tools);
        if (rightTools.size() > size) {
            log.error("find wrong lang={} tools={}", langId, tools);
            throw new RuntimeException("find wrong lang=" + langId + " tools=" + tools);
        }
    }

    /**
     * parse 自任务消息回调
     * 
     * @param request
     *            消息信息
     */
    private void dealSubtask(SubTaskBo request) {
        // 1.更新子任务状态 update
        SubTaskDto subTaskDto = new SubTaskDto();
        BeanUtils.copyProperties(request, subTaskDto);
        subTaskDao.updateSubTask(subTaskDto);

        // 2.查询当前任务是否全部解析成功 成功=当前任务是否全部成功 失败=通知portal
        if (ParserResultStatus.SUCCESS.getCode().equals(request.getStatus())) {
            if (taskDao.unSuccTaskCount(request.getTaskId(), request.getScanTypeId()) == 0) {
                kafkaProducer.parserStartMergeResolve(new ParserStartMergeResolve(request.getTaskId()));
            }
        } else {
            PortalReceiveScanResult portalReceiveScanResult = new PortalReceiveScanResult();
            portalReceiveScanResult.setJobId(request.getSubTaskId());
            portalReceiveScanResult.setStatus(false);
            portalReceiveScanResult.setScanType(request.getScanTypeId());
            portalReceiveScanResult.setErrDesc(request.getErrDesc());
            portalReceiveScanResult.setErrDetail(request.getErrDetail());
            kafkaProducer.scanTaskCallbackTopic(portalReceiveScanResult);
        }
    }

    /**
     * parse 合并任务消息回调
     * 
     * @param request
     *            消息信息
     */
    private void dealMerge(ParserResolveComplete request) {
        List<SubTaskDto> subTaskDtoList = subTaskDao.selectByTaskId(request.getTaskId());
        if (CollectionUtils.isEmpty(subTaskDtoList)) {
            log.error("dealMerge error, subTaskDtoList is empty, request:{}", JsonUtil.obj2String(request));
            return;
        }
        if (ParserResultStatus.FAIL.getCode().equals(request.getStatus())) {
            subTaskDao.updateStatusErrDescByTaskId(request.getTaskId(), request.getErrDesc(),
                ESubtaskStatus.FAIL.getCode());
        }
        // 合并扫描完成之后，只发一次Q即可
        SubTaskDto subTaskDto = subTaskDtoList.get(0);
        PortalReceiveScanResult portalReceiveScanResult = new PortalReceiveScanResult();
        portalReceiveScanResult.setJobId(subTaskDto.getSubTaskId());
        portalReceiveScanResult.setScanType(subTaskDto.getScanTypeId());
        portalReceiveScanResult.setErrDesc(request.getErrDesc());
        portalReceiveScanResult.setErrDetail(request.getErrDetail());
        portalReceiveScanResult.setStatus(ESubtaskStatus.SUCCESS.getCode().equals(request.getStatus()));
        kafkaProducer.scanTaskCallbackTopic(portalReceiveScanResult);
    }

    private String dealScanPath(SubTaskBo subTaskBo){
        if(StringUtils.isNotBlank(subTaskBo.getTaskBo().getScanPath())){
            String languageName = langToolDao.getLangNameById(subTaskBo.getLangId());
            String[] fileNameArr = subTaskBo.getTaskBo().getScanPath().split(",");
            Set<String> langList = new HashSet<>();
            for (String fileName : fileNameArr) {
                String langName = ScanPathUtil.getLangFromFileName(fileName);
                langList.add(langName);
            }
            Set<String> langNames = ScanPathUtil.getPatterLangNames(langList);
            log.info("dealScanPath langNames----------->" + langNames);
            HashMap<String, String> diffFileDict =
                    ScanPathUtil.getDiffFileDict(langNames, fileNameArr);
            log.info("dealScanPath diffFileDict------->:{}",diffFileDict);
            return diffFileDict.get(languageName.toLowerCase());

        }
        return subTaskBo.getTaskBo().getScanPath();

    }

}

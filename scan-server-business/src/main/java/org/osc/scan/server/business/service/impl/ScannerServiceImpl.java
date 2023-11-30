package org.osc.scan.server.business.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.osc.common.util.JsonUtil;
import org.osc.scan.server.business.config.JenkinsConfigProperties;
import org.osc.scan.server.business.pojo.bo.StartScannerParamsBo;
import org.osc.scan.server.business.pojo.template.ConfigTemplate;
import org.osc.scan.server.business.service.ScannerService;
import org.osc.scan.server.business.util.FreemarkerUtil;
import org.osc.scan.server.business.util.JenkinsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.JobWithDetails;

import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;

/**
 * @author : zbz
 * @date : 2021/4/17
 */
@Slf4j
@Service
public class ScannerServiceImpl implements ScannerService {

    @Autowired
    private JenkinsConfigProperties jenkinsConfigProperties;

    /**
     * 启动scanner
     */
    @Override
    public boolean startScanner(StartScannerParamsBo startScannerParamsBo) {
        if (startScannerParamsBo == null) {
            throw new RuntimeException("startScannerParamsBo is null");
        }
        // step1: 获取jenkins连接
        JenkinsServer jenkinsServer = JenkinsUtil.getConnection(jenkinsConfigProperties.getUrl(),
            jenkinsConfigProperties.getAccount(), jenkinsConfigProperties.getPassword());
        // step2: 检查对应job是否存在
        JobWithDetails jobWithDetails = JenkinsUtil.getJobByJobName(jenkinsServer, startScannerParamsBo.getJobName());
        if (jobWithDetails == null) {
            jobWithDetails = JenkinsUtil.createJob(jenkinsServer, startScannerParamsBo.getJobName(), buildJobXml());
        }
        // step3: 对job进行build
        Map<String, String> params = new HashMap<>(1);
        params.put("json_param", JsonUtil.obj2String(startScannerParamsBo.getJenkinsJobBuildParamsBo()));
        return JenkinsUtil.buildJobWithParams(jobWithDetails, params);
    }

    private String buildJobXml() {
        String jobXml;
        try {
            ConfigTemplate configTemplate = new ConfigTemplate();
            configTemplate.setCommand(jenkinsConfigProperties.getCommand());
            configTemplate.setScript(jenkinsConfigProperties.getScript());
            configTemplate.setAssignedNode(jenkinsConfigProperties.getAssignedNode());
            jobXml = FreemarkerUtil.toStr("config.ftl", configTemplate);
        } catch (IOException | TemplateException e) {
            throw new RuntimeException("buildJobXml error");
        }
        return jobXml;
    }
}

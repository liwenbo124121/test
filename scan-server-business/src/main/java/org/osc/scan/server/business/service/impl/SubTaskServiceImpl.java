package org.osc.scan.server.business.service.impl;

import org.osc.common.util.JsonUtil;
import org.osc.scan.server.business.dao.SubTaskDao;
import org.osc.scan.server.business.pojo.bo.ScannerUpdateSubTaskParamsBo;
import org.osc.scan.server.business.pojo.dto.SubTaskDto;
import org.osc.scan.server.business.pojo.kafka.PortalReceiveScanResult;
import org.osc.scan.server.business.producer.KafkaProducer;
import org.osc.scan.server.business.service.SubTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * { "status": 1, "err_desc": "", "err_detail": "", "file_count": 1, "code_count": 11, "starttime": "2021-05-11
 * 12:00:00", "node_name": "123", }
 *
 * @author : zbz
 * @date : 2021/4/19
 */
@Service
@Slf4j
public class SubTaskServiceImpl implements SubTaskService {

    @Autowired
    private SubTaskDao subTaskDao;

    @Autowired
    private KafkaProducer kafkaProducer;

    @Override
    public void scannerUpdateSubTask(ScannerUpdateSubTaskParamsBo scannerUpdateSubTaskParamsBo) {
        if (scannerUpdateSubTaskParamsBo == null) {
            throw new RuntimeException("scannerUpdateSubTaskParamsBo in null");
        }
        log.info("find sub task. param={}", JsonUtil.obj2String(scannerUpdateSubTaskParamsBo));
        SubTaskDto subTaskDto = subTaskDao.find(scannerUpdateSubTaskParamsBo.getSubTaskId());
        if (subTaskDto == null) {
            throw new RuntimeException("subTaskDto in not exist");
        }
        subTaskDto.setStatus(scannerUpdateSubTaskParamsBo.getStatus());
        subTaskDto.setErrDesc(scannerUpdateSubTaskParamsBo.getErrDetail());
        subTaskDto.setErrDetail(scannerUpdateSubTaskParamsBo.getErrDetail());
        subTaskDto.setFileCount(scannerUpdateSubTaskParamsBo.getFileCount());
        subTaskDto.setCodeCount(scannerUpdateSubTaskParamsBo.getCodeCount());
        subTaskDto.setStarttime(scannerUpdateSubTaskParamsBo.getStartTime());
        subTaskDto.setNodeName(scannerUpdateSubTaskParamsBo.getNodeName());
        subTaskDao.updateSubTask(subTaskDto);

        // 扫描任务失败，通知portal
        if (null != scannerUpdateSubTaskParamsBo.getStatus() && scannerUpdateSubTaskParamsBo.getStatus() == 4) {
            PortalReceiveScanResult portalReceiveScanResult = new PortalReceiveScanResult();
            portalReceiveScanResult.setScanType(subTaskDto.getScanTypeId());
            portalReceiveScanResult.setJobId(scannerUpdateSubTaskParamsBo.getSubTaskId());
            portalReceiveScanResult.setStatus(false);
            portalReceiveScanResult.setErrDesc(scannerUpdateSubTaskParamsBo.getErrDesc());
            portalReceiveScanResult.setErrDetail(scannerUpdateSubTaskParamsBo.getErrDetail());
            kafkaProducer.scanTaskCallbackTopic(portalReceiveScanResult);
        }
    }

}

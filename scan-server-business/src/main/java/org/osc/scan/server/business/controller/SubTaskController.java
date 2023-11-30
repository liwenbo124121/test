package org.osc.scan.server.business.controller;

import lombok.extern.slf4j.Slf4j;
import org.osc.common.util.JsonUtil;
import org.osc.scan.server.business.pojo.bo.ScannerUpdateSubTaskParamsBo;
import org.osc.scan.server.business.pojo.vo.ScannerUpdateSubTaskRequestVo;
import org.osc.scan.server.business.pojo.vo.ScannerUpdateSubTaskUpdateResponseVo;
import org.osc.scan.server.business.service.SubTaskService;
import org.osc.scan.server.business.util.ResponseInfoUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : zbz
 * @date : 2021/4/19
 */
@RequestMapping("/subTask")
@RestController
@Slf4j
public class SubTaskController {

    @Autowired
    private SubTaskService subTaskService;

    @PostMapping("/scannerUpdateSubTask")
    public ResponseInfoUtil.ResponseInfo<ScannerUpdateSubTaskUpdateResponseVo>
        scannerUpdateSubTask(@RequestBody ScannerUpdateSubTaskRequestVo scannerUpdateSubTaskRequestVo) {
        try {
            log.info("update sub task. params={}", JsonUtil.obj2String(scannerUpdateSubTaskRequestVo));
            ScannerUpdateSubTaskParamsBo scannerUpdateSubTaskParamsBo = new ScannerUpdateSubTaskParamsBo();
            BeanUtils.copyProperties(scannerUpdateSubTaskRequestVo, scannerUpdateSubTaskParamsBo);
            subTaskService.scannerUpdateSubTask(scannerUpdateSubTaskParamsBo);
        } catch (RuntimeException e) {
            return ResponseInfoUtil.fail(e.getMessage());
        }
        return ResponseInfoUtil.success(null);
    }

}

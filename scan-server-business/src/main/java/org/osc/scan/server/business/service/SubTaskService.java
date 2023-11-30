package org.osc.scan.server.business.service;

import org.osc.scan.server.business.pojo.bo.ScannerUpdateSubTaskParamsBo;

/**
 * @author : zbz
 * @date : 2021/4/19
 */
public interface SubTaskService {

    /**
     * scanner回调更新SubTask
     * 
     * @param scannerUpdateSubTaskParamsBo
     *            scannerUpdateSubTask入参
     */
    void scannerUpdateSubTask(ScannerUpdateSubTaskParamsBo scannerUpdateSubTaskParamsBo);
}

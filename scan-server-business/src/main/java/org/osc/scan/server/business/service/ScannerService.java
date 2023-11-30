package org.osc.scan.server.business.service;

import org.osc.scan.server.business.pojo.bo.StartScannerParamsBo;

/**
 * @author : zbz
 * @date : 2021/4/18
 */
public interface ScannerService {

    /**
     * 启动scanner
     * 
     * @param startScannerParamsBo
     *            启动scanner的参数
     * @return 启动结果
     */
    boolean startScanner(StartScannerParamsBo startScannerParamsBo);

}

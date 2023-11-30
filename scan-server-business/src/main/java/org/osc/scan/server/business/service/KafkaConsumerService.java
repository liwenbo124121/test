package org.osc.scan.server.business.service;

import org.osc.scan.server.business.pojo.kafka.ParserResolveComplete;
import org.osc.scan.server.business.pojo.kafka.ScanServerSend;

/**
 * @author : xyih
 * @date : 2021/4/16
 */
public interface KafkaConsumerService {

    /**
     * 启动scanner扫描
     * 
     * @param scanServerSend
     *            scanServerSend
     */
    void scanServerSendTopic(ScanServerSend scanServerSend);

    /**
     * 处理parser完成解析的回调
     * 
     * @param request
     *            请求参数
     */
    void parserResolveComplete(ParserResolveComplete request);

}

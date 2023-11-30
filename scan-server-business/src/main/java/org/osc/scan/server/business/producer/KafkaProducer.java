package org.osc.scan.server.business.producer;

import org.osc.common.util.JsonUtil;
import org.osc.scan.server.business.metadata.TopicConstant;
import org.osc.scan.server.business.pojo.kafka.ParserStartMergeResolve;
import org.osc.scan.server.business.pojo.kafka.PortalReceiveScanResult;
import org.osc.scan.server.business.pojo.kafka.ScanTaskUpdateSend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * @author : zbz
 * @date : 2021/4/18
 */
@Service
public class KafkaProducer extends BaseProducer<String, String> {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    /**
     * 告诉portal扫描结果
     * 
     * @param data
     *            数据
     */
    public void scanTaskCallbackTopic(PortalReceiveScanResult data) {
        super.callBack(kafkaTemplate.send(TopicConstant.SCAN_TASK_CALLBACK_TOPIC, JsonUtil.obj2String(data)),
            TopicConstant.SCAN_TASK_CALLBACK_TOPIC);
    }

    /**
     * 通知parser合并扫描
     * 
     * @param data
     *            数据
     */
    public void parserStartMergeResolve(ParserStartMergeResolve data) {
        super.callBack(kafkaTemplate.send(TopicConstant.PARSER_START_MERGE_RESOLVE, JsonUtil.obj2String(data)),
            TopicConstant.PARSER_START_MERGE_RESOLVE);
    }

    /**
     * task update topic
     *
     * @param data
     *            数据
     */
    public void scanTaskUpdate(ScanTaskUpdateSend data) {
        super.callBack(kafkaTemplate.send(TopicConstant.SCAN_TASK_UPDATE_TOPIC, JsonUtil.obj2String(data)),
            TopicConstant.SCAN_TASK_UPDATE_TOPIC);
    }

}

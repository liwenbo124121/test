package org.osc.scan.server.business.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.osc.common.util.JsonUtil;
import org.osc.scan.server.business.metadata.TopicConstant;
import org.osc.scan.server.business.pojo.kafka.ParserResolveComplete;
import org.osc.scan.server.business.pojo.kafka.ScanServerSend;
import org.osc.scan.server.business.service.KafkaConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;

import lombok.extern.slf4j.Slf4j;

/**
 * @author : zbz
 * @date : 2021/4/18
 */
@Slf4j
@Component
public class KafkaConsumer {

    @Autowired
    private KafkaConsumerService kafkaConsumerService;

    /**
     * parser解析完成回调
     *
     * @param message
     *            消息内容
     */
    @KafkaListener(groupId = TopicConstant.PARSER_RESOLVE_COMPLETE, topics = TopicConstant.PARSER_RESOLVE_COMPLETE)
    public void parserResolveComplete(ConsumerRecord<String, String> message) {
        log.info("parserResolveComplete received message: {}", message.value());
        kafkaConsumerService
            .parserResolveComplete(JsonUtil.string2Obj(message.value(), new TypeReference<ParserResolveComplete>() {}));
    }

    /**
     * 触发server扫描
     *
     * @param message
     *            消息内容
     */
    @KafkaListener(groupId = TopicConstant.SCAN_SERVER_SEND_TOPIC,topics = TopicConstant.SCAN_SERVER_SEND_TOPIC)
    public void scanServerSendTopic(ConsumerRecord<String, String> message) {
        log.info("portalToServer received message: {}", message.value());
        kafkaConsumerService.scanServerSendTopic(JsonUtil.string2Obj(message.value(), new TypeReference<ScanServerSend>() {}));
    }

}

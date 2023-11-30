package org.osc.scan.server.business.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.osc.scan.server.business.metadata.TopicConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : zbz
 * @date : 2021/4/16
 */
@Configuration
public class KafkaTopicConfig {

    /**
     * 建立通知parser开始单个扫描和合并扫描的topic
     */
    @Bean
    public NewTopic parserScan() {
        return new NewTopic(TopicConstant.PARSER_START_MERGE_RESOLVE, 1, (short)1);
    }

    /**
     * 通知portal扫描结果的topic
     */
    @Bean
    public NewTopic scanTaskCallbackTopic() {
        return new NewTopic(TopicConstant.SCAN_TASK_CALLBACK_TOPIC, 1, (short)1);
    }
}

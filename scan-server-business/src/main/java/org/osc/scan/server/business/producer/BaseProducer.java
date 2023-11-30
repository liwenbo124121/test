package org.osc.scan.server.business.producer;

import org.osc.common.util.JsonUtil;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.kafka.core.KafkaSendCallback;
import org.springframework.kafka.support.SendResult;
import org.springframework.lang.NonNull;
import org.springframework.util.concurrent.ListenableFuture;

import lombok.extern.slf4j.Slf4j;

/**
 * @author : xyih
 * @date : 2021/4/17
 */
@Slf4j
public class BaseProducer<K, V> {

    /**
     * 发送消息结果的处理
     */
    protected void callBack(ListenableFuture<SendResult<K, V>> future, String topicName) {
        // 发送消息
        future.addCallback(new KafkaSendCallback<K, V>() {
            @Override
            public void onFailure(@NonNull KafkaProducerException kafkaProducerException) {
                // 发送失败的处理
                log.error("topic={} 消息发送失败:{}", topicName, kafkaProducerException.getMessage(), kafkaProducerException);
            }

            @Override
            public void onSuccess(SendResult<K, V> result) {
                // 成功的处理
                log.info("topic={} 消息发送成功:{}", topicName, JsonUtil.obj2String(result));
            }
        });
    }
}

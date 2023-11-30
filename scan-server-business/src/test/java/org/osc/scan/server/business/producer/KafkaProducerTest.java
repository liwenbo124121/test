package org.osc.scan.server.business.producer;

import lombok.Data;
import org.junit.jupiter.api.Test;
import org.osc.common.util.JsonUtil;
import org.osc.scan.server.business.pojo.kafka.PortalReceiveScanResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : zbz
 * @date : 2021/4/30
 */
@SpringBootTest
public class KafkaProducerTest {

    @Autowired
    private KafkaProducer kafkaProducer;

    @Test
    public void scanTaskCallbackTopicTest() {
        PortalReceiveScanResult data = new PortalReceiveScanResult();
        data.setScanType(1);
        data.setStatus(false);
        data.setJobId(1);
        kafkaProducer.scanTaskCallbackTopic(data);
    }

}

package org.osc.scan.server.business.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;
import org.osc.common.util.JsonUtil;
import org.osc.scan.server.business.dao.LangToolDao;
import org.osc.scan.server.business.dao.TaskDao;
import org.osc.scan.server.business.pojo.kafka.ParserResolveComplete;
import org.osc.scan.server.business.pojo.kafka.ScanServerSend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author : zbz
 * @date : 2021/4/27
 */
@SpringBootTest
public class KafkaConsumerServiceImplTest {

    @Autowired
    private KafkaConsumerServiceImpl kafkaConsumerService;

    @Autowired
    LangToolDao langToolDao;

    @Autowired
    TaskDao taskDao;

    @Test
    public void scanServerSendTopicTest() {
        ScanServerSend scanServerSend = new ScanServerSend();
        scanServerSend.setCodePath("D://");
        scanServerSend.setSubTaskId(1);
        kafkaConsumerService.scanServerSendTopic(scanServerSend);
    }

    @Test
    public void testLangTool() {
        String json =
            "{\"status\": 3, \"err_desc\": \"\", \"type\": 1, \"endtime\": \"2021-05-11 16:06:57\", \"task_id\": 1131}";
        ParserResolveComplete parserResolveComplete =
            JsonUtil.string2Obj(json, new TypeReference<ParserResolveComplete>() {});
        kafkaConsumerService.parserResolveComplete(parserResolveComplete);
    }

}

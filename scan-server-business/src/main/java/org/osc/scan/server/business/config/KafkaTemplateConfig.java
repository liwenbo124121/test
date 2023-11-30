package org.osc.scan.server.business.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

/**
 * @author : zbz
 * @date : 2021/4/18
 */
@Configuration
public class KafkaTemplateConfig {
    @Bean
    public KafkaTemplate<String, String> stringTemplate(ProducerFactory<String, String> pf) {
        return new KafkaTemplate<>(pf);
    }
}

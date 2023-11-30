package org.osc.scan.server.business.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * @author : zbz
 * @date : 2021/4/19
 */
@Data
@ConfigurationProperties(prefix = "jenkins")
public class JenkinsConfigProperties {
    private String url;

    private String account;

    private String assignedNode;

    private String password;

    private String command;

    private String script;
}

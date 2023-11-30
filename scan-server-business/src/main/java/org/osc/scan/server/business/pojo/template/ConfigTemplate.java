package org.osc.scan.server.business.pojo.template;

import lombok.Data;

/**
 * @author : zbz
 * @date : 2021/4/19
 */
@Data
public class ConfigTemplate {

    private String command;

    private String script;

    private String assignedNode;

    private String module;
}

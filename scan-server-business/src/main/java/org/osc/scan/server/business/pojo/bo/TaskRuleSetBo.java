package org.osc.scan.server.business.pojo.bo;

import java.io.Serializable;

import lombok.Data;

/**
 * @author : xyih
 * @date : 2021/4/17
 */
@Data
public class TaskRuleSetBo implements Serializable {

    private static final long serialVersionUID = -1306434692323705975L;
    private Integer taskId;
    private String ruleIds;
    private String ruleNames;

    public TaskRuleSetBo(Integer taskId, String ruleIds, String ruleNames) {
        this.taskId = taskId;
        this.ruleIds = ruleIds;
        this.ruleNames = ruleNames;
    }
}

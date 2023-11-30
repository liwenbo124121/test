package org.osc.scan.server.business.pojo.kafka;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * @author : xyih
 * @date : 2021/4/27
 */
@Data
public class ParserStartMergeResolve implements Serializable {

    @JsonProperty("task_id")
    private Integer taskId;

    public ParserStartMergeResolve(Integer taskId) {
        this.taskId = taskId;
    }
}

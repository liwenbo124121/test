package org.osc.scan.server.business.pojo.kafka;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Parse服务[单个任务/合并任务]解析完成发送消息
 * 
 * @author : xyih
 * @date : 2021/4/19
 */
@Data
public class ParserResolveComplete implements Serializable {

    private static final long serialVersionUID = 3874296094570841831L;
    /**
     * call type: 0=单个任务解析回调 1=合并任务解析回调
     */
    private Integer type;

    /**
     * subtask: sub_task_id
     */
    @JsonProperty("sub_task_id")
    private Integer subTaskId;

    /**
     * subtask: status[任务状态：等待0，执行中1，结束（成功3，失败4）]
     */
    private Integer status;

    /**
     * subtask: err_desc[描述：失败之后给出错误环节描述信息]
     */
    @JsonProperty("err_desc")
    private String errDesc;

    /**
     * subtask: err_detail[描述：失败详细信息]
     */
    @JsonProperty("err_detail")
    private String errDetail;

    @JsonProperty("task_id")
    private Integer taskId;
}

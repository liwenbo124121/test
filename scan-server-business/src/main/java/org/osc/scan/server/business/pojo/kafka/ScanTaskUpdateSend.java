package org.osc.scan.server.business.pojo.kafka;

import java.io.Serializable;

import lombok.Data;

/**
 * @author : xyih
 * @date : 2021/4/30
 */
@Data
public class ScanTaskUpdateSend implements Serializable {

    private static final long serialVersionUID = -2330101822129364842L;
    /**
     * task_id
     */
    private Integer taskId;
    /**
     * 扫描类型 1 2=缺陷、安全 3
     */
    private Integer scanType;
    /**
     * 状态：1=扫描中
     */
    private Integer status;

}

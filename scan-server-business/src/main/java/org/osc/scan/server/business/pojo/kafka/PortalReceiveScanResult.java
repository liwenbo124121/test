package org.osc.scan.server.business.pojo.kafka;

import lombok.Data;

/**
 * @author : zbz
 * @date : 2021/4/20
 */
@Data
public class PortalReceiveScanResult {

    /**
     * 扫描维度id（1规范,2缺陷,3可维护性）
     */
    private Integer scanType;

    /**
     * 任务id（缺陷对应是subtaskId，其他对应taskId）
     */
    private Integer jobId;

    /**
     * 扫描结果（true 成功，false 失败）
     */
    private Boolean status;

    /**
     * 失败描述
     */
    private String errDesc;

    /**
     * 失败具体原因
     */
    private String errDetail;
}

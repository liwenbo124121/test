package org.osc.scan.server.business.pojo.bo;

import lombok.Data;

/**
 * @author : zbz
 * @date : 2021/4/17
 */
@Data
public class StartScannerParamsBo {
    private String mCreator;
    private String mCiServer;
    private String mAssignedNode;
    /**
     * jenkins job的名称（使用module的id_name）
     */
    private String jobName;
    private Integer subTaskId;
    private Integer scanTypeId;
    private JenkinsJobBuildParamsBo jenkinsJobBuildParamsBo;
}

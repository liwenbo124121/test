package org.osc.scan.server.business.pojo.bo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * @author : xyih
 * @date : 2021/4/16
 */
@Data
public class JenkinsJobBuildParamsBo implements Serializable {

    private static final long serialVersionUID = 4504555697519728394L;

    @JsonProperty("SCconfig")
    private String scConfig;
    @JsonProperty("sub_task_id")
    private String subTaskId;
    @JsonProperty("code_type")
    private String codeType;
    @JsonProperty("st_check")
    private String stCheck;
    @JsonProperty("mi_check")
    private String miCheck;
    @JsonProperty("code_pwd")
    private String codePwd;
    @JsonProperty("start_type")
    private String startType;
    @JsonProperty("code_user")
    private String codeUser;
    @JsonProperty("target_branch")
    private String targetBranch;
    @JsonProperty("commit_id_ref")
    private String commitIdRef;
    @JsonProperty("code_path")
    private String codePath;
    @JsonProperty("commit_id")
    private String commitId;
    @JsonProperty("product_path")
    private String productPath;
    @JsonProperty("branch_name")
    private String branchName;
    @JsonProperty("scan_path")
    private String scanPath;
    @JsonProperty("filter_path")
    private String filterPath;
    @JsonProperty("bug_priority")
    private String bugPriority;
    @JsonProperty("tool_type")
    private String toolType;
    @JsonProperty("base_commit_id")
    private String baseCommitId;
    @JsonProperty("task_id")
    private String taskId;
    @JsonProperty("third_party_name")
    private String thirdPartyName;
    @JsonProperty("job_pattern")
    private String jobPattern;
    @JsonProperty("cpd_check")
    private String cpdCheck;
    @JsonProperty("target_type")
    private String targetType;
    @JsonProperty("patch")
    private String patch;
    @JsonProperty("compile_cmd")
    private String compileCmd;
    @JsonProperty("projectKey")
    private String projectKey;
    private String moduleId;
    @JsonProperty("start_way")
    private String startWay;
    @JsonProperty("scanFileUri")
    private String scanFileUri;
}

package org.osc.scan.server.business.pojo.vo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author : xyih
 * @date : 2021/5/7
 */
@Data
public class UpdateParentTaskVo implements Serializable {

    private static final long serialVersionUID = 5569226614890507941L;
    /**
     * 扫描任务id:定时任务和即时ci任务共用这一张表
     */
    @JsonProperty("task_id")
    private Integer taskId;

    /**
     * 任务名称
     */
    @JsonProperty("task_name")
    private String taskName;

    /**
     * 对应模块id，手工任务为该任务的语言id
     */
    @JsonProperty("module_id")
    private Integer moduleId;

    /**
     * 启动方式：0平台启动，1 ci触发 2 api接入 3手工提交n4 本地提交
     */
    @JsonProperty("start_way")
    private Integer startWay;

    /**
     * 任务启动者id：agent接入需指定用户组以方便其他人查看结果，其他方式接入需指定普通用户账号
     */
    @JsonProperty("task_owner")
    private Integer taskOwner;

    /**
     * 描述：失败之后给出错误环节描述信息
     */
    @JsonProperty("err_desc")
    private String errDesc;

    /**
     * 指定的扫描路径，多路径逗号间隔
     */
    @JsonProperty("scan_path")
    private String scanPath;

    /**
     * scan_path的md5
     */
    @JsonProperty("scan_path_md5")
    private String scanPathMd5;

    /**
     * 需要过滤的文件，以，分割
     */
    @JsonProperty("filter_path")
    private String filterPath;

    /**
     * filter_path_md5
     */
    @JsonProperty("filter_path_md5")
    private String filterPathMd5;

    /**
     * 增量，全量
     */
    @JsonProperty("type")
    private String type;

    /**
     * 是否基线
     */
    @JsonProperty("is_baseline")
    private String isBaseline;

    /**
     * 1、2、3分别对应高中低扫描级别
     */
    @JsonProperty("bug_priority")
    private Integer bugPriority;

    /**
     * 0表示不需要推送扫描结果，1表示需要推送
     */
    @JsonProperty("reply")
    private Integer reply;

    /**
     * 分支名称
     */
    @JsonProperty("branch_name")
    private String branchName;

    /**
     * svn或者git的提交版本号
     */
    @JsonProperty("commit_id")
    private String commitId;

    /**
     * base_commit_id
     */
    @JsonProperty("base_commit_id")
    private String baseCommitId;

    /**
     * git临时评审版本号
     */
    @JsonProperty("commit_id_ref")
    private String commitIdRef;

    /**
     * git版本号的提交时间
     */
    @JsonProperty("commit_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date commitTime;

    /**
     * 第三方任务id
     */
    @JsonProperty("third_party_id")
    private Integer thirdPartyId;

    /**
     * 代码最近提交人
     */
    @JsonProperty("committer")
    private String committer;

    /**
     * 定时任务 预约启动的时间
     */
    @JsonProperty("ordertime")
    private String ordertime;

    /**
     * 任务创建时间
     */
    @JsonProperty("createtime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createtime;

    /**
     * 任务开始时间，任务可能排队一定时间后才开始
     */
    @JsonProperty("starttime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date starttime;

    /**
     * 任务执行结束时间，执行成功或执行失败更新描述信息时写入时间
     */
    @JsonProperty("endtime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endtime;

    /**
     * 等待0，执行中1，结束（没有基准版本，代码下载失败2，成功3，失败4）
     */
    @JsonProperty("computeHash_status")
    private Integer computehashStatus;

    /**
     * 指纹特征计算状态描述
     */
    @JsonProperty("computeHash_msg")
    private String computehashMsg;

    /**
     * 门禁-标签
     */
    @JsonProperty("access_id")
    private Integer accessId;

    /**
     * 0-1-code2-pipe
     */
    @JsonProperty("third_party_type")
    private Integer thirdPartyType;

    /**
     * 规则集id
     */
    @JsonProperty("rule_set_ids")
    private String ruleSetIds;

    /**
     * 规则集名称
     */
    @JsonProperty("rule_set_names")
    private String ruleSetNames;

    /**
     * 公司名称
     */
    @JsonProperty("company")
    private String company;

    /**
     * 项目名称
     */
    @JsonProperty("project")
    private String project;

    /**
     * parser服务地址
     */
    @JsonProperty("parser_name")
    private String parserName;

    /**
     * 启动时间：0非定时 1定时任务 预定的时间点即create_time
     */
    @JsonProperty("is_cronjob")
    private Integer isCronjob;
}

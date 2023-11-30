package org.osc.scan.server.business.pojo.bo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @author : zbz
 * @date : 2021/4/16
 */
@Data
public class UpdateTaskBo implements Serializable {
    private static final long serialVersionUID = -102421164108515169L;
    /**
     * 扫描任务id:定时任务和即时ci任务共用这一张表
     */
    private Integer taskId;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 对应模块id，手工任务为该任务的语言id
     */
    private Integer moduleId;

    /**
     * 启动方式：0平台启动，1 ci触发 2 api接入 3手工提交n4 本地提交
     */
    private Integer startWay;

    /**
     * 任务启动者id：agent接入需指定用户组以方便其他人查看结果，其他方式接入需指定普通用户账号
     */
    private Integer taskOwner;

    /**
     * 任务状态：等待(0排队中，任务在队列中， 5构建中，表示从队列中取出到job构建成功)
     */
    private Integer status;

    /**
     * 描述：失败之后给出错误环节描述信息
     */
    private String errDesc;

    /**
     * 指定的扫描路径，多路径逗号间隔
     */
    private String scanPath;

    /**
     * scan_path的md5
     */
    private String scanPathMd5;

    /**
     * 需要过滤的文件，以，分割
     */
    private String filterPath;

    /**
     * filter_path_md5
     */
    private String filterPathMd5;

    /**
     * 增量，全量
     */
    private String type;

    /**
     * 是否基线
     */
    private String isBaseline;

    /**
     * 1、2、3分别对应高中低扫描级别
     */
    private Integer bugPriority;

    /**
     * 0表示不需要推送扫描结果，1表示需要推送
     */
    private Integer reply;

    /**
     * 分支名称
     */
    private String branchName;

    /**
     * svn或者git的提交版本号
     */
    private String commitId;

    /**
     * base_commit_id
     */
    private String baseCommitId;

    /**
     * git临时评审版本号
     */
    private String commitIdRef;

    /**
     * git版本号的提交时间
     */
    private Date commitTime;

    /**
     * 第三方任务id
     */
    private Integer thirdPartyId;

    /**
     * 代码最近提交人
     */
    private String committer;

    /**
     * 定时任务 预约启动的时间
     */
    private String ordertime;

    /**
     * 任务创建时间
     */
    private Date createtime;

    /**
     * 任务开始时间，任务可能排队一定时间后才开始
     */
    private Date starttime;

    /**
     * 任务执行结束时间，执行成功或执行失败更新描述信息时写入时间
     */
    private Date endtime;

    /**
     * 等待0，执行中1，结束（没有基准版本，代码下载失败2，成功3，失败4）
     */
    private Integer computehashStatus;

    /**
     * 指纹特征计算状态描述
     */
    private String computehashMsg;

    /**
     * 门禁-标签
     */
    private Integer accessId;

    /**
     * 0-1-code2-pipe
     */
    private Integer thirdPartyType;

    /**
     * 规则集id
     */
    private String ruleSetIds;

    /**
     * 规则集名称
     */
    private String ruleSetNames;

    /**
     * 公司名称
     */
    private String company;

    /**
     * 项目名称
     */
    private String project;

    /**
     * parser服务地址
     */
    private String parserName;

    /**
     * 启动时间：0非定时 1定时任务 预定的时间点即create_time
     */
    private Integer isCronjob;
}

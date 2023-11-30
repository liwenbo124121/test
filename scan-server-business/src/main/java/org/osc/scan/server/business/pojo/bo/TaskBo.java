package org.osc.scan.server.business.pojo.bo;

import java.io.Serializable;

import org.springframework.beans.BeanUtils;

import lombok.Data;

/**
 * TaskInfo 任务信息
 * 
 * @author : xyih
 * @date : 2021/4/16
 */
@Data
public class TaskBo implements Serializable {

    private static final long serialVersionUID = 6917296238802457418L;
    /**
     * 任务Id
     */
    private Integer taskId;
    /**
     * 1、2、3分别对应高中低扫描级别
     */
    private Integer bugPriority;
    /**
     * 启动时间：0非定时 1定时任务 预定的时间点即create_time
     */
    private Integer isCronjob;
    /**
     * 定时任务 预约启动的时间
     */
    private String ordertime;
    /**
     * 分支名称
     */
    private String branchName;
    /**
     * 指定的扫描路径，多路径逗号间隔
     */
    private String scanPath;
    /**
     * 需要过滤的文件，以,分割
     */
    private String filterPath;
    /**
     * 0表示不需要推送扫描结果，1表示需要推送
     */
    private Integer reply;
    /**
     * Svn或者git的提交版本号
     */
    private String commitId;
    /**
     * taskOwner
     */
    private Integer taskOwner;
    /**
     * 模块信息Id
     */
    private Integer moduleId;
    /**
     * 启动方式：0平台启动，1 ci触发 2 api接入 3手工提交n4 本地提交
     */
    private Integer startWay;
    private String commitIdRef;
    private String baseCommitId;
    private String type;
    private Integer thirdPartyId;

    public TaskBo(Object source) {
        if (null != source) {
            BeanUtils.copyProperties(source, this);
        }
    }
}
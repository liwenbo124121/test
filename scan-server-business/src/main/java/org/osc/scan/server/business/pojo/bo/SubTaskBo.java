package org.osc.scan.server.business.pojo.bo;

import java.io.Serializable;
import java.util.Date;

import org.springframework.beans.BeanUtils;

import lombok.Data;

/**
 * @author : xyih
 * @date : 2021/4/16
 */
@Data
public class SubTaskBo implements Serializable {

    private static final long serialVersionUID = -2060914560150699873L;
    /**
     * 扫描任务id:定时任务和即时ci任务共用这一张表
     */
    private Integer subTaskId;

    /**
     * 父任务task_id
     */
    private Integer taskId;

    /**
     * 任务状态：等待0，执行中1，结束（成功3，失败4）
     */
    private Integer status;

    /**
     * 描述：失败之后给出错误环节描述信息
     */
    private String errDesc;

    /**
     * 描述：失败详细信息
     */
    private String errDetail;

    /**
     * 文件数
     */
    private Integer fileCount;

    /**
     * 代码行数
     */
    private Integer codeCount;

    /**
     * 执行任务的toolid
     */
    private Integer toolId;

    /**
     * bug数目
     */
    private Integer bugCount;

    /**
     * 单语言bug数量汇总
     */
    private Integer langBugCount;

    /**
     * bug密度，数目/文件行数
     */
    private Double bugDensity;

    /**
     * 单语言bug密度，数目/文件行数
     */
    private Double langBugDensity;

    /**
     * 标记该次任务是否扫出增量bug或者高危bug
     */
    private Integer resultLevel;

    /**
     * 结果文件存放的位置
     */
    private String resultPath;

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
     * 任务所在的扫描节点
     */
    private String nodeName;

    /**
     * 该子任务是否有cpd检测 0-无 1-有
     */
    private Integer cpdChecked;

    /**
     * 该子任务是否有代码度量检测 0-无 1-有
     */
    private Integer stChecked;

    /**
     * 该子任务是否有mi扫描 0-无 1-有
     */
    private Integer miChecked;

    /**
     * jenkins构建编号
     */
    private Integer buildNumber;

    /**
     * 多语言扫描时关联module_id
     */
    private Integer subModuleId;

    /**
     * 构建该job的节点
     */
    private String buildName;

    /**
     * 语言id
     */
    private Long langId;

    /**
     * 扫描维度id
     */
    private Integer scanTypeId;

    /**
     * task info
     */
    private TaskBo taskBo;

    public SubTaskBo() {}

    public SubTaskBo(Object source) {
        if (null != source) {
            BeanUtils.copyProperties(source, this);
        }
    }

    public SubTaskBo(Integer taskId, Integer subTaskId, Integer status, String errDesc, String errDetail,
        Integer scanTypeId) {
        this.taskId = taskId;
        this.subTaskId = subTaskId;
        this.status = status;
        this.errDesc = errDesc;
        this.errDetail = errDetail;
        this.scanTypeId = scanTypeId;
    }
}
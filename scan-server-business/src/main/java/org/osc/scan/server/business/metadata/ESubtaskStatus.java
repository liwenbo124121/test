package org.osc.scan.server.business.metadata;

/**
 * TaskStatusEnum 任务状态
 * 
 * @author : xyih
 * @date : 2021/4/16
 */
public enum ESubtaskStatus {

    /**
     * 未处理
     */
    UN_DEAL(0, "未处理"),
    /**
     * 执行中
     */
    RUN(1, "执行中"),
    /**
     * 成功
     */
    SUCCESS(3, "成功"),
    /**
     * 失败
     */
    FAIL(4, "失败");

    private final Integer code;

    private final String text;

    ESubtaskStatus(Integer code, String text) {
        this.code = code;
        this.text = text;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getText() {
        return this.text;
    }

}
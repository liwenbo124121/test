package org.osc.scan.server.business.metadata;

/**
 * Parser解析结果
 * 
 * @author : xyih
 * @date : 2021/4/16
 */
public enum ParserResultStatus {

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

    ParserResultStatus(Integer code, String text) {
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
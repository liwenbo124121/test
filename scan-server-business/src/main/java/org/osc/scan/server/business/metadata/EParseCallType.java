package org.osc.scan.server.business.metadata;

import org.apache.commons.lang.StringUtils;

/**
 * @author : xyih
 * @date : 2021/4/16
 */
public enum EParseCallType {

    SUB_PARSE(0, "子任务解析回调"), MERGE_PARSE(1, "合并任务解析回调");

    private Integer code;
    private String text;

    EParseCallType(Integer code, String text) {
        this.code = code;
        this.text = text;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getText() {
        return this.text;
    }

    /**
     * 根据枚举Code获取Text
     *
     * @param code
     * @return
     */
    public static String getTextByCode(Integer code) {
        if (null == code) {
            return StringUtils.EMPTY;
        }

        ESubtaskStatus[] values = ESubtaskStatus.values();

        int index = values.length - 1;
        do {
            if (values[index].getCode().compareTo(code) == 0) {
                return values[index].getText();
            }
        } while (--index >= 0);

        // 未匹配到值
        return StringUtils.EMPTY;
    }

    /**
     * compare
     * 
     * @param code
     * @return
     */
    public boolean compareWithCode(Integer code) {
        if (null == code) {
            return false;
        }
        return this.code.compareTo(code) == 0;
    }

}

package org.osc.scan.server.business.pojo.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * @author : xyih
 * @date : 2021/4/17
 */
@Data
public class ToolDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 工具id
     */
    private Integer toolId;

    /**
     * 工具名称
     */
    private String toolName;

    /**
     * need_cmd
     */
    private Integer needCmd;

    /**
     * tool_category
     */
    private Integer toolCategory;

}

package org.osc.scan.server.business.pojo.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @author : xyih
 * @date : 2021/4/17
 */
@Data
public class RuleSetDto implements Serializable {

    private static final long serialVersionUID = -2811938311695603059L;
    /**
     * 规则集id
     */
    private Integer rsId;

    /**
     * 规则集名称
     */
    private String rsName;

    /**
     * 规则集说明
     */
    private String rsInfo;

    /**
     * 规则集创建者id
     */
    private Integer rsOwner;

    /**
     * 规则集关联的工具id
     */
    private Integer rsTool;

    /**
     * 语言id
     */
    private Integer langId;

    /**
     * 规则集创建时间
     */
    private Date rsCreateTime;

    /**
     * project
     */
    private String project;

    /**
     * company
     */
    private String company;

}

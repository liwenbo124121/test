package org.osc.scan.server.business.pojo.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @author : zbz
 * @date : 2021/4/16
 */
@Data
public class ModuleDto implements Serializable {

    private static final long serialVersionUID = -2417413062472429344L;
    /**
     * 接入模块id
     */
    private Integer moduleId;

    /**
     * 模块名称
     */
    private String moduleName;

    /**
     * 公司名称
     */
    private String company;

    /**
     * 项目名称
     */
    private String project;

    /**
     * 创建者的用户id
     */
    private Integer creator;

    /**
     * 仓库类别（git、svn）
     */
    private Integer getcodeType;

    /**
     * 代码管理路径
     */
    private String codePath;

    /**
     * 获取代码账号
     */
    private String codeUser;

    /**
     * 获取代码账号密码
     */
    private String codePwd;

    /**
     * 模块过滤路径，以，分割
     */
    private String filterPath;

    /**
     * 模块扫描路径，以，分割
     */
    private String scanPath;

    /**
     * 指定私有节点的name
     */
    private String agentName;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 上次修改时间
     */
    private Date editTime;

    /**
     * 标记平台注册模块第三方来源cov， eagle， titans， agile分别对应1234，平台注册为0，plugin为5，github导入为6
     */
    private Integer moduleTag;

    /**
     * 模块是否可见，1-可见，0-不可见
     */
    private Integer visible;

    /**
     * 对应jenkins项目名称
     */
    private String jobName;

    /**
     * 代码工程编译命令
     */
    private String compileCmd;

    /**
     * jenkins地址
     */
    private String ciServer;
}

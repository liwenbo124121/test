package org.osc.scan.server.business.pojo.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * @author : xyih
 * @date : 2021/4/17
 */
@Data
public class UserDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * account
     */
    private String account;

    /**
     * 0平台普通用户，1平台管理员
     */
    private Integer role;

    /**
     * 0表示未关注服务号，1表示已关注
     */
    private Integer follow;

    /**
     * email_follow
     */
    private Integer emailFollow;

    /**
     * icafe接口虚拟密码
     */
    private String virtualPwd;

    /**
     * 新功能引引导标识
     */
    private Integer newFunc;
}
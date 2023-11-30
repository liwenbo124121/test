package org.osc.scan.server.business.pojo.bo;

import java.io.Serializable;

import org.springframework.beans.BeanUtils;

import lombok.Data;

/**
 * @author : xyih
 * @date : 2021/4/16
 */
@Data
public class ModuleBo implements Serializable {

    private static final long serialVersionUID = -8034250330585500875L;
    private String codePath;
    private String codePwd;
    private String codeUser;
    private String moduleName;
    private String getCodeType;
    private String compileCmd;
    private String jobName;
    private String creator;
    private String ciServer;
    private String agentName;
    private String lang;
    private Integer moduleId;

    public ModuleBo(Object source) {
        if (null != source) {
            BeanUtils.copyProperties(source, this);
        }
    }

}

package org.osc.scan.server.business.pojo.bo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @author : zbz
 * @date : 2021/4/19
 */
@Data
public class ScannerUpdateSubTaskParamsBo implements Serializable {

    private static final long serialVersionUID = 405491767829624188L;
    private Integer subTaskId;
    private String errDesc;
    private String errDetail;
    private Integer fileCount;
    private Integer codeCount;
    private String nodeName;
    private Date startTime;
    private Integer status;
}

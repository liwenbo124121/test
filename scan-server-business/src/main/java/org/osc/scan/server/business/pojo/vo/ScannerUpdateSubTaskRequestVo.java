package org.osc.scan.server.business.pojo.vo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 
 * { "status": 1, "err_desc": "", "err_detail": "", "file_count": 1, "code_count": 11, "starttime": "2021-05-11
 * 12:00:00", "node_name": "123", }
 * 
 * @author : zbz
 * @date : 2021/4/19
 */
@Data
public class ScannerUpdateSubTaskRequestVo implements Serializable {

    private static final long serialVersionUID = -8826325067616079568L;
    @JsonProperty("sub_task_id")
    private Integer subTaskId;
    @JsonProperty("err_desc")
    private String errDesc;
    @JsonProperty("err_detail")
    private String errDetail;
    @JsonProperty("file_count")
    private Integer fileCount;
    @JsonProperty("code_count")
    private Integer codeCount;
    @JsonProperty("node_name")
    private String nodeName;
    @JsonProperty("starttime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;
    @JsonProperty("status")
    private Integer status;
}

package org.osc.scan.server.business.pojo.kafka;

import java.io.Serializable;

import lombok.Data;

/**
 * @author : xyih
 * @date : 2021/4/29
 */
@Data
public class ScanServerSend implements Serializable {
    private static final long serialVersionUID = -9192804816237395851L;

    private Integer subTaskId;
    @Deprecated
    private String codePath;
    private String scanFileUri;
}

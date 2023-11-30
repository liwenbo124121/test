package org.osc.scan.server.business.util;

import java.io.Serializable;

import lombok.Data;

/**
 * @author : zbz
 * @date : 2021/4/19
 */
@Data
public class ResponseInfoUtil implements Serializable {
    private static final String SUCCESS = "OK";
    private static final String FAILED = "FAILED";

    public static <T> ResponseInfo<T> success(T data) {
        ResponseInfo<T> responseInfo = new ResponseInfo<>();
        responseInfo.setStatus(SUCCESS);
        responseInfo.setMessage("操作成功");
        responseInfo.setData(data);
        return responseInfo;
    }

    public static <T> ResponseInfo<T> fail(String msg) {
        ResponseInfo<T> responseInfo = new ResponseInfo<>();
        responseInfo.setStatus(FAILED);
        responseInfo.setMessage(msg);
        return responseInfo;
    }

    @Data
    public static class ResponseInfo<T> {
        private String status;
        private String message = "";
        private T data;
    }

}

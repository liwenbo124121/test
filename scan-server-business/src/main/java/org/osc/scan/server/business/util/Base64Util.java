package org.osc.scan.server.business.util;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

import lombok.extern.slf4j.Slf4j;

/**
 * base64
 * 
 * @author : xyih
 * @date : 2021/5/7
 */
@Slf4j
public class Base64Util {

    private static final String CHARSET = "utf-8";

    /**
     * 解密
     * 
     * @param data
     * @return
     */
    public static String decode(String data) {
        try {
            if (null == data) {
                return null;
            }
            return new String(Base64.getDecoder().decode(data.getBytes(CHARSET)), CHARSET);
        } catch (UnsupportedEncodingException e) {
            log.error(String.format("字符串：%s，解密异常", data), e);
        }

        return null;
    }

    /**
     * 加密
     * 
     * @param data
     * @return
     */
    public static String encode(String data) {
        try {
            if (null == data) {
                return null;
            }
            return new String(Base64.getEncoder().encode(data.getBytes(CHARSET)), CHARSET);
        } catch (UnsupportedEncodingException e) {
            log.error(String.format("字符串：%s，加密异常", data), e);
        }
        return null;
    }
}

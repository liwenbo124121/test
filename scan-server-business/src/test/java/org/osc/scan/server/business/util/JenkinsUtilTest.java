package org.osc.scan.server.business.util;

import org.junit.jupiter.api.Test;

import com.offbytwo.jenkins.JenkinsServer;

/**
 * @author : zbz
 * @date : 2021/4/17
 */
public class JenkinsUtilTest {
    /**
     * 账号
     */
    private final static String JENKINS_DEFAULT_ACCOUNT = "http://192.168.80.83:8801/";

    /**
     * 服务器
     */
    private final static String JENKINS_DEFAULT_URL = "";

    /**
     * 节点
     */
    private final static String JENKINS_DEFAULT_NODE = "";

    @Test
    public void getConnectionTest() {
        JenkinsServer connection =
            JenkinsUtil.getConnection(JENKINS_DEFAULT_ACCOUNT, JENKINS_DEFAULT_URL, JENKINS_DEFAULT_NODE);
        assert connection != null;
    }
}

package org.osc.scan.server.business.util;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.client.JenkinsHttpClient;
import com.offbytwo.jenkins.model.JobWithDetails;

import lombok.extern.slf4j.Slf4j;

/**
 * jenkins工具类
 * 
 * @author : zbz
 * @date : 2021/4/16
 */
@Slf4j
public class JenkinsUtil {

    public static JenkinsServer getConnection(String url, String account, String password) {
        try {
            JenkinsHttpClient client = new JenkinsHttpClient(new URI(url), account, password);
            JenkinsServer jenkinsServer = new JenkinsServer(client);
            if (checkConnection(jenkinsServer)) {
                return jenkinsServer;
            }
        } catch (URISyntaxException e) {
            log.error("get jenkins connection fail", e);
        }
        return null;
    }

    public static boolean checkConnection(JenkinsServer jenkinsServer) {
        try {
            jenkinsServer.getJobs();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static JobWithDetails createJob(JenkinsServer jenkinsServer, String jobName, String jobXml) {
        if (jenkinsServer == null || StringUtils.isBlank(jobName)) {
            return null;
        }
        JobWithDetails jobWithDetails;
        try {
            jenkinsServer.createJob(jobName, jobXml);
            jobWithDetails = jenkinsServer.getJob(jobName);
            return jobWithDetails;
        } catch (IOException e) {
            log.error(" jenkins create job fail", e);
        }
        return null;
    }

    public static boolean delJob(JenkinsServer jenkinsServer, String jobName) {
        if (jenkinsServer == null || StringUtils.isBlank(jobName)) {
            return false;
        }
        try {
            jenkinsServer.deleteJob(jobName);
            return true;
        } catch (IOException e) {
            log.error(" jenkins del job fail", e);
        }
        return false;
    }

    public static JobWithDetails getJobByJobName(JenkinsServer jenkinsServer, String jobName) {
        if (jenkinsServer == null || StringUtils.isBlank(jobName)) {
            return null;
        }
        try {
            return jenkinsServer.getJob(jobName);
        } catch (IOException e) {
            log.error(" jenkins del job fail", e);
        }
        return null;
    }

    public static boolean buildJobWithParams(JobWithDetails jobWithDetails, Map<String, String> params) {
        if (jobWithDetails == null) {
            return false;
        }
        try {
            new MyJenkinsJob(jobWithDetails).build(params);
            return true;
        } catch (IOException e) {
            log.error(" jenkins job build fail", e);
        }
        return false;
    }
}

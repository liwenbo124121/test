package org.osc.scan.server.business.controller;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.JobWithDetails;
import freemarker.template.TemplateException;
import org.osc.scan.server.business.config.JenkinsConfigProperties;
import org.osc.scan.server.business.pojo.template.ConfigTemplate;
import org.osc.scan.server.business.util.FreemarkerUtil;
import org.osc.scan.server.business.util.JenkinsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author: jiaoyaju
 * @date: 2021/10/9 14:13
 * @description:
 */
@RequestMapping
@RestController
public class JenkinsController {

    @Autowired
    private JenkinsConfigProperties jenkinsConfigProperties;

    @RequestMapping("jenkinsPing")
    public ResponseEntity jenkinsPing() {
        JenkinsServer jenkinsServer = JenkinsUtil.getConnection(jenkinsConfigProperties.getUrl(),
                jenkinsConfigProperties.getAccount(), jenkinsConfigProperties.getPassword());
        assert jenkinsServer != null;
        if (jenkinsServer.isRunning()) {
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping("jenkinsCreate/{jobName}")
    public ResponseEntity createJob(@PathVariable String jobName) {
        // step1: 获取jenkins连接
        JenkinsServer jenkinsServer = JenkinsUtil.getConnection(jenkinsConfigProperties.getUrl(),
                jenkinsConfigProperties.getAccount(), jenkinsConfigProperties.getPassword());
        // step2: 检查对应job是否存在
        try {
            JobWithDetails jobWithDetails = JenkinsUtil.getJobByJobName(jenkinsServer, jobName);
            if (jobWithDetails == null) {
                jobWithDetails = JenkinsUtil.createJob(jenkinsServer, jobName, buildJobXml());
            }
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    private String buildJobXml() {
        String jobXml;
        try {
            ConfigTemplate configTemplate = new ConfigTemplate();
            configTemplate.setCommand(jenkinsConfigProperties.getCommand());
            configTemplate.setScript(jenkinsConfigProperties.getScript());
            configTemplate.setAssignedNode(jenkinsConfigProperties.getAssignedNode());
            jobXml = FreemarkerUtil.toStr("config.ftl", configTemplate);
        } catch (IOException | TemplateException e) {
            throw new RuntimeException("buildJobXml error");
        }
        return jobXml;
    }
}

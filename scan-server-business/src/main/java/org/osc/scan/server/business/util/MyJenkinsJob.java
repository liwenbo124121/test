package org.osc.scan.server.business.util;

import com.offbytwo.jenkins.model.Job;
import lombok.Data;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class MyJenkinsJob {
    private String name;
    private String url;
    private String fullName;

    public MyJenkinsJob(Job job) {
        this.name = job.getName();
        this.url = job.getUrl();
        this.fullName = job.getFullName();
    }

    public String build(Map<String, String> params) throws IOException {
        String url = this.url + "buildWithParameters";
        try {
            CloseableHttpClient httpclient = null;
            CloseableHttpResponse httpresponse = null;
            try {
                httpclient = HttpClients.createDefault();
                HttpPost httppost = new HttpPost(url);
                List<BasicNameValuePair> paramList = params.entrySet().stream()
                        .map(item -> new BasicNameValuePair(item.getKey(), String.valueOf(item.getValue())))
                        .collect(Collectors.toList());
                httppost.setEntity(new UrlEncodedFormEntity(paramList, StandardCharsets.UTF_8));
                httpresponse = httpclient.execute(httppost);
                String response = EntityUtils.toString(httpresponse.getEntity(), StandardCharsets.UTF_8);
                return response;
            } finally {
                if (httpclient != null) {
                    httpclient.close();
                }
                if (httpresponse != null) {
                    httpresponse.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

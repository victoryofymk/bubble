package com.it.ymk.image;

import java.text.DecimalFormat;
import java.util.List;

import org.sonar.wsclient.Host;
import org.sonar.wsclient.Sonar;
import org.sonar.wsclient.connectors.HttpClient4Connector;
import org.sonar.wsclient.services.Measure;
import org.sonar.wsclient.services.Resource;
import org.sonar.wsclient.services.ResourceQuery;

/**
 * Created by yanmingkun on 2017/2/17.
 */
public class SonarDemo {
    static String   host            = "http://127.0.0.1:9000";
    static String   username        = "admin";
    static String   password        = "admin";
    static String   resourceKey     = "org.codehaus.sonar:sonar-ws-client";
    static String[] MEASURES_TO_GET = new String[] { "violations", "lines" };

    public static void main(String[] args) {
        DecimalFormat df = new DecimalFormat("#.##");
        //创建Sonar
        Sonar sonar = new Sonar(new HttpClient4Connector(new Host(host, username, password)));
        //执行资源请求
        ResourceQuery query = ResourceQuery.createForMetrics(resourceKey, MEASURES_TO_GET);
        query.setIncludeTrends(true);
        Resource resource = sonar.find(query);
        // 循环遍历获取"violations", "lines"
        List<Measure> allMeasures = resource.getMeasures();
        for (Measure measure : allMeasures) {
            System.out.println((measure.getMetricKey() + ": " +
                                df.format(measure.getValue())));
        }
    }
}

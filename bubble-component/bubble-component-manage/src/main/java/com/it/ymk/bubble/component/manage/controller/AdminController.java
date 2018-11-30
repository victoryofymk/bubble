package com.it.ymk.bubble.component.manage.controller;

import java.lang.management.MonitorInfo;
import java.lang.management.ThreadInfo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.it.ymk.bubble.core.utils.ThreadUtilities;
import com.it.ymk.bubble.core.utils.VelocityUtil;

import io.swagger.annotations.Api;

/**
 * 应用管理工具
 *
 * @author yanmingkun
 * @date 2018-11-30 17:24
 */
@Api("管理工具")
@RestController
@RequestMapping(value = "/managetool")
public class AdminController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

    /**
     * 根据进程ID查找
     * @param threadId
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/getThreadInfo")
    public String getThreadInfo(@RequestParam(value = "threadId") String threadId, HttpServletRequest request,
        HttpServletResponse response) {

        LOGGER.debug("执行选择的Job对象 queryQuartzLog quartzLog:");
        try {
            if (StringUtils.isBlank(threadId)) {
                return "";
            }

            ThreadInfo info = ThreadUtilities.getExtendThreadInfo(Long.valueOf(threadId));
            List list = new ArrayList();
            StackTraceElement[] st = info.getStackTrace();
            int index = 0;
            MonitorInfo[] monitors = info.getLockedMonitors();
            for (StackTraceElement e : st) {
                StringBuffer sb = new StringBuffer();
                if (index > 0) {
                    sb.append(" <- ");
                    sb.append(System.getProperty("line.separator"));
                }
                sb.append(e.toString() + "/n");
                list.add(sb.toString());
                if (monitors != null) {
                    for (MonitorInfo mi : monitors) {
                        if (mi.getLockedStackDepth() == index) {
                            list.add(" <- - locked " + mi.toString());
                        }
                    }
                }
                index++;
            }
            return VelocityUtil.generateHtml("getStackTrace.vm", list);
        } catch (Exception e) {
            LOGGER.error("doRunQuartzJob 执行选择的Job对象出错", e);
            return "fail";
        }
    }

    /**
     * 查询全部进程
     * 
     * @return
     */
    @RequestMapping(value = "/getThreadList")
    public List<ThreadInfo> getThreadList() {
        ThreadInfo[] threadInfos = ThreadUtilities.getThreadInfoList();
        ArrayList<ThreadInfo> arrayList = new ArrayList<ThreadInfo>(threadInfos.length);
        Collections.addAll(arrayList, threadInfos);
        return arrayList;
    }
}

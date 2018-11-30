package com.it.ymk.bubble.core.utils;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * @author yanmingkun
 * @date 2018-11-30 11:20
 */
public class ThreadUtilities {
    public static ThreadInfo getExtendThreadInfo(final long threadID) {
        ThreadInfo ti = null;
        final ThreadMXBean threadMBean = ManagementFactory.getThreadMXBean();
        if (threadMBean.isObjectMonitorUsageSupported()) {
            // VMs that support the monitor usage monitoring
            ThreadInfo[] infos = threadMBean.dumpAllThreads(true, false);
            for (ThreadInfo info : infos) {
                if (info.getThreadId() == threadID) {
                    ti = info;
                    break;
                }
            }
        }
        else {
            // VM doesn't support monitor usage monitoring
            ti = threadMBean.getThreadInfo(threadID, Integer.MAX_VALUE);
        }
        return ti;
    }

    public static ThreadInfo[] getThreadInfoList() {
        ThreadInfo[] infos = null;
        final ThreadMXBean threadMBean = ManagementFactory.getThreadMXBean();
        if (threadMBean.isObjectMonitorUsageSupported()) {
            // VMs that support the monitor usage monitoring
            infos = threadMBean.dumpAllThreads(true, false);
        }
        return infos;
    }
}

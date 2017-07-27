package com.booyildirim.tutorials;


import java.io.IOException;
import java.lang.management.ManagementFactory;

public final class VisualVmAttacher {

    private static final Runtime RUNTIME = Runtime.getRuntime();

    private VisualVmAttacher() {

    }

    public static void attach() {
        long pid = getPid();
        try {
            RUNTIME.exec("jvisualvm --openpid " + pid);
        } catch (IOException e) {
            throw new RuntimeException("can not attach to pid: " + pid);
        }
    }

    private static long getPid() {
        String processName = ManagementFactory.getRuntimeMXBean().getName();
        return Long.parseLong(processName.split("@")[0]);
    }


}

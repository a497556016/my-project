package com.heshaowei.myproj.file.common.global;

public class DownloadLog {
    private static long times = 0;

    public static void add() {
        times++;
    }

    public static long get() {
        return times;
    }
}

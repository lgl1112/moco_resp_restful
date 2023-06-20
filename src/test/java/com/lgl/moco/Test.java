package com.lgl.moco;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.watch.SimpleWatcher;
import cn.hutool.core.io.watch.WatchMonitor;
import cn.hutool.core.io.watch.watchers.DelayWatcher;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.WatchEvent;

public class Test {
    public static void main(String[] args) {
        File file = FileUtil.file("D:\\program\\idea\\moco_resp_restful\\src\\main\\resources\\Json.json");

        WatchMonitor monitor = WatchMonitor.createAll(file, new DelayWatcher(new SimpleWatcher(){
            @Override
            public void onModify(WatchEvent<?> event, Path currentPath) {
                System.out.println("bsasd");
            }
        }, 500));
        monitor.start();
        System.out.println("asdada");
    }
}

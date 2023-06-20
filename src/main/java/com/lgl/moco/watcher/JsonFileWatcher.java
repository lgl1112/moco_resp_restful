package com.lgl.moco.watcher;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.watch.SimpleWatcher;
import cn.hutool.core.io.watch.WatchMonitor;
import cn.hutool.core.io.watch.Watcher;
import cn.hutool.core.io.watch.watchers.DelayWatcher;
import cn.hutool.core.lang.Console;
import com.lgl.moco.service.LoadJsonFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.WatchEvent;

@Slf4j
@Component
public class JsonFileWatcher {
    @Value("${JsonFile.path:classpath:Json.json}")
    private String jsonFilePath;

    @Resource
    LoadJsonFile loadJsonFile;
    @Bean
    public void watchDog() {
        Thread thread = new Thread(new WatchRunnable());
        thread.start();
    }

    class WatchRunnable implements Runnable {

        @Override
        public void run() {
            File file = FileUtil.file(jsonFilePath);
            String absolutePath = file.getAbsolutePath();
            String parent = file.getParent();
            System.out.println(absolutePath+ "---- " + parent);
//            log.info("开始监听----有devtools在进行监听，此监听并未进行文件加载操作");
            log.info("开始监听----");
            WatchMonitor monitor = WatchMonitor.createAll(file, new DelayWatcher(new SimpleWatcher(){
                @Override
                public void onModify(WatchEvent<?> event, Path currentPath) {
                    log.info("文件已被修改,开始重新加载返回配置");
                    loadJsonFile.loadJsonStringToMap();
                    log.info("返回配置加载完成");
                }
            }, 500));
            monitor.start();
        }
    }
}

package com.lgl.moco;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.watch.SimpleWatcher;
import cn.hutool.core.io.watch.WatchMonitor;
import cn.hutool.core.io.watch.watchers.DelayWatcher;
import com.lgl.moco.service.LoadJsonFile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.WatchEvent;

@SpringBootTest
class MocoRespRestfulApplicationTests {

    @Autowired
    LoadJsonFile loadJsonFile;

    @Value("${JsonFile.path:classpath:Json.json}")
    private String jsonFilePath;
    @Test
    void contextLoads() {
        File file = FileUtil.file(jsonFilePath);

        WatchMonitor monitor = WatchMonitor.createAll(file, new DelayWatcher(new SimpleWatcher(){
            @Override
            public void onModify(WatchEvent<?> event, Path currentPath) {
                System.out.println("event = " + event);
            }
        }, 500));
        monitor.start();
    }

}

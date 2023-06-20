package com.lgl.moco.service;

import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.lgl.moco.cache.JsonPespCache;
import com.lgl.moco.entity.MoCoBean;
import com.lgl.moco.utils.JsonParseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
@Component
public class LoadJsonFile {

    @Value("${JsonFile.path:classpath:Json.json}")
    private String jsonFilePath;

    private static Lock lock = new ReentrantLock();

    public void loadJsonStringToMap(){
        lock.lock();
        try{
            JsonPespCache.clear();
            File file = FileUtil.file(jsonFilePath);
            JSONArray jsonArray = JSONUtil.readJSONArray(file, StandardCharsets.UTF_8);
            Object[] array = jsonArray.toArray();
            for (Object obj :
                    array) {
                MoCoBean moCoBean = JsonParseUtil.jsonParse(obj);
                JsonPespCache.putIn(moCoBean);
            }
            JsonPespCache.printMap();
        }catch (Exception exception){
            log.error("加载文件出错");
        }finally {
            lock.unlock();
        }
    }
}

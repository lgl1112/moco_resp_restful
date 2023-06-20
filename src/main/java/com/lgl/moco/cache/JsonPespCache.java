package com.lgl.moco.cache;

import cn.hutool.core.collection.CollectionUtil;
import com.lgl.moco.entity.MoCoBean;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class JsonPespCache {

    private static Map<String, MoCoBean> jsonRespMap = new ConcurrentHashMap<>();

    public static void putIn(MoCoBean moCoBean) {
        if (moCoBean != null && moCoBean.getRequest().getUrl() != null){
            jsonRespMap.put(moCoBean.getRequest().getUrl(),moCoBean);
        }
    }

    public static MoCoBean getMoCoBean(String url) {
        return jsonRespMap.get(url);
    }

    public static void printMap(){
        System.out.println(jsonRespMap);
    }

    public static boolean isNull() {
        if (CollectionUtil.isEmpty(jsonRespMap)){
            return true;
        }else {
            return false;
        }
    }

    public static void  clear() {
        jsonRespMap = new ConcurrentHashMap<>();
    }

    public static Map<String, MoCoBean> getJsonRespMap(){
        return jsonRespMap;
    }
}

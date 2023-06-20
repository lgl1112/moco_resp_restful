package com.lgl.moco.entity;

import lombok.Data;

import java.util.Iterator;
import java.util.Map;

@Data
public class MoCoRequest {
    private String url;

    private Map<String, Object> headers;

    @Override
    public String toString() {
        return "{" + "\nurl : "+url + "\nheaders= {\n" + mapToString() + " }"+"\n}";
    }

    private String mapToString() {
        Iterator<String> iterator = headers.keySet().iterator();
        String line = "";
        while (iterator.hasNext()){
            String next = iterator.next();
            line = line + "       " + next + " : " + headers.get(next)+"\n";
        }
        return line;
    }
}

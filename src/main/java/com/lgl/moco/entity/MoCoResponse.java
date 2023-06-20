package com.lgl.moco.entity;

import lombok.Data;

import java.util.Iterator;
import java.util.Map;
@Data
public class MoCoResponse {

    private String status;

    private Map<String, Object> headers;

    private String data;

    @Override
    public String toString() {
        return "{" + "\nstatus : "+status + "\nheaders= {\n" + mapToString() + " }\ndata : " + data+"\n}";
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

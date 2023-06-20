package com.lgl.moco.utils;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.lgl.moco.entity.MoCoBean;
import com.lgl.moco.entity.MoCoRequest;
import com.lgl.moco.entity.MoCoResponse;

public class JsonParseUtil {
    public static MoCoBean jsonParse(Object obj){
        JSONObject entries = JSONUtil.parseObj(obj);
        MoCoRequest request = entries.get("request", MoCoRequest.class);
        MoCoResponse response = entries.get("response", MoCoResponse.class);
        MoCoBean moCoBean = new MoCoBean(request,response);
        return moCoBean;
    }
}

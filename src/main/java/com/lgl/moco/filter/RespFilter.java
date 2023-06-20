package com.lgl.moco.filter;

import com.lgl.moco.cache.JsonPespCache;
import com.lgl.moco.entity.MoCoBean;
import com.lgl.moco.entity.MoCoResponse;
import com.lgl.moco.service.LoadJsonFile;
import com.lgl.moco.utils.RestClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;


import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

@Slf4j
@Order(1)
@WebFilter(urlPatterns = "/*")
public class RespFilter implements Filter{

    @Resource
    LoadJsonFile loadJsonFile;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        if(JsonPespCache.isNull()){
            loadJsonFile.loadJsonStringToMap();
        }
//        JsonPespCache.printMap();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        String code = "123413";
        boolean flag = mappingResp((HttpServletRequest) servletRequest, (HttpServletResponse) servletResponse, code);
        if (flag) {
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }


    private boolean mappingResp(HttpServletRequest request, HttpServletResponse response, String data){
        String requestURI = request.getRequestURI();
        if (requestURI.contains("thymeleaf")) {
            return false;
        }
        System.out.println("requestURI = " + requestURI);
        MoCoBean moCoBean = JsonPespCache.getMoCoBean(requestURI);
        String returnMsg = "";
        if (moCoBean == null){
            response.setStatus(404);
            returnMsg="无此配置";
            log.error(returnMsg);
        }else {
            MoCoResponse moCoResponse = moCoBean.getResponse();
            Map<String, Object> headers = moCoResponse.getHeaders();
            Iterator<String> iterator = headers.keySet().iterator();
            String headerContens = "";
            while (iterator.hasNext()) {
                String key = iterator.next();
                headerContens = headers.get(key).toString();
                response.setHeader(key,headerContens);
            }
            response.setStatus(Integer.parseInt(moCoResponse.getStatus()));
            data = moCoResponse.getData();
        }
        RestClient.writeJsonStringToClient(request,response,data);
        return true;
    }

    @Override
    public void destroy() {

    }
}

package com.lgl.moco.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class RestClient {

    public static void writeJsonStringToClient(HttpServletRequest request, HttpServletResponse response, String data){
        String contentType = request.getContentType();

        if (contentType == null || contentType.startsWith("multipart/form-data")){
            writeTestStringToClient(response, data, request.isSecure());
        }else {
            writeJsonStringToClient(response,data);
        }
    }

    public static void writeJsonStringToClient(HttpServletResponse response, String data){
        response.setHeader("Programa","no-cache");
        response.setDateHeader("Expries",-1L);
        response.setContentType("application/json;charset=utf-8");

        daWriteMove(response, data);
    }

    private static void daWriteMove(HttpServletResponse response, String data) {
        PrintWriter printWriter = null;
        try{
            printWriter = response.getWriter();
            IOUtils.write(data,printWriter);
            response.flushBuffer();
        }catch (IOException ioException){
            log.error(String.valueOf(ioException));
        }finally {
            if (printWriter != null){
                printWriter.close();
            }
        }
    }

    private static void writeTestStringToClient(HttpServletResponse response, String data, boolean secure) {
        response.setHeader("Programa","no-cache");
        response.setDateHeader("Expries",-1L);
        response.setContentType("text/html; charset=UTF-8");

        if(secure) {
            response.setHeader("Strict-Transport-Security","max-age=3122400; includeSunDomains");
        }

        daWriteMove(response, data);
    }

    private static String objectToJsonString(Object data) {
        try{
            return new ObjectMapper().writeValueAsString(data);
        } catch (IOException exception ){
            log.error(String.valueOf(exception));
        }
        return null;
    }
}

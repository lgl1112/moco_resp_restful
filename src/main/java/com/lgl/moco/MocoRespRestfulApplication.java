package com.lgl.moco;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class MocoRespRestfulApplication {

    public static void main(String[] args) {
        SpringApplication.run(MocoRespRestfulApplication.class, args);
    }

}

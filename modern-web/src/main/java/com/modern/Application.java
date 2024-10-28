package com.modern;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.servlet.MultipartConfigElement;

/**
 * 启动程序
 *
 * @author tzh
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableAsync
public class Application {
    public static void main(String[] args) {

        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(Application.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  Tsp-平台启动成功   ლ(´ڡ`ლ)ﾞ  \n");
    }

    @Bean
    MultipartConfigElement multipartConfigElement(){
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setLocation("/home/data/apps/temp");
        return factory.createMultipartConfig();
    }
}

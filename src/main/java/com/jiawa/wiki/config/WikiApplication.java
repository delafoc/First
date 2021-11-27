package com.jiawa.wiki.config;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;


// 由于@ComponentScan只扫描启动类所在的包名，当启动类位置改变，报错404
//可通过添加@ComponentScan（“包名”）解决
@ComponentScan("com.jiawa.wiki")
//用@ComponentScan{("com.jiawa.wiki"), "com.jiawa.controller"}可以扫描多个包
@SpringBootApplication
@MapperScan("com.jiawa.wiki.mapper") //扫描注解
public class WikiApplication {


    private static final Logger LOG = LoggerFactory.getLogger(WikiApplication.class);

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(WikiApplication.class);
        Environment env = app.run(args).getEnvironment();
        LOG.info("启动成功！！");
        LOG.info("地址：\thttp：//127.0.0.1:{}", env.getProperty("server.port"));
    }
}

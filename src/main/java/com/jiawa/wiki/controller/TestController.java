package com.jiawa.wiki.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController // 用于返回字符串，@RestController等于@Controller加上@ResponseBody
//@Controller     //用于返回页面（前后端分离，一般用不到）
public class TestController {

    @Value("${test.hello:TEST}")
    private  String testHello;

    //http：//127.0.0.1:8085/hello

    //常见的http请求方式有四种
    /**
     * GET, POST, PUT, DELETE
     * /user？id=1
     * /user/1
     * @return
     */

    // 注解，表示接口的请求地址
    //@RequestMapping("/hello")
//    @GetMapping("/hello")
//    @PostMapping("/hello") // 与默认请求方法不同， 报错405
//    @PutMapping
//    @DeleteMapping
    @GetMapping("/hello")
    public String hello() {
        return "Hello World!" + testHello;
    }

    @PostMapping("/hello/Post")
    public String helloPost(String name) {
        return "Hello World!" + name;
    }
}

package com.jiawa.wiki.controller;

import com.jiawa.wiki.domain.Demo;
import com.jiawa.wiki.service.DemoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


@RestController
//@RestController 加下面写的 @GetMapping("/demo/List") 等于
// @RestController("/demo") 加下面写的 @GetMapping(" /List")
public class DemoController {
    @Resource
    private DemoService demoService;

    @GetMapping("/demo/List")
    public List<Demo> list() {
        return demoService.list();
    }

    //可增加demo表的增删改查

}

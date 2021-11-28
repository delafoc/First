package com.jiawa.wiki.controller;

import com.jiawa.wiki.domain.Ebook;
import com.jiawa.wiki.service.EbookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


@RestController
//@RestController 加下面写的 @GetMapping("/ebook/List") 等于
// @RestController("/ebook") 加下面写的 @GetMapping(" /List")
public class EbookController {
    @Resource
    private EbookService ebookService;

    @GetMapping("/ebook/List")
    public List<Ebook> list() {
        return ebookService.list();
    }

    //可增加ebook表的增删改查

}

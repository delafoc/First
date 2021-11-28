package com.jiawa.wiki.controller;

import com.jiawa.wiki.req.EbookReq;
import com.jiawa.wiki.resp.CommonResp;
import com.jiawa.wiki.resp.EbookResp;
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
    public CommonResp list(EbookReq req) {
        CommonResp<List<EbookResp>> resp = new CommonResp<>();
        List<EbookResp> list = ebookService.list(req);
        resp.setContent(list);
        return resp;

        //可增加ebook表的增删改查
    }
}

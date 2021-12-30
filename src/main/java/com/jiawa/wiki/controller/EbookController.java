package com.jiawa.wiki.controller;

import com.jiawa.wiki.req.EbookQueryReq;
import com.jiawa.wiki.req.EbookSaveReq;
import com.jiawa.wiki.resp.CommonResp;
import com.jiawa.wiki.resp.EbookQueryResp;
import com.jiawa.wiki.resp.PageResp;
import com.jiawa.wiki.service.EbookService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;


@RestController
//@RestController 加下面写的 @GetMapping("/ebook/List") 等于
// @RestController("/ebook") 加下面写的 @GetMapping(" /List")
@RequestMapping("/ebook")
public class EbookController {
    @Resource
    private EbookService ebookService;

    //    @GetMapping("/ebook/List")
    @GetMapping("/List")
    public CommonResp list(@Valid EbookQueryReq req) {
        // 加@Valid 表示开启校验规则
        CommonResp<PageResp<EbookQueryResp>> resp = new CommonResp<>();
//        List<EbookQueryResp> list = ebookService.list(req);
        PageResp<EbookQueryResp> list = ebookService.list(req);
        resp.setContent(list);
        return resp;

        //可增加ebook表的增删改查
    }

    // 更新编辑操作
    @PostMapping("/save") //保存、更新类的一般用PostMapping
    public CommonResp save(@Valid @RequestBody EbookSaveReq req) {
        CommonResp resp = new CommonResp<>();
        ebookService.save(req);
        return resp;
    }

    // 删除操作
    @DeleteMapping("/delete/{id}") // 删除一般按照ID来删除
    public CommonResp delete(@PathVariable Long id) {
        CommonResp resp = new CommonResp<>();
        ebookService.delete(id);
        return resp;
    }
}

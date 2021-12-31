package com.jiawa.wiki.controller;

import com.jiawa.wiki.req.CategoryQueryReq;
import com.jiawa.wiki.req.CategorySaveReq;
import com.jiawa.wiki.resp.CommonResp;
import com.jiawa.wiki.resp.CategoryQueryResp;
import com.jiawa.wiki.resp.PageResp;
import com.jiawa.wiki.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;


@RestController
//@RestController 加下面写的 @GetMapping("/category/List") 等于
// @RestController("/category") 加下面写的 @GetMapping(" /List")
@RequestMapping("/category")
public class CategoryController {
    @Resource
    private CategoryService categoryService;

    //    @GetMapping("/category/List")
    @GetMapping("/List")
    public CommonResp list(@Valid CategoryQueryReq req) {
        // 加@Valid 表示开启校验规则
        CommonResp<PageResp<CategoryQueryResp>> resp = new CommonResp<>();
//        List<CategoryQueryResp> list = categoryService.list(req);
        PageResp<CategoryQueryResp> list = categoryService.list(req);
        resp.setContent(list);
        return resp;

        //可增加category表的增删改查
    }

    // 更新编辑操作
    @PostMapping("/save") //保存、更新类的一般用PostMapping
    public CommonResp save(@Valid @RequestBody CategorySaveReq req) {
        CommonResp resp = new CommonResp<>();
        categoryService.save(req);
        return resp;
    }

    // 删除操作
    @DeleteMapping("/delete/{id}") // 删除一般按照ID来删除
    public CommonResp delete(@PathVariable Long id) {
        CommonResp resp = new CommonResp<>();
        categoryService.delete(id);
        return resp;
    }
}

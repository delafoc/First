package com.jiawa.wiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiawa.wiki.domain.Category;
import com.jiawa.wiki.domain.CategoryExample;
import com.jiawa.wiki.mapper.CategoryMapper;
import com.jiawa.wiki.req.CategoryQueryReq;
import com.jiawa.wiki.req.CategorySaveReq;
import com.jiawa.wiki.resp.CategoryQueryResp;
import com.jiawa.wiki.resp.PageResp;
import com.jiawa.wiki.util.CopyUtil;
import com.jiawa.wiki.util.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CategoryService {

    private static final Logger LOG = LoggerFactory.getLogger(CategoryService.class);

//    @Autowired // 与@Resource两个都可以
    @Resource // 注入CategoryMapper
    private CategoryMapper categoryMapper;

    @Resource // 注入SnowFlake
    private SnowFlake snowFlake;

    public PageResp<CategoryQueryResp> list(CategoryQueryReq req) {
        // 表示第一页数组从1开始 而且只能查下面的第一条数据库查询语句， 最好就放在查询语句的前一行
//        PageHelper.startPage(1, 3);
        CategoryExample categoryExample = new CategoryExample();
        CategoryExample.Criteria criteria = categoryExample.createCriteria();
        // 上面的两行的写法固定
        PageHelper.startPage(req.getPage(), req.getSize());
        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);

        PageInfo<Category> pageInfo = new PageInfo<>(categoryList); // 得到PageInfo， 需要把列表数据放进来

        //打印日志，输出总行数和总页数
        LOG.info("总行数：{}",pageInfo.getTotal()); // 写日志用占位符{}相连， 而不用+号
        LOG.info("总页数：{}",pageInfo.getPages()); // 总页数


        //实现返回类的封装
//        List<CategoryQueryResp> respList = new ArrayList<>();
//
//        for (Category category : categoryList) {
////            CategoryQueryResp categoryResp = new CategoryQueryResp();
//////            categoryResp.setId(category.getId());
////            BeanUtils.copyProperties(category, categoryResp); // 复制
//////            categoryResp.setId(123L);
//

        //对象复制
//            CategoryQueryResp categoryResp = CopyUtil.copy(category, CategoryQueryResp.class);
//
//            respList.add(categoryResp);
//        }




        // 列表复制
        List<CategoryQueryResp> list = CopyUtil.copyList(categoryList, CategoryQueryResp.class);

        PageResp<CategoryQueryResp> pageResp = new PageResp();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);

        return pageResp;
    }


    /**
     * 更新保存的方法
     */

    public void save(CategorySaveReq req) {
        Category category = CopyUtil.copy(req, Category.class);
        if (ObjectUtils.isEmpty(req.getId())) {
            // 新增
            category.setId(snowFlake.nextId());
            categoryMapper.insert(category);
        } else {
            // 更新
            categoryMapper.updateByPrimaryKey(category);
        }
    }

    /**
     * 删除
     */
    public void delete(Long id) {
        categoryMapper.deleteByPrimaryKey(id);
    }
}

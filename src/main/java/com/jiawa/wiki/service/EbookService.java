package com.jiawa.wiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiawa.wiki.domain.Ebook;
import com.jiawa.wiki.domain.EbookExample;
import com.jiawa.wiki.mapper.EbookMapper;
import com.jiawa.wiki.req.EbookReq;
import com.jiawa.wiki.resp.EbookResp;
import com.jiawa.wiki.resp.PageResp;
import com.jiawa.wiki.util.CopyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EbookService {

    private static final Logger LOG = LoggerFactory.getLogger(EbookService.class);

//    @Autowired // 与@Resource两个都可以
    @Resource // 注入EbookMapper
    private EbookMapper ebookMapper;

    public PageResp<EbookResp> list(EbookReq req) {
        // 表示第一页数组从1开始 而且只能查下面的第一条数据库查询语句， 最好就放在查询语句的前一行
//        PageHelper.startPage(1, 3);
        EbookExample ebookExample = new EbookExample();
        EbookExample.Criteria criteria = ebookExample.createCriteria();
        // 上面的两行的写法固定
        if (!ObjectUtils.isEmpty(req.getName())) {
            // 参数传递模糊查询
            criteria.andNameLike("%" + req.getName() + "%");
        }
        PageHelper.startPage(req.getPage(), req.getSize());
        List<Ebook> ebookList = ebookMapper.selectByExample(ebookExample);

        PageInfo<Ebook> pageInfo = new PageInfo<>(ebookList); // 得到PageInfo， 需要把列表数据放进来

        //打印日志，输出总行数和总页数
        LOG.info("总行数：{}",pageInfo.getTotal()); // 写日志用占位符{}相连， 而不用+号
        LOG.info("总页数：{}",pageInfo.getPages()); // 总页数


        //实现返回类的封装
//        List<EbookResp> respList = new ArrayList<>();
//
//        for (Ebook ebook : ebookList) {
////            EbookResp ebookResp = new EbookResp();
//////            ebookResp.setId(ebook.getId());
////            BeanUtils.copyProperties(ebook, ebookResp); // 复制
//////            ebookResp.setId(123L);
//

        //对象复制
//            EbookResp ebookResp = CopyUtil.copy(ebook, EbookResp.class);
//
//            respList.add(ebookResp);
//        }




        // 列表复制
        List<EbookResp> list = CopyUtil.copyList(ebookList, EbookResp.class);

        PageResp<EbookResp> pageResp = new PageResp();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);

        return pageResp;
    }
}

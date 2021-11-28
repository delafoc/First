package com.jiawa.wiki.service;

import com.jiawa.wiki.domain.Ebook;
import com.jiawa.wiki.domain.EbookExample;
import com.jiawa.wiki.mapper.EbookMapper;
import com.jiawa.wiki.req.EbookReq;
import com.jiawa.wiki.resp.EbookResp;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class EbookService {

//    @Autowired // 与@Resource两个都可以
    @Resource // 注入EbookMapper
    private EbookMapper ebookMapper;

    public List<EbookResp> list(EbookReq req) {
        EbookExample ebookExample = new EbookExample();
        EbookExample.Criteria criteria = ebookExample.createCriteria();
        // 上面的两行的写法固定

        // 参数传递模糊查询
        criteria.andNameLike("%" + req.getName() + "%");

        List<Ebook> ebookList = ebookMapper.selectByExample(ebookExample);

        //实现返回类的封装
        List<EbookResp> respList = new ArrayList<>();

        for (Ebook ebook : ebookList) {
            EbookResp ebookResp = new EbookResp();
//            ebookResp.setId(ebook.getId());
            BeanUtils.copyProperties(ebook, ebookResp); // 复制
//            ebookResp.setId(123L);
            respList.add(ebookResp);
        }

        return respList;
    }
}

package com.jiawa.wiki.service;

import com.jiawa.wiki.domain.Test;
import com.jiawa.wiki.mapper.TestMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TestService {

//    @Autowired // 与@Resource两个都可以
    @Resource // 注入TestMapper
    private TestMapper testMapper;

    public List<Test> list() {
        return testMapper.list();
    }
}

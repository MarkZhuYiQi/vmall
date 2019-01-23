package com.mark.manager.daoImpl;

import com.alibaba.fastjson.JSON;
import com.mark.common.jedis.JedisClient;
import com.mark.manager.dao.CategoryDao;
import com.mark.manager.mapper.VproNavbarMapper;
import com.mark.manager.pojo.VproNavbar;
import com.mark.manager.pojo.VproNavbarExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
@Component("categoryDB")
public class CategoryDaoByDBImpl implements CategoryDao {
    @Autowired
    VproNavbarMapper vproNavbarMapper;
    @Autowired
    JedisClient jedisClient;
    @Value("${navbarPrefix}")
    String navbarPrefix;

    @Override
    public List<VproNavbar> getCategories() {
        VproNavbarExample vproNavbarExample = new VproNavbarExample();
        vproNavbarExample.createCriteria();
        List<VproNavbar> navbars = vproNavbarMapper.selectByExample(vproNavbarExample);
        String str = JSON.toJSONString(navbars);
        jedisClient.set(navbarPrefix, str);
        return navbars;
    }
}

package com.mark.manager.daoImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mark.common.jedis.JedisClient;
import com.mark.manager.dao.CategoryDao;
import com.mark.manager.pojo.VproNavbar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
@Component("categoryRedis")
public class CategoryDaoByRedisImpl implements CategoryDao {
    @Autowired
    JedisClient jedisClient;
    @Value("${navbarPrefix}")
    String navbarPrefix;
    @Override
    public List<VproNavbar> getCategories() {
        String navbar = jedisClient.get(navbarPrefix);
        return JSON.parseArray(navbar, VproNavbar.class);
    }
}

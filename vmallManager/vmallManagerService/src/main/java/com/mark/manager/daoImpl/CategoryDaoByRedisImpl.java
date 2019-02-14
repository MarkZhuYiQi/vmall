package com.mark.manager.daoImpl;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mark.common.exception.CategoryException;
import com.mark.common.jedis.JedisClient;
import com.mark.common.pojo.CategoryNode;
import com.mark.common.util.BeanUtil;
import com.mark.manager.dao.CategoryDao;
import com.mark.manager.pojo.VproNavbar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.beans.IntrospectionException;
import java.util.List;
import java.util.Map;
@Component("categoryRedis")
public class CategoryDaoByRedisImpl implements CategoryDao {
    private static final Logger logger = LoggerFactory.getLogger(CategoryDaoByRedisImpl.class);
    @Autowired
    JedisClient jedisClient;

    @Value("${navbarPrefix}")
    String navbarPrefix;

    @Value("${navbarTreePrefix}")
    String navbarTreePrefix;

    @Override
    public List<VproNavbar> getCategories() throws CategoryException {
        String navbar = jedisClient.get(navbarPrefix);
        if (StringUtils.isEmpty(navbar)) throw new CategoryException("get data from redis failed");
        return JSON.parseArray(navbar, VproNavbar.class);
    }

    @Override
    public VproNavbar getCategoryById(Integer navId) throws CategoryException {
        Map<String, String> navMap = jedisClient.hgetAll(navbarPrefix + navId);
        if (navMap.size() == 0) throw new CategoryException("get data from redis failed");
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(navMap, VproNavbar.class);
    }

    @Override
    public List<CategoryNode> getCategoriesTree(List<VproNavbar> navbars) throws CategoryException {
        String navTreeStr = jedisClient.get(navbarTreePrefix);
        if (StringUtils.isEmpty(navTreeStr)) throw new CategoryException("categoryTree could not be got in redis");
        List<CategoryNode> navTree = JSON.parseArray(navTreeStr, CategoryNode.class);
        return navTree;
    }
}
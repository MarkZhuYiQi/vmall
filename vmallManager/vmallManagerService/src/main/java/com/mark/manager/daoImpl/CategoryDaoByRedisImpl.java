package com.mark.manager.daoImpl;

import com.alibaba.fastjson.JSON;
import com.mark.common.exception.CategoryException;
import com.mark.common.jedis.JedisClient;
import com.mark.common.util.BeanUtil;
import com.mark.manager.dao.CategoryDao;
import com.mark.manager.pojo.VproNavbar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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
    @Override
    public List<VproNavbar> getCategories() {
        System.out.println(navbarPrefix);
        String navbar = jedisClient.get(navbarPrefix);
        System.out.println("CategoryDaoByRedis: " + navbar.length());
        return JSON.parseArray(navbar, VproNavbar.class);
    }

    @Override
    public VproNavbar getCategoryById(Integer navId) throws CategoryException {
        Map<String, String> navMap = jedisClient.hgetAll(navbarPrefix + navId);
        logger.info("CategoryDaoByRedisImpl: getCategoryById: " + navId+ ", res: " + navMap);
        if (navMap.size() == 0) throw new CategoryException("get data from redis failed");
        try {
            return BeanUtil.map2bean(navMap, VproNavbar.class);
        } catch (IntrospectionException e) {
            logger.info("map convert to navbar object failed");
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            logger.info("map convert to navbar object failed");
            e.printStackTrace();
        } catch (InstantiationException e) {
            logger.info("map convert to navbar object failed");
            e.printStackTrace();
        }
        throw new CategoryException("get data from redis failed");
    }
}

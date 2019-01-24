package com.mark.manager.daoImpl;

import com.alibaba.fastjson.JSON;
import com.mark.common.jedis.JedisClient;
import com.mark.common.util.BeanUtil;
import com.mark.manager.dao.CategoryDao;
import com.mark.manager.mapper.VproNavbarMapper;
import com.mark.manager.pojo.VproNavbar;
import com.mark.manager.pojo.VproNavbarExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.beans.IntrospectionException;
import java.util.List;
import java.util.Map;

@Component("categoryDB")
public class CategoryDaoByDBImpl implements CategoryDao {
    private static final Logger logger = LoggerFactory.getLogger(CategoryDaoByDBImpl.class);

    @Autowired
    VproNavbarMapper vproNavbarMapper;
    @Autowired
    JedisClient jedisClient;
    @Value("${navbarPrefix}")
    String navbarPrefix;

    @Override
    public List<VproNavbar> getCategories() {
        System.out.println(navbarPrefix);
        VproNavbarExample vproNavbarExample = new VproNavbarExample();
        vproNavbarExample.createCriteria();
        List<VproNavbar> navbars = vproNavbarMapper.selectByExample(vproNavbarExample);
        String str = JSON.toJSONString(navbars);
        jedisClient.set(navbarPrefix, str);
        return navbars;
    }

    @Override
    public VproNavbar getCategoryById(Integer navId) {
        VproNavbar vproNavbar = vproNavbarMapper.selectByPrimaryKey(navId);
        try {
            Map<String, String> navbarMap = BeanUtil.bean2map(vproNavbar);
            for(Map.Entry<String, String> m : navbarMap.entrySet()) {
                jedisClient.hset(navbarPrefix + navId, m.getKey(), m.getValue());
            }
        } catch (IntrospectionException e) {
            logger.info("object convert to map failed");
            e.printStackTrace();
        }
        return vproNavbar;
    }
}

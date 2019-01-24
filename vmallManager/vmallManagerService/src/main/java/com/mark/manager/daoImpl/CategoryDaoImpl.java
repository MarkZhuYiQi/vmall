package com.mark.manager.daoImpl;

import com.mark.manager.dao.CategoryDao;
import com.mark.manager.pojo.VproNavbar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("categoryDao")
public class CategoryDaoImpl implements CategoryDao {
    private static final Logger logger = LoggerFactory.getLogger(CategoryDaoImpl.class);
    @Autowired
    @Qualifier("categoryDB")
    CategoryDao categoryDB;

    @Autowired
    @Qualifier("categoryRedis")
    CategoryDao categoryRedis;

    @Override
    public List<VproNavbar> getCategories() {
        List<VproNavbar> list;
        list = categoryRedis.getCategories();
        if (list == null) {
            logger.info("get navbar data from redis failed.");
            list = categoryDB.getCategories();
        }
        return list;
    }

    @Override
    public VproNavbar getCategoryById(Integer navId) {
        VproNavbar vproNavbar = new VproNavbar();
        vproNavbar = categoryRedis.getCategoryById(navId);
        System.out.println(vproNavbar.toString());
        System.out.println(vproNavbar);
        if (vproNavbar == null) {
            logger.info("get navbar" + navId + " data from redis failed.");
            vproNavbar = categoryDB.getCategoryById(navId);
        }
        return vproNavbar;

    }
}

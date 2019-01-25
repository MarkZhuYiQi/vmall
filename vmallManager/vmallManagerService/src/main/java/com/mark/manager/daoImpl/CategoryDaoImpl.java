package com.mark.manager.daoImpl;

import com.mark.common.constant.CategoryConstant;
import com.mark.common.exception.CategoryException;
import com.mark.common.pojo.CategoryNode;
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
    public VproNavbar getCategoryById(Integer navId) throws CategoryException {
        VproNavbar vproNavbar = new VproNavbar();
        try {
            vproNavbar = categoryRedis.getCategoryById(navId);
        } catch (CategoryException e) {
            logger.info("get navbar data from redis failed, navId: " + navId);
            try{
                vproNavbar = categoryDB.getCategoryById(navId);
            } catch (CategoryException ex) {
                logger.info("get navbar data from mysql failed, navId: " + navId);
                throw new CategoryException("get navbar from database failed, check the navId if exists! navId: " + navId, CategoryConstant.NAVBAR_NULL_BY_ID);
            }
        }
        return vproNavbar;
    }

    @Override
    public List<CategoryNode> getCategoriesTree(List<VproNavbar> navbars) throws CategoryException {
        return categoryRedis.getCategoriesTree(navbars);
    }
}

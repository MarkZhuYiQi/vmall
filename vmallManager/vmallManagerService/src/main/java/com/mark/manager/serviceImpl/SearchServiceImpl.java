package com.mark.manager.serviceImpl;

import com.mark.common.elasticsearch.ElasticsearchUtil;
import com.mark.common.pojo.ESQuery;
import com.mark.common.pojo.SearchResult;
import com.mark.common.util.BeanUtil;
import com.mark.manager.dto.Search;
import com.mark.manager.pojo.VproNavbar;
import com.mark.manager.service.CategoryService;
import com.mark.manager.service.SearchService;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.transport.TransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SearchServiceImpl implements SearchService {
    private static final Logger logger = LoggerFactory.getLogger(SearchService.class);
    @Autowired
    GenericObjectPool<TransportClient> clientPool;
    @Autowired
    GenericObjectPool<RestHighLevelClient> restClientPool;
    @Autowired
    CategoryService categoryService;

    @Override
    public SearchResult simpleQuery(Search search) {
        SearchResult searchResult = new SearchResult();
        List<Map<String, Object>> matches = new ArrayList<Map<String, Object>>();
        Map<String, String> criteria = new HashMap<String, String>();
        Map<String, Object> range = new HashMap<String, Object>();
        criteria.put("searchKey", "courseTitle");
        criteria.put("searchValue", search.getSearchItem().getCourseTitle());
        range.put("start", search.getStart());
        range.put("size", search.getSize());
        try {
            TransportClient client = clientPool.borrowObject();
            searchResult = ElasticsearchUtil.simpleQuery(client, criteria, range);
            clientPool.returnObject(client);
            return searchResult;
//        } catch (UnknownHostException e) {
//            logger.error("es链接失败");
//            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return searchResult;
    }

    @Override
    public SearchResult criteriaQuery(Search search) {
        SearchResult searchResult = new SearchResult();
        try {
            RestHighLevelClient restHighLevelClient = restClientPool.borrowObject();
            Map<String, Object> courseRange = BeanUtil.bean2map4criteria(search);
            System.out.println(courseRange);
            ESQuery esQuery = new ESQuery();
//            System.out.println(courseCriteria);
//            System.out.println(courseRange);
            // 限定范围pid
            if (search.getSearchItem().getCoursePid() != -1) {
                List<VproNavbar> navbars = categoryService.getCategories();
                List<Integer> idList = categoryService.getSubIdFromCategory(search.getSearchItem().getCoursePid(), navbars, new ArrayList<Integer>());
                // 去重
                Set<Integer> set = new HashSet<Integer>();
                for(int i = 0; i < idList.size(); i++) {
                    set.add(idList.get(i));
                }
                // jdk6以后创建用空数组
                Integer[] ids = set.toArray(new Integer[0]);
//                Integer[] ids = idList.toArray(new Integer[idList.size()]);
//                search.getSearchItem().setCoursePids(ids);
                search.getSearchItem().setCoursePid(-1);
                Map<String, Object> coursePids = new HashMap<String, Object>();
                coursePids.put("coursePid", idList);
                esQuery.setFilter(coursePids);
            }
            Map<String, Object> courseCriteria = BeanUtil.bean2map4criteria(search.getSearchItem());
            esQuery.setMust(courseCriteria);
            searchResult = ElasticsearchUtil.boolQuery(restHighLevelClient, esQuery, courseRange);
            restClientPool.returnObject(restHighLevelClient);
            return searchResult;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return searchResult;
    }
}

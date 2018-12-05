package com.mark.manager.controller;

import com.mark.common.pojo.SearchResult;
import com.mark.manager.bo.Result;
import com.mark.manager.dto.Search;
import com.mark.manager.service.SearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("search")
public class
SearchController {
    private static final Logger logger = LoggerFactory.getLogger(SearchController.class);

    @Autowired
    private SearchService searchService;

    @PostMapping("simple")
    public Result simpleSearch(@RequestBody Search search) {
        SearchResult searchResult = new SearchResult();
        Result result = new Result();
        List<Map<String, Object>> matches = new ArrayList<Map<String, Object>>();
        if (search.getType().equals("simple")) {
            searchResult = searchService.simpleQuery(search);
            result.setData(searchResult);
        }
        return result;
    }

    @PostMapping("criteria")
    public Result criteriaSearch(@RequestBody Search search) {
        SearchResult searchResult = new SearchResult();
        Result result = new Result();
        if (search.getType().equals("criteria")) {
            searchResult = searchService.criteriaQuery(search);
            result.setData(searchResult);
        }
        return result;
    }
}

package com.mark.manager.service;

import com.mark.common.pojo.SearchResult;
import com.mark.manager.dto.Search;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface SearchService {
    SearchResult simpleQuery(Search search);
    SearchResult criteriaQuery(Search search);
}

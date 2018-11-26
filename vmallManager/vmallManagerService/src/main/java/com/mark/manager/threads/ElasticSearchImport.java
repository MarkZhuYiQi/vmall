package com.mark.manager.threads;

import com.mark.common.elasticsearch.ElasticsearchUtil;

import java.util.concurrent.Callable;

public class ElasticSearchImport implements Callable<String> {
    private String filename;
    public ElasticSearchImport(String filename) {
        this.filename = filename;
    }

    @Override
    public String call() throws Exception {
        return ElasticsearchUtil.ImportBulk(this.filename);
    }
}

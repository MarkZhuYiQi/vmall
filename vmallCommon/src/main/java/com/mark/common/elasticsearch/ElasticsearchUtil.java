package com.mark.common.elasticsearch;

import com.alibaba.fastjson.JSON;
import com.mark.common.pojo.ESQuery;
import com.mark.common.pojo.SearchResult;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

public class ElasticsearchUtil {
    private final static String course = "course";
    private final static String detail = "detail";

    public static TransportClient getClient() throws UnknownHostException {
        // 设置集群名称
        Settings settings = Settings.builder().put("cluster.name", "vpro-cluster").build();
        // 创建client, 端口是tcp port
//        TransportClient transportClient = new PreBuiltTransportClient(settings);
        TransportClient transportClient = new PreBuiltTransportClient(Settings.EMPTY).addTransportAddress(new TransportAddress(InetAddress.getByName("223.112.88.214"), 9400))
        .addTransportAddress(new TransportAddress(InetAddress.getByName("223.112.88.214"), 9401));
        return transportClient;
    }
    // 关闭client
    public static void closeClient(TransportClient transportClient) {
        transportClient.close();
    }

    public static void createIndexAndMapping() throws Exception {
        TransportClient transportClient = getClient();
        XContentBuilder builder = jsonBuilder()
                .startObject()
                .startObject("properties")
                .startObject("courseDiscountPrice")
                .field("type", "scaled_float")
                .field("scaling_factor", 1)
                .endObject()
                .startObject("authIp")
                .field("type", "text")
                .endObject()
                .startObject("courseCoverIsused")
                .field("type", "byte")
                .endObject()
                .startObject("authToken")
                .field("type", "text")
                .endObject()
                .startObject("authTime")
                .field("type", "long")
                .endObject()
                .startObject("authNkname")
                .field("type", "text")
                .endObject()
                .startObject("courseCoverId")
                .field("type", "long")
                .endObject()
                .startObject("authId")
                .field("type", "integer")
                .endObject()
                .startObject("authRolesId")
                .field("type", "short")
                .endObject()
                .startObject("courseTitle")
                .field("type", "text")
                .field("analyzer", "ik_max_word")
                .endObject()
                .startObject("courseCoverUptime")
                .field("type", "long")
                .endObject()
                .startObject("authAppkey")
                .field("type", "text")
                .endObject()
                .startObject("courseCoverIsuploaded")
                .field("type", "byte")
                .endObject()
                .startObject("coursePrice")
                .field("type", "scaled_float")
                .field("scaling_factor", 1)
                .endObject()
                .startObject("courseCover")
                .field("type", "text")
                .endObject()
                .startObject("courseId")
                .field("type", "long")
                .endObject()
                .startObject("authAppid")
                .field("type", "text")
                .endObject()
                .startObject("courseCoverKey")
                .field("type", "text")
                .endObject()
                .startObject("courseCoverAddress")
                .field("type", "text")
                .endObject()
                .startObject("coursePid")
                .field("type", "integer")
                .endObject()
                .startObject("courseAuthor")
                .field("type", "text")
                .field("analyzer", "ik_max_word")
                .endObject()
                .startObject("courseStatus")
                .field("type", "byte")
                .endObject()
                .startObject("courseTime")
                .field("type", "long")
                .endObject()
                .endObject()
                .endObject();

//        cib.addMapping("courses", builder);
//        CreateIndexResponse res=cib.execute().actionGet();

        transportClient.admin().indices().prepareCreate("vpro").addMapping("courses", builder).get();

        closeClient(transportClient);
    }
    public static void testBulk() throws Exception {
        TransportClient transportClient = getClient();
        BulkRequestBuilder bulkRequest = transportClient.prepareBulk();
        String json = "{\"courseDiscountPrice\":\"-1.0\",\"authIp\":\"\",\"courseCoverIsused\":\"0\",\"authToken\":\"\",\"authTime\":\"\",\"authNkname\":\"\",\"courseCoverId\":\"193001\",\"authId\":\"292\",\"authRolesId\":\"\",\"courseTitle\":\"3D次世代游戏机械制作教程\",\"courseCoverUptime\":\"1511395564\",\"authAppkey\":\"\",\"courseCoverIsuploaded\":\"1\",\"coursePrice\":\"0.0\",\"courseCover\":\"193001\",\"courseId\":\"193001\",\"authAppid\":\"游艺网game798\",\"courseCoverKey\":\"193001.jpg\",\"courseCoverAddress\":\"http://ozg76yopg.bkt.clouddn.com/193001.jpg\",\"coursePid\":\"133\",\"courseAuthor\":\"292\",\"courseStatus\":\"1\",\"courseTime\":\"1519970945\"}";
        bulkRequest.add(transportClient.prepareIndex("vpro","courses").setSource(JSON.parseObject(json, Map.class)));
        BulkResponse bulkResponse = bulkRequest.get();
        if (bulkResponse.hasFailures())
        {
            System.out.println("导入失败");
        }
        closeClient(transportClient);
    }
    public static SearchResult simpleQuery(TransportClient client, Map<String, String> criteria, Map<String, Object> range) {
        SearchResult searchResult = new SearchResult();
        List<Map<String, Object>> queries = new ArrayList<Map<String, Object>>();
        // 4中方法，最精确，性能最差的方法。
        SearchRequestBuilder searchRequestBuilder = client.prepareSearch().setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
        searchRequestBuilder.setQuery(QueryBuilders.matchQuery(criteria.get("searchKey"), criteria.get("searchValue")));
        SearchResponse searchResponse = searchRequestBuilder
                .setFrom((Integer) range.get("start"))
                .setSize((Integer) range.get("size"))
                .execute().actionGet();
        System.out.println(searchResponse.getHits().getTotalHits());
        SearchHits searchHits = searchResponse.getHits();
        for(SearchHit hit : searchResponse.getHits()) {
            System.out.println(hit.getSourceAsString());
            Map<String, Object> res = hit.getSourceAsMap();
            queries.add(res);
        }
        searchResult.setQueries(queries);
        searchResult.setCount(searchResponse.getHits().getTotalHits());
        return searchResult;
    }
    public static SearchResult boolQuery(RestHighLevelClient client, ESQuery esQuery, Map<String, Object> range) throws IOException {
        List<Map<String, Object>> queries = new ArrayList<Map<String, Object>>();
        // 创建search
        SearchRequest searchRequest = new SearchRequest("vpro");
        // 创建builder
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // boolquery
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilderGenerator(boolQueryBuilder, esQuery.getMust(), "must");
        boolQueryBuilderGenerator(boolQueryBuilder, esQuery.getFilter(), "filter");
        // range
        RangeQueryBuilder rangeQueryBuilder = dateRange(range);
        if (rangeQueryBuilder != null) boolQueryBuilder.filter(rangeQueryBuilder);
        // filter
        String[] includeFields = new String[] {
                "courseDiscountPrice",
                "courseCoverAddress",
                "courseCoverId",
                "coursePid",
                "authRolesId",
                "courseTitle",
                "coursePrice",
                "courseStatus",
                "courseId",
                "authAppid",
                "courseTime"
        };
        // 需要引入的字段和排除的字段
        searchSourceBuilder.fetchSource(includeFields, new String[] {});
        // 组合query
        searchSourceBuilder.query(boolQueryBuilder);
        // 排序
        searchSourceBuilder.sort(new ScoreSortBuilder().order(SortOrder.DESC));
        // 限定范围
        searchSourceBuilder.from(Integer.parseInt(String.valueOf(range.get("start")))).size(Integer.parseInt(String.valueOf(range.get("size"))));
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println(searchResponse.getHits().getTotalHits());
        for(SearchHit hit : searchResponse.getHits()) {
//            System.out.println(hit.getSourceAsString());
            queries.add(hit.getSourceAsMap());
        }
        SearchResult searchResult = new SearchResult();
        searchResult.setQueries(queries);
        searchResult.setCount(searchResponse.getHits().getTotalHits());
        return searchResult;
    }
    public static BoolQueryBuilder boolQueryBuilderGenerator(BoolQueryBuilder boolQueryBuilder, Map<String, Object> map, String type) {
        if (map.size() == 0) return boolQueryBuilder;
        Iterator iter = map.entrySet().iterator();
        while(iter.hasNext()) {
            Map.Entry entry = (Map.Entry)iter.next();
            switch(type) {
                case "must":
                    System.out.println(entry.getValue());
                    boolQueryBuilder.must(QueryBuilders.matchQuery((String)entry.getKey(), entry.getValue()));
                    break;
                case "mustNot":
                    boolQueryBuilder.mustNot(QueryBuilders.matchQuery((String)entry.getKey(), entry.getValue()));
                    break;
                case "should":
                    boolQueryBuilder.should(QueryBuilders.matchQuery((String)entry.getKey(), entry.getValue()));
                    break;
                case "filter":
                    boolQueryBuilder.filter(QueryBuilders.termsQuery((String)entry.getKey(), (List<Integer>)entry.getValue()));
                    break;
            }
        }
//        System.out.println(boolQueryBuilder.toString());
        return boolQueryBuilder;
    }
    public static RangeQueryBuilder dateRange(Map<String, Object> range) {
        // range
        Long startDate = range.get("startDate") != null ? Long.parseLong(String.valueOf(range.get("startDate"))) : -1;
        Long endDate = range.get("endDate") != null ? Long.parseLong(String.valueOf(range.get("endDate"))) : -1;
        if ( startDate != -1 || endDate != -1) {
            RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("courseTime");
            if (startDate != -1) {
                rangeQueryBuilder.gte(startDate);
            }
            if (endDate != -1) {
                rangeQueryBuilder.lte(endDate);
            }
            return rangeQueryBuilder;
        }
        return null;
    }
    public static String ImportBulk(String path) throws Exception {
        TransportClient transportClient = getClient();
        FileReader fr = null;
        BufferedReader bfr = null;
        String line = null;
        try {
            File file = new File(path);
            fr = new FileReader(file);
            bfr = new BufferedReader(fr);
            // 准备批量导入
            BulkRequestBuilder bulkRequest = transportClient.prepareBulk();
            int count = 0;
            while((line = bfr.readLine()) != null) {
                Map<String, String> courseDetail = JSON.parseObject(line, Map.class);
                // 添加文档
                bulkRequest.add(transportClient.prepareIndex("vpro","courses").setId(courseDetail.get("courseId")).setSource(courseDetail));
                if (count % 100 == 0) {
                    // 批量加入
                    bulkRequest.execute().actionGet();
                    // 清空了bulk内容，否则会重复提交
                    bulkRequest = transportClient.prepareBulk();
                    System.out.println("提交了" + count);
                }
                count++;
            }
            // 最后不满10条的信息加入
            bulkRequest.execute().actionGet();
            return "导入成功!!!";
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                bfr.close();
                fr.close();
                closeClient(transportClient);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}

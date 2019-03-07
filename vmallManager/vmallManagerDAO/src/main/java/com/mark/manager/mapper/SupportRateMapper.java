package com.mark.manager.mapper;

import com.mark.manager.pojo.VproCommentSupportRate;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SupportRateMapper {
    List<VproCommentSupportRate> getSupportRateByLimit(
            @Param("limit") Map<String, Integer> limit
    );
    List<VproCommentSupportRate> batchUpdateSupportRate(
            @Param("update") List<Map<String, Integer>> update,
            @Param("updateType") String updateType
    );
}

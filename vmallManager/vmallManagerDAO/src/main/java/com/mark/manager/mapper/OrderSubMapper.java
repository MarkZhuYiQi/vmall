package com.mark.manager.mapper;

import com.mark.manager.pojo.VproOrderSub;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderSubMapper {
    Integer batchInsertSubOrder(@Param("subs") List<VproOrderSub> subs);
}

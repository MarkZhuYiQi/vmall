package com.mark.manager.mapper;

import com.mark.manager.pojo.VproOrderLogs;
import com.mark.manager.pojo.VproOrderLogsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VproOrderLogsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_order_logs
     *
     * @mbg.generated Wed Feb 27 17:28:28 CST 2019
     */
    long countByExample(VproOrderLogsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_order_logs
     *
     * @mbg.generated Wed Feb 27 17:28:28 CST 2019
     */
    int deleteByExample(VproOrderLogsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_order_logs
     *
     * @mbg.generated Wed Feb 27 17:28:28 CST 2019
     */
    int deleteByPrimaryKey(Integer logId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_order_logs
     *
     * @mbg.generated Wed Feb 27 17:28:28 CST 2019
     */
    int insert(VproOrderLogs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_order_logs
     *
     * @mbg.generated Wed Feb 27 17:28:28 CST 2019
     */
    int insertSelective(VproOrderLogs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_order_logs
     *
     * @mbg.generated Wed Feb 27 17:28:28 CST 2019
     */
    List<VproOrderLogs> selectByExampleWithBLOBs(VproOrderLogsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_order_logs
     *
     * @mbg.generated Wed Feb 27 17:28:28 CST 2019
     */
    List<VproOrderLogs> selectByExample(VproOrderLogsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_order_logs
     *
     * @mbg.generated Wed Feb 27 17:28:28 CST 2019
     */
    VproOrderLogs selectByPrimaryKey(Integer logId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_order_logs
     *
     * @mbg.generated Wed Feb 27 17:28:28 CST 2019
     */
    int updateByExampleSelective(@Param("record") VproOrderLogs record, @Param("example") VproOrderLogsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_order_logs
     *
     * @mbg.generated Wed Feb 27 17:28:28 CST 2019
     */
    int updateByExampleWithBLOBs(@Param("record") VproOrderLogs record, @Param("example") VproOrderLogsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_order_logs
     *
     * @mbg.generated Wed Feb 27 17:28:28 CST 2019
     */
    int updateByExample(@Param("record") VproOrderLogs record, @Param("example") VproOrderLogsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_order_logs
     *
     * @mbg.generated Wed Feb 27 17:28:28 CST 2019
     */
    int updateByPrimaryKeySelective(VproOrderLogs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_order_logs
     *
     * @mbg.generated Wed Feb 27 17:28:28 CST 2019
     */
    int updateByPrimaryKeyWithBLOBs(VproOrderLogs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_order_logs
     *
     * @mbg.generated Wed Feb 27 17:28:28 CST 2019
     */
    int updateByPrimaryKey(VproOrderLogs record);
}
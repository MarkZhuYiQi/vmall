package com.mark.manager.mapper;

import com.mark.manager.pojo.VproCartDetail;
import com.mark.manager.pojo.VproCartDetailExample;
import com.mark.manager.pojo.VproCartDetailKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VproCartDetailMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_cart_detail
     *
     * @mbg.generated Thu Feb 21 13:25:44 CST 2019
     */
    long countByExample(VproCartDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_cart_detail
     *
     * @mbg.generated Thu Feb 21 13:25:44 CST 2019
     */
    int deleteByExample(VproCartDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_cart_detail
     *
     * @mbg.generated Thu Feb 21 13:25:44 CST 2019
     */
    int deleteByPrimaryKey(VproCartDetailKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_cart_detail
     *
     * @mbg.generated Thu Feb 21 13:25:44 CST 2019
     */
    int insert(VproCartDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_cart_detail
     *
     * @mbg.generated Thu Feb 21 13:25:44 CST 2019
     */
    int insertSelective(VproCartDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_cart_detail
     *
     * @mbg.generated Thu Feb 21 13:25:44 CST 2019
     */
    List<VproCartDetail> selectByExample(VproCartDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_cart_detail
     *
     * @mbg.generated Thu Feb 21 13:25:44 CST 2019
     */
    VproCartDetail selectByPrimaryKey(VproCartDetailKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_cart_detail
     *
     * @mbg.generated Thu Feb 21 13:25:44 CST 2019
     */
    int updateByExampleSelective(@Param("record") VproCartDetail record, @Param("example") VproCartDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_cart_detail
     *
     * @mbg.generated Thu Feb 21 13:25:44 CST 2019
     */
    int updateByExample(@Param("record") VproCartDetail record, @Param("example") VproCartDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_cart_detail
     *
     * @mbg.generated Thu Feb 21 13:25:44 CST 2019
     */
    int updateByPrimaryKeySelective(VproCartDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_cart_detail
     *
     * @mbg.generated Thu Feb 21 13:25:44 CST 2019
     */
    int updateByPrimaryKey(VproCartDetail record);
}
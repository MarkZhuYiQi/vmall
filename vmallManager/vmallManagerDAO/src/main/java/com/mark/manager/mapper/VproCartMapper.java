package com.mark.manager.mapper;

import com.mark.manager.pojo.VproCart;
import com.mark.manager.pojo.VproCartExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VproCartMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_cart
     *
     * @mbg.generated Thu Feb 21 13:25:44 CST 2019
     */
    long countByExample(VproCartExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_cart
     *
     * @mbg.generated Thu Feb 21 13:25:44 CST 2019
     */
    int deleteByExample(VproCartExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_cart
     *
     * @mbg.generated Thu Feb 21 13:25:44 CST 2019
     */
    int deleteByPrimaryKey(Long cartId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_cart
     *
     * @mbg.generated Thu Feb 21 13:25:44 CST 2019
     */
    int insert(VproCart record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_cart
     *
     * @mbg.generated Thu Feb 21 13:25:44 CST 2019
     */
    int insertSelective(VproCart record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_cart
     *
     * @mbg.generated Thu Feb 21 13:25:44 CST 2019
     */
    List<VproCart> selectByExample(VproCartExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_cart
     *
     * @mbg.generated Thu Feb 21 13:25:44 CST 2019
     */
    VproCart selectByPrimaryKey(Long cartId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_cart
     *
     * @mbg.generated Thu Feb 21 13:25:44 CST 2019
     */
    int updateByExampleSelective(@Param("record") VproCart record, @Param("example") VproCartExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_cart
     *
     * @mbg.generated Thu Feb 21 13:25:44 CST 2019
     */
    int updateByExample(@Param("record") VproCart record, @Param("example") VproCartExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_cart
     *
     * @mbg.generated Thu Feb 21 13:25:44 CST 2019
     */
    int updateByPrimaryKeySelective(VproCart record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_cart
     *
     * @mbg.generated Thu Feb 21 13:25:44 CST 2019
     */
    int updateByPrimaryKey(VproCart record);
}
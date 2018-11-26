package com.mark.manager.mapper;

import com.mark.manager.pojo.VproCoursesCover;
import com.mark.manager.pojo.VproCoursesCoverExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VproCoursesCoverMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_courses_cover
     *
     * @mbg.generated Fri Oct 26 15:02:11 CST 2018
     */
    long countByExample(VproCoursesCoverExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_courses_cover
     *
     * @mbg.generated Fri Oct 26 15:02:11 CST 2018
     */
    int deleteByExample(VproCoursesCoverExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_courses_cover
     *
     * @mbg.generated Fri Oct 26 15:02:11 CST 2018
     */
    int deleteByPrimaryKey(Integer courseCoverId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_courses_cover
     *
     * @mbg.generated Fri Oct 26 15:02:11 CST 2018
     */
    int insert(VproCoursesCover record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_courses_cover
     *
     * @mbg.generated Fri Oct 26 15:02:11 CST 2018
     */
    int insertSelective(VproCoursesCover record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_courses_cover
     *
     * @mbg.generated Fri Oct 26 15:02:11 CST 2018
     */
    List<VproCoursesCover> selectByExample(VproCoursesCoverExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_courses_cover
     *
     * @mbg.generated Fri Oct 26 15:02:11 CST 2018
     */
    VproCoursesCover selectByPrimaryKey(Integer courseCoverId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_courses_cover
     *
     * @mbg.generated Fri Oct 26 15:02:11 CST 2018
     */
    int updateByExampleSelective(@Param("record") VproCoursesCover record, @Param("example") VproCoursesCoverExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_courses_cover
     *
     * @mbg.generated Fri Oct 26 15:02:11 CST 2018
     */
    int updateByExample(@Param("record") VproCoursesCover record, @Param("example") VproCoursesCoverExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_courses_cover
     *
     * @mbg.generated Fri Oct 26 15:02:11 CST 2018
     */
    int updateByPrimaryKeySelective(VproCoursesCover record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_courses_cover
     *
     * @mbg.generated Fri Oct 26 15:02:11 CST 2018
     */
    int updateByPrimaryKey(VproCoursesCover record);
}
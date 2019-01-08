package com.mark.manager.mapper;

import com.mark.manager.pojo.VproCourses;
import com.mark.manager.pojo.VproCoursesExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VproCoursesMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_courses
     *
     * @mbg.generated Tue Jan 08 13:35:00 CST 2019
     */
    long countByExample(VproCoursesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_courses
     *
     * @mbg.generated Tue Jan 08 13:35:00 CST 2019
     */
    int deleteByExample(VproCoursesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_courses
     *
     * @mbg.generated Tue Jan 08 13:35:00 CST 2019
     */
    int deleteByPrimaryKey(Integer courseId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_courses
     *
     * @mbg.generated Tue Jan 08 13:35:00 CST 2019
     */
    int insert(VproCourses record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_courses
     *
     * @mbg.generated Tue Jan 08 13:35:00 CST 2019
     */
    int insertSelective(VproCourses record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_courses
     *
     * @mbg.generated Tue Jan 08 13:35:00 CST 2019
     */
    List<VproCourses> selectByExample(VproCoursesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_courses
     *
     * @mbg.generated Tue Jan 08 13:35:00 CST 2019
     */
    VproCourses selectByPrimaryKey(Integer courseId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_courses
     *
     * @mbg.generated Tue Jan 08 13:35:00 CST 2019
     */
    int updateByExampleSelective(@Param("record") VproCourses record, @Param("example") VproCoursesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_courses
     *
     * @mbg.generated Tue Jan 08 13:35:00 CST 2019
     */
    int updateByExample(@Param("record") VproCourses record, @Param("example") VproCoursesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_courses
     *
     * @mbg.generated Tue Jan 08 13:35:00 CST 2019
     */
    int updateByPrimaryKeySelective(VproCourses record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_courses
     *
     * @mbg.generated Tue Jan 08 13:35:00 CST 2019
     */
    int updateByPrimaryKey(VproCourses record);
}
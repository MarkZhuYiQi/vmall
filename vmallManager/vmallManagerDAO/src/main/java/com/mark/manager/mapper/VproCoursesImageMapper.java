package com.mark.manager.mapper;

import com.mark.manager.pojo.VproCoursesImage;
import com.mark.manager.pojo.VproCoursesImageExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VproCoursesImageMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_courses_image
     *
     * @mbg.generated Sun Jan 13 11:45:07 CST 2019
     */
    long countByExample(VproCoursesImageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_courses_image
     *
     * @mbg.generated Sun Jan 13 11:45:07 CST 2019
     */
    int deleteByExample(VproCoursesImageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_courses_image
     *
     * @mbg.generated Sun Jan 13 11:45:07 CST 2019
     */
    int deleteByPrimaryKey(Integer imageId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_courses_image
     *
     * @mbg.generated Sun Jan 13 11:45:07 CST 2019
     */
    int insert(VproCoursesImage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_courses_image
     *
     * @mbg.generated Sun Jan 13 11:45:07 CST 2019
     */
    int insertSelective(VproCoursesImage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_courses_image
     *
     * @mbg.generated Sun Jan 13 11:45:07 CST 2019
     */
    List<VproCoursesImage> selectByExampleWithBLOBs(VproCoursesImageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_courses_image
     *
     * @mbg.generated Sun Jan 13 11:45:07 CST 2019
     */
    List<VproCoursesImage> selectByExample(VproCoursesImageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_courses_image
     *
     * @mbg.generated Sun Jan 13 11:45:07 CST 2019
     */
    VproCoursesImage selectByPrimaryKey(Integer imageId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_courses_image
     *
     * @mbg.generated Sun Jan 13 11:45:07 CST 2019
     */
    int updateByExampleSelective(@Param("record") VproCoursesImage record, @Param("example") VproCoursesImageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_courses_image
     *
     * @mbg.generated Sun Jan 13 11:45:07 CST 2019
     */
    int updateByExampleWithBLOBs(@Param("record") VproCoursesImage record, @Param("example") VproCoursesImageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_courses_image
     *
     * @mbg.generated Sun Jan 13 11:45:07 CST 2019
     */
    int updateByExample(@Param("record") VproCoursesImage record, @Param("example") VproCoursesImageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_courses_image
     *
     * @mbg.generated Sun Jan 13 11:45:07 CST 2019
     */
    int updateByPrimaryKeySelective(VproCoursesImage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_courses_image
     *
     * @mbg.generated Sun Jan 13 11:45:07 CST 2019
     */
    int updateByPrimaryKeyWithBLOBs(VproCoursesImage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_courses_image
     *
     * @mbg.generated Sun Jan 13 11:45:07 CST 2019
     */
    int updateByPrimaryKey(VproCoursesImage record);
}
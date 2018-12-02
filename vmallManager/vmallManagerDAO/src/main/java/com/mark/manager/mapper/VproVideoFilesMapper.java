package com.mark.manager.mapper;

import com.mark.manager.pojo.VproVideoFiles;
import com.mark.manager.pojo.VproVideoFilesExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VproVideoFilesMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_video_files
     *
     * @mbg.generated Sat Dec 01 12:09:12 CST 2018
     */
    long countByExample(VproVideoFilesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_video_files
     *
     * @mbg.generated Sat Dec 01 12:09:12 CST 2018
     */
    int deleteByExample(VproVideoFilesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_video_files
     *
     * @mbg.generated Sat Dec 01 12:09:12 CST 2018
     */
    int deleteByPrimaryKey(Integer videoFileId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_video_files
     *
     * @mbg.generated Sat Dec 01 12:09:12 CST 2018
     */
    int insert(VproVideoFiles record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_video_files
     *
     * @mbg.generated Sat Dec 01 12:09:12 CST 2018
     */
    int insertSelective(VproVideoFiles record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_video_files
     *
     * @mbg.generated Sat Dec 01 12:09:12 CST 2018
     */
    List<VproVideoFiles> selectByExample(VproVideoFilesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_video_files
     *
     * @mbg.generated Sat Dec 01 12:09:12 CST 2018
     */
    VproVideoFiles selectByPrimaryKey(Integer videoFileId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_video_files
     *
     * @mbg.generated Sat Dec 01 12:09:12 CST 2018
     */
    int updateByExampleSelective(@Param("record") VproVideoFiles record, @Param("example") VproVideoFilesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_video_files
     *
     * @mbg.generated Sat Dec 01 12:09:12 CST 2018
     */
    int updateByExample(@Param("record") VproVideoFiles record, @Param("example") VproVideoFilesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_video_files
     *
     * @mbg.generated Sat Dec 01 12:09:12 CST 2018
     */
    int updateByPrimaryKeySelective(VproVideoFiles record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_video_files
     *
     * @mbg.generated Sat Dec 01 12:09:12 CST 2018
     */
    int updateByPrimaryKey(VproVideoFiles record);
}
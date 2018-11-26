package com.mark.manager.pojo;

import java.io.Serializable;

public class VproCoursesCover implements Serializable {
    @Override
    public String toString() {
        return "VproCoursesCover{" +
                "courseCoverId=" + courseCoverId +
                ", courseCoverKey='" + courseCoverKey + '\'' +
                ", courseCoverAddress='" + courseCoverAddress + '\'' +
                ", courseCoverUptime='" + courseCoverUptime + '\'' +
                ", courseCoverIsuploaded=" + courseCoverIsuploaded +
                ", courseCoverIsused=" + courseCoverIsused +
                '}';
    }

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column vpro_courses_cover.course_cover_id
     *
     * @mbg.generated Fri Oct 26 15:02:11 CST 2018
     */
    private Integer courseCoverId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column vpro_courses_cover.course_cover_key
     *
     * @mbg.generated Fri Oct 26 15:02:11 CST 2018
     */
    private String courseCoverKey;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column vpro_courses_cover.course_cover_address
     *
     * @mbg.generated Fri Oct 26 15:02:11 CST 2018
     */
    private String courseCoverAddress;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column vpro_courses_cover.course_cover_uptime
     *
     * @mbg.generated Fri Oct 26 15:02:11 CST 2018
     */
    private String courseCoverUptime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column vpro_courses_cover.course_cover_isuploaded
     *
     * @mbg.generated Fri Oct 26 15:02:11 CST 2018
     */
    private Byte courseCoverIsuploaded;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column vpro_courses_cover.course_cover_isused
     *
     * @mbg.generated Fri Oct 26 15:02:11 CST 2018
     */
    private Byte courseCoverIsused;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column vpro_courses_cover.course_cover_id
     *
     * @return the value of vpro_courses_cover.course_cover_id
     *
     * @mbg.generated Fri Oct 26 15:02:11 CST 2018
     */
    public Integer getCourseCoverId() {
        return courseCoverId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column vpro_courses_cover.course_cover_id
     *
     * @param courseCoverId the value for vpro_courses_cover.course_cover_id
     *
     * @mbg.generated Fri Oct 26 15:02:11 CST 2018
     */
    public void setCourseCoverId(Integer courseCoverId) {
        this.courseCoverId = courseCoverId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column vpro_courses_cover.course_cover_key
     *
     * @return the value of vpro_courses_cover.course_cover_key
     *
     * @mbg.generated Fri Oct 26 15:02:11 CST 2018
     */
    public String getCourseCoverKey() {
        return courseCoverKey;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column vpro_courses_cover.course_cover_key
     *
     * @param courseCoverKey the value for vpro_courses_cover.course_cover_key
     *
     * @mbg.generated Fri Oct 26 15:02:11 CST 2018
     */
    public void setCourseCoverKey(String courseCoverKey) {
        this.courseCoverKey = courseCoverKey;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column vpro_courses_cover.course_cover_address
     *
     * @return the value of vpro_courses_cover.course_cover_address
     *
     * @mbg.generated Fri Oct 26 15:02:11 CST 2018
     */
    public String getCourseCoverAddress() {
        return courseCoverAddress;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column vpro_courses_cover.course_cover_address
     *
     * @param courseCoverAddress the value for vpro_courses_cover.course_cover_address
     *
     * @mbg.generated Fri Oct 26 15:02:11 CST 2018
     */
    public void setCourseCoverAddress(String courseCoverAddress) {
        this.courseCoverAddress = courseCoverAddress;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column vpro_courses_cover.course_cover_uptime
     *
     * @return the value of vpro_courses_cover.course_cover_uptime
     *
     * @mbg.generated Fri Oct 26 15:02:11 CST 2018
     */
    public String getCourseCoverUptime() {
        return courseCoverUptime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column vpro_courses_cover.course_cover_uptime
     *
     * @param courseCoverUptime the value for vpro_courses_cover.course_cover_uptime
     *
     * @mbg.generated Fri Oct 26 15:02:11 CST 2018
     */
    public void setCourseCoverUptime(String courseCoverUptime) {
        this.courseCoverUptime = courseCoverUptime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column vpro_courses_cover.course_cover_isuploaded
     *
     * @return the value of vpro_courses_cover.course_cover_isuploaded
     *
     * @mbg.generated Fri Oct 26 15:02:11 CST 2018
     */
    public Byte getCourseCoverIsuploaded() {
        return courseCoverIsuploaded;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column vpro_courses_cover.course_cover_isuploaded
     *
     * @param courseCoverIsuploaded the value for vpro_courses_cover.course_cover_isuploaded
     *
     * @mbg.generated Fri Oct 26 15:02:11 CST 2018
     */
    public void setCourseCoverIsuploaded(Byte courseCoverIsuploaded) {
        this.courseCoverIsuploaded = courseCoverIsuploaded;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column vpro_courses_cover.course_cover_isused
     *
     * @return the value of vpro_courses_cover.course_cover_isused
     *
     * @mbg.generated Fri Oct 26 15:02:11 CST 2018
     */
    public Byte getCourseCoverIsused() {
        return courseCoverIsused;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column vpro_courses_cover.course_cover_isused
     *
     * @param courseCoverIsused the value for vpro_courses_cover.course_cover_isused
     *
     * @mbg.generated Fri Oct 26 15:02:11 CST 2018
     */
    public void setCourseCoverIsused(Byte courseCoverIsused) {
        this.courseCoverIsused = courseCoverIsused;
    }
}
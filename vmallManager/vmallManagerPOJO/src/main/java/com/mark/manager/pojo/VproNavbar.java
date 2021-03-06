package com.mark.manager.pojo;

import java.io.Serializable;

public class VproNavbar implements Serializable {

    @Override
    public String toString() {
        return "VproNavbar{" +
                "navId=" + navId +
                ", navText='" + navText + '\'' +
                ", navUrl='" + navUrl + '\'' +
                ", navPid=" + navPid +
                ", navNickname='" + navNickname + '\'' +
                ", navStatus=" + navStatus +
                ", navIsParent=" + navIsParent +
                '}';
    }

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column vpro_navbar.nav_id
     *
     * @mbg.generated Wed Oct 17 23:12:35 CST 2018
     */
    private Integer navId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column vpro_navbar.nav_text
     *
     * @mbg.generated Wed Oct 17 23:12:35 CST 2018
     */
    private String navText;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column vpro_navbar.nav_url
     *
     * @mbg.generated Wed Oct 17 23:12:35 CST 2018
     */
    private String navUrl;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column vpro_navbar.nav_pid
     *
     * @mbg.generated Wed Oct 17 23:12:35 CST 2018
     */
    private Integer navPid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column vpro_navbar.nav_nickname
     *
     * @mbg.generated Wed Oct 17 23:12:35 CST 2018
     */
    private String navNickname;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column vpro_navbar.nav_status
     *
     * @mbg.generated Wed Oct 17 23:12:35 CST 2018
     */
    private Boolean navStatus;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column vpro_navbar.nav_is_parent
     *
     * @mbg.generated Wed Oct 17 23:12:35 CST 2018
     */
    private Boolean navIsParent;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column vpro_navbar.nav_id
     *
     * @return the value of vpro_navbar.nav_id
     *
     * @mbg.generated Wed Oct 17 23:12:35 CST 2018
     */
    public Integer getNavId() {
        return navId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column vpro_navbar.nav_id
     *
     * @param navId the value for vpro_navbar.nav_id
     *
     * @mbg.generated Wed Oct 17 23:12:35 CST 2018
     */
    public void setNavId(Integer navId) {
        this.navId = navId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column vpro_navbar.nav_text
     *
     * @return the value of vpro_navbar.nav_text
     *
     * @mbg.generated Wed Oct 17 23:12:35 CST 2018
     */
    public String getNavText() {
        return navText;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column vpro_navbar.nav_text
     *
     * @param navText the value for vpro_navbar.nav_text
     *
     * @mbg.generated Wed Oct 17 23:12:35 CST 2018
     */
    public void setNavText(String navText) {
        this.navText = navText;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column vpro_navbar.nav_url
     *
     * @return the value of vpro_navbar.nav_url
     *
     * @mbg.generated Wed Oct 17 23:12:35 CST 2018
     */
    public String getNavUrl() {
        return navUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column vpro_navbar.nav_url
     *
     * @param navUrl the value for vpro_navbar.nav_url
     *
     * @mbg.generated Wed Oct 17 23:12:35 CST 2018
     */
    public void setNavUrl(String navUrl) {
        this.navUrl = navUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column vpro_navbar.nav_pid
     *
     * @return the value of vpro_navbar.nav_pid
     *
     * @mbg.generated Wed Oct 17 23:12:35 CST 2018
     */
    public Integer getNavPid() {
        return navPid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column vpro_navbar.nav_pid
     *
     * @param navPid the value for vpro_navbar.nav_pid
     *
     * @mbg.generated Wed Oct 17 23:12:35 CST 2018
     */
    public void setNavPid(Integer navPid) {
        this.navPid = navPid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column vpro_navbar.nav_nickname
     *
     * @return the value of vpro_navbar.nav_nickname
     *
     * @mbg.generated Wed Oct 17 23:12:35 CST 2018
     */
    public String getNavNickname() {
        return navNickname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column vpro_navbar.nav_nickname
     *
     * @param navNickname the value for vpro_navbar.nav_nickname
     *
     * @mbg.generated Wed Oct 17 23:12:35 CST 2018
     */
    public void setNavNickname(String navNickname) {
        this.navNickname = navNickname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column vpro_navbar.nav_status
     *
     * @return the value of vpro_navbar.nav_status
     *
     * @mbg.generated Wed Oct 17 23:12:35 CST 2018
     */
    public Boolean getNavStatus() {
        return navStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column vpro_navbar.nav_status
     *
     * @param navStatus the value for vpro_navbar.nav_status
     *
     * @mbg.generated Wed Oct 17 23:12:35 CST 2018
     */
    public void setNavStatus(Boolean navStatus) {
        this.navStatus = navStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column vpro_navbar.nav_is_parent
     *
     * @return the value of vpro_navbar.nav_is_parent
     *
     * @mbg.generated Wed Oct 17 23:12:35 CST 2018
     */
    public Boolean getNavIsParent() {
        return navIsParent;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column vpro_navbar.nav_is_parent
     *
     * @param navIsParent the value for vpro_navbar.nav_is_parent
     *
     * @mbg.generated Wed Oct 17 23:12:35 CST 2018
     */
    public void setNavIsParent(Boolean navIsParent) {
        this.navIsParent = navIsParent;
    }
}
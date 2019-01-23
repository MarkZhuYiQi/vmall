package com.mark.common.pojo;

import java.io.Serializable;
import java.util.List;

public class CategoryNode implements Serializable {

    private Integer navId;

    private String navText;

    private String navUrl;

    private Integer navPid;

    private String navNickname;

    private Boolean navStatus;

    private Boolean navIsParent;

    private List<CategoryNode> subNav;

    public List<CategoryNode> getSubNav() {
        return subNav;
    }

    public void setSubNav(List<CategoryNode> subNav) {
        this.subNav = subNav;
    }

    public CategoryNode() {
    }

    public Integer getNavId() {
        return navId;
    }

    public void setNavId(Integer navId) {
        this.navId = navId;
    }

    public String getNavText() {
        return navText;
    }

    public void setNavText(String navText) {
        this.navText = navText;
    }

    public String getNavUrl() {
        return navUrl;
    }

    public void setNavUrl(String navUrl) {
        this.navUrl = navUrl;
    }

    public Integer getNavPid() {
        return navPid;
    }

    public void setNavPid(Integer navPid) {
        this.navPid = navPid;
    }

    public String getNavNickname() {
        return navNickname;
    }

    public void setNavNickname(String navNickname) {
        this.navNickname = navNickname;
    }

    public Boolean getNavStatus() {
        return navStatus;
    }

    public void setNavStatus(Boolean navStatus) {
        this.navStatus = navStatus;
    }

    public Boolean getNavIsParent() {
        return navIsParent;
    }

    public void setNavIsParent(Boolean navIsParent) {
        this.navIsParent = navIsParent;
    }

    @Override
    public String toString() {
        return "CategoryNode{" +
                "navId=" + navId +
                ", navText='" + navText + '\'' +
                ", navUrl='" + navUrl + '\'' +
                ", navPid=" + navPid +
                ", navNickname='" + navNickname + '\'' +
                ", navStatus=" + navStatus +
                ", navIsParent=" + navIsParent +
                ", subNav=" + subNav +
                '}';
    }
}

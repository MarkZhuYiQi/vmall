package com.mark.manager.pojo;

public class Navbar {
    private Long navId;
    private String navText;
    private String navUrl;
    private Integer navPid;
    private String navNickname;
    private Integer navStatus;
    private Integer navIsParent;

    public Navbar() {
    }

    public Long getNavId() {
        return navId;
    }

    public void setNavId(Long navId) {
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

    public Integer getNavStatus() {
        return navStatus;
    }

    public void setNavStatus(Integer navStatus) {
        this.navStatus = navStatus;
    }

    public Integer getNavIsParent() {
        return navIsParent;
    }

    public void setNavIsParent(Integer navIsParent) {
        this.navIsParent = navIsParent;
    }
}

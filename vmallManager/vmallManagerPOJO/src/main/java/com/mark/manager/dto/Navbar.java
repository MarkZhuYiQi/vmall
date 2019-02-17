package com.mark.manager.dto;

import com.mark.manager.pojo.VproNavbar;

import java.io.Serializable;

public class Navbar implements Serializable {
    private Integer navId;
    private String navText;
    private String navUrl;
    private Integer navPid;
    private String navNickname;
    private Boolean navStatus;
    private Boolean navIsParent;
    private Navbar navbar;

    public Navbar() {
    }
    public Navbar(VproNavbar vproNavbar) {
        setNavId(vproNavbar.getNavId());
        setNavText(vproNavbar.getNavText());
        setNavUrl(vproNavbar.getNavUrl());
        setNavPid(vproNavbar.getNavPid());
        setNavNickname(vproNavbar.getNavNickname());
        setNavIsParent(vproNavbar.getNavIsParent());
    }

    @Override
    public String toString() {
        return "Navbar{" +
                "navId=" + navId +
                ", navText='" + navText + '\'' +
                ", navUrl='" + navUrl + '\'' +
                ", navPid=" + navPid +
                ", navNickname='" + navNickname + '\'' +
                ", navStatus=" + navStatus +
                ", navIsParent=" + navIsParent +
                ", navbar=" + navbar +
                '}';
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

    public Navbar getNavbar() {
        return navbar;
    }

    public void setNavbar(Navbar navbar) {
        this.navbar = navbar;
    }
}

package com.mark.manager.dto;

import com.mark.common.validateGroup.GetImage;
import com.mark.common.validateGroup.InsertImage;
import com.mark.common.validateGroup.UpdateImageUsage;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class DetailImage implements Serializable {
    @NotNull(groups = {UpdateImageUsage.class})
    private Integer imageId;
    @NotBlank(groups = {InsertImage.class})
    private String imgUrl;
    @NotNull(groups = {UpdateImageUsage.class})
    private Integer imageUsedLocation;
    @NotNull(groups = {InsertImage.class, UpdateImageUsage.class, GetImage.class})
    private Integer imageAuthor;
    // 0是删去使用id，1是添加使用courseId
    @NotNull(groups = {UpdateImageUsage.class})
    @Digits(integer = 1, fraction = 0,groups = UpdateImageUsage.class)
    private Integer type;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getImageUsedLocation() {
        return imageUsedLocation;
    }

    public void setImageUsedLocation(Integer imageUsedLocation) {
        this.imageUsedLocation = imageUsedLocation;
    }

    public Integer getImageAuthor() {
        return imageAuthor;
    }

    public void setImageAuthor(Integer imageAuthor) {
        this.imageAuthor = imageAuthor;
    }

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "DetailImage{" +
                "imgUrl='" + imgUrl + '\'' +
                ", imageUsedLocation=" + imageUsedLocation +
                ", imageAuthor=" + imageAuthor +
                '}';
    }

    public DetailImage() {
    }
}

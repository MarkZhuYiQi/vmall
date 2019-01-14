package com.mark.manager.service;

import com.mark.common.exception.ImageException;
import com.mark.manager.dto.DetailImage;
import com.mark.manager.pojo.VproCoursesCover;
import com.mark.manager.pojo.VproCoursesImage;

import java.util.List;

public interface ImageService {
    VproCoursesCover updateCover(VproCoursesCover vproCoursesCover);
    VproCoursesCover getCoverById(Integer courseCoverId);
    VproCoursesCover setCover(VproCoursesCover vproCoursesCover);
    VproCoursesImage setImage(DetailImage detailImage) throws ImageException;
    VproCoursesImage useImage(DetailImage detailImage) throws ImageException;
    List<VproCoursesImage> getImagesByAuthor(Integer authorId);
}

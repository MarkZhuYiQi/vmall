package com.mark.manager.service;

import com.mark.manager.pojo.VproCoursesCover;

public interface ImageService {
    VproCoursesCover updateCover(VproCoursesCover vproCoursesCover);
    VproCoursesCover getCoverById(Integer courseCoverId);
    VproCoursesCover setCover(VproCoursesCover vproCoursesCover);
}

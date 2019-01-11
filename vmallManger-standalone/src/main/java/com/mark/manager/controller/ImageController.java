package com.mark.manager.controller;

import com.mark.common.constant.ImageConstant;
import com.mark.common.exception.ImageException;
import com.mark.manager.bo.Result;
import com.mark.manager.pojo.VproCoursesCover;
import com.mark.manager.service.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "img")
public class ImageController {
    private static final Logger logger = LoggerFactory.getLogger(ImageController.class);
    @Autowired
    ImageService imageService;
    @PostMapping(value = "update")
    public Result updateCover(@RequestBody VproCoursesCover vproCoursesCover) {
        VproCoursesCover cover = new VproCoursesCover();
        try {
            if (vproCoursesCover.getCourseCoverId() == null || vproCoursesCover.getCourseCoverAddress() == null || vproCoursesCover.getCourseCoverKey() == null) {
                return new Result(ImageConstant.IMAGE_UPDATE_INFO_DAMAGED, "封面信息更新不完整");
            }
            cover = imageService.updateCover(vproCoursesCover);
        } catch(ImageException e) {
            logger.error(e.getMsg());
            return new Result(e.getCode(), e.getMessage());
        }
        return new Result(cover);
    }
    @PutMapping("create")
    public Result setCover(@RequestBody VproCoursesCover vproCoursesCover) {
        if (vproCoursesCover.getCourseCoverId() == null) throw new ImageException("传递数据错误，新建数据失败");
        VproCoursesCover cover = imageService.setCover(vproCoursesCover);
        return new Result(cover);
    }
}

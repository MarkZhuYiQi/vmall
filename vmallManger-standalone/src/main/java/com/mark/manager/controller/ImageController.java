package com.mark.manager.controller;

import com.mark.common.constant.ImageConstant;
import com.mark.common.exception.CourseException;
import com.mark.common.exception.ImageException;
import com.mark.manager.bo.Result;
import com.mark.manager.dto.CourseUpdate;
import com.mark.manager.pojo.VproCoursesCover;
import com.mark.manager.service.CourseService;
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
    @Autowired
    CourseService courseService;
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

    /**
     * 创建新封面的时候必须把封面信息更新到主课程中
     * @param vproCoursesCover
     * @return
     */
    @PutMapping("create")
    public Result setCover(@RequestBody VproCoursesCover vproCoursesCover) {
        try{
            if (vproCoursesCover.getCourseCoverId() == null) return new Result(ImageConstant.SET_IMAGE_WITHOUT_INFO, "传递数据错误，新建数据失败");
            VproCoursesCover cover = imageService.setCover(vproCoursesCover);
            CourseUpdate courseUpdate = new CourseUpdate();
            courseUpdate.setCourseId(vproCoursesCover.getCourseCoverId());
            courseService.updateCourse(courseUpdate);
            return new Result(cover);
        } catch (CourseException ec) {
            return new Result(ec.getCode(), ec.getMessage());
        }catch (ImageException ei) {
            return new Result(ei.getCode(), ei.getMessage());
        }
    }
}

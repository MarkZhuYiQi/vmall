package com.mark.manager.controller;

import com.mark.common.constant.CourseConstant;
import com.mark.common.constant.ImageConstant;
import com.mark.common.exception.CourseException;
import com.mark.common.exception.ImageException;
import com.mark.common.validateGroup.GetImage;
import com.mark.common.validateGroup.UpdateImageUsage;
import com.mark.manager.bo.Result;
import com.mark.manager.dto.CourseUpdate;
import com.mark.manager.dto.DetailImage;
import com.mark.manager.pojo.VproCoursesCover;
import com.mark.manager.pojo.VproCoursesImage;
import com.mark.manager.service.AuthService;
import com.mark.manager.service.CourseService;
import com.mark.manager.service.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "img")
public class ImageController {
    private static final Logger logger = LoggerFactory.getLogger(ImageController.class);
    @Autowired
    ImageService imageService;

    @Autowired
    CourseService courseService;

    @Autowired
    AuthService authService;

    @GetMapping("")
    public Result getImages(@RequestParam("author") String author) {
        // 从security中获取用户信息，实际上可以存对象，默认Object
        String appAuthId = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // 从redis获得用户信息
        Map<String, String> info = authService.getAuthByAuthIdFromRedis(appAuthId);
        if (info.size() == 0) return new Result("author could not be found", CourseConstant.INSERT_COURSE_AUTHOR_FAILURE);
        // 后端前端比对，如果不一样说明作者有问题
        if (!info.get("authAppid").equals(author)) return new Result("author mismatch", CourseConstant.INSERT_COURSE_AUTHOR_FAILURE);
        List<VproCoursesImage> list = imageService.getImagesByAuthor(Integer.parseInt(info.get("authId")));
        return new Result(list);
    }
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
            courseUpdate.setCourseCover(vproCoursesCover.getCourseCoverId());
            courseService.updateCourse(courseUpdate);
            return new Result(cover);
        } catch (CourseException ec) {
            return new Result(ec.getCode(), ec.getMessage());
        }catch (ImageException ei) {
            return new Result(ei.getCode(), ei.getMessage());
        }
    }
    @PutMapping("detail")
    public Result putDetailImage(@Validated @RequestBody DetailImage detailImage) {
        try {
            VproCoursesImage vproCoursesImage = imageService.setImage(detailImage);
            return new Result(vproCoursesImage);
        } catch (ImageException e) {
            return new Result(e.getCode(), e.getMessage());
        }
    }
    @PostMapping("detail")
    public Result updateDetailImage(@Validated({UpdateImageUsage.class}) @RequestBody DetailImage detailImage) {
        try {
            VproCoursesImage vproCoursesImage = imageService.useImage(detailImage);
            return new Result(vproCoursesImage);
        } catch(ImageException e) {
            return new Result(e.getCode(), e.getMessage());
        }
    }
}

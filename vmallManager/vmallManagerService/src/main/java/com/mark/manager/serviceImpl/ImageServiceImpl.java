package com.mark.manager.serviceImpl;

import com.mark.common.constant.ImageConstant;
import com.mark.common.exception.ImageException;
import com.mark.manager.dto.DetailImage;
import com.mark.manager.dto.DtoUtil;
import com.mark.manager.mapper.VproCoursesCoverMapper;
import com.mark.manager.mapper.VproCoursesImageMapper;
import com.mark.manager.pojo.*;
import com.mark.manager.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    VproCoursesCoverMapper vproCoursesCoverMapper;
    @Autowired
    VproCoursesImageMapper vproCoursesImageMapper;
    public VproCoursesCover updateCover(VproCoursesCover vproCoursesCover) {
        vproCoursesCover.setCourseCoverUptime(String.valueOf(System.currentTimeMillis() / 1000));
        VproCoursesCoverExample vproCoursesCoverExample = new VproCoursesCoverExample();
        vproCoursesCoverExample.createCriteria().andCourseCoverIdEqualTo(vproCoursesCover.getCourseCoverId());
        if (vproCoursesCoverMapper.updateByExampleSelective(vproCoursesCover, vproCoursesCoverExample) != 1) {
            throw new ImageException("更新商品详情失败", ImageConstant.IMAGE_UPDATE_FAILURE);
        }
        return getCoverById(vproCoursesCover.getCourseCoverId());
    }
    public VproCoursesCover getCoverById(Integer courseCoverId) {
        return vproCoursesCoverMapper.selectByPrimaryKey(courseCoverId);
    }
    @Override
    public VproCoursesCover setCover(VproCoursesCover vproCoursesCover) {
        vproCoursesCover.setCourseCoverUptime(String.valueOf(System.currentTimeMillis() / 1000));
        System.out.println(vproCoursesCover.getCourseCoverUptime());
        int res = vproCoursesCoverMapper.insertSelective(vproCoursesCover);
        if (res <= 0) {
            throw new ImageException("插入封面失败，检查日志", ImageConstant.IMAGE_INSERT_FAILURE);
        }
        return getCoverById(vproCoursesCover.getCourseCoverId());
    }

    @Override
    public VproCoursesImage setImage(DetailImage detailImage) throws ImageException {
        VproCoursesImage vproCoursesImage = DtoUtil.detailImage2VproCoursesImage(detailImage);
        Integer imageId = vproCoursesImageMapper.insertSelective(vproCoursesImage);
        if (imageId <= 0) {
            throw new ImageException("插入图片失败，检查数据库写入问题", ImageConstant.IMAGE_DETAIL_INSERT_FAILURE);
        }
        return getImageById(imageId);
    }
    private VproCoursesImage getImageById(Integer imageId) {
        return vproCoursesImageMapper.selectByPrimaryKey(imageId);
    }
    @Override
    public VproCoursesImage useImage(DetailImage detailImage) throws ImageException {
        VproCoursesImage vproCoursesImage = DtoUtil.detailImage2VproCoursesImage(detailImage);
        VproCoursesImage image = vproCoursesImageMapper.selectByPrimaryKey(vproCoursesImage.getImageId());
        if (image == null) throw new ImageException("图片不存在", ImageConstant.IMAGE_DETAIL_UPDATE_FAILURE);
        if (detailImage.getType() == 1) {
            image.setImageUsedLocation(image.getImageUsedLocation() + String.valueOf(detailImage.getImageUsedLocation()) + ",");
        } else if (detailImage.getType() == 0) {
            String usage = image.getImageUsedLocation().replace(vproCoursesImage.getImageUsedLocation() + ",", "");
            image.setImageUsedLocation(usage);
        } else {
            throw new ImageException("操作类型错误", ImageConstant.IMAGE_DETAIL_SET_USAGE_TYPE_FAILURE);
        }
        Integer res = vproCoursesImageMapper.updateByPrimaryKeySelective(image);
        if (res <= 0) throw new ImageException("更新图片使用信息失败！", ImageConstant.IMAGE_DETAIL_USAGE_UPDATE_FAILURE);
        return getImageById(image.getImageId());
    }

    public List<VproCoursesImage> getImagesByAuthor(Integer authorId) {
        VproCoursesImageExample vproCoursesImageExample = new VproCoursesImageExample();
        vproCoursesImageExample.createCriteria().andImageIdEqualTo(authorId);
        return vproCoursesImageMapper.selectByExample(vproCoursesImageExample);

    }
}

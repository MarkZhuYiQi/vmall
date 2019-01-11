package com.mark.manager.serviceImpl;

import com.mark.common.constant.ImageConstant;
import com.mark.common.exception.ImageException;
import com.mark.manager.mapper.VproCoursesCoverMapper;
import com.mark.manager.pojo.VproCoursesCover;
import com.mark.manager.pojo.VproCoursesCoverExample;
import com.mark.manager.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    VproCoursesCoverMapper vproCoursesCoverMapper;
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
}

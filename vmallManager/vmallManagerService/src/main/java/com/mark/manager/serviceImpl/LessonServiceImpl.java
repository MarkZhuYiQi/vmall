package com.mark.manager.serviceImpl;
import com.mark.common.exception.LessonException;
import com.mark.manager.daoImpl.LessonDaoByDBImpl;
import com.mark.manager.daoImpl.LessonDaoByRedisImpl;
import com.mark.manager.dto.LessonsOps;
import com.mark.manager.mapper.LessonsMapper;
import com.mark.manager.mapper.VproCoursesLessonListMapper;
import com.mark.manager.pojo.VproCoursesCoverExample;
import com.mark.manager.pojo.VproCoursesLessonList;
import com.mark.manager.pojo.VproCoursesLessonListExample;
import com.mark.manager.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.beans.IntrospectionException;
import java.util.List;
@Repository
public class LessonServiceImpl implements LessonService {

    @Autowired
    LessonDaoByDBImpl lessonDaoByDB;
    @Autowired
    LessonDaoByRedisImpl lessonDaoByRedis;
    @Autowired
    VproCoursesLessonListMapper vproCoursesLessonListMapper;
    @Autowired
    LessonsMapper lessonsMapper;

    private static final String lessonPrefix = "lesson";
    @Override
    public List<VproCoursesLessonList> getLessonsList(Integer courseId) {
        List<VproCoursesLessonList> list = lessonDaoByRedis.getLessonsList(courseId);
        if (lessonDaoByRedis.getLessonsList(courseId).size() == 0) {
            list = lessonDaoByDB.getLessonsList(courseId);
        }
        return list;
        /*List<VproCoursesLessonList> list = lessonDaoByRedis.getLessonsList();
        if (list.size() == 0) {
            try {
                lessonDaoByRedis.cacheLessonsList(lessonDaoByDB.getLessonsList());
            } catch (IntrospectionException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(list);
        }
        return list;*/
    }

    @Override
    public VproCoursesLessonList getLesson(Integer lessonId) {
        VproCoursesLessonList vproCoursesLessonList = new VproCoursesLessonList();
        // 先去redis获取，没拿到就去数据库找，但是看结果是否命中，如果没有命中则直接返回空，
        // 同时可以给这个键放一个空值。
        // 为了防止缓存穿透，给这个访问ip做一个限制，如果这个访问多次没有命中，就直接封杀这个IP
        try {
            vproCoursesLessonList = lessonDaoByRedis.getLesson(lessonId);
            if (vproCoursesLessonList == null) {
                vproCoursesLessonList = lessonDaoByDB.getLesson(lessonId);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return vproCoursesLessonList;
    }

    @Override
    public VproCoursesLessonList insertLessonToLocationSpecified() {
        return null;
    }

    /**
     * 将lesson转移到目标位置
     * @param original 源lesson
     * @param destination 目标lesson
     * @return
     */
    @Override
    public VproCoursesLessonList updateLessonToLocationSpecified(VproCoursesLessonList original, VproCoursesLessonList destination) {
        // 修改课程的序列到目的地
        VproCoursesLessonList vproCoursesLessonList = new VproCoursesLessonList();
        vproCoursesLessonList.setLessonLid(destination.getLessonLid());

        VproCoursesLessonListExample vproCoursesLessonListExample= new VproCoursesLessonListExample();
        vproCoursesLessonListExample.createCriteria().andLessonIdEqualTo(Integer.parseInt(destination.getLessonId()));
        if (vproCoursesLessonListMapper.updateByExampleSelective(vproCoursesLessonList, vproCoursesLessonListExample) != 1) {
            throw new LessonException("lesson移动到目标位置失败，被更新lesson信息：" + original.toString() + ", 目的地lesson信息：" + destination.toString());
        }
        return getLesson(Integer.parseInt(original.getLessonId()));
    }

    /**
     * 搬迁范围内的lessons留出空间
     * @param lessonIds 需要搬迁的lessons的id
     * @param type      上偏移还是下偏移
     * @param offset    偏移量
     * @param courseId  课程id
     * @param lessonPid 子标题id（寻找归属）
     * @return
     */
    @Override
    public boolean adjestLessonSequence(List<Integer> lessonIds, Byte type, Integer offset, Integer courseId, Integer lessonPid) {
        // 搬迁课程留出空间。
        Integer res = lessonsMapper.updateLessonsSequence(lessonIds, type, offset, courseId, lessonPid);
        if(res != 0) return true;
        return false;
    }

    @Override
    public List<VproCoursesLessonList> checkIfHasLessonsUnderSubTitle() {
        return null;
    }

    @Override
    public VproCoursesLessonList insertSubTitle() {
        return null;
    }

    @Override
    public VproCoursesLessonList updateSubTitle() {
        return null;
    }

    @Override
    public boolean moveLessonInnerTitle(LessonsOps lessonsOps) {
        if (lessonsOps.getType() == 0) {
            VproCoursesLessonList originalMovedToDestination = updateLessonToLocationSpecified(lessonsOps.getOriginal(), lessonsOps.getDestination());

        }
    }

    @Override
    public boolean moveLessonOuterTitle(LessonsOps lessonsOps) {
        return false;
    }

    @Override
    public boolean removeLesson() {
        return false;
    }
}

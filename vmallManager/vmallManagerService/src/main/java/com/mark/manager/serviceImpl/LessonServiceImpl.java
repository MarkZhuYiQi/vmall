package com.mark.manager.serviceImpl;
import com.mark.common.exception.LessonException;
import com.mark.manager.daoImpl.LessonDaoByDBImpl;
import com.mark.manager.daoImpl.LessonDaoByRedisImpl;
import com.mark.manager.dto.LessonsOps;
import com.mark.manager.mapper.LessonsMapper;
import com.mark.manager.mapper.VproCoursesLessonListMapper;
import com.mark.manager.pojo.VproCoursesLessonList;
import com.mark.manager.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.beans.IntrospectionException;
import java.util.ArrayList;
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
     * 得到需要搬迁的lessonsId
     * @param lessonsOps DTO
     * @return List<Integer> id集合
     */
    @Override
    public List<Integer> getLessonsNeedReLocation(LessonsOps lessonsOps) {
        List<Integer> list = new ArrayList<Integer>();
        // 拿到被移动的lesson的课程内部位置
        Integer original = Integer.parseInt(lessonsOps.getOriginal().getLessonLid());
        // 拿到需要被移动到的目标位置
        Integer destination = Integer.parseInt(lessonsOps.getDestination().getLessonLid());
        if (original == null || destination == null) throw new LessonException("获取需要搬迁的lessonsLid为null，操作信息：" + lessonsOps.toString());
        Integer start = null;
        Integer end = null;
        // type决定是up（1）还是down（2）
        if (lessonsOps.getType() == 1) {
            start = destination;
            end = original;
        } else if (lessonsOps.getType() == 2) {
            start = original;
            end = destination;
        } else {
            throw new LessonException("操作对象dto的type非法，dto信息：" + lessonsOps.toString());
        }
        // 这里有个参数是isTitle，表明是否为
        list = lessonsMapper.getLessonsNeedReLocation(start, end, lessonsOps.getCourseId(), lessonsOps.getType(), lessonsOps.getIsTitle());
        if (list.size() == 0) throw new LessonException("获得的搬迁lessons为空，dto信息：" + lessonsOps.toString());
        return list;
    }

    /**
     * 将lesson转移到目标位置
     * @param original 源lesson
     * @param destination 目标lesson
     * @return 返回目标位置的lesson对象
     */
    @Override
    @Transactional
    public VproCoursesLessonList updateLessonToLocationSpecified(VproCoursesLessonList original, VproCoursesLessonList destination) {
        // 修改lesson/subtitle的序列到目的地
        VproCoursesLessonList vproCoursesLessonList = new VproCoursesLessonList();
        vproCoursesLessonList.setLessonLid(destination.getLessonLid());
        // 筛选条件
        vproCoursesLessonList.setLessonId(destination.getLessonId());
        if (Integer.parseInt(original.getLessonPid()) != Integer.parseInt(destination.getLessonPid())) {
            // 如果pid不同则说明是跨标题的移动，需要修改lesson的pid
            vproCoursesLessonList.setLessonPid(destination.getLessonPid());
        }
        if (vproCoursesLessonListMapper.updateByPrimaryKeySelective(vproCoursesLessonList) != 1) {
            throw new LessonException("lesson/subtitle移动到目标位置失败，被更新lesson信息：" + original.toString() + ", 目的地lesson/subtitle信息：" + destination.toString());
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
     * @return 返回布尔值，成功true，失败false
     */
    @Override
    public boolean adjustLessonSequence(List<Integer> lessonIds, Integer type, Integer offset, Integer courseId, Integer lessonPid, Integer isTitle) {
        // 搬迁课程留出空间
        Integer res = lessonsMapper.updateLessonsSequence(lessonIds, type, offset, courseId, lessonPid, isTitle);
        if(res != 0) return true;
        return false;
    }

    @Override
    public boolean removeLesson(LessonsOps lessonsOps) {
        if (lessonsOps.getOriginal() == null) {
            throw new LessonException("删除lesson信息错误， DTO信息： " + lessonsOps.toString());
        }
        lessonsOps.getOriginal().setLessonIsDeleted("1");
        Integer res = vproCoursesLessonListMapper.updateByPrimaryKeySelective(lessonsOps.getOriginal());
        if (res > 0)return true;
        return false;
    }

    @Override
    @Transactional
    public VproCoursesLessonList addLesson(LessonsOps lessonsOps) {
        Integer primaryId = 0;
        if (lessonsOps.getOriginal() != null && lessonsOps.getDestination() == null) {
            // 只是顺序插入一个lesson，跟在最后
            Integer maxLessonLid = lessonsMapper.getLastLessonLid(
                    lessonsOps.getCourseId(),
                    Integer.valueOf(lessonsOps.getOriginal().getLessonIsChapterHead()),
                    Integer.parseInt(lessonsOps.getOriginal().getLessonPid())
            );
            lessonsOps.getOriginal().setLessonLid(String.valueOf(maxLessonLid + 1));
            primaryId = vproCoursesLessonListMapper.insert(lessonsOps.getOriginal());
            if (primaryId == 0) {
                throw new LessonException("插入新lesson失败，dto信息：" + lessonsOps.toString());
            }
            return getLesson(primaryId);
        } else if (lessonsOps .getOriginal() != null && lessonsOps.getDestination() != null) {
            primaryId = vproCoursesLessonListMapper.insert(lessonsOps.getOriginal());
            if (primaryId == 0) {
                throw new LessonException("插入新lesson失败，dto信息：" + lessonsOps.toString());
            }
            Integer maxLessonLid = lessonsMapper.getLastLessonLid(
                    lessonsOps.getCourseId(),
                    Integer.valueOf(lessonsOps.getOriginal().getLessonIsChapterHead()),
                    null
            );
            List<Integer> lessonIds = lessonsMapper.getLessonsNeedReLocation(
                    Integer.valueOf(lessonsOps.getDestination().getLessonLid()),
                    maxLessonLid,
                    lessonsOps.getCourseId(),
                    3,
                    lessonsOps.getIsTitle()
            );
            adjustLessonSequence(lessonIds, 3, 1, lessonsOps.getCourseId(), null, lessonsOps.getIsTitle());
            return getLesson(primaryId);
        }
        return null;
    }

    /**
     * 移动 lesson / subtitle 的完整流程方法
     * @param lessonsOps
     * @return
     */
    @Override
    @Transactional
    public boolean moveLesson(LessonsOps lessonsOps) {
        // 获得搬迁lessonsIds区间（或者需要移动的所有副标题id）
        List<Integer> list = getLessonsNeedReLocation(lessonsOps);
        // 将需要移动的lesson/subtitle转移到目标位置
        VproCoursesLessonList targetLocationLesson = updateLessonToLocationSpecified(lessonsOps.getOriginal(), lessonsOps.getDestination());
        // 将其他指定范围内的lessons/subtitle搬迁，移动位置保证顺序一致。
        adjustLessonSequence(list,
                lessonsOps.getType(),
                list.size(),
                lessonsOps.getCourseId(),
                Integer.valueOf(lessonsOps.getDestination().getLessonPid()),
                lessonsOps.getIsTitle()
        );
        return true;
    }

    @Override
    @Transactional
    public VproCoursesLessonList addSubTitle(LessonsOps lessonsOps) {
        if (lessonsOps.getOriginal() != null && lessonsOps.getDestination() == null) {
            if (lessonsOps.getOriginal().getLessonIsChapterHead().equals("1")) {
                return addLesson(lessonsOps);
            }
            throw new LessonException("添加subtitle失败，对象为空或属性错误，DTO信息：" + lessonsOps.toString());
        } else if (lessonsOps.getOriginal() != null && lessonsOps.getDestination() != null) {
            if (lessonsOps.getOriginal().getLessonIsChapterHead().equals("1") && lessonsOps.getDestination().getLessonIsChapterHead().equals("1")) {
                return addLesson(lessonsOps);
            }
            throw new LessonException("添加subtitle到指定位置失败，对象为空或属性错误，DTO信息：" + lessonsOps.toString());
        }
        throw new LessonException("添加subtitle的对象不能为空，DTO信息：" + lessonsOps.toString());
    }

    /**
     *         如果是上移：
     *         那么搬迁范围是[目标位置第一个lessonLid，源位置第一个lessonLid)，要判断这两者的大小: 目标位置第一个lessonLid < 源位置第一个lessonLid
     *         得到这个区间内的lessonId，lessonLid集体偏移，偏移量：lessons.size()
     *         如果是下移：
     *         那么搬迁范围是(源位置最后一个lessonLid，目标位置最后一个lessonLid]，要判断这两者的大小: 源位置最后一个lessonLid < 目标位置最后一个lessonLid
     *         得到这个区间内的lessonId，lessonLid集体偏移，偏移量：lessons.size()
     * @param lessonsOps
     * @return
     */
    private List<Integer> getTransferLessonsRange(LessonsOps lessonsOps) {
        Integer relocateStart = null;
        Integer relocateEnd = null;
        // 根据上移还是下移得到需要移动的数据范围
        if (lessonsOps.getType() == 1) {
            relocateStart = lessonsMapper.getLessonLidSpecified(
                    lessonsOps.getType(),
                    lessonsOps.getCourseId(),
                    Integer.parseInt(lessonsOps.getDestination().getLessonLid())
            );
            relocateEnd = lessonsMapper.getLessonLidSpecified(
                    lessonsOps.getType(),
                    lessonsOps.getCourseId(),
                    Integer.parseInt(lessonsOps.getOriginal().getLessonLid())
            );
        } else if (lessonsOps.getType() == 2) {
            relocateStart = lessonsMapper.getLessonLidSpecified(lessonsOps.getType(), lessonsOps.getCourseId(), Integer.parseInt(lessonsOps.getOriginal().getLessonLid()));
            relocateEnd = lessonsMapper.getLessonLidSpecified(lessonsOps.getType(), lessonsOps.getCourseId(), Integer.parseInt(lessonsOps.getDestination().getLessonLid()));
        }
        if (relocateEnd == null || relocateStart == null || relocateEnd <= relocateStart)
            throw new LessonException("移动副标题时，搬迁lessons失败，搬迁范围:(" + relocateStart + "~" + relocateEnd + "], DTO信息：" + lessonsOps.toString());
        // 需要搬迁的数据id范围
        return lessonsMapper.getLessonsNeedReLocation(relocateStart, relocateEnd, lessonsOps.getCourseId(), lessonsOps.getType(), lessonsOps.getIsTitle());

    }
    @Override
    /**
     * 1. 将副标题转移到需要移动到的位置。（lessonLid的值更改）（旗下的lessons的pid依然是这个subTitle，但是lessonLid是错误的）
     * 2. 获取转移到的目标副标题中的第一个课程
     */
    @Transactional
    public boolean moveSubTitle(LessonsOps lessonsOps) {
        moveLesson(lessonsOps);
        // -----------至此，标题搬迁完毕，剩下的事情是lessons-------------
/*
        如果是上移：
        那么搬迁范围是[目标位置第一个lessonLid，源位置第一个lessonLid)，要判断这两者的大小: 目标位置第一个lessonLid < 源位置第一个lessonLid
        得到这个区间内的lessonId，lessonLid集体偏移，偏移量：lessons.size()
        如果是下移：
        那么搬迁范围是(源位置最后一个lessonLid，目标位置最后一个lessonLid]，要判断这两者的大小: 源位置最后一个lessonLid < 目标位置最后一个lessonLid
        得到这个区间内的lessonId，lessonLid集体偏移，偏移量：lessons.size()
*/
        // 需要搬迁的数据id范围
        List<Integer> lessonIdsNeedMove = getTransferLessonsRange(lessonsOps);

        // 得到被转移的标题旗下的所有lessons
        List<Integer> subTitleIds = new ArrayList<Integer>();
        subTitleIds.add(Integer.parseInt(lessonsOps.getOriginal().getLessonId()));
        List<Integer> lessonsInnerSubTitle = lessonsMapper.getLessonsInnerTitle(subTitleIds, lessonsOps.getCourseId());
        // 移动到目标位置，跟随副标题
        lessonsMapper.updateLessonsSequence(
                lessonsInnerSubTitle,
                (lessonsOps.getType() == 1 ? 0 : 1),
                lessonIdsNeedMove.size(),
                lessonsOps.getCourseId(),
                Integer.valueOf(lessonsOps.getDestination().getLessonPid()),
                lessonsOps.getIsTitle()
        );
        // 其余lessons搬迁，继续按照顺序排列。
        lessonsMapper.updateLessonsSequence(
                lessonIdsNeedMove,
                lessonsOps.getType(),
                lessonsInnerSubTitle.size(),
                lessonsOps.getCourseId(),
                null,
                lessonsOps.getIsTitle()
        );
        return true;
    }
}

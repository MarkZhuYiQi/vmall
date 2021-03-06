package com.mark.manager.serviceImpl;
import com.mark.common.exception.LessonException;
import com.mark.manager.dao.LessonDao;
import com.mark.manager.dto.LessonsOps;
import com.mark.manager.dto.LessonsOpsList;
import com.mark.manager.mapper.LessonsMapper;
import com.mark.manager.mapper.VproCoursesLessonListMapper;
import com.mark.manager.pojo.VproCoursesLessonList;
import com.mark.manager.pojo.VproCoursesLessonListExample;
import com.mark.manager.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
@Service
public class LessonServiceImpl implements LessonService {

    @Autowired
    @Qualifier("lessonDao")
    LessonDao lessonDao;

    @Autowired
    VproCoursesLessonListMapper vproCoursesLessonListMapper;

    @Autowired
    LessonsMapper lessonsMapper;

    @Override
    public List<VproCoursesLessonList> getLessonsList(Integer courseId) throws LessonException {
        return lessonDao.getLessonsList(courseId);
    }

    @Override
    public VproCoursesLessonList getLesson(Integer lessonId) throws LessonException {
        // 先去redis获取，没拿到就去数据库找，但是看结果是否命中，如果没有命中则直接返回空，
        // 同时可以给这个键放一个空值。
        // 为了防止缓存穿透，给这个访问ip做一个限制，如果这个访问多次没有命中，就直接封杀这个IP
        return lessonDao.getLesson(lessonId);
    }

    @Override
    public VproCoursesLessonList insertLessonToLocationSpecified() {
        return null;
    }

    /**
     * 得到需要搬迁的lessonsId
     * 除了区分是上移还是下移，还要区分是放置到目标位置的上方还是下方（before，after，inner）
     * up before: x >= start x < end 1 + 1
     * up after: x > start x < end 1 + 2
     * down before: x > start x < end 2 + 1
     * down after: x > start x <= end 2 + 2
     * @param lessonsOps DTO
     * @return List<Integer> id集合
     */
    @Override
    public List<Integer> getLessonsNeedReLocation(LessonsOps lessonsOps) throws LessonException {
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
        // 如果只是从第三个标题的第一个位置移动到第二个标题的最后一个位置，实际上只是更改了pid所以不涉及其他内容
        if (end - start <= 1 && (!lessonsOps.getOriginal().getLessonPid().equals(lessonsOps.getDestination().getLessonPid()))) return null;
        // 这里有个参数是isTitle，表明是否为标题
        list = lessonsMapper.getLessonsNeedReLocation(
                start,
                end,
                lessonsOps.getCourseId(),
                lessonsOps.getType() + lessonsOps.getDropType(),
                lessonsOps.getIsTitle()
        );
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
    public VproCoursesLessonList updateLessonToLocationSpecified(VproCoursesLessonList original, VproCoursesLessonList destination) throws LessonException {
        // 修改lesson/subtitle的序列到目的地
        VproCoursesLessonList vproCoursesLessonList = new VproCoursesLessonList();
        if (
                Math.abs(Integer.parseInt(original.getLessonLid()) - Integer.parseInt(destination.getLessonLid())) <= 1 &&
                        !original.getLessonPid().equals(destination.getLessonPid())
        ) {
            // 此情况为：lessons只是在上一个标题的最后一个位置移动到下一个标题的第一个位置，不涉及其他内容
            vproCoursesLessonList.setLessonLid(original.getLessonLid());
        } else {
            vproCoursesLessonList.setLessonLid(destination.getLessonLid());
        }
        // 筛选条件, 修改的是被移动的节点
        vproCoursesLessonList.setLessonId(original.getLessonId());
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
    public boolean removeLesson(LessonsOps lessonsOps) throws LessonException {
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
    public VproCoursesLessonList addLesson(LessonsOps lessonsOps) throws LessonException {
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
            // 在原有基础中间插入一个课程
            primaryId = vproCoursesLessonListMapper.insert(lessonsOps.getOriginal());
            if (primaryId == 0) {
                throw new LessonException("插入新lesson失败，dto信息：" + lessonsOps.toString());
            }
            Integer maxLessonLid = lessonsMapper.getLastLessonLid(
                    lessonsOps.getCourseId(),
                    Integer.valueOf(lessonsOps.getOriginal().getLessonIsChapterHead()),
                    null
            );
            /**
             * wait change, 操作type有问题，需要更改，这里涉及插入口的调整
             */
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
    public boolean moveLesson(LessonsOps lessonsOps) throws LessonException {
        List<Integer> list = new ArrayList<Integer>();
        // 获得搬迁lessonsIds区间（或者需要移动的所有副标题id）
        // 有一种情况， 如果只是从下一个标题的第一个移动到上一个标题的最后一个，不涉及其他内容调整，只需要更改该元素的pid
        // 所以list是空的
        list = getLessonsNeedReLocation(lessonsOps);

        // 将需要移动的lesson/subtitle转移到目标位置
        VproCoursesLessonList targetLocationLesson = updateLessonToLocationSpecified(lessonsOps.getOriginal(), lessonsOps.getDestination());
        System.out.println(targetLocationLesson.toString());
        // 将其他指定范围内的lessons/subtitle搬迁，移动位置保证顺序一致。
        if (list != null) {
            adjustLessonSequence(list,
                    lessonsOps.getType(),
                    1,
                    lessonsOps.getCourseId(),
                    null,
                    lessonsOps.getIsTitle()
            );
        }
        return true;
    }

    @Override
    @Transactional
    public VproCoursesLessonList addSubTitle(LessonsOps lessonsOps) throws LessonException {
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
    private List<Integer> getTransferLessonsRange(LessonsOps lessonsOps) throws LessonException {
        Integer relocateStart = null;
        Integer relocateEnd = null;
        // 根据上移还是下移得到需要移动的数据范围
        if (lessonsOps.getType() == 1) {
             relocateStart = lessonsMapper.getLessonLidSpecified(
                    lessonsOps.getType(),
                    lessonsOps.getCourseId(),
                    Integer.parseInt(lessonsOps.getDestination().getLessonId())
            );
            relocateEnd = lessonsMapper.getLessonLidSpecified(
                    lessonsOps.getType(),
                    lessonsOps.getCourseId(),
                    Integer.parseInt(lessonsOps.getOriginal().getLessonId())
            );
        } else if (lessonsOps.getType() == 2) {
            relocateStart = lessonsMapper.getLessonLidSpecified(
                    lessonsOps.getType(),
                    lessonsOps.getCourseId(),
                    Integer.parseInt(lessonsOps.getOriginal().getLessonId())
            );
            relocateEnd = lessonsMapper.getLessonLidSpecified(
                    lessonsOps.getType(),
                    lessonsOps.getCourseId(),
                    Integer.parseInt(lessonsOps.getDestination().getLessonId())
            );
        }
        if (relocateEnd == null || relocateStart == null || relocateEnd <= relocateStart)
            throw new LessonException("移动副标题时，搬迁lessons失败，搬迁范围:(" + relocateStart + "~" + relocateEnd + "], DTO信息：" + lessonsOps.toString());
        // 需要搬迁的数据id范围
        return lessonsMapper.getLessonsNeedReLocation(relocateStart, relocateEnd, lessonsOps.getCourseId(), lessonsOps.getType() + lessonsOps.getDropType(), 0);

    }
    @Override
    /**
     * 1. 将副标题转移到需要移动到的位置。（lessonLid的值更改）（旗下的lessons的pid依然是这个subTitle，但是lessonLid是错误的）
     * 2. 获取转移到的目标副标题中的第一个课程
     */
    @Transactional
    public boolean moveSubTitle(LessonsOps lessonsOps) throws LessonException {
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
        // 标题下的所有lesson在此
        List<Integer> lessonsInnerSubTitle = lessonsMapper.getLessonsInnerTitle(subTitleIds, lessonsOps.getCourseId());
        // 移动到目标位置，跟随副标题
        System.out.println("type: (决定是加号还是减号):" + String.valueOf(lessonsOps.getType() == 1 ? 0 : 1) );
        // 标题转移后，标题下的lesson全部转移到新位置的下方
        if (lessonsInnerSubTitle.size() > 0) {
            Integer lessonInnerTitleTransfer = lessonsMapper.updateLessonsSequence(
                    lessonsInnerSubTitle,
                    (lessonsOps.getType() == 1 ? 2 : 1),
                    lessonIdsNeedMove.size(),
                    lessonsOps.getCourseId(),
                    Integer.valueOf(lessonsOps.getOriginal().getLessonId()),
                    0
            );
            if (lessonInnerTitleTransfer == 0 ) throw new LessonException("转移标题后，标题内部的lesson跟随转移顺序失败，需要转移的lessons: " + lessonsInnerSubTitle.toString());
        }
        // 其余lessons搬迁，继续按照顺序排列。
        if (lessonIdsNeedMove.size() > 0) {
            Integer lessonAffectedByTitleTransfer = lessonsMapper.updateLessonsSequence(
                    lessonIdsNeedMove,
                    lessonsOps.getType(),
                    lessonsInnerSubTitle.size(),
                    lessonsOps.getCourseId(),
                    null,
                    0
            );
            if (lessonAffectedByTitleTransfer == 0 ) throw new LessonException("被转移标题影响的其他lessons转移顺序失败，需要转移的lessons: " + lessonAffectedByTitleTransfer.toString());
        }
        return true;
    }

    @Override
    public boolean removeSubTitle(LessonsOps lessonsOps) throws LessonException {
        VproCoursesLessonList vproCoursesLessonList = new VproCoursesLessonList();
        vproCoursesLessonList.setLessonIsDeleted("1");
        removeLesson(lessonsOps);
        VproCoursesLessonListExample vproCoursesLessonListExample = new VproCoursesLessonListExample();
        vproCoursesLessonListExample.createCriteria()
                .andLessonPidEqualTo(Integer.parseInt(lessonsOps.getOriginal().getLessonId()))
                .andLessonCourseIdEqualTo(lessonsOps.getCourseId());
        if (vproCoursesLessonListMapper.updateByExampleSelective(vproCoursesLessonList, vproCoursesLessonListExample) > 0) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean manageEdit(LessonsOpsList lessonsOpsList) throws LessonException {
        boolean res = false;
        VproCoursesLessonList vproCoursesLessonList = null;
        for(LessonsOps l : lessonsOpsList.getLessonsOpsList()) {
            switch(l.getOps()) {
                case 101:
                    vproCoursesLessonList = addLesson(l);
                    break;
                case 102:
                    res = moveLesson(l);
                    break;
                case 103:
                    res = removeLesson(l);
                    break;
                case 201:
                    vproCoursesLessonList = addSubTitle(l);
                    break;
                case 202:
                    res = moveSubTitle(l);
                    break;
                case 203:
                    res = removeLesson(l);
                    break;
            }
            if (l.getOps() == 102 || l.getOps() == 202 || l.getOps() == 103 || l.getOps() == 203) {
                if (!res) return false;
            } else {
                if (vproCoursesLessonList == null) return false;
            }
        }
        return true;
    }
}

package com.mark.manager.pojo;

import java.util.ArrayList;
import java.util.List;

public class VproCoursesLessonListExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table vpro_courses_lesson_list
     *
     * @mbg.generated Mon Dec 17 20:27:24 CST 2018
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table vpro_courses_lesson_list
     *
     * @mbg.generated Mon Dec 17 20:27:24 CST 2018
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table vpro_courses_lesson_list
     *
     * @mbg.generated Mon Dec 17 20:27:24 CST 2018
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_courses_lesson_list
     *
     * @mbg.generated Mon Dec 17 20:27:24 CST 2018
     */
    public VproCoursesLessonListExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_courses_lesson_list
     *
     * @mbg.generated Mon Dec 17 20:27:24 CST 2018
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_courses_lesson_list
     *
     * @mbg.generated Mon Dec 17 20:27:24 CST 2018
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_courses_lesson_list
     *
     * @mbg.generated Mon Dec 17 20:27:24 CST 2018
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_courses_lesson_list
     *
     * @mbg.generated Mon Dec 17 20:27:24 CST 2018
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_courses_lesson_list
     *
     * @mbg.generated Mon Dec 17 20:27:24 CST 2018
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_courses_lesson_list
     *
     * @mbg.generated Mon Dec 17 20:27:24 CST 2018
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_courses_lesson_list
     *
     * @mbg.generated Mon Dec 17 20:27:24 CST 2018
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_courses_lesson_list
     *
     * @mbg.generated Mon Dec 17 20:27:24 CST 2018
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_courses_lesson_list
     *
     * @mbg.generated Mon Dec 17 20:27:24 CST 2018
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_courses_lesson_list
     *
     * @mbg.generated Mon Dec 17 20:27:24 CST 2018
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table vpro_courses_lesson_list
     *
     * @mbg.generated Mon Dec 17 20:27:24 CST 2018
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andLessonIdIsNull() {
            addCriterion("lesson_id is null");
            return (Criteria) this;
        }

        public Criteria andLessonIdIsNotNull() {
            addCriterion("lesson_id is not null");
            return (Criteria) this;
        }

        public Criteria andLessonIdEqualTo(Integer value) {
            addCriterion("lesson_id =", value, "lessonId");
            return (Criteria) this;
        }

        public Criteria andLessonIdNotEqualTo(Integer value) {
            addCriterion("lesson_id <>", value, "lessonId");
            return (Criteria) this;
        }

        public Criteria andLessonIdGreaterThan(Integer value) {
            addCriterion("lesson_id >", value, "lessonId");
            return (Criteria) this;
        }

        public Criteria andLessonIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("lesson_id >=", value, "lessonId");
            return (Criteria) this;
        }

        public Criteria andLessonIdLessThan(Integer value) {
            addCriterion("lesson_id <", value, "lessonId");
            return (Criteria) this;
        }

        public Criteria andLessonIdLessThanOrEqualTo(Integer value) {
            addCriterion("lesson_id <=", value, "lessonId");
            return (Criteria) this;
        }

        public Criteria andLessonIdIn(List<Integer> values) {
            addCriterion("lesson_id in", values, "lessonId");
            return (Criteria) this;
        }

        public Criteria andLessonIdNotIn(List<Integer> values) {
            addCriterion("lesson_id not in", values, "lessonId");
            return (Criteria) this;
        }

        public Criteria andLessonIdBetween(Integer value1, Integer value2) {
            addCriterion("lesson_id between", value1, value2, "lessonId");
            return (Criteria) this;
        }

        public Criteria andLessonIdNotBetween(Integer value1, Integer value2) {
            addCriterion("lesson_id not between", value1, value2, "lessonId");
            return (Criteria) this;
        }

        public Criteria andLessonLidIsNull() {
            addCriterion("lesson_lid is null");
            return (Criteria) this;
        }

        public Criteria andLessonLidIsNotNull() {
            addCriterion("lesson_lid is not null");
            return (Criteria) this;
        }

        public Criteria andLessonLidEqualTo(Integer value) {
            addCriterion("lesson_lid =", value, "lessonLid");
            return (Criteria) this;
        }

        public Criteria andLessonLidNotEqualTo(Integer value) {
            addCriterion("lesson_lid <>", value, "lessonLid");
            return (Criteria) this;
        }

        public Criteria andLessonLidGreaterThan(Integer value) {
            addCriterion("lesson_lid >", value, "lessonLid");
            return (Criteria) this;
        }

        public Criteria andLessonLidGreaterThanOrEqualTo(Integer value) {
            addCriterion("lesson_lid >=", value, "lessonLid");
            return (Criteria) this;
        }

        public Criteria andLessonLidLessThan(Integer value) {
            addCriterion("lesson_lid <", value, "lessonLid");
            return (Criteria) this;
        }

        public Criteria andLessonLidLessThanOrEqualTo(Integer value) {
            addCriterion("lesson_lid <=", value, "lessonLid");
            return (Criteria) this;
        }

        public Criteria andLessonLidIn(List<Integer> values) {
            addCriterion("lesson_lid in", values, "lessonLid");
            return (Criteria) this;
        }

        public Criteria andLessonLidNotIn(List<Integer> values) {
            addCriterion("lesson_lid not in", values, "lessonLid");
            return (Criteria) this;
        }

        public Criteria andLessonLidBetween(Integer value1, Integer value2) {
            addCriterion("lesson_lid between", value1, value2, "lessonLid");
            return (Criteria) this;
        }

        public Criteria andLessonLidNotBetween(Integer value1, Integer value2) {
            addCriterion("lesson_lid not between", value1, value2, "lessonLid");
            return (Criteria) this;
        }

        public Criteria andLessonPidIsNull() {
            addCriterion("lesson_pid is null");
            return (Criteria) this;
        }

        public Criteria andLessonPidIsNotNull() {
            addCriterion("lesson_pid is not null");
            return (Criteria) this;
        }

        public Criteria andLessonPidEqualTo(Integer value) {
            addCriterion("lesson_pid =", value, "lessonPid");
            return (Criteria) this;
        }

        public Criteria andLessonPidNotEqualTo(Integer value) {
            addCriterion("lesson_pid <>", value, "lessonPid");
            return (Criteria) this;
        }

        public Criteria andLessonPidGreaterThan(Integer value) {
            addCriterion("lesson_pid >", value, "lessonPid");
            return (Criteria) this;
        }

        public Criteria andLessonPidGreaterThanOrEqualTo(Integer value) {
            addCriterion("lesson_pid >=", value, "lessonPid");
            return (Criteria) this;
        }

        public Criteria andLessonPidLessThan(Integer value) {
            addCriterion("lesson_pid <", value, "lessonPid");
            return (Criteria) this;
        }

        public Criteria andLessonPidLessThanOrEqualTo(Integer value) {
            addCriterion("lesson_pid <=", value, "lessonPid");
            return (Criteria) this;
        }

        public Criteria andLessonPidIn(List<Integer> values) {
            addCriterion("lesson_pid in", values, "lessonPid");
            return (Criteria) this;
        }

        public Criteria andLessonPidNotIn(List<Integer> values) {
            addCriterion("lesson_pid not in", values, "lessonPid");
            return (Criteria) this;
        }

        public Criteria andLessonPidBetween(Integer value1, Integer value2) {
            addCriterion("lesson_pid between", value1, value2, "lessonPid");
            return (Criteria) this;
        }

        public Criteria andLessonPidNotBetween(Integer value1, Integer value2) {
            addCriterion("lesson_pid not between", value1, value2, "lessonPid");
            return (Criteria) this;
        }

        public Criteria andLessonTitleIsNull() {
            addCriterion("lesson_title is null");
            return (Criteria) this;
        }

        public Criteria andLessonTitleIsNotNull() {
            addCriterion("lesson_title is not null");
            return (Criteria) this;
        }

        public Criteria andLessonTitleEqualTo(String value) {
            addCriterion("lesson_title =", value, "lessonTitle");
            return (Criteria) this;
        }

        public Criteria andLessonTitleNotEqualTo(String value) {
            addCriterion("lesson_title <>", value, "lessonTitle");
            return (Criteria) this;
        }

        public Criteria andLessonTitleGreaterThan(String value) {
            addCriterion("lesson_title >", value, "lessonTitle");
            return (Criteria) this;
        }

        public Criteria andLessonTitleGreaterThanOrEqualTo(String value) {
            addCriterion("lesson_title >=", value, "lessonTitle");
            return (Criteria) this;
        }

        public Criteria andLessonTitleLessThan(String value) {
            addCriterion("lesson_title <", value, "lessonTitle");
            return (Criteria) this;
        }

        public Criteria andLessonTitleLessThanOrEqualTo(String value) {
            addCriterion("lesson_title <=", value, "lessonTitle");
            return (Criteria) this;
        }

        public Criteria andLessonTitleLike(String value) {
            addCriterion("lesson_title like", value, "lessonTitle");
            return (Criteria) this;
        }

        public Criteria andLessonTitleNotLike(String value) {
            addCriterion("lesson_title not like", value, "lessonTitle");
            return (Criteria) this;
        }

        public Criteria andLessonTitleIn(List<String> values) {
            addCriterion("lesson_title in", values, "lessonTitle");
            return (Criteria) this;
        }

        public Criteria andLessonTitleNotIn(List<String> values) {
            addCriterion("lesson_title not in", values, "lessonTitle");
            return (Criteria) this;
        }

        public Criteria andLessonTitleBetween(String value1, String value2) {
            addCriterion("lesson_title between", value1, value2, "lessonTitle");
            return (Criteria) this;
        }

        public Criteria andLessonTitleNotBetween(String value1, String value2) {
            addCriterion("lesson_title not between", value1, value2, "lessonTitle");
            return (Criteria) this;
        }

        public Criteria andLessonIsChapterHeadIsNull() {
            addCriterion("lesson_is_chapter_head is null");
            return (Criteria) this;
        }

        public Criteria andLessonIsChapterHeadIsNotNull() {
            addCriterion("lesson_is_chapter_head is not null");
            return (Criteria) this;
        }

        public Criteria andLessonIsChapterHeadEqualTo(Boolean value) {
            addCriterion("lesson_is_chapter_head =", value, "lessonIsChapterHead");
            return (Criteria) this;
        }

        public Criteria andLessonIsChapterHeadNotEqualTo(Boolean value) {
            addCriterion("lesson_is_chapter_head <>", value, "lessonIsChapterHead");
            return (Criteria) this;
        }

        public Criteria andLessonIsChapterHeadGreaterThan(Boolean value) {
            addCriterion("lesson_is_chapter_head >", value, "lessonIsChapterHead");
            return (Criteria) this;
        }

        public Criteria andLessonIsChapterHeadGreaterThanOrEqualTo(Boolean value) {
            addCriterion("lesson_is_chapter_head >=", value, "lessonIsChapterHead");
            return (Criteria) this;
        }

        public Criteria andLessonIsChapterHeadLessThan(Boolean value) {
            addCriterion("lesson_is_chapter_head <", value, "lessonIsChapterHead");
            return (Criteria) this;
        }

        public Criteria andLessonIsChapterHeadLessThanOrEqualTo(Boolean value) {
            addCriterion("lesson_is_chapter_head <=", value, "lessonIsChapterHead");
            return (Criteria) this;
        }

        public Criteria andLessonIsChapterHeadIn(List<Boolean> values) {
            addCriterion("lesson_is_chapter_head in", values, "lessonIsChapterHead");
            return (Criteria) this;
        }

        public Criteria andLessonIsChapterHeadNotIn(List<Boolean> values) {
            addCriterion("lesson_is_chapter_head not in", values, "lessonIsChapterHead");
            return (Criteria) this;
        }

        public Criteria andLessonIsChapterHeadBetween(Boolean value1, Boolean value2) {
            addCriterion("lesson_is_chapter_head between", value1, value2, "lessonIsChapterHead");
            return (Criteria) this;
        }

        public Criteria andLessonIsChapterHeadNotBetween(Boolean value1, Boolean value2) {
            addCriterion("lesson_is_chapter_head not between", value1, value2, "lessonIsChapterHead");
            return (Criteria) this;
        }

        public Criteria andLessonCourseIdIsNull() {
            addCriterion("lesson_course_id is null");
            return (Criteria) this;
        }

        public Criteria andLessonCourseIdIsNotNull() {
            addCriterion("lesson_course_id is not null");
            return (Criteria) this;
        }

        public Criteria andLessonCourseIdEqualTo(Integer value) {
            addCriterion("lesson_course_id =", value, "lessonCourseId");
            return (Criteria) this;
        }

        public Criteria andLessonCourseIdNotEqualTo(Integer value) {
            addCriterion("lesson_course_id <>", value, "lessonCourseId");
            return (Criteria) this;
        }

        public Criteria andLessonCourseIdGreaterThan(Integer value) {
            addCriterion("lesson_course_id >", value, "lessonCourseId");
            return (Criteria) this;
        }

        public Criteria andLessonCourseIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("lesson_course_id >=", value, "lessonCourseId");
            return (Criteria) this;
        }

        public Criteria andLessonCourseIdLessThan(Integer value) {
            addCriterion("lesson_course_id <", value, "lessonCourseId");
            return (Criteria) this;
        }

        public Criteria andLessonCourseIdLessThanOrEqualTo(Integer value) {
            addCriterion("lesson_course_id <=", value, "lessonCourseId");
            return (Criteria) this;
        }

        public Criteria andLessonCourseIdIn(List<Integer> values) {
            addCriterion("lesson_course_id in", values, "lessonCourseId");
            return (Criteria) this;
        }

        public Criteria andLessonCourseIdNotIn(List<Integer> values) {
            addCriterion("lesson_course_id not in", values, "lessonCourseId");
            return (Criteria) this;
        }

        public Criteria andLessonCourseIdBetween(Integer value1, Integer value2) {
            addCriterion("lesson_course_id between", value1, value2, "lessonCourseId");
            return (Criteria) this;
        }

        public Criteria andLessonCourseIdNotBetween(Integer value1, Integer value2) {
            addCriterion("lesson_course_id not between", value1, value2, "lessonCourseId");
            return (Criteria) this;
        }

        public Criteria andLessonIsDeletedIsNull() {
            addCriterion("lesson_is_deleted is null");
            return (Criteria) this;
        }

        public Criteria andLessonIsDeletedIsNotNull() {
            addCriterion("lesson_is_deleted is not null");
            return (Criteria) this;
        }

        public Criteria andLessonIsDeletedEqualTo(Integer value) {
            addCriterion("lesson_is_deleted =", value, "lessonIsDeleted");
            return (Criteria) this;
        }

        public Criteria andLessonIsDeletedNotEqualTo(Integer value) {
            addCriterion("lesson_is_deleted <>", value, "lessonIsDeleted");
            return (Criteria) this;
        }

        public Criteria andLessonIsDeletedGreaterThan(Integer value) {
            addCriterion("lesson_is_deleted >", value, "lessonIsDeleted");
            return (Criteria) this;
        }

        public Criteria andLessonIsDeletedGreaterThanOrEqualTo(Integer value) {
            addCriterion("lesson_is_deleted >=", value, "lessonIsDeleted");
            return (Criteria) this;
        }

        public Criteria andLessonIsDeletedLessThan(Integer value) {
            addCriterion("lesson_is_deleted <", value, "lessonIsDeleted");
            return (Criteria) this;
        }

        public Criteria andLessonIsDeletedLessThanOrEqualTo(Integer value) {
            addCriterion("lesson_is_deleted <=", value, "lessonIsDeleted");
            return (Criteria) this;
        }

        public Criteria andLessonIsDeletedIn(List<Integer> values) {
            addCriterion("lesson_is_deleted in", values, "lessonIsDeleted");
            return (Criteria) this;
        }

        public Criteria andLessonIsDeletedNotIn(List<Integer> values) {
            addCriterion("lesson_is_deleted not in", values, "lessonIsDeleted");
            return (Criteria) this;
        }

        public Criteria andLessonIsDeletedBetween(Integer value1, Integer value2) {
            addCriterion("lesson_is_deleted between", value1, value2, "lessonIsDeleted");
            return (Criteria) this;
        }

        public Criteria andLessonIsDeletedNotBetween(Integer value1, Integer value2) {
            addCriterion("lesson_is_deleted not between", value1, value2, "lessonIsDeleted");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table vpro_courses_lesson_list
     *
     * @mbg.generated do_not_delete_during_merge Mon Dec 17 20:27:24 CST 2018
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table vpro_courses_lesson_list
     *
     * @mbg.generated Mon Dec 17 20:27:24 CST 2018
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}
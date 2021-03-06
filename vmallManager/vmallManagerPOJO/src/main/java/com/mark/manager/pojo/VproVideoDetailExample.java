package com.mark.manager.pojo;

import java.util.ArrayList;
import java.util.List;

public class VproVideoDetailExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table vpro_video_detail
     *
     * @mbg.generated Sat Dec 01 12:09:12 CST 2018
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table vpro_video_detail
     *
     * @mbg.generated Sat Dec 01 12:09:12 CST 2018
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table vpro_video_detail
     *
     * @mbg.generated Sat Dec 01 12:09:12 CST 2018
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_video_detail
     *
     * @mbg.generated Sat Dec 01 12:09:12 CST 2018
     */
    public VproVideoDetailExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_video_detail
     *
     * @mbg.generated Sat Dec 01 12:09:12 CST 2018
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_video_detail
     *
     * @mbg.generated Sat Dec 01 12:09:12 CST 2018
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_video_detail
     *
     * @mbg.generated Sat Dec 01 12:09:12 CST 2018
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_video_detail
     *
     * @mbg.generated Sat Dec 01 12:09:12 CST 2018
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_video_detail
     *
     * @mbg.generated Sat Dec 01 12:09:12 CST 2018
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_video_detail
     *
     * @mbg.generated Sat Dec 01 12:09:12 CST 2018
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_video_detail
     *
     * @mbg.generated Sat Dec 01 12:09:12 CST 2018
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_video_detail
     *
     * @mbg.generated Sat Dec 01 12:09:12 CST 2018
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
     * This method corresponds to the database table vpro_video_detail
     *
     * @mbg.generated Sat Dec 01 12:09:12 CST 2018
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_video_detail
     *
     * @mbg.generated Sat Dec 01 12:09:12 CST 2018
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table vpro_video_detail
     *
     * @mbg.generated Sat Dec 01 12:09:12 CST 2018
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

        public Criteria andDetailIdIsNull() {
            addCriterion("detail_id is null");
            return (Criteria) this;
        }

        public Criteria andDetailIdIsNotNull() {
            addCriterion("detail_id is not null");
            return (Criteria) this;
        }

        public Criteria andDetailIdEqualTo(Integer value) {
            addCriterion("detail_id =", value, "detailId");
            return (Criteria) this;
        }

        public Criteria andDetailIdNotEqualTo(Integer value) {
            addCriterion("detail_id <>", value, "detailId");
            return (Criteria) this;
        }

        public Criteria andDetailIdGreaterThan(Integer value) {
            addCriterion("detail_id >", value, "detailId");
            return (Criteria) this;
        }

        public Criteria andDetailIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("detail_id >=", value, "detailId");
            return (Criteria) this;
        }

        public Criteria andDetailIdLessThan(Integer value) {
            addCriterion("detail_id <", value, "detailId");
            return (Criteria) this;
        }

        public Criteria andDetailIdLessThanOrEqualTo(Integer value) {
            addCriterion("detail_id <=", value, "detailId");
            return (Criteria) this;
        }

        public Criteria andDetailIdIn(List<Integer> values) {
            addCriterion("detail_id in", values, "detailId");
            return (Criteria) this;
        }

        public Criteria andDetailIdNotIn(List<Integer> values) {
            addCriterion("detail_id not in", values, "detailId");
            return (Criteria) this;
        }

        public Criteria andDetailIdBetween(Integer value1, Integer value2) {
            addCriterion("detail_id between", value1, value2, "detailId");
            return (Criteria) this;
        }

        public Criteria andDetailIdNotBetween(Integer value1, Integer value2) {
            addCriterion("detail_id not between", value1, value2, "detailId");
            return (Criteria) this;
        }

        public Criteria andDetailLessonIdIsNull() {
            addCriterion("detail_lesson_id is null");
            return (Criteria) this;
        }

        public Criteria andDetailLessonIdIsNotNull() {
            addCriterion("detail_lesson_id is not null");
            return (Criteria) this;
        }

        public Criteria andDetailLessonIdEqualTo(Integer value) {
            addCriterion("detail_lesson_id =", value, "detailLessonId");
            return (Criteria) this;
        }

        public Criteria andDetailLessonIdNotEqualTo(Integer value) {
            addCriterion("detail_lesson_id <>", value, "detailLessonId");
            return (Criteria) this;
        }

        public Criteria andDetailLessonIdGreaterThan(Integer value) {
            addCriterion("detail_lesson_id >", value, "detailLessonId");
            return (Criteria) this;
        }

        public Criteria andDetailLessonIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("detail_lesson_id >=", value, "detailLessonId");
            return (Criteria) this;
        }

        public Criteria andDetailLessonIdLessThan(Integer value) {
            addCriterion("detail_lesson_id <", value, "detailLessonId");
            return (Criteria) this;
        }

        public Criteria andDetailLessonIdLessThanOrEqualTo(Integer value) {
            addCriterion("detail_lesson_id <=", value, "detailLessonId");
            return (Criteria) this;
        }

        public Criteria andDetailLessonIdIn(List<Integer> values) {
            addCriterion("detail_lesson_id in", values, "detailLessonId");
            return (Criteria) this;
        }

        public Criteria andDetailLessonIdNotIn(List<Integer> values) {
            addCriterion("detail_lesson_id not in", values, "detailLessonId");
            return (Criteria) this;
        }

        public Criteria andDetailLessonIdBetween(Integer value1, Integer value2) {
            addCriterion("detail_lesson_id between", value1, value2, "detailLessonId");
            return (Criteria) this;
        }

        public Criteria andDetailLessonIdNotBetween(Integer value1, Integer value2) {
            addCriterion("detail_lesson_id not between", value1, value2, "detailLessonId");
            return (Criteria) this;
        }

        public Criteria andDetailFavoriteIsNull() {
            addCriterion("detail_favorite is null");
            return (Criteria) this;
        }

        public Criteria andDetailFavoriteIsNotNull() {
            addCriterion("detail_favorite is not null");
            return (Criteria) this;
        }

        public Criteria andDetailFavoriteEqualTo(Integer value) {
            addCriterion("detail_favorite =", value, "detailFavorite");
            return (Criteria) this;
        }

        public Criteria andDetailFavoriteNotEqualTo(Integer value) {
            addCriterion("detail_favorite <>", value, "detailFavorite");
            return (Criteria) this;
        }

        public Criteria andDetailFavoriteGreaterThan(Integer value) {
            addCriterion("detail_favorite >", value, "detailFavorite");
            return (Criteria) this;
        }

        public Criteria andDetailFavoriteGreaterThanOrEqualTo(Integer value) {
            addCriterion("detail_favorite >=", value, "detailFavorite");
            return (Criteria) this;
        }

        public Criteria andDetailFavoriteLessThan(Integer value) {
            addCriterion("detail_favorite <", value, "detailFavorite");
            return (Criteria) this;
        }

        public Criteria andDetailFavoriteLessThanOrEqualTo(Integer value) {
            addCriterion("detail_favorite <=", value, "detailFavorite");
            return (Criteria) this;
        }

        public Criteria andDetailFavoriteIn(List<Integer> values) {
            addCriterion("detail_favorite in", values, "detailFavorite");
            return (Criteria) this;
        }

        public Criteria andDetailFavoriteNotIn(List<Integer> values) {
            addCriterion("detail_favorite not in", values, "detailFavorite");
            return (Criteria) this;
        }

        public Criteria andDetailFavoriteBetween(Integer value1, Integer value2) {
            addCriterion("detail_favorite between", value1, value2, "detailFavorite");
            return (Criteria) this;
        }

        public Criteria andDetailFavoriteNotBetween(Integer value1, Integer value2) {
            addCriterion("detail_favorite not between", value1, value2, "detailFavorite");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table vpro_video_detail
     *
     * @mbg.generated do_not_delete_during_merge Sat Dec 01 12:09:12 CST 2018
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table vpro_video_detail
     *
     * @mbg.generated Sat Dec 01 12:09:12 CST 2018
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
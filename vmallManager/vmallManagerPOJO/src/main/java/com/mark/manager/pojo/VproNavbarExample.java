package com.mark.manager.pojo;

import java.util.ArrayList;
import java.util.List;

public class VproNavbarExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table vpro_navbar
     *
     * @mbg.generated Wed Oct 17 23:12:35 CST 2018
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table vpro_navbar
     *
     * @mbg.generated Wed Oct 17 23:12:35 CST 2018
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table vpro_navbar
     *
     * @mbg.generated Wed Oct 17 23:12:35 CST 2018
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_navbar
     *
     * @mbg.generated Wed Oct 17 23:12:35 CST 2018
     */
    public VproNavbarExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_navbar
     *
     * @mbg.generated Wed Oct 17 23:12:35 CST 2018
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_navbar
     *
     * @mbg.generated Wed Oct 17 23:12:35 CST 2018
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_navbar
     *
     * @mbg.generated Wed Oct 17 23:12:35 CST 2018
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_navbar
     *
     * @mbg.generated Wed Oct 17 23:12:35 CST 2018
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_navbar
     *
     * @mbg.generated Wed Oct 17 23:12:35 CST 2018
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_navbar
     *
     * @mbg.generated Wed Oct 17 23:12:35 CST 2018
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_navbar
     *
     * @mbg.generated Wed Oct 17 23:12:35 CST 2018
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_navbar
     *
     * @mbg.generated Wed Oct 17 23:12:35 CST 2018
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
     * This method corresponds to the database table vpro_navbar
     *
     * @mbg.generated Wed Oct 17 23:12:35 CST 2018
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_navbar
     *
     * @mbg.generated Wed Oct 17 23:12:35 CST 2018
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table vpro_navbar
     *
     * @mbg.generated Wed Oct 17 23:12:35 CST 2018
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

        public Criteria andNavIdIsNull() {
            addCriterion("nav_id is null");
            return (Criteria) this;
        }

        public Criteria andNavIdIsNotNull() {
            addCriterion("nav_id is not null");
            return (Criteria) this;
        }

        public Criteria andNavIdEqualTo(Integer value) {
            addCriterion("nav_id =", value, "navId");
            return (Criteria) this;
        }

        public Criteria andNavIdNotEqualTo(Integer value) {
            addCriterion("nav_id <>", value, "navId");
            return (Criteria) this;
        }

        public Criteria andNavIdGreaterThan(Integer value) {
            addCriterion("nav_id >", value, "navId");
            return (Criteria) this;
        }

        public Criteria andNavIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("nav_id >=", value, "navId");
            return (Criteria) this;
        }

        public Criteria andNavIdLessThan(Integer value) {
            addCriterion("nav_id <", value, "navId");
            return (Criteria) this;
        }

        public Criteria andNavIdLessThanOrEqualTo(Integer value) {
            addCriterion("nav_id <=", value, "navId");
            return (Criteria) this;
        }

        public Criteria andNavIdIn(List<Integer> values) {
            addCriterion("nav_id in", values, "navId");
            return (Criteria) this;
        }

        public Criteria andNavIdNotIn(List<Integer> values) {
            addCriterion("nav_id not in", values, "navId");
            return (Criteria) this;
        }

        public Criteria andNavIdBetween(Integer value1, Integer value2) {
            addCriterion("nav_id between", value1, value2, "navId");
            return (Criteria) this;
        }

        public Criteria andNavIdNotBetween(Integer value1, Integer value2) {
            addCriterion("nav_id not between", value1, value2, "navId");
            return (Criteria) this;
        }

        public Criteria andNavTextIsNull() {
            addCriterion("nav_text is null");
            return (Criteria) this;
        }

        public Criteria andNavTextIsNotNull() {
            addCriterion("nav_text is not null");
            return (Criteria) this;
        }

        public Criteria andNavTextEqualTo(String value) {
            addCriterion("nav_text =", value, "navText");
            return (Criteria) this;
        }

        public Criteria andNavTextNotEqualTo(String value) {
            addCriterion("nav_text <>", value, "navText");
            return (Criteria) this;
        }

        public Criteria andNavTextGreaterThan(String value) {
            addCriterion("nav_text >", value, "navText");
            return (Criteria) this;
        }

        public Criteria andNavTextGreaterThanOrEqualTo(String value) {
            addCriterion("nav_text >=", value, "navText");
            return (Criteria) this;
        }

        public Criteria andNavTextLessThan(String value) {
            addCriterion("nav_text <", value, "navText");
            return (Criteria) this;
        }

        public Criteria andNavTextLessThanOrEqualTo(String value) {
            addCriterion("nav_text <=", value, "navText");
            return (Criteria) this;
        }

        public Criteria andNavTextLike(String value) {
            addCriterion("nav_text like", value, "navText");
            return (Criteria) this;
        }

        public Criteria andNavTextNotLike(String value) {
            addCriterion("nav_text not like", value, "navText");
            return (Criteria) this;
        }

        public Criteria andNavTextIn(List<String> values) {
            addCriterion("nav_text in", values, "navText");
            return (Criteria) this;
        }

        public Criteria andNavTextNotIn(List<String> values) {
            addCriterion("nav_text not in", values, "navText");
            return (Criteria) this;
        }

        public Criteria andNavTextBetween(String value1, String value2) {
            addCriterion("nav_text between", value1, value2, "navText");
            return (Criteria) this;
        }

        public Criteria andNavTextNotBetween(String value1, String value2) {
            addCriterion("nav_text not between", value1, value2, "navText");
            return (Criteria) this;
        }

        public Criteria andNavUrlIsNull() {
            addCriterion("nav_url is null");
            return (Criteria) this;
        }

        public Criteria andNavUrlIsNotNull() {
            addCriterion("nav_url is not null");
            return (Criteria) this;
        }

        public Criteria andNavUrlEqualTo(String value) {
            addCriterion("nav_url =", value, "navUrl");
            return (Criteria) this;
        }

        public Criteria andNavUrlNotEqualTo(String value) {
            addCriterion("nav_url <>", value, "navUrl");
            return (Criteria) this;
        }

        public Criteria andNavUrlGreaterThan(String value) {
            addCriterion("nav_url >", value, "navUrl");
            return (Criteria) this;
        }

        public Criteria andNavUrlGreaterThanOrEqualTo(String value) {
            addCriterion("nav_url >=", value, "navUrl");
            return (Criteria) this;
        }

        public Criteria andNavUrlLessThan(String value) {
            addCriterion("nav_url <", value, "navUrl");
            return (Criteria) this;
        }

        public Criteria andNavUrlLessThanOrEqualTo(String value) {
            addCriterion("nav_url <=", value, "navUrl");
            return (Criteria) this;
        }

        public Criteria andNavUrlLike(String value) {
            addCriterion("nav_url like", value, "navUrl");
            return (Criteria) this;
        }

        public Criteria andNavUrlNotLike(String value) {
            addCriterion("nav_url not like", value, "navUrl");
            return (Criteria) this;
        }

        public Criteria andNavUrlIn(List<String> values) {
            addCriterion("nav_url in", values, "navUrl");
            return (Criteria) this;
        }

        public Criteria andNavUrlNotIn(List<String> values) {
            addCriterion("nav_url not in", values, "navUrl");
            return (Criteria) this;
        }

        public Criteria andNavUrlBetween(String value1, String value2) {
            addCriterion("nav_url between", value1, value2, "navUrl");
            return (Criteria) this;
        }

        public Criteria andNavUrlNotBetween(String value1, String value2) {
            addCriterion("nav_url not between", value1, value2, "navUrl");
            return (Criteria) this;
        }

        public Criteria andNavPidIsNull() {
            addCriterion("nav_pid is null");
            return (Criteria) this;
        }

        public Criteria andNavPidIsNotNull() {
            addCriterion("nav_pid is not null");
            return (Criteria) this;
        }

        public Criteria andNavPidEqualTo(Integer value) {
            addCriterion("nav_pid =", value, "navPid");
            return (Criteria) this;
        }

        public Criteria andNavPidNotEqualTo(Integer value) {
            addCriterion("nav_pid <>", value, "navPid");
            return (Criteria) this;
        }

        public Criteria andNavPidGreaterThan(Integer value) {
            addCriterion("nav_pid >", value, "navPid");
            return (Criteria) this;
        }

        public Criteria andNavPidGreaterThanOrEqualTo(Integer value) {
            addCriterion("nav_pid >=", value, "navPid");
            return (Criteria) this;
        }

        public Criteria andNavPidLessThan(Integer value) {
            addCriterion("nav_pid <", value, "navPid");
            return (Criteria) this;
        }

        public Criteria andNavPidLessThanOrEqualTo(Integer value) {
            addCriterion("nav_pid <=", value, "navPid");
            return (Criteria) this;
        }

        public Criteria andNavPidIn(List<Integer> values) {
            addCriterion("nav_pid in", values, "navPid");
            return (Criteria) this;
        }

        public Criteria andNavPidNotIn(List<Integer> values) {
            addCriterion("nav_pid not in", values, "navPid");
            return (Criteria) this;
        }

        public Criteria andNavPidBetween(Integer value1, Integer value2) {
            addCriterion("nav_pid between", value1, value2, "navPid");
            return (Criteria) this;
        }

        public Criteria andNavPidNotBetween(Integer value1, Integer value2) {
            addCriterion("nav_pid not between", value1, value2, "navPid");
            return (Criteria) this;
        }

        public Criteria andNavNicknameIsNull() {
            addCriterion("nav_nickname is null");
            return (Criteria) this;
        }

        public Criteria andNavNicknameIsNotNull() {
            addCriterion("nav_nickname is not null");
            return (Criteria) this;
        }

        public Criteria andNavNicknameEqualTo(String value) {
            addCriterion("nav_nickname =", value, "navNickname");
            return (Criteria) this;
        }

        public Criteria andNavNicknameNotEqualTo(String value) {
            addCriterion("nav_nickname <>", value, "navNickname");
            return (Criteria) this;
        }

        public Criteria andNavNicknameGreaterThan(String value) {
            addCriterion("nav_nickname >", value, "navNickname");
            return (Criteria) this;
        }

        public Criteria andNavNicknameGreaterThanOrEqualTo(String value) {
            addCriterion("nav_nickname >=", value, "navNickname");
            return (Criteria) this;
        }

        public Criteria andNavNicknameLessThan(String value) {
            addCriterion("nav_nickname <", value, "navNickname");
            return (Criteria) this;
        }

        public Criteria andNavNicknameLessThanOrEqualTo(String value) {
            addCriterion("nav_nickname <=", value, "navNickname");
            return (Criteria) this;
        }

        public Criteria andNavNicknameLike(String value) {
            addCriterion("nav_nickname like", value, "navNickname");
            return (Criteria) this;
        }

        public Criteria andNavNicknameNotLike(String value) {
            addCriterion("nav_nickname not like", value, "navNickname");
            return (Criteria) this;
        }

        public Criteria andNavNicknameIn(List<String> values) {
            addCriterion("nav_nickname in", values, "navNickname");
            return (Criteria) this;
        }

        public Criteria andNavNicknameNotIn(List<String> values) {
            addCriterion("nav_nickname not in", values, "navNickname");
            return (Criteria) this;
        }

        public Criteria andNavNicknameBetween(String value1, String value2) {
            addCriterion("nav_nickname between", value1, value2, "navNickname");
            return (Criteria) this;
        }

        public Criteria andNavNicknameNotBetween(String value1, String value2) {
            addCriterion("nav_nickname not between", value1, value2, "navNickname");
            return (Criteria) this;
        }

        public Criteria andNavStatusIsNull() {
            addCriterion("nav_status is null");
            return (Criteria) this;
        }

        public Criteria andNavStatusIsNotNull() {
            addCriterion("nav_status is not null");
            return (Criteria) this;
        }

        public Criteria andNavStatusEqualTo(Boolean value) {
            addCriterion("nav_status =", value, "navStatus");
            return (Criteria) this;
        }

        public Criteria andNavStatusNotEqualTo(Boolean value) {
            addCriterion("nav_status <>", value, "navStatus");
            return (Criteria) this;
        }

        public Criteria andNavStatusGreaterThan(Boolean value) {
            addCriterion("nav_status >", value, "navStatus");
            return (Criteria) this;
        }

        public Criteria andNavStatusGreaterThanOrEqualTo(Boolean value) {
            addCriterion("nav_status >=", value, "navStatus");
            return (Criteria) this;
        }

        public Criteria andNavStatusLessThan(Boolean value) {
            addCriterion("nav_status <", value, "navStatus");
            return (Criteria) this;
        }

        public Criteria andNavStatusLessThanOrEqualTo(Boolean value) {
            addCriterion("nav_status <=", value, "navStatus");
            return (Criteria) this;
        }

        public Criteria andNavStatusIn(List<Boolean> values) {
            addCriterion("nav_status in", values, "navStatus");
            return (Criteria) this;
        }

        public Criteria andNavStatusNotIn(List<Boolean> values) {
            addCriterion("nav_status not in", values, "navStatus");
            return (Criteria) this;
        }

        public Criteria andNavStatusBetween(Boolean value1, Boolean value2) {
            addCriterion("nav_status between", value1, value2, "navStatus");
            return (Criteria) this;
        }

        public Criteria andNavStatusNotBetween(Boolean value1, Boolean value2) {
            addCriterion("nav_status not between", value1, value2, "navStatus");
            return (Criteria) this;
        }

        public Criteria andNavIsParentIsNull() {
            addCriterion("nav_is_parent is null");
            return (Criteria) this;
        }

        public Criteria andNavIsParentIsNotNull() {
            addCriterion("nav_is_parent is not null");
            return (Criteria) this;
        }

        public Criteria andNavIsParentEqualTo(Boolean value) {
            addCriterion("nav_is_parent =", value, "navIsParent");
            return (Criteria) this;
        }

        public Criteria andNavIsParentNotEqualTo(Boolean value) {
            addCriterion("nav_is_parent <>", value, "navIsParent");
            return (Criteria) this;
        }

        public Criteria andNavIsParentGreaterThan(Boolean value) {
            addCriterion("nav_is_parent >", value, "navIsParent");
            return (Criteria) this;
        }

        public Criteria andNavIsParentGreaterThanOrEqualTo(Boolean value) {
            addCriterion("nav_is_parent >=", value, "navIsParent");
            return (Criteria) this;
        }

        public Criteria andNavIsParentLessThan(Boolean value) {
            addCriterion("nav_is_parent <", value, "navIsParent");
            return (Criteria) this;
        }

        public Criteria andNavIsParentLessThanOrEqualTo(Boolean value) {
            addCriterion("nav_is_parent <=", value, "navIsParent");
            return (Criteria) this;
        }

        public Criteria andNavIsParentIn(List<Boolean> values) {
            addCriterion("nav_is_parent in", values, "navIsParent");
            return (Criteria) this;
        }

        public Criteria andNavIsParentNotIn(List<Boolean> values) {
            addCriterion("nav_is_parent not in", values, "navIsParent");
            return (Criteria) this;
        }

        public Criteria andNavIsParentBetween(Boolean value1, Boolean value2) {
            addCriterion("nav_is_parent between", value1, value2, "navIsParent");
            return (Criteria) this;
        }

        public Criteria andNavIsParentNotBetween(Boolean value1, Boolean value2) {
            addCriterion("nav_is_parent not between", value1, value2, "navIsParent");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table vpro_navbar
     *
     * @mbg.generated do_not_delete_during_merge Wed Oct 17 23:12:35 CST 2018
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table vpro_navbar
     *
     * @mbg.generated Wed Oct 17 23:12:35 CST 2018
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
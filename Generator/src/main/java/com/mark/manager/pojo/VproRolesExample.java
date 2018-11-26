package com.mark.manager.pojo;

import java.util.ArrayList;
import java.util.List;

public class VproRolesExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table vpro_roles
     *
     * @mbg.generated Thu Oct 18 10:09:12 CST 2018
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table vpro_roles
     *
     * @mbg.generated Thu Oct 18 10:09:12 CST 2018
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table vpro_roles
     *
     * @mbg.generated Thu Oct 18 10:09:12 CST 2018
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_roles
     *
     * @mbg.generated Thu Oct 18 10:09:12 CST 2018
     */
    public VproRolesExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_roles
     *
     * @mbg.generated Thu Oct 18 10:09:12 CST 2018
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_roles
     *
     * @mbg.generated Thu Oct 18 10:09:12 CST 2018
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_roles
     *
     * @mbg.generated Thu Oct 18 10:09:12 CST 2018
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_roles
     *
     * @mbg.generated Thu Oct 18 10:09:12 CST 2018
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_roles
     *
     * @mbg.generated Thu Oct 18 10:09:12 CST 2018
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_roles
     *
     * @mbg.generated Thu Oct 18 10:09:12 CST 2018
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_roles
     *
     * @mbg.generated Thu Oct 18 10:09:12 CST 2018
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_roles
     *
     * @mbg.generated Thu Oct 18 10:09:12 CST 2018
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
     * This method corresponds to the database table vpro_roles
     *
     * @mbg.generated Thu Oct 18 10:09:12 CST 2018
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_roles
     *
     * @mbg.generated Thu Oct 18 10:09:12 CST 2018
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table vpro_roles
     *
     * @mbg.generated Thu Oct 18 10:09:12 CST 2018
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

        public Criteria andRolesIdIsNull() {
            addCriterion("roles_id is null");
            return (Criteria) this;
        }

        public Criteria andRolesIdIsNotNull() {
            addCriterion("roles_id is not null");
            return (Criteria) this;
        }

        public Criteria andRolesIdEqualTo(Integer value) {
            addCriterion("roles_id =", value, "rolesId");
            return (Criteria) this;
        }

        public Criteria andRolesIdNotEqualTo(Integer value) {
            addCriterion("roles_id <>", value, "rolesId");
            return (Criteria) this;
        }

        public Criteria andRolesIdGreaterThan(Integer value) {
            addCriterion("roles_id >", value, "rolesId");
            return (Criteria) this;
        }

        public Criteria andRolesIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("roles_id >=", value, "rolesId");
            return (Criteria) this;
        }

        public Criteria andRolesIdLessThan(Integer value) {
            addCriterion("roles_id <", value, "rolesId");
            return (Criteria) this;
        }

        public Criteria andRolesIdLessThanOrEqualTo(Integer value) {
            addCriterion("roles_id <=", value, "rolesId");
            return (Criteria) this;
        }

        public Criteria andRolesIdIn(List<Integer> values) {
            addCriterion("roles_id in", values, "rolesId");
            return (Criteria) this;
        }

        public Criteria andRolesIdNotIn(List<Integer> values) {
            addCriterion("roles_id not in", values, "rolesId");
            return (Criteria) this;
        }

        public Criteria andRolesIdBetween(Integer value1, Integer value2) {
            addCriterion("roles_id between", value1, value2, "rolesId");
            return (Criteria) this;
        }

        public Criteria andRolesIdNotBetween(Integer value1, Integer value2) {
            addCriterion("roles_id not between", value1, value2, "rolesId");
            return (Criteria) this;
        }

        public Criteria andRolesNameIsNull() {
            addCriterion("roles_name is null");
            return (Criteria) this;
        }

        public Criteria andRolesNameIsNotNull() {
            addCriterion("roles_name is not null");
            return (Criteria) this;
        }

        public Criteria andRolesNameEqualTo(String value) {
            addCriterion("roles_name =", value, "rolesName");
            return (Criteria) this;
        }

        public Criteria andRolesNameNotEqualTo(String value) {
            addCriterion("roles_name <>", value, "rolesName");
            return (Criteria) this;
        }

        public Criteria andRolesNameGreaterThan(String value) {
            addCriterion("roles_name >", value, "rolesName");
            return (Criteria) this;
        }

        public Criteria andRolesNameGreaterThanOrEqualTo(String value) {
            addCriterion("roles_name >=", value, "rolesName");
            return (Criteria) this;
        }

        public Criteria andRolesNameLessThan(String value) {
            addCriterion("roles_name <", value, "rolesName");
            return (Criteria) this;
        }

        public Criteria andRolesNameLessThanOrEqualTo(String value) {
            addCriterion("roles_name <=", value, "rolesName");
            return (Criteria) this;
        }

        public Criteria andRolesNameLike(String value) {
            addCriterion("roles_name like", value, "rolesName");
            return (Criteria) this;
        }

        public Criteria andRolesNameNotLike(String value) {
            addCriterion("roles_name not like", value, "rolesName");
            return (Criteria) this;
        }

        public Criteria andRolesNameIn(List<String> values) {
            addCriterion("roles_name in", values, "rolesName");
            return (Criteria) this;
        }

        public Criteria andRolesNameNotIn(List<String> values) {
            addCriterion("roles_name not in", values, "rolesName");
            return (Criteria) this;
        }

        public Criteria andRolesNameBetween(String value1, String value2) {
            addCriterion("roles_name between", value1, value2, "rolesName");
            return (Criteria) this;
        }

        public Criteria andRolesNameNotBetween(String value1, String value2) {
            addCriterion("roles_name not between", value1, value2, "rolesName");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table vpro_roles
     *
     * @mbg.generated do_not_delete_during_merge Thu Oct 18 10:09:12 CST 2018
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table vpro_roles
     *
     * @mbg.generated Thu Oct 18 10:09:12 CST 2018
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
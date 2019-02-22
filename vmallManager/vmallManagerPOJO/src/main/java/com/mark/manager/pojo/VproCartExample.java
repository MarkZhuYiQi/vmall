package com.mark.manager.pojo;

import java.util.ArrayList;
import java.util.List;

public class VproCartExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table vpro_cart
     *
     * @mbg.generated Thu Feb 21 13:25:44 CST 2019
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table vpro_cart
     *
     * @mbg.generated Thu Feb 21 13:25:44 CST 2019
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table vpro_cart
     *
     * @mbg.generated Thu Feb 21 13:25:44 CST 2019
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_cart
     *
     * @mbg.generated Thu Feb 21 13:25:44 CST 2019
     */
    public VproCartExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_cart
     *
     * @mbg.generated Thu Feb 21 13:25:44 CST 2019
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_cart
     *
     * @mbg.generated Thu Feb 21 13:25:44 CST 2019
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_cart
     *
     * @mbg.generated Thu Feb 21 13:25:44 CST 2019
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_cart
     *
     * @mbg.generated Thu Feb 21 13:25:44 CST 2019
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_cart
     *
     * @mbg.generated Thu Feb 21 13:25:44 CST 2019
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_cart
     *
     * @mbg.generated Thu Feb 21 13:25:44 CST 2019
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_cart
     *
     * @mbg.generated Thu Feb 21 13:25:44 CST 2019
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_cart
     *
     * @mbg.generated Thu Feb 21 13:25:44 CST 2019
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
     * This method corresponds to the database table vpro_cart
     *
     * @mbg.generated Thu Feb 21 13:25:44 CST 2019
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_cart
     *
     * @mbg.generated Thu Feb 21 13:25:44 CST 2019
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table vpro_cart
     *
     * @mbg.generated Thu Feb 21 13:25:44 CST 2019
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

        public Criteria andCartIdIsNull() {
            addCriterion("cart_id is null");
            return (Criteria) this;
        }

        public Criteria andCartIdIsNotNull() {
            addCriterion("cart_id is not null");
            return (Criteria) this;
        }

        public Criteria andCartIdEqualTo(Long value) {
            addCriterion("cart_id =", value, "cartId");
            return (Criteria) this;
        }

        public Criteria andCartIdNotEqualTo(Long value) {
            addCriterion("cart_id <>", value, "cartId");
            return (Criteria) this;
        }

        public Criteria andCartIdGreaterThan(Long value) {
            addCriterion("cart_id >", value, "cartId");
            return (Criteria) this;
        }

        public Criteria andCartIdGreaterThanOrEqualTo(Long value) {
            addCriterion("cart_id >=", value, "cartId");
            return (Criteria) this;
        }

        public Criteria andCartIdLessThan(Long value) {
            addCriterion("cart_id <", value, "cartId");
            return (Criteria) this;
        }

        public Criteria andCartIdLessThanOrEqualTo(Long value) {
            addCriterion("cart_id <=", value, "cartId");
            return (Criteria) this;
        }

        public Criteria andCartIdIn(List<Long> values) {
            addCriterion("cart_id in", values, "cartId");
            return (Criteria) this;
        }

        public Criteria andCartIdNotIn(List<Long> values) {
            addCriterion("cart_id not in", values, "cartId");
            return (Criteria) this;
        }

        public Criteria andCartIdBetween(Long value1, Long value2) {
            addCriterion("cart_id between", value1, value2, "cartId");
            return (Criteria) this;
        }

        public Criteria andCartIdNotBetween(Long value1, Long value2) {
            addCriterion("cart_id not between", value1, value2, "cartId");
            return (Criteria) this;
        }

        public Criteria andCartAddtimeIsNull() {
            addCriterion("cart_addtime is null");
            return (Criteria) this;
        }

        public Criteria andCartAddtimeIsNotNull() {
            addCriterion("cart_addtime is not null");
            return (Criteria) this;
        }

        public Criteria andCartAddtimeEqualTo(String value) {
            addCriterion("cart_addtime =", value, "cartAddtime");
            return (Criteria) this;
        }

        public Criteria andCartAddtimeNotEqualTo(String value) {
            addCriterion("cart_addtime <>", value, "cartAddtime");
            return (Criteria) this;
        }

        public Criteria andCartAddtimeGreaterThan(String value) {
            addCriterion("cart_addtime >", value, "cartAddtime");
            return (Criteria) this;
        }

        public Criteria andCartAddtimeGreaterThanOrEqualTo(String value) {
            addCriterion("cart_addtime >=", value, "cartAddtime");
            return (Criteria) this;
        }

        public Criteria andCartAddtimeLessThan(String value) {
            addCriterion("cart_addtime <", value, "cartAddtime");
            return (Criteria) this;
        }

        public Criteria andCartAddtimeLessThanOrEqualTo(String value) {
            addCriterion("cart_addtime <=", value, "cartAddtime");
            return (Criteria) this;
        }

        public Criteria andCartAddtimeLike(String value) {
            addCriterion("cart_addtime like", value, "cartAddtime");
            return (Criteria) this;
        }

        public Criteria andCartAddtimeNotLike(String value) {
            addCriterion("cart_addtime not like", value, "cartAddtime");
            return (Criteria) this;
        }

        public Criteria andCartAddtimeIn(List<String> values) {
            addCriterion("cart_addtime in", values, "cartAddtime");
            return (Criteria) this;
        }

        public Criteria andCartAddtimeNotIn(List<String> values) {
            addCriterion("cart_addtime not in", values, "cartAddtime");
            return (Criteria) this;
        }

        public Criteria andCartAddtimeBetween(String value1, String value2) {
            addCriterion("cart_addtime between", value1, value2, "cartAddtime");
            return (Criteria) this;
        }

        public Criteria andCartAddtimeNotBetween(String value1, String value2) {
            addCriterion("cart_addtime not between", value1, value2, "cartAddtime");
            return (Criteria) this;
        }

        public Criteria andCartUseridIsNull() {
            addCriterion("cart_userid is null");
            return (Criteria) this;
        }

        public Criteria andCartUseridIsNotNull() {
            addCriterion("cart_userid is not null");
            return (Criteria) this;
        }

        public Criteria andCartUseridEqualTo(Integer value) {
            addCriterion("cart_userid =", value, "cartUserid");
            return (Criteria) this;
        }

        public Criteria andCartUseridNotEqualTo(Integer value) {
            addCriterion("cart_userid <>", value, "cartUserid");
            return (Criteria) this;
        }

        public Criteria andCartUseridGreaterThan(Integer value) {
            addCriterion("cart_userid >", value, "cartUserid");
            return (Criteria) this;
        }

        public Criteria andCartUseridGreaterThanOrEqualTo(Integer value) {
            addCriterion("cart_userid >=", value, "cartUserid");
            return (Criteria) this;
        }

        public Criteria andCartUseridLessThan(Integer value) {
            addCriterion("cart_userid <", value, "cartUserid");
            return (Criteria) this;
        }

        public Criteria andCartUseridLessThanOrEqualTo(Integer value) {
            addCriterion("cart_userid <=", value, "cartUserid");
            return (Criteria) this;
        }

        public Criteria andCartUseridIn(List<Integer> values) {
            addCriterion("cart_userid in", values, "cartUserid");
            return (Criteria) this;
        }

        public Criteria andCartUseridNotIn(List<Integer> values) {
            addCriterion("cart_userid not in", values, "cartUserid");
            return (Criteria) this;
        }

        public Criteria andCartUseridBetween(Integer value1, Integer value2) {
            addCriterion("cart_userid between", value1, value2, "cartUserid");
            return (Criteria) this;
        }

        public Criteria andCartUseridNotBetween(Integer value1, Integer value2) {
            addCriterion("cart_userid not between", value1, value2, "cartUserid");
            return (Criteria) this;
        }

        public Criteria andCartPaymentIsNull() {
            addCriterion("cart_payment is null");
            return (Criteria) this;
        }

        public Criteria andCartPaymentIsNotNull() {
            addCriterion("cart_payment is not null");
            return (Criteria) this;
        }

        public Criteria andCartPaymentEqualTo(String value) {
            addCriterion("cart_payment =", value, "cartPayment");
            return (Criteria) this;
        }

        public Criteria andCartPaymentNotEqualTo(String value) {
            addCriterion("cart_payment <>", value, "cartPayment");
            return (Criteria) this;
        }

        public Criteria andCartPaymentGreaterThan(String value) {
            addCriterion("cart_payment >", value, "cartPayment");
            return (Criteria) this;
        }

        public Criteria andCartPaymentGreaterThanOrEqualTo(String value) {
            addCriterion("cart_payment >=", value, "cartPayment");
            return (Criteria) this;
        }

        public Criteria andCartPaymentLessThan(String value) {
            addCriterion("cart_payment <", value, "cartPayment");
            return (Criteria) this;
        }

        public Criteria andCartPaymentLessThanOrEqualTo(String value) {
            addCriterion("cart_payment <=", value, "cartPayment");
            return (Criteria) this;
        }

        public Criteria andCartPaymentLike(String value) {
            addCriterion("cart_payment like", value, "cartPayment");
            return (Criteria) this;
        }

        public Criteria andCartPaymentNotLike(String value) {
            addCriterion("cart_payment not like", value, "cartPayment");
            return (Criteria) this;
        }

        public Criteria andCartPaymentIn(List<String> values) {
            addCriterion("cart_payment in", values, "cartPayment");
            return (Criteria) this;
        }

        public Criteria andCartPaymentNotIn(List<String> values) {
            addCriterion("cart_payment not in", values, "cartPayment");
            return (Criteria) this;
        }

        public Criteria andCartPaymentBetween(String value1, String value2) {
            addCriterion("cart_payment between", value1, value2, "cartPayment");
            return (Criteria) this;
        }

        public Criteria andCartPaymentNotBetween(String value1, String value2) {
            addCriterion("cart_payment not between", value1, value2, "cartPayment");
            return (Criteria) this;
        }

        public Criteria andCartStatusIsNull() {
            addCriterion("cart_status is null");
            return (Criteria) this;
        }

        public Criteria andCartStatusIsNotNull() {
            addCriterion("cart_status is not null");
            return (Criteria) this;
        }

        public Criteria andCartStatusEqualTo(String value) {
            addCriterion("cart_status =", value, "cartStatus");
            return (Criteria) this;
        }

        public Criteria andCartStatusNotEqualTo(String value) {
            addCriterion("cart_status <>", value, "cartStatus");
            return (Criteria) this;
        }

        public Criteria andCartStatusGreaterThan(String value) {
            addCriterion("cart_status >", value, "cartStatus");
            return (Criteria) this;
        }

        public Criteria andCartStatusGreaterThanOrEqualTo(String value) {
            addCriterion("cart_status >=", value, "cartStatus");
            return (Criteria) this;
        }

        public Criteria andCartStatusLessThan(String value) {
            addCriterion("cart_status <", value, "cartStatus");
            return (Criteria) this;
        }

        public Criteria andCartStatusLessThanOrEqualTo(String value) {
            addCriterion("cart_status <=", value, "cartStatus");
            return (Criteria) this;
        }

        public Criteria andCartStatusLike(String value) {
            addCriterion("cart_status like", value, "cartStatus");
            return (Criteria) this;
        }

        public Criteria andCartStatusNotLike(String value) {
            addCriterion("cart_status not like", value, "cartStatus");
            return (Criteria) this;
        }

        public Criteria andCartStatusIn(List<String> values) {
            addCriterion("cart_status in", values, "cartStatus");
            return (Criteria) this;
        }

        public Criteria andCartStatusNotIn(List<String> values) {
            addCriterion("cart_status not in", values, "cartStatus");
            return (Criteria) this;
        }

        public Criteria andCartStatusBetween(String value1, String value2) {
            addCriterion("cart_status between", value1, value2, "cartStatus");
            return (Criteria) this;
        }

        public Criteria andCartStatusNotBetween(String value1, String value2) {
            addCriterion("cart_status not between", value1, value2, "cartStatus");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table vpro_cart
     *
     * @mbg.generated do_not_delete_during_merge Thu Feb 21 13:25:44 CST 2019
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table vpro_cart
     *
     * @mbg.generated Thu Feb 21 13:25:44 CST 2019
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
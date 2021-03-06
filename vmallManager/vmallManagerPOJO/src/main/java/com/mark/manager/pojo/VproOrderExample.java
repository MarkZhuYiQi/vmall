package com.mark.manager.pojo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class VproOrderExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table vpro_order
     *
     * @mbg.generated Thu Feb 28 12:48:35 CST 2019
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table vpro_order
     *
     * @mbg.generated Thu Feb 28 12:48:35 CST 2019
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table vpro_order
     *
     * @mbg.generated Thu Feb 28 12:48:35 CST 2019
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_order
     *
     * @mbg.generated Thu Feb 28 12:48:35 CST 2019
     */
    public VproOrderExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_order
     *
     * @mbg.generated Thu Feb 28 12:48:35 CST 2019
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_order
     *
     * @mbg.generated Thu Feb 28 12:48:35 CST 2019
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_order
     *
     * @mbg.generated Thu Feb 28 12:48:35 CST 2019
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_order
     *
     * @mbg.generated Thu Feb 28 12:48:35 CST 2019
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_order
     *
     * @mbg.generated Thu Feb 28 12:48:35 CST 2019
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_order
     *
     * @mbg.generated Thu Feb 28 12:48:35 CST 2019
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_order
     *
     * @mbg.generated Thu Feb 28 12:48:35 CST 2019
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_order
     *
     * @mbg.generated Thu Feb 28 12:48:35 CST 2019
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
     * This method corresponds to the database table vpro_order
     *
     * @mbg.generated Thu Feb 28 12:48:35 CST 2019
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_order
     *
     * @mbg.generated Thu Feb 28 12:48:35 CST 2019
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table vpro_order
     *
     * @mbg.generated Thu Feb 28 12:48:35 CST 2019
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

        public Criteria andOrderIdIsNull() {
            addCriterion("order_id is null");
            return (Criteria) this;
        }

        public Criteria andOrderIdIsNotNull() {
            addCriterion("order_id is not null");
            return (Criteria) this;
        }

        public Criteria andOrderIdEqualTo(Long value) {
            addCriterion("order_id =", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotEqualTo(Long value) {
            addCriterion("order_id <>", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThan(Long value) {
            addCriterion("order_id >", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThanOrEqualTo(Long value) {
            addCriterion("order_id >=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThan(Long value) {
            addCriterion("order_id <", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThanOrEqualTo(Long value) {
            addCriterion("order_id <=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdIn(List<Long> values) {
            addCriterion("order_id in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotIn(List<Long> values) {
            addCriterion("order_id not in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdBetween(Long value1, Long value2) {
            addCriterion("order_id between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotBetween(Long value1, Long value2) {
            addCriterion("order_id not between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderPriceIsNull() {
            addCriterion("order_price is null");
            return (Criteria) this;
        }

        public Criteria andOrderPriceIsNotNull() {
            addCriterion("order_price is not null");
            return (Criteria) this;
        }

        public Criteria andOrderPriceEqualTo(BigDecimal value) {
            addCriterion("order_price =", value, "orderPrice");
            return (Criteria) this;
        }

        public Criteria andOrderPriceNotEqualTo(BigDecimal value) {
            addCriterion("order_price <>", value, "orderPrice");
            return (Criteria) this;
        }

        public Criteria andOrderPriceGreaterThan(BigDecimal value) {
            addCriterion("order_price >", value, "orderPrice");
            return (Criteria) this;
        }

        public Criteria andOrderPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("order_price >=", value, "orderPrice");
            return (Criteria) this;
        }

        public Criteria andOrderPriceLessThan(BigDecimal value) {
            addCriterion("order_price <", value, "orderPrice");
            return (Criteria) this;
        }

        public Criteria andOrderPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("order_price <=", value, "orderPrice");
            return (Criteria) this;
        }

        public Criteria andOrderPriceIn(List<BigDecimal> values) {
            addCriterion("order_price in", values, "orderPrice");
            return (Criteria) this;
        }

        public Criteria andOrderPriceNotIn(List<BigDecimal> values) {
            addCriterion("order_price not in", values, "orderPrice");
            return (Criteria) this;
        }

        public Criteria andOrderPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("order_price between", value1, value2, "orderPrice");
            return (Criteria) this;
        }

        public Criteria andOrderPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("order_price not between", value1, value2, "orderPrice");
            return (Criteria) this;
        }

        public Criteria andOrderTimeIsNull() {
            addCriterion("order_time is null");
            return (Criteria) this;
        }

        public Criteria andOrderTimeIsNotNull() {
            addCriterion("order_time is not null");
            return (Criteria) this;
        }

        public Criteria andOrderTimeEqualTo(String value) {
            addCriterion("order_time =", value, "orderTime");
            return (Criteria) this;
        }

        public Criteria andOrderTimeNotEqualTo(String value) {
            addCriterion("order_time <>", value, "orderTime");
            return (Criteria) this;
        }

        public Criteria andOrderTimeGreaterThan(String value) {
            addCriterion("order_time >", value, "orderTime");
            return (Criteria) this;
        }

        public Criteria andOrderTimeGreaterThanOrEqualTo(String value) {
            addCriterion("order_time >=", value, "orderTime");
            return (Criteria) this;
        }

        public Criteria andOrderTimeLessThan(String value) {
            addCriterion("order_time <", value, "orderTime");
            return (Criteria) this;
        }

        public Criteria andOrderTimeLessThanOrEqualTo(String value) {
            addCriterion("order_time <=", value, "orderTime");
            return (Criteria) this;
        }

        public Criteria andOrderTimeLike(String value) {
            addCriterion("order_time like", value, "orderTime");
            return (Criteria) this;
        }

        public Criteria andOrderTimeNotLike(String value) {
            addCriterion("order_time not like", value, "orderTime");
            return (Criteria) this;
        }

        public Criteria andOrderTimeIn(List<String> values) {
            addCriterion("order_time in", values, "orderTime");
            return (Criteria) this;
        }

        public Criteria andOrderTimeNotIn(List<String> values) {
            addCriterion("order_time not in", values, "orderTime");
            return (Criteria) this;
        }

        public Criteria andOrderTimeBetween(String value1, String value2) {
            addCriterion("order_time between", value1, value2, "orderTime");
            return (Criteria) this;
        }

        public Criteria andOrderTimeNotBetween(String value1, String value2) {
            addCriterion("order_time not between", value1, value2, "orderTime");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(Integer value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Integer value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Integer value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Integer value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Integer> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Integer> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Integer value1, Integer value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andOrderCouponUsedIsNull() {
            addCriterion("order_coupon_used is null");
            return (Criteria) this;
        }

        public Criteria andOrderCouponUsedIsNotNull() {
            addCriterion("order_coupon_used is not null");
            return (Criteria) this;
        }

        public Criteria andOrderCouponUsedEqualTo(Integer value) {
            addCriterion("order_coupon_used =", value, "orderCouponUsed");
            return (Criteria) this;
        }

        public Criteria andOrderCouponUsedNotEqualTo(Integer value) {
            addCriterion("order_coupon_used <>", value, "orderCouponUsed");
            return (Criteria) this;
        }

        public Criteria andOrderCouponUsedGreaterThan(Integer value) {
            addCriterion("order_coupon_used >", value, "orderCouponUsed");
            return (Criteria) this;
        }

        public Criteria andOrderCouponUsedGreaterThanOrEqualTo(Integer value) {
            addCriterion("order_coupon_used >=", value, "orderCouponUsed");
            return (Criteria) this;
        }

        public Criteria andOrderCouponUsedLessThan(Integer value) {
            addCriterion("order_coupon_used <", value, "orderCouponUsed");
            return (Criteria) this;
        }

        public Criteria andOrderCouponUsedLessThanOrEqualTo(Integer value) {
            addCriterion("order_coupon_used <=", value, "orderCouponUsed");
            return (Criteria) this;
        }

        public Criteria andOrderCouponUsedIn(List<Integer> values) {
            addCriterion("order_coupon_used in", values, "orderCouponUsed");
            return (Criteria) this;
        }

        public Criteria andOrderCouponUsedNotIn(List<Integer> values) {
            addCriterion("order_coupon_used not in", values, "orderCouponUsed");
            return (Criteria) this;
        }

        public Criteria andOrderCouponUsedBetween(Integer value1, Integer value2) {
            addCriterion("order_coupon_used between", value1, value2, "orderCouponUsed");
            return (Criteria) this;
        }

        public Criteria andOrderCouponUsedNotBetween(Integer value1, Integer value2) {
            addCriterion("order_coupon_used not between", value1, value2, "orderCouponUsed");
            return (Criteria) this;
        }

        public Criteria andOrderDiscountIsNull() {
            addCriterion("order_discount is null");
            return (Criteria) this;
        }

        public Criteria andOrderDiscountIsNotNull() {
            addCriterion("order_discount is not null");
            return (Criteria) this;
        }

        public Criteria andOrderDiscountEqualTo(Integer value) {
            addCriterion("order_discount =", value, "orderDiscount");
            return (Criteria) this;
        }

        public Criteria andOrderDiscountNotEqualTo(Integer value) {
            addCriterion("order_discount <>", value, "orderDiscount");
            return (Criteria) this;
        }

        public Criteria andOrderDiscountGreaterThan(Integer value) {
            addCriterion("order_discount >", value, "orderDiscount");
            return (Criteria) this;
        }

        public Criteria andOrderDiscountGreaterThanOrEqualTo(Integer value) {
            addCriterion("order_discount >=", value, "orderDiscount");
            return (Criteria) this;
        }

        public Criteria andOrderDiscountLessThan(Integer value) {
            addCriterion("order_discount <", value, "orderDiscount");
            return (Criteria) this;
        }

        public Criteria andOrderDiscountLessThanOrEqualTo(Integer value) {
            addCriterion("order_discount <=", value, "orderDiscount");
            return (Criteria) this;
        }

        public Criteria andOrderDiscountIn(List<Integer> values) {
            addCriterion("order_discount in", values, "orderDiscount");
            return (Criteria) this;
        }

        public Criteria andOrderDiscountNotIn(List<Integer> values) {
            addCriterion("order_discount not in", values, "orderDiscount");
            return (Criteria) this;
        }

        public Criteria andOrderDiscountBetween(Integer value1, Integer value2) {
            addCriterion("order_discount between", value1, value2, "orderDiscount");
            return (Criteria) this;
        }

        public Criteria andOrderDiscountNotBetween(Integer value1, Integer value2) {
            addCriterion("order_discount not between", value1, value2, "orderDiscount");
            return (Criteria) this;
        }

        public Criteria andOrderPaymentIsNull() {
            addCriterion("order_payment is null");
            return (Criteria) this;
        }

        public Criteria andOrderPaymentIsNotNull() {
            addCriterion("order_payment is not null");
            return (Criteria) this;
        }

        public Criteria andOrderPaymentEqualTo(Integer value) {
            addCriterion("order_payment =", value, "orderPayment");
            return (Criteria) this;
        }

        public Criteria andOrderPaymentNotEqualTo(Integer value) {
            addCriterion("order_payment <>", value, "orderPayment");
            return (Criteria) this;
        }

        public Criteria andOrderPaymentGreaterThan(Integer value) {
            addCriterion("order_payment >", value, "orderPayment");
            return (Criteria) this;
        }

        public Criteria andOrderPaymentGreaterThanOrEqualTo(Integer value) {
            addCriterion("order_payment >=", value, "orderPayment");
            return (Criteria) this;
        }

        public Criteria andOrderPaymentLessThan(Integer value) {
            addCriterion("order_payment <", value, "orderPayment");
            return (Criteria) this;
        }

        public Criteria andOrderPaymentLessThanOrEqualTo(Integer value) {
            addCriterion("order_payment <=", value, "orderPayment");
            return (Criteria) this;
        }

        public Criteria andOrderPaymentIn(List<Integer> values) {
            addCriterion("order_payment in", values, "orderPayment");
            return (Criteria) this;
        }

        public Criteria andOrderPaymentNotIn(List<Integer> values) {
            addCriterion("order_payment not in", values, "orderPayment");
            return (Criteria) this;
        }

        public Criteria andOrderPaymentBetween(Integer value1, Integer value2) {
            addCriterion("order_payment between", value1, value2, "orderPayment");
            return (Criteria) this;
        }

        public Criteria andOrderPaymentNotBetween(Integer value1, Integer value2) {
            addCriterion("order_payment not between", value1, value2, "orderPayment");
            return (Criteria) this;
        }

        public Criteria andOrderTitleIsNull() {
            addCriterion("order_title is null");
            return (Criteria) this;
        }

        public Criteria andOrderTitleIsNotNull() {
            addCriterion("order_title is not null");
            return (Criteria) this;
        }

        public Criteria andOrderTitleEqualTo(String value) {
            addCriterion("order_title =", value, "orderTitle");
            return (Criteria) this;
        }

        public Criteria andOrderTitleNotEqualTo(String value) {
            addCriterion("order_title <>", value, "orderTitle");
            return (Criteria) this;
        }

        public Criteria andOrderTitleGreaterThan(String value) {
            addCriterion("order_title >", value, "orderTitle");
            return (Criteria) this;
        }

        public Criteria andOrderTitleGreaterThanOrEqualTo(String value) {
            addCriterion("order_title >=", value, "orderTitle");
            return (Criteria) this;
        }

        public Criteria andOrderTitleLessThan(String value) {
            addCriterion("order_title <", value, "orderTitle");
            return (Criteria) this;
        }

        public Criteria andOrderTitleLessThanOrEqualTo(String value) {
            addCriterion("order_title <=", value, "orderTitle");
            return (Criteria) this;
        }

        public Criteria andOrderTitleLike(String value) {
            addCriterion("order_title like", value, "orderTitle");
            return (Criteria) this;
        }

        public Criteria andOrderTitleNotLike(String value) {
            addCriterion("order_title not like", value, "orderTitle");
            return (Criteria) this;
        }

        public Criteria andOrderTitleIn(List<String> values) {
            addCriterion("order_title in", values, "orderTitle");
            return (Criteria) this;
        }

        public Criteria andOrderTitleNotIn(List<String> values) {
            addCriterion("order_title not in", values, "orderTitle");
            return (Criteria) this;
        }

        public Criteria andOrderTitleBetween(String value1, String value2) {
            addCriterion("order_title between", value1, value2, "orderTitle");
            return (Criteria) this;
        }

        public Criteria andOrderTitleNotBetween(String value1, String value2) {
            addCriterion("order_title not between", value1, value2, "orderTitle");
            return (Criteria) this;
        }

        public Criteria andOrderPaymentIdIsNull() {
            addCriterion("order_payment_id is null");
            return (Criteria) this;
        }

        public Criteria andOrderPaymentIdIsNotNull() {
            addCriterion("order_payment_id is not null");
            return (Criteria) this;
        }

        public Criteria andOrderPaymentIdEqualTo(String value) {
            addCriterion("order_payment_id =", value, "orderPaymentId");
            return (Criteria) this;
        }

        public Criteria andOrderPaymentIdNotEqualTo(String value) {
            addCriterion("order_payment_id <>", value, "orderPaymentId");
            return (Criteria) this;
        }

        public Criteria andOrderPaymentIdGreaterThan(String value) {
            addCriterion("order_payment_id >", value, "orderPaymentId");
            return (Criteria) this;
        }

        public Criteria andOrderPaymentIdGreaterThanOrEqualTo(String value) {
            addCriterion("order_payment_id >=", value, "orderPaymentId");
            return (Criteria) this;
        }

        public Criteria andOrderPaymentIdLessThan(String value) {
            addCriterion("order_payment_id <", value, "orderPaymentId");
            return (Criteria) this;
        }

        public Criteria andOrderPaymentIdLessThanOrEqualTo(String value) {
            addCriterion("order_payment_id <=", value, "orderPaymentId");
            return (Criteria) this;
        }

        public Criteria andOrderPaymentIdLike(String value) {
            addCriterion("order_payment_id like", value, "orderPaymentId");
            return (Criteria) this;
        }

        public Criteria andOrderPaymentIdNotLike(String value) {
            addCriterion("order_payment_id not like", value, "orderPaymentId");
            return (Criteria) this;
        }

        public Criteria andOrderPaymentIdIn(List<String> values) {
            addCriterion("order_payment_id in", values, "orderPaymentId");
            return (Criteria) this;
        }

        public Criteria andOrderPaymentIdNotIn(List<String> values) {
            addCriterion("order_payment_id not in", values, "orderPaymentId");
            return (Criteria) this;
        }

        public Criteria andOrderPaymentIdBetween(String value1, String value2) {
            addCriterion("order_payment_id between", value1, value2, "orderPaymentId");
            return (Criteria) this;
        }

        public Criteria andOrderPaymentIdNotBetween(String value1, String value2) {
            addCriterion("order_payment_id not between", value1, value2, "orderPaymentId");
            return (Criteria) this;
        }

        public Criteria andOrderPaymentPriceIsNull() {
            addCriterion("order_payment_price is null");
            return (Criteria) this;
        }

        public Criteria andOrderPaymentPriceIsNotNull() {
            addCriterion("order_payment_price is not null");
            return (Criteria) this;
        }

        public Criteria andOrderPaymentPriceEqualTo(BigDecimal value) {
            addCriterion("order_payment_price =", value, "orderPaymentPrice");
            return (Criteria) this;
        }

        public Criteria andOrderPaymentPriceNotEqualTo(BigDecimal value) {
            addCriterion("order_payment_price <>", value, "orderPaymentPrice");
            return (Criteria) this;
        }

        public Criteria andOrderPaymentPriceGreaterThan(BigDecimal value) {
            addCriterion("order_payment_price >", value, "orderPaymentPrice");
            return (Criteria) this;
        }

        public Criteria andOrderPaymentPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("order_payment_price >=", value, "orderPaymentPrice");
            return (Criteria) this;
        }

        public Criteria andOrderPaymentPriceLessThan(BigDecimal value) {
            addCriterion("order_payment_price <", value, "orderPaymentPrice");
            return (Criteria) this;
        }

        public Criteria andOrderPaymentPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("order_payment_price <=", value, "orderPaymentPrice");
            return (Criteria) this;
        }

        public Criteria andOrderPaymentPriceIn(List<BigDecimal> values) {
            addCriterion("order_payment_price in", values, "orderPaymentPrice");
            return (Criteria) this;
        }

        public Criteria andOrderPaymentPriceNotIn(List<BigDecimal> values) {
            addCriterion("order_payment_price not in", values, "orderPaymentPrice");
            return (Criteria) this;
        }

        public Criteria andOrderPaymentPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("order_payment_price between", value1, value2, "orderPaymentPrice");
            return (Criteria) this;
        }

        public Criteria andOrderPaymentPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("order_payment_price not between", value1, value2, "orderPaymentPrice");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table vpro_order
     *
     * @mbg.generated do_not_delete_during_merge Thu Feb 28 12:48:35 CST 2019
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table vpro_order
     *
     * @mbg.generated Thu Feb 28 12:48:35 CST 2019
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
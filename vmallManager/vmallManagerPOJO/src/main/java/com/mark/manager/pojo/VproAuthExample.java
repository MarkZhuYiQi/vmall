package com.mark.manager.pojo;

import java.util.ArrayList;
import java.util.List;

public class VproAuthExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table vpro_auth
     *
     * @mbg.generated Wed Oct 17 23:12:35 CST 2018
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table vpro_auth
     *
     * @mbg.generated Wed Oct 17 23:12:35 CST 2018
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table vpro_auth
     *
     * @mbg.generated Wed Oct 17 23:12:35 CST 2018
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_auth
     *
     * @mbg.generated Wed Oct 17 23:12:35 CST 2018
     */
    public VproAuthExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_auth
     *
     * @mbg.generated Wed Oct 17 23:12:35 CST 2018
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_auth
     *
     * @mbg.generated Wed Oct 17 23:12:35 CST 2018
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_auth
     *
     * @mbg.generated Wed Oct 17 23:12:35 CST 2018
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_auth
     *
     * @mbg.generated Wed Oct 17 23:12:35 CST 2018
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_auth
     *
     * @mbg.generated Wed Oct 17 23:12:35 CST 2018
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_auth
     *
     * @mbg.generated Wed Oct 17 23:12:35 CST 2018
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_auth
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
     * This method corresponds to the database table vpro_auth
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
     * This method corresponds to the database table vpro_auth
     *
     * @mbg.generated Wed Oct 17 23:12:35 CST 2018
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpro_auth
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
     * This class corresponds to the database table vpro_auth
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

        public Criteria andAuthIdIsNull() {
            addCriterion("auth_id is null");
            return (Criteria) this;
        }

        public Criteria andAuthIdIsNotNull() {
            addCriterion("auth_id is not null");
            return (Criteria) this;
        }

        public Criteria andAuthIdEqualTo(Integer value) {
            addCriterion("auth_id =", value, "authId");
            return (Criteria) this;
        }

        public Criteria andAuthIdNotEqualTo(Integer value) {
            addCriterion("auth_id <>", value, "authId");
            return (Criteria) this;
        }

        public Criteria andAuthIdGreaterThan(Integer value) {
            addCriterion("auth_id >", value, "authId");
            return (Criteria) this;
        }

        public Criteria andAuthIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("auth_id >=", value, "authId");
            return (Criteria) this;
        }

        public Criteria andAuthIdLessThan(Integer value) {
            addCriterion("auth_id <", value, "authId");
            return (Criteria) this;
        }

        public Criteria andAuthIdLessThanOrEqualTo(Integer value) {
            addCriterion("auth_id <=", value, "authId");
            return (Criteria) this;
        }

        public Criteria andAuthIdIn(List<Integer> values) {
            addCriterion("auth_id in", values, "authId");
            return (Criteria) this;
        }

        public Criteria andAuthIdNotIn(List<Integer> values) {
            addCriterion("auth_id not in", values, "authId");
            return (Criteria) this;
        }

        public Criteria andAuthIdBetween(Integer value1, Integer value2) {
            addCriterion("auth_id between", value1, value2, "authId");
            return (Criteria) this;
        }

        public Criteria andAuthIdNotBetween(Integer value1, Integer value2) {
            addCriterion("auth_id not between", value1, value2, "authId");
            return (Criteria) this;
        }

        public Criteria andAuthAppidIsNull() {
            addCriterion("auth_appid is null");
            return (Criteria) this;
        }

        public Criteria andAuthAppidIsNotNull() {
            addCriterion("auth_appid is not null");
            return (Criteria) this;
        }

        public Criteria andAuthAppidEqualTo(String value) {
            addCriterion("auth_appid =", value, "authAppid");
            return (Criteria) this;
        }

        public Criteria andAuthAppidNotEqualTo(String value) {
            addCriterion("auth_appid <>", value, "authAppid");
            return (Criteria) this;
        }

        public Criteria andAuthAppidGreaterThan(String value) {
            addCriterion("auth_appid >", value, "authAppid");
            return (Criteria) this;
        }

        public Criteria andAuthAppidGreaterThanOrEqualTo(String value) {
            addCriterion("auth_appid >=", value, "authAppid");
            return (Criteria) this;
        }

        public Criteria andAuthAppidLessThan(String value) {
            addCriterion("auth_appid <", value, "authAppid");
            return (Criteria) this;
        }

        public Criteria andAuthAppidLessThanOrEqualTo(String value) {
            addCriterion("auth_appid <=", value, "authAppid");
            return (Criteria) this;
        }

        public Criteria andAuthAppidLike(String value) {
            addCriterion("auth_appid like", value, "authAppid");
            return (Criteria) this;
        }

        public Criteria andAuthAppidNotLike(String value) {
            addCriterion("auth_appid not like", value, "authAppid");
            return (Criteria) this;
        }

        public Criteria andAuthAppidIn(List<String> values) {
            addCriterion("auth_appid in", values, "authAppid");
            return (Criteria) this;
        }

        public Criteria andAuthAppidNotIn(List<String> values) {
            addCriterion("auth_appid not in", values, "authAppid");
            return (Criteria) this;
        }

        public Criteria andAuthAppidBetween(String value1, String value2) {
            addCriterion("auth_appid between", value1, value2, "authAppid");
            return (Criteria) this;
        }

        public Criteria andAuthAppidNotBetween(String value1, String value2) {
            addCriterion("auth_appid not between", value1, value2, "authAppid");
            return (Criteria) this;
        }

        public Criteria andAuthAppkeyIsNull() {
            addCriterion("auth_appkey is null");
            return (Criteria) this;
        }

        public Criteria andAuthAppkeyIsNotNull() {
            addCriterion("auth_appkey is not null");
            return (Criteria) this;
        }

        public Criteria andAuthAppkeyEqualTo(String value) {
            addCriterion("auth_appkey =", value, "authAppkey");
            return (Criteria) this;
        }

        public Criteria andAuthAppkeyNotEqualTo(String value) {
            addCriterion("auth_appkey <>", value, "authAppkey");
            return (Criteria) this;
        }

        public Criteria andAuthAppkeyGreaterThan(String value) {
            addCriterion("auth_appkey >", value, "authAppkey");
            return (Criteria) this;
        }

        public Criteria andAuthAppkeyGreaterThanOrEqualTo(String value) {
            addCriterion("auth_appkey >=", value, "authAppkey");
            return (Criteria) this;
        }

        public Criteria andAuthAppkeyLessThan(String value) {
            addCriterion("auth_appkey <", value, "authAppkey");
            return (Criteria) this;
        }

        public Criteria andAuthAppkeyLessThanOrEqualTo(String value) {
            addCriterion("auth_appkey <=", value, "authAppkey");
            return (Criteria) this;
        }

        public Criteria andAuthAppkeyLike(String value) {
            addCriterion("auth_appkey like", value, "authAppkey");
            return (Criteria) this;
        }

        public Criteria andAuthAppkeyNotLike(String value) {
            addCriterion("auth_appkey not like", value, "authAppkey");
            return (Criteria) this;
        }

        public Criteria andAuthAppkeyIn(List<String> values) {
            addCriterion("auth_appkey in", values, "authAppkey");
            return (Criteria) this;
        }

        public Criteria andAuthAppkeyNotIn(List<String> values) {
            addCriterion("auth_appkey not in", values, "authAppkey");
            return (Criteria) this;
        }

        public Criteria andAuthAppkeyBetween(String value1, String value2) {
            addCriterion("auth_appkey between", value1, value2, "authAppkey");
            return (Criteria) this;
        }

        public Criteria andAuthAppkeyNotBetween(String value1, String value2) {
            addCriterion("auth_appkey not between", value1, value2, "authAppkey");
            return (Criteria) this;
        }

        public Criteria andAuthTimeIsNull() {
            addCriterion("auth_time is null");
            return (Criteria) this;
        }

        public Criteria andAuthTimeIsNotNull() {
            addCriterion("auth_time is not null");
            return (Criteria) this;
        }

        public Criteria andAuthTimeEqualTo(String value) {
            addCriterion("auth_time =", value, "authTime");
            return (Criteria) this;
        }

        public Criteria andAuthTimeNotEqualTo(String value) {
            addCriterion("auth_time <>", value, "authTime");
            return (Criteria) this;
        }

        public Criteria andAuthTimeGreaterThan(String value) {
            addCriterion("auth_time >", value, "authTime");
            return (Criteria) this;
        }

        public Criteria andAuthTimeGreaterThanOrEqualTo(String value) {
            addCriterion("auth_time >=", value, "authTime");
            return (Criteria) this;
        }

        public Criteria andAuthTimeLessThan(String value) {
            addCriterion("auth_time <", value, "authTime");
            return (Criteria) this;
        }

        public Criteria andAuthTimeLessThanOrEqualTo(String value) {
            addCriterion("auth_time <=", value, "authTime");
            return (Criteria) this;
        }

        public Criteria andAuthTimeLike(String value) {
            addCriterion("auth_time like", value, "authTime");
            return (Criteria) this;
        }

        public Criteria andAuthTimeNotLike(String value) {
            addCriterion("auth_time not like", value, "authTime");
            return (Criteria) this;
        }

        public Criteria andAuthTimeIn(List<String> values) {
            addCriterion("auth_time in", values, "authTime");
            return (Criteria) this;
        }

        public Criteria andAuthTimeNotIn(List<String> values) {
            addCriterion("auth_time not in", values, "authTime");
            return (Criteria) this;
        }

        public Criteria andAuthTimeBetween(String value1, String value2) {
            addCriterion("auth_time between", value1, value2, "authTime");
            return (Criteria) this;
        }

        public Criteria andAuthTimeNotBetween(String value1, String value2) {
            addCriterion("auth_time not between", value1, value2, "authTime");
            return (Criteria) this;
        }

        public Criteria andAuthTokenIsNull() {
            addCriterion("auth_token is null");
            return (Criteria) this;
        }

        public Criteria andAuthTokenIsNotNull() {
            addCriterion("auth_token is not null");
            return (Criteria) this;
        }

        public Criteria andAuthTokenEqualTo(String value) {
            addCriterion("auth_token =", value, "authToken");
            return (Criteria) this;
        }

        public Criteria andAuthTokenNotEqualTo(String value) {
            addCriterion("auth_token <>", value, "authToken");
            return (Criteria) this;
        }

        public Criteria andAuthTokenGreaterThan(String value) {
            addCriterion("auth_token >", value, "authToken");
            return (Criteria) this;
        }

        public Criteria andAuthTokenGreaterThanOrEqualTo(String value) {
            addCriterion("auth_token >=", value, "authToken");
            return (Criteria) this;
        }

        public Criteria andAuthTokenLessThan(String value) {
            addCriterion("auth_token <", value, "authToken");
            return (Criteria) this;
        }

        public Criteria andAuthTokenLessThanOrEqualTo(String value) {
            addCriterion("auth_token <=", value, "authToken");
            return (Criteria) this;
        }

        public Criteria andAuthTokenLike(String value) {
            addCriterion("auth_token like", value, "authToken");
            return (Criteria) this;
        }

        public Criteria andAuthTokenNotLike(String value) {
            addCriterion("auth_token not like", value, "authToken");
            return (Criteria) this;
        }

        public Criteria andAuthTokenIn(List<String> values) {
            addCriterion("auth_token in", values, "authToken");
            return (Criteria) this;
        }

        public Criteria andAuthTokenNotIn(List<String> values) {
            addCriterion("auth_token not in", values, "authToken");
            return (Criteria) this;
        }

        public Criteria andAuthTokenBetween(String value1, String value2) {
            addCriterion("auth_token between", value1, value2, "authToken");
            return (Criteria) this;
        }

        public Criteria andAuthTokenNotBetween(String value1, String value2) {
            addCriterion("auth_token not between", value1, value2, "authToken");
            return (Criteria) this;
        }

        public Criteria andAuthIpIsNull() {
            addCriterion("auth_ip is null");
            return (Criteria) this;
        }

        public Criteria andAuthIpIsNotNull() {
            addCriterion("auth_ip is not null");
            return (Criteria) this;
        }

        public Criteria andAuthIpEqualTo(String value) {
            addCriterion("auth_ip =", value, "authIp");
            return (Criteria) this;
        }

        public Criteria andAuthIpNotEqualTo(String value) {
            addCriterion("auth_ip <>", value, "authIp");
            return (Criteria) this;
        }

        public Criteria andAuthIpGreaterThan(String value) {
            addCriterion("auth_ip >", value, "authIp");
            return (Criteria) this;
        }

        public Criteria andAuthIpGreaterThanOrEqualTo(String value) {
            addCriterion("auth_ip >=", value, "authIp");
            return (Criteria) this;
        }

        public Criteria andAuthIpLessThan(String value) {
            addCriterion("auth_ip <", value, "authIp");
            return (Criteria) this;
        }

        public Criteria andAuthIpLessThanOrEqualTo(String value) {
            addCriterion("auth_ip <=", value, "authIp");
            return (Criteria) this;
        }

        public Criteria andAuthIpLike(String value) {
            addCriterion("auth_ip like", value, "authIp");
            return (Criteria) this;
        }

        public Criteria andAuthIpNotLike(String value) {
            addCriterion("auth_ip not like", value, "authIp");
            return (Criteria) this;
        }

        public Criteria andAuthIpIn(List<String> values) {
            addCriterion("auth_ip in", values, "authIp");
            return (Criteria) this;
        }

        public Criteria andAuthIpNotIn(List<String> values) {
            addCriterion("auth_ip not in", values, "authIp");
            return (Criteria) this;
        }

        public Criteria andAuthIpBetween(String value1, String value2) {
            addCriterion("auth_ip between", value1, value2, "authIp");
            return (Criteria) this;
        }

        public Criteria andAuthIpNotBetween(String value1, String value2) {
            addCriterion("auth_ip not between", value1, value2, "authIp");
            return (Criteria) this;
        }

        public Criteria andAuthNknameIsNull() {
            addCriterion("auth_nkname is null");
            return (Criteria) this;
        }

        public Criteria andAuthNknameIsNotNull() {
            addCriterion("auth_nkname is not null");
            return (Criteria) this;
        }

        public Criteria andAuthNknameEqualTo(String value) {
            addCriterion("auth_nkname =", value, "authNkname");
            return (Criteria) this;
        }

        public Criteria andAuthNknameNotEqualTo(String value) {
            addCriterion("auth_nkname <>", value, "authNkname");
            return (Criteria) this;
        }

        public Criteria andAuthNknameGreaterThan(String value) {
            addCriterion("auth_nkname >", value, "authNkname");
            return (Criteria) this;
        }

        public Criteria andAuthNknameGreaterThanOrEqualTo(String value) {
            addCriterion("auth_nkname >=", value, "authNkname");
            return (Criteria) this;
        }

        public Criteria andAuthNknameLessThan(String value) {
            addCriterion("auth_nkname <", value, "authNkname");
            return (Criteria) this;
        }

        public Criteria andAuthNknameLessThanOrEqualTo(String value) {
            addCriterion("auth_nkname <=", value, "authNkname");
            return (Criteria) this;
        }

        public Criteria andAuthNknameLike(String value) {
            addCriterion("auth_nkname like", value, "authNkname");
            return (Criteria) this;
        }

        public Criteria andAuthNknameNotLike(String value) {
            addCriterion("auth_nkname not like", value, "authNkname");
            return (Criteria) this;
        }

        public Criteria andAuthNknameIn(List<String> values) {
            addCriterion("auth_nkname in", values, "authNkname");
            return (Criteria) this;
        }

        public Criteria andAuthNknameNotIn(List<String> values) {
            addCriterion("auth_nkname not in", values, "authNkname");
            return (Criteria) this;
        }

        public Criteria andAuthNknameBetween(String value1, String value2) {
            addCriterion("auth_nkname between", value1, value2, "authNkname");
            return (Criteria) this;
        }

        public Criteria andAuthNknameNotBetween(String value1, String value2) {
            addCriterion("auth_nkname not between", value1, value2, "authNkname");
            return (Criteria) this;
        }

        public Criteria andAuthRolesIdIsNull() {
            addCriterion("auth_roles_id is null");
            return (Criteria) this;
        }

        public Criteria andAuthRolesIdIsNotNull() {
            addCriterion("auth_roles_id is not null");
            return (Criteria) this;
        }

        public Criteria andAuthRolesIdEqualTo(Integer value) {
            addCriterion("auth_roles_id =", value, "authRolesId");
            return (Criteria) this;
        }

        public Criteria andAuthRolesIdNotEqualTo(Integer value) {
            addCriterion("auth_roles_id <>", value, "authRolesId");
            return (Criteria) this;
        }

        public Criteria andAuthRolesIdGreaterThan(Integer value) {
            addCriterion("auth_roles_id >", value, "authRolesId");
            return (Criteria) this;
        }

        public Criteria andAuthRolesIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("auth_roles_id >=", value, "authRolesId");
            return (Criteria) this;
        }

        public Criteria andAuthRolesIdLessThan(Integer value) {
            addCriterion("auth_roles_id <", value, "authRolesId");
            return (Criteria) this;
        }

        public Criteria andAuthRolesIdLessThanOrEqualTo(Integer value) {
            addCriterion("auth_roles_id <=", value, "authRolesId");
            return (Criteria) this;
        }

        public Criteria andAuthRolesIdIn(List<Integer> values) {
            addCriterion("auth_roles_id in", values, "authRolesId");
            return (Criteria) this;
        }

        public Criteria andAuthRolesIdNotIn(List<Integer> values) {
            addCriterion("auth_roles_id not in", values, "authRolesId");
            return (Criteria) this;
        }

        public Criteria andAuthRolesIdBetween(Integer value1, Integer value2) {
            addCriterion("auth_roles_id between", value1, value2, "authRolesId");
            return (Criteria) this;
        }

        public Criteria andAuthRolesIdNotBetween(Integer value1, Integer value2) {
            addCriterion("auth_roles_id not between", value1, value2, "authRolesId");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table vpro_auth
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
     * This class corresponds to the database table vpro_auth
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
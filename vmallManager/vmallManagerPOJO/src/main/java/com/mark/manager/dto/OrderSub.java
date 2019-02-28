package com.mark.manager.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderSub implements Serializable {
    private Integer orderSubId;

    private Long orderId;

    private Integer courseId;

    private BigDecimal coursePrice;

    private String courseTitle;

    private String courseAuthor;

    private String courseCover;
}
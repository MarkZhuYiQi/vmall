package com.mark.manager.mq;

import com.mark.common.exception.CategoryException;
import com.mark.common.exception.CourseException;
import com.mark.common.util.Utils;
import com.mark.manager.dto.Courses;
import com.mark.manager.service.CategoryService;
import com.mark.manager.service.CourseService;
import com.rabbitmq.client.Channel;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ExpiredMessageListener implements ChannelAwareMessageListener {
    private static final Logger logger = LoggerFactory.getLogger(ExpiredMessageListener.class);
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CourseService courseService;

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        byte[] body = message.getBody();
        Integer navId = Integer.parseInt(Utils.getDigits(new String(body)));
        System.out.println("navId: " + navId);
        Map<Integer, List<Courses>> indexCourses;
        try {
            // 封面课程
            Map<Integer, List<Integer>> navIds = categoryService.getSubIds(navId);
            indexCourses = courseService.getIndexCoursesInfo(navId, navIds);
            System.out.println(indexCourses);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (CategoryException ee) {
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
            logger.warn(ee.getMsg());
        } catch (CourseException ec) {
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
            logger.warn(ec.getMsg());
        }
    }
}

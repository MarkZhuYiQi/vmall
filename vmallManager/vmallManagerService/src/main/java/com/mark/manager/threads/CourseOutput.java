package com.mark.manager.threads;

import com.alibaba.fastjson.JSON;
import com.mark.common.elasticsearch.ElasticsearchUtil;
import com.mark.common.jedis.JedisClient;
import com.mark.common.util.BeanUtil;
import com.mark.manager.dto.Courses;
import com.mark.manager.mapper.CoursesMapper;
import com.mark.manager.pojo.VproCoursesContent;
import com.mark.manager.pojo.VproCoursesCover;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.client.transport.TransportClient;

import java.beans.IntrospectionException;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class CourseOutput implements Callable<String> {

    private CoursesMapper coursesMapper;
    private JedisClient jedisClient;
    private Map<String, String> idRange;
    private String coursePrefix;

    public CourseOutput(Map<String, String> idRange, CoursesMapper coursesMapper, JedisClient jedisClient, String coursePrefix) {
        this.idRange = idRange;
        this.coursesMapper = coursesMapper;
        this.jedisClient = jedisClient;
        this.coursePrefix = coursePrefix;
    }

    public void vproCoursesContentSet(VproCoursesContent vproCoursesContent) {
        if (vproCoursesContent != null) {
            String content = vproCoursesContent.getCourseContent().toString();
            jedisClient.set(coursePrefix + "Content" + vproCoursesContent.getCourseId(), content);
        }
    }

    @Override
    public String call() throws Exception {
        System.out.println(idRange);
        List<Courses> list =  coursesMapper.getCourses(idRange);
//        String filename = "G:/wamp/www/SSM/vmall/course" + Thread.currentThread().getName() + ".txt";
        String filename = "D:/wamp/www/vmall/" + Thread.currentThread().getName() + ".txt";
        FileWriter fw = null;
        BufferedWriter bfw = null;
        try{
            File file = new File(filename);
            fw = new FileWriter(filename);
            bfw = new BufferedWriter(fw);
            if (list.size() != 0) {
                for(Courses courses : list) {
                    Map<String, String> course = new HashMap<String, String>();
                    course = BeanUtil.bean2map(courses);
                    course.putAll(BeanUtil.bean2map(courses.getVproAuth()));
                    if (courses.getVproCoursesCover() == null) {
                        course.putAll(BeanUtil.bean2map(new VproCoursesCover()));
                    } else {
                        course.putAll(BeanUtil.bean2map(courses.getVproCoursesCover()));
                    }
                    vproCoursesContentSet(courses.getVproCoursesContent());
                    course.remove("vproAuth");
                    course.remove("vproCoursesCover");
                    course.remove("vproCoursesContent");
                    String json = BeanUtil.parseObjToJson(course);
                    bfw.write(json);
                    bfw.newLine();
                    bfw.flush();
                    jedisClient.set(coursePrefix, json);
                }
            }
        } catch (IOException | IntrospectionException e) {
            e.printStackTrace();
        } finally {
            try {
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return Thread.currentThread().getName();
    }

}

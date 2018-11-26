package com.mark.manager.threads;

import com.alibaba.fastjson.JSON;
import com.mark.common.elasticsearch.ElasticsearchUtil;
import com.mark.common.util.BeanUtil;
import com.mark.manager.dto.Courses;
import com.mark.manager.mapper.CoursesMapper;
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
    private Map<String, String> idRange;

    public CourseOutput(Map<String, String> idRange, CoursesMapper coursesMapper) {
        this.idRange = idRange;
        this.coursesMapper = coursesMapper;
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
                    course.remove("vproAuth");
                    course.remove("vproCoursesCover");
                    bfw.write(JSON.toJSONString(course));
                    bfw.newLine();
                    bfw.flush();
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

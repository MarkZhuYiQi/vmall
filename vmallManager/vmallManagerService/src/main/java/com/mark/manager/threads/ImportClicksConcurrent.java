package com.mark.manager.threads;

import com.mark.manager.mapper.CoursesMapper;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;

public class ImportClicksConcurrent implements Runnable {
    private Semaphore semaphore;
    private List<Map<String, Integer>> update;
    private CoursesMapper coursesMapper;
    public ImportClicksConcurrent(Semaphore semaphore, List<Map<String, Integer>> update, CoursesMapper coursesMapper) {
        this.semaphore = semaphore;
        this.update = update;
        this.coursesMapper = coursesMapper;
    }
    @Override
    public void run() {
        try {
            semaphore.acquire();
            coursesMapper.batchUpdateClick(update);
//            coursesMapper.batchUpdateClickCaseWhen(update);
            semaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

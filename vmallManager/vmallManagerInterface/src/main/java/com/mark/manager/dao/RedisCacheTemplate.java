package com.mark.manager.dao;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public abstract class RedisCacheTemplate {
    private Object data = null;
    protected boolean cacheValid = false;
    private ReadWriteLock rwl = new ReentrantReadWriteLock();
    public Object processCache() {
        rwl.readLock().lock();
        data = null;
        try {
            data = getDataFromCache();
            if (data == null) {
                rwl.readLock().unlock();
                rwl.writeLock().lock();
                try {
                    if (data == null) {
                        data = setDataToCache();
                    }
                } finally {
                    rwl.writeLock().unlock();
                }
                rwl.readLock().lock();
            }
        } finally {
            rwl.readLock().unlock();
        }
        return data;
    }
    public abstract Object getDataFromCache();
    public abstract Object setDataToCache();
}

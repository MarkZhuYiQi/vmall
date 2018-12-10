package com.mark.manager.service;

import java.util.Map;

public interface ShoppingService {
    void test();
    Map<String, String> decreaseStock() throws InterruptedException;
    Map<String, String> decreaseStock2();
}

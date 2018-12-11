package com.mark.manager.service;

import java.util.Map;

public interface ShoppingService {
    void test();
    Map<String, String> decreaseStock1();
    Map<String, String> decreaseStock2();
    Map<String, String> decreaseStock3(String name);
}

package com.mark.common.util;

public class SortUtil {
    // 二分法
    public static int dichotomy(int arr[], int n, int key) {
        int low = 0;
        int high = n - 1;
        int mid, count = 0;
        while(low <= high) {
            mid = (low + high) / 2;
            if (arr[mid] == key) {
                return mid;
            }
            if (arr[mid] < key) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }
}

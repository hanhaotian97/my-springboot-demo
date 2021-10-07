package com.hht.myspringbootdemo.algorithm;

import java.util.Arrays;

/**
 * <br/>Description : 描述 冒泡排序法
 * <br/>CreateTime : 2021/7/23
 * @author hanhaotian
 */
public class bubbleSort {
    public static void main(String[] args) {
        int[] arr = {3, 5, 1, 9, 2, 4, 6};

        System.out.println(Arrays.toString(arr));

        // 相邻元素两两比较，大的往后放，每一次完毕，最大值出现在了最大索引处；
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    //值交换
                    int t = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = t;
                }
            }
        }
        System.out.println(Arrays.toString(arr));
    }
}

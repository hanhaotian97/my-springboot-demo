package com.hht.myspringbootdemo.algorithm;

import java.util.HashSet;
import java.util.Set;

/**
 * <br/>Description : 描述
 * <br/>CreateTime : 2023/10/16
 * @author hanhaotian
 */
public class MovingWindow {
    public static void main(String[] args) {
        Integer a = 123;
        changeTo456(a);
        System.out.println(a);
        System.out.println(lengthOfLongestSubstring2("dvdf"));
    }

    public static void changeTo456(Integer a) {
        a = 456;
    }

    /**
     * 给定一个字符串 s ，请你找出其中不含有重复字符的 最长子串 的长度。
     * @param s dvdf
     * @return 不含有重复字符的 最长子串为vdf,返回3
     */
    public static int lengthOfLongestSubstring2(String s) {
        int length = s.length();
        int right = 0, max = 0;
        Set<Character> wSet = new HashSet<>();//窗口内容列表
        for (int left = 0; left < length; left++) {
            if (left != 0) {
                wSet.remove(s.charAt(left - 1));//移除窗口的左边界
            }

            //右区间值小于字符串长度 且 窗口不包含该右区间值,拓展窗口的右边界
            while (right < length && !wSet.contains(s.charAt(right))) {
                wSet.add(s.charAt(right));
                right++;
            }
            max = Math.max(max, wSet.size());
        }
        return max;
    }
}

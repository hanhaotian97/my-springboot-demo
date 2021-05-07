package com.hht.myspringbootdemo.jdk8;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * <br/>Author hanhaotian
 * <br/>Description :
 * <br/>CreateTime 2021/3/22
 */
public class Lamda {
    public static void main(String[] args) {
        Person person1 = new Person(1, "aaa", 10, false, new Date());
        Person person2 = new Person(2, "bbb", 30, true, new Date(2021, 3, 22));
        Person personDistinct2 = new Person(2, "bbb", 30, true, new Date(2021, 3, 22));
        Person person3 = new Person(3, "ddd", 12, false, new Date(2021, 3, 22));
        Person person4 = new Person(4, "ccc", 13, false, new Date(2021, 3, 22));
        Person person5 = new Person(5, "eee", 14, false, new Date(2021, 3, 22));
        Person person6 = new Person(6, "fff", 15, false, new Date(2021, 3, 22));
        Person person7 = new Person(7, "bbb", 16, false, new Date(2021, 3, 22));
        List<Person> list = new ArrayList<>();
        list.add(person1);
        list.add(person2);
        list.add(personDistinct2);
        list.add(person3);
        list.add(person4);
        list.add(person5);
        list.add(person6);
        list.add(person7);
        System.out.println("Stream处理前的原始列表: \t" + list + "\n");

        //stream()方法创建并行流获取流对象
        //1. Stream是元素的集合，这点让Stream看起来用些类似Iterator；
        //2. 可以支持顺序和并行的对原Stream进行汇聚的操作；
        Stream<Person> stream = list.stream();

        Map<Integer, Person> result = stream
                .distinct()
                .collect(Collectors.toMap(Person::getId, Person->Person));
        System.out.println(result);

        List<String> result2 = list.stream()
                //filter筛选属性为true的元素
                //.filter(Person::isStudent)
                //去重, 依赖equal和hashCode
                .distinct()
                //跳过第一个
                .skip(1)
                //截取前3个
                .limit(3)
                //映射: 对流中每个元素执行函数, 将其转换为另一种类型
                .map(Person::getName)
                //排序
                .sorted()
                .collect(Collectors.toList());

        System.out.println("Stream处理后\t" + result2);
        //常用
        //common(list);
    }

    private static void common() {
        Person person1 = new Person(1, "aaa", 10, false, new Date());
        Person person2 = new Person(2, "bbb", 30, true, new Date(2021, 3, 22));
        List<Person> list = new ArrayList<>();
        list.add(person1);
        list.add(person2);

        //对象集合转为某个属性列表
        List<Integer> tableNames = list.stream()
                .map(Person::getId)
                .collect(Collectors.toList());
        System.out.println("对象集合转为某个属性列表\t" + tableNames);

        //对象集合 转为 MAP(对象属性->对象)
        Map<Integer, Object> userAccountMap = list.stream()
                .collect(Collectors.toMap(Person::getId, (p) -> p));
        System.out.println("对象集合 转为 MAP(对象属性->对象)\t" + userAccountMap);

        //字符串"1,2,3,4"转为list
        String idListStr ="1,2,3,4";
        List<Long> hotAuctionGoodsIdList = Arrays.stream(idListStr.split(","))
                .map(s -> Long.parseLong(s.trim()))
                .collect(Collectors.toList());
        System.out.println("字符串\"1,2,3,4\"转为list\t" + hotAuctionGoodsIdList);
    }


}

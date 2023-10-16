package com.hht.myspringbootdemo.jdk8;

import org.junit.Test;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <br/>Description : function函数式接口测试, 以及optional空元素判断测试
 * <br/>CreateTime : 2021/8/26
 * @author hanhaotian
 */
public class FunctionInterface {
    /**
     * consumer接口测试
     * consumer接口就是一个消费型的接口，通过传入参数，然后输出值
     * ① Consumer是一个接口，并且只要实现一个 accept 方法，就可以作为一个“消费者”输出信息。
     * ② 其实，lambda 表达式、方法引用的返回值都是 Consumer 类型，所以，他们能够作为 forEach 方法的参数，并且输出一个值。
     */
    @Test
    public void test_Consumer() {
        //① 使用consumer接口实现方法
        Consumer<String> consumer = new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        };
        Stream<String> stream = Stream.of("aaa", "bbb", "ddd", "ccc", "fff");
        stream.forEach(consumer);

        System.out.println("********************");

        //② 使用lambda表达式，forEach方法需要的就是一个Consumer接口
        stream = Stream.of("aaa", "bbb", "ddd", "ccc", "fff");
        //lambda表达式返回的就是一个Consumer接口
        Consumer<String> consumer1 = (s) -> System.out.println(s);
        stream.forEach(consumer1);
        //更直接的方式 stream.forEach((s) -> System.out.println(s));
        System.out.println("********************");

        //③ 使用方法引用，方法引用也是一个consumer
        stream = Stream.of("aaa", "bbb", "ddd", "ccc", "fff");
        Consumer consumer2 = System.out::println;
        stream.forEach(consumer);
        //更直接的方式 stream.forEach(System.out::println);
    }

    /**
     * Supplier接口测试，supplier相当一个容器或者变量，可以存储值.
     * 声明之后并不会占用内存，只有执行了get()方法之后，才会调用构造方法创建出对象
     */
    @Test
    public void test_Supplier() {
        //① 使用Supplier接口实现方法,只有一个get方法，无参数，返回一个值
        Supplier<Integer> supplier = new Supplier<Integer>() {
            @Override
            public Integer get() {
                //返回一个随机值
                return new Random().nextInt();
            }
        };

        System.out.println(supplier.get());

        System.out.println("********************");

        //② 使用lambda表达式，
        supplier = () -> new Random().nextInt();
        System.out.println(supplier.get());
        System.out.println("********************");

        //③ 使用方法引用
        Supplier<Double> supplier2 = Math::random;
        System.out.println(supplier2.get());
    }

    /**
     * Supplier接口测试2，使用需要Supplier的接口方法
     */
    @Test
    public void test_Supplier2() {
        Stream<Integer> stream = Stream.of(1, 2, 3, 4, 5);
        //返回一个optional对象
        Optional<Integer> first = stream.filter(i -> i > 4).findFirst();

        //optional对象有需要Supplier接口的方法
        //orElse，如果first中存在数，就返回这个数，如果不存在，就放回传入的数
        System.out.println(first.orElse(1));
        System.out.println(first.orElse(7));

        System.out.println("********************");

        Supplier<Integer> supplier = new Supplier<Integer>() {
            @Override
            public Integer get() {
                //返回一个随机值
                return new Random().nextInt();
            }
        };

        //orElseGet，如果first中存在数，就返回这个数，如果不存在，就返回supplier返回的值
        System.out.println(first.orElseGet(supplier));
    }

    /**
     * Predicate谓词测试，谓词其实就是一个判断的作用类似bool的作用
     */
    @Test
    public void test_Predicate() {
        //① 使用Predicate接口实现方法,只有一个test方法，传入一个参数，返回一个bool值
        Predicate<Integer> predicate = new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) {
                return integer > 5;
            }
        };
        System.out.println(predicate.test(6));
        System.out.println("********************");

        //② 使用lambda表达式，
        predicate = (t) -> t > 5;
        System.out.println(predicate.test(1));
        System.out.println("********************\n********************\n********************");

        //① 将Predicate作为filter接口，Predicate起到一个判断的作用
        Predicate<Integer> predicate2 = new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) {
                if (integer > 5) {
                    return true;
                }
                return false;
            }
        };

        Stream<Integer> stream = Stream.of(1, 23, 3, 4, 5, 56, 6, 6);
        List<Integer> list = stream.filter(predicate2).collect(Collectors.toList());
        list.forEach(System.out::println);
        System.out.println("********************");
    }

    /**
     * Function测试，function的作用是转换，将一个值转为另外一个值
     */
    @Test
    public void test_Function() {
        //① 使用map方法，泛型的第一个参数是转换前的类型，第二个是转化后的类型
        Function<String, Integer> function = new Function<String, Integer>() {
            @Override
            public Integer apply(String s) {
                return s.length();//获取每个字符串的长度，并且返回
            }
        };

        Stream<String> stream = Stream.of("aaa", "bbbbb", "ccccccv");
        Stream<Integer> stream1 = stream.map(function);
        stream1.forEach(System.out::println);

        System.out.println("********************");
    }

    /**
     * optional测试
     */
    @Test
    public void test_optional() {
        Person person1 = new Person(1, "aaa1", 12, true, new Date());
        Person person2 = new Person(2, null, 13, true, new Date());
        Person person3 = new Person(3, "aaa3", 41, false, new Date());
        Person person4 = null;

        Optional.ofNullable(person1).ifPresent(p -> System.out.println("不为空, 输出: 年龄" + p.getAge()));
        //filter(predicate)  根据传入的谓语过滤出符合条件的对象
        Optional.ofNullable(person3).filter(person -> (person.getAge() > 20)).ifPresent(p -> System.out.println("不为空且年龄大于20, 输出: 年龄" + p.getAge()));
        Optional.ofNullable(person4).ifPresent(p -> System.out.println("年龄" + p.getAge()));

        //orElse(obj) 如果ofNullable的对象为空, 就用替换它
        System.out.println("person4 orElse before:" + person4);
        Person personOrElse = Optional.ofNullable(person4).orElse(new Person(4, "aaa4", 30, false, new Date()));
        System.out.println("personOrElse :" + personOrElse);
        System.out.println("person4 orElse after:" + person4);

        //Optional.map()方法(对象进行二次包装)
        String msg = Optional.ofNullable(person2).map(p -> p.getName()).orElse("person2对象name为空");
        System.out.println(msg);

        Supplier<Person> supplier = new Supplier<Person>() {
            @Override
            public Person get() {
                //返回一个随机值
                Person person = new Person();
                person.setAge(1000);
                return person;
            }
        };
        Person person = Optional.ofNullable(person4).orElseGet(supplier);
        System.out.println("person4 为空 执行supplier.getTokenAndKey()方法" + person);

        Optional.ofNullable(person4).orElseThrow(() -> new NoSuchFieldError("person4 元素为空 报错!"));
    }
}

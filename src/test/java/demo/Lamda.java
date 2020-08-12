package demo;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.function.Supplier;

/**
 * <br/>Author hanhaotian
 * <br/>Description :
 * <br/>CreateTime 2020/2/5
 */
public class Lamda {

    @Test
    public void test1() {
        ArrayList<Integer> arrayList = new ArrayList<>();
        System.out.println("123");
        //这个叫断言，比较第一个参数和第二个参数是否相等，省的打印就能判断有没有达到预期，这个测试中用不着
        Assert.assertEquals(1,1);
    }

    @Test
    public void test2() {
        List<String> names1 = Arrays.asList("Google", "Runoob", "Taobao", "Baidu", "Sina");
        List<String> names2 = Arrays.asList("Google", "Runoob", "Taobao", "Baidu", "Sina");

        Collections.sort(names1, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.compareTo(s2);
            }
        });
        System.out.println(names1);

        names2.sort(String::compareTo);
        System.out.println("使用 Java 8 语法: \n"+names2);
        names2.forEach(System.out::println);

        System.out.println("\nlambda方法引用：通过方法的名字来指向一个方法");
        final Car car = Car.create(Car::new);
        final List<Car> cars = Arrays.asList(car, Car.create(Car::new), Car.create(Car::new));
        cars.forEach(Car::collide);
        cars.forEach(Car::repair);
        final Car police = Car.create(Car::new);
        cars.forEach(police::follow);
    }
}


class Car {
    //Supplier是jdk1.8的接口，这里和lamda一起使用了
    public static Car create(final Supplier<Car> supplier) {
        return supplier.get();
    }

    public static void collide(final Car car) {
        System.out.println("Collided " + car.toString());
    }

    public void follow(final Car another) {
        System.out.println("Following the " + another.toString());
    }

    public void repair() {
        System.out.println("Repaired " + this.toString());
    }
}
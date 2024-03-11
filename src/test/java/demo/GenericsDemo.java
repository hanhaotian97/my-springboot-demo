package demo;

import com.hht.myspringbootdemo.entity.Apple;
import com.hht.myspringbootdemo.entity.Fruit;
import com.hht.myspringbootdemo.entity.RedApple;

import java.util.ArrayList;
import java.util.List;

/**
 * <br/>Description : 描述 泛型测试
 * <br/>CreateTime : 2021/7/15
 * @author hanhaotian
 */
public class GenericsDemo {
    public static void main(String[] args) {
        //指定元素类型
        ObjectTool<String> tool = new ObjectTool<>();
        tool.setObj("钟福成");
        //tool.setObj(1);  //设置非指定的元素类型会报错
        String s = tool.getObj();
        System.out.println(s);

        show(123);
        show("abc");

        //List集合装载的是Integer，可以调用该方法
        List<Integer> integer = new ArrayList<>();
        showIterNumber(integer);
        //List集合装载的是String，在编译时期就报错了
        //List<String> strings = new ArrayList<>();
        //showIterNumber(strings);

        List<String> list = new ArrayList<>();
        //类型被擦除了，保留的是类型的上限，String的上限就是Object
        List list1 = list;

        testPecs();
    }

    /**
     * 定义泛型方法
     * 如果参数之间的类型有依赖关系，或者返回值是与参数之间有依赖关系的。那么就使用泛型方法
     */
    private static <T> void show(T t) {
        System.out.println(t);
    }

    /**
     * 定义泛型通配符方法
     * 如果没有依赖关系的，就使用通配符，通配符会灵活一些.
     * ?号通配符表示可以匹配任意类型，任意的Java类都可以匹配
     */
    private static void showIter(List<?> list) {
        for (Object o : list) {
            System.out.println(o);
        }
    }

    /**
     * 定义泛型方法-集合-设定通配符上限
     */
    public static void showIterNumber(List<? extends Number> list) {

    }

    /**
     * 通配符的PECS原则:
     * 如果要从集合中读取类型T的数据，并且不能写入，可以使用 ? extends 通配符；(Producer Extends)
     * 如果要从集合中写入类型T的数据，并且不需要读取，可以使用 ? super 通配符；(Consumer Super)
     * 如果既要存又要取，那么就不要使用任何通配符。
     */
    private static void testPecs() {
        //? extends上限: 装载的元素只能是该类的子类或者自身, 不能写入元素, 只能读取元素
        List<Integer> integers = new ArrayList<Integer>();
        integers.add(1);
        //编译器只知道integers是number的子类, 但不知道具体的子类,为了类型安全拒绝所有向其加入任何子类
        List<? extends Number> number = integers; //works, apple is a subclass of Fruit.
        //number.add(new Long(1L));        //compile error
        Number number1 = number.get(0);
        int i = number1.intValue();
        System.out.println("? extends  " + i);


        //? super 下限: 只能写入该类的子类或者自身, 读取时只能读取到object类
        List<Fruit> fruits = new ArrayList<Fruit>();
        List<? super Apple> aa = fruits;
        aa.add(new Apple());
        aa.add(new RedApple());
        Object o1 = aa.get(0);
        System.out.println(o1);
        Object o2 = aa.get(1);
        System.out.println(o2);
    }
}

/**
 * 泛型类
 * @param <T>
 */
class ObjectTool<T> {
    private T obj;

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }
}

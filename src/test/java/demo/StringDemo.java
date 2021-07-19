package demo;

import com.hht.myspringbootdemo.entity.User;

/**
 * <br/>Description : 描述 string的测试
 * <br/>CreateTime : 2021/7/13
 * @author hanhaotian
 */
public class StringDemo {
    public static void main(String[] args) {
        //new方式赋值: 常量池中没有 在常量池和堆中创建对象；常量池中有则在堆中实例化该对象，然后返回对象内存地址。
        String a = new String("123");
        //字符串赋值: 常量池中有 返回该对象, 常量池中没有 创建该对象并返回
        String b = "123";
        String c = "123";
        System.out.println("a == b : " + (a == b));  //false
        System.out.println("b == c : " + (b == c));  //true

        String str1 = "3";
        //纯字符串拼接的值, 最终也会放到常量池中
        String str2 = "12" + "3";
        //字符串加对象拼接, 最终是放到堆中
        String str3 = "12" + str1;
        System.out.println("b == str2 : " + (b == str2));  //true
        System.out.println("b == str3 : " + (b == str3));  //false
        //intern()方法是用来检查常量池, 如果该值存在于常量池就返回常量池对象, 否则就放入常量池并返回
        String intern = str2.intern();
        System.out.println("b == intern : " + (b == intern));  //true

        //原因在于值传递与引用传递的不同
        //string是不可变类型，所以在方法内部对string变量赋值就相当于开辟了一块新的内存, 使方法中拷贝的引用指向了新内存，退出方法后，原引用还是指向原来的那块内存。
        changeStrTo456(a);
        System.out.println("changeStrTo456 a : " + a);  //123
        changeStrTo456(b);
        System.out.println("changeStrTo456 b : " + b);  //123
        //值传递 : 调用函数时将实际参数复制一份传递到函数中, 函数中对值的修改不影响实际参数
        //引用传递 : 调用函数时将实际参数的地址 直接传递到函数中, 函数中对值的修改影响实际参数
        User user = new User(1, "123", null, null);
        changeUser(user);
        System.out.println("changeUser after : " + user);  //值被修改为456
        User user2 = new User(1, "123", null, null);
        newUser(user2);
        System.out.println("newUser after : " + user2);  //值没有被修改

    }

    private static void changeStrTo456(String a) {
        a = "456";
    }

    private static void changeUser(User u) {
        u.setId(2);
        u.setName("456");
        System.out.println("changeUser : " + u);
    }

    private static void newUser(User u) {
        //Java中其实还是值传递的，只不过对于对象参数，值的内容是对象的引用。
        //传入的实参user的地址值被赋值给了形参u, 但此时形参u被赋值一个新的对象, 拿到的是栈内存中的一个新内存地址, 此时对形参u的修改与实参user没有关系
        u = new User();
        u.setId(2);
        u.setName("456");
        System.out.println("newUser : " + u);
    }
}

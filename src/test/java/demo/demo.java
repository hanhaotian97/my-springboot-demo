package demo;

import java.util.Stack;

/**
 * <br/>Description : 描述
 * <br/>CreateTime : 2022/3/1
 * @author hanhaotian
 */
public class demo {
    public static void main(String[] args) {
        //demo1();
        System.out.println(fibonacciRecursion(10));
        System.out.println(fibonacciCircle(10000));

        //(1+2+2)-(5+4)=-4
        String s = "1 2 + 2 + 5 4 + -";
        System.out.println(evalRPN(s.split(" ")));
    }

    /**
     * 逆波兰表达式
     */
    public static int evalRPN(String[] tokens) {
        if (tokens == null || tokens.length == 0) {
            return 0;
        }
        if (tokens.length == 1) {
            return Integer.parseInt(tokens[0]);
        }
        Stack<Integer> stack = new Stack<Integer>();
       // LinkedList<Integer> stack = new LinkedList<Integer>();
        for (int i = 0; i < tokens.length; i++) {
            String temp = tokens[i];
            if (temp.equals("+")) {
                int a = stack.pop();
                int b = stack.pop();
                stack.push(a + b);
            } else if (temp.equals("*")) {
                int a = stack.pop();
                int b = stack.pop();
                stack.push(a * b);
            } else if (temp.equals("-")) {
                int a = stack.pop();
                int b = stack.pop();
                stack.push(b - a);
            } else if (temp.equals("/")) {
                int a = stack.pop();
                int b = stack.pop();
                stack.push(b / a);
            } else {
                stack.add(Integer.parseInt(temp));
            }
        }
        return stack.pop();
    }

    /**
     * 斐波那契函数的时间复杂度
     * 循环和递归
     */
    public static long fibonacciRecursion(long n) {
        return n <= 2 ? 1 : fibonacciRecursion(n - 1) + fibonacciRecursion(n - 2);
    }

    public static long fibonacciCircle(long n) {
        if (n <= 2) {
            return 1;
        }
        int n1 = 1, n2 = 1, sum = 0;
        for (int i = 0; i < n - 2; i++) {
            sum = n1 + n2;
            n1 = n2;
            n2 = sum;
        }
        return sum;
    }

    private static void demo1() {
        int a = 1234567;
        String aStr = String.valueOf(a);
        String s = aStr.substring(5) + aStr.substring(0, 5);
        System.out.println(Integer.parseInt(s));
    }
}

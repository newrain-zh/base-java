package org.newrain.base.question;

/**
 * static 静态代码块示例
 * 调整静态块顺序 执行顺序也有不同
 */
public class StaticTest {

    static int cnt = 6;

    static {
        cnt /= 3;
        System.out.println("static2: " + cnt);
    }

    static {
        cnt += 9;
        System.out.println("static1: " + cnt);
    }


    public static void main(String[] args) {
        System.out.println("cnt =" + cnt);
    }


}
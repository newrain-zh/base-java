package org.newrain.base.question;

/**
 * 奇偶数判断示例
 */
public class EvenNumber {

    public static void main(String[] args) {
        System.out.println(test1(-1));
        System.out.println(test2(-1));
        System.out.println(remainder(2, -1));
        System.out.println(2-2/-1*-1); //0
    }

    /**
     * 用奇数判断
     *
     * @param num
     * @return
     */
    public static String test1(int num) {
        return num + "->" + (num % 2 == 1 ? "奇数" : "偶数");
    }

    /**
     * 用偶数判断
     *
     * @param num
     * @return
     */
    public static String test2(int num) {
        return num + "->" + (num % 2 == 0 ? "偶数" : "奇数");
    }

    /**
     * 模拟取余计算
     *
     * @param dividend 被除数 2
     * @param divisor  除数 -1
     * @return
     */
    public static int remainder(int dividend, int divisor) {
        //被除数-被除数/除数*除数
        //2-2/-1*-1
        return dividend - dividend / divisor * divisor;
    }
}
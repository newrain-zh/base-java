package org.newrain.base.question;

import java.util.Arrays;

/**
 * 字符串切割示例
 */
public class StringTest {


    public static void main(String[] args) {
        String str = "12.03";
        String[] res = str.split("."); //res数组为空
        //String[] res = str.split("\\."); //正确使用方式
        for (String re : res) System.out.println(re);
        System.out.println(Arrays.toString(res));
    }

}
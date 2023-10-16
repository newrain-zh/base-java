package org.newrain.base.core;

public class CatchExample {


    public static void main(String[] args) {
//        System.out.println(test01());//0
//        System.out.println(test02());//0

        int i = 10;
        i++;
        System.out.println(i); //11
        ++i;
        System.out.println(i); //12

        //作为表达式
        System.out.println(i++);//12
        System.out.println(i);//13


    }


    /**
     * try 代码块中有返回语句
     * 执行结果如下：
     * 执行try：0
     * 执行finally：1
     * 1
     *
     * @return
     */
    public static int test01() {
        int i = 0;
        try {
            System.out.println("执行try：" + i);
            return i; //此时已返回
        } finally {
            i++;
            System.out.println("执行finally：" + i);
        }
    }

    /**
     * try finally 都有返回语句
     * <p>
     * 执行结果如下：
     * 执行try：0
     * 执行finally：1
     * 1
     *
     * @return
     */
    private static int test02() {
        int i = 0;
        try {
            System.out.println("执行try：" + i);
//            return i++;
            return ++i; //注意此处i++ 和++i的区别
        } finally {
            System.out.println("执行finally：" + i);
//            return ++i;
            return ++i; //注意此处i++ 和++i的区别
        }
    }
}

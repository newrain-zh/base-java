package org.newrain.jvm.escapeAnalysi;

/**
 * 逃逸分析代码
 * vm参数
 * -Xmx1G
 * -Xms1G
 * -XX:+DoEscapeAnalysis //开启逃逸分析
 * -XX:-DoEscapeAnalysis //关闭逃逸分析
 * -XX:+PrintGCDetails
 * -XX:+HeapDumpOnOutOfMemoryError
 * 运行后使用 jmap -histo [pid] 查看org.xinyu.jvm.escapeAnalysi.AllotOnStackTest$User 的instances计数
 *
 * @author newRain
 * @description 逃逸分析代码
 */
public class AllotOnStackExample {

    public static void main(String[] args) {
        long a1 = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            alloc();
        }
        // 查看执行时间
        long a2 = System.currentTimeMillis();
        System.out.println("cost " + (a2 - a1) + " ms");
        // 为了方便查看堆内存中对象个数，线程sleep
        try {
            Thread.sleep(100000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
    }

    private static void alloc() {
        User user = new User();
    }

    static class User {

    }
}

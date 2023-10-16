package org.newrain.jvm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 运行参数
 * -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
 * 堆溢出
 */
public class HeapOOM {

    static class OOMObject {

    }

    private static final int _1MB = 1024 * 1024;

    public static void main(String[] args) {
        System.out.println(Arrays.toString(args));
        List<Integer> list = new ArrayList<>();
        while (true) {
            list.add(1024 * 1024);
//            list.add(new String("hello world"));
        }
    }
}

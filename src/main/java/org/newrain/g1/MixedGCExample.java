package org.newrain.g1;

import java.util.ArrayList;
import java.util.List;

public class MixedGCExample {

    private static final List list = new ArrayList<>();

    /*
        -Xmx256M -XX:+UseG1GC -XX:+PrintGCTimeStamps -XX:+PrintGCDetails
        --测试混合GC
     */
    public static void main(String[] args) {
        while (true) {
            byte[] data = new byte[1024 * 256];
            for (int i = 0; i < 50; i++) {
                data = new byte[1024 * 256];
                byte[] data2 = new byte[1024 * 256];
                list.add(data2);
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
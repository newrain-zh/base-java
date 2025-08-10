package org.newrain.g1;

import java.util.ArrayList;
import java.util.List;

public class MixedGCExample1 {

    private static final List list = new ArrayList<>();

    /*
       --XX:InitialHeapSize=128 -Xmx256M -XX:+UseG1GC -XX:+PrintGCTimeStamps -XX:+PrintGCDetails -XX:MaxGCPauseMillis=20
        --测试混合GC
     */
    public static void main(String[] args) {
        while (true) {
            byte[] data = new byte[1024 * 256];
            for (int i = 0; i < 15; i++) {
                data = new byte[1024 * 256];
            }
            for (int i = 0; i < 5; i++) {
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
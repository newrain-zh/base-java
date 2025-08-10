package org.newrain.g1;

public class RegionExample {

    /*
        测试 Region大小
       -Xmx128M -XX:+UseG1GC -XX:+PrintGCTimeStamps -XX:+PrintGCDetails
     */
    public static void main(String[] args) {
        byte[] data = new byte[1024 * 256];
        for (int i = 0; i < 100; i++) {
            data = new byte[1024 * 256];
        }
    }
}
package org.newrain.g1;

public class RegionExample1 {

    /*
        测试 大对象 GC
       -Xmx128M -XX:+UseG1GC -XX:+PrintGCTimeStamps -XX:+PrintGCDetails
     */
    public static void main(String[] args) {
        byte[] data = new byte[1024 * 512];
        for (int i = 0; i < 100; i++) {
            data = new byte[1024 * 512];
        }
    }
}
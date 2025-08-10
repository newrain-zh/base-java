package org.newrain.g1;

public class RegionExample3 {

    /*
       测试新生代、老年代占比
       -Xmx128M -Xms128M -XX:+UseG1GC -XX:+PrintGCTimeStamps -XX:+PrintGCDetails
     */
    public static void main(String[] args) {
        byte[] data = new byte[1024 * 256];
        for (int i = 0; i < 1000; i++) {
            data = new byte[1024 * 256];
        }
    }
}
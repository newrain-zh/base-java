package org.newrain.jvm.g1;

public class RegionExample2 {

    /*
       测试新生代、老年代占比
       -Xmx128M -Xms128M -XX:+UseG1GC -XX:+PrintGCTimeStamps -XX:+PrintGCDetails
       第一次：[Eden: 24.0M(24.0M)->0.0B(40.0M) Survivors: 0.0B->2048.0K Heap: 24.0M(128.0M)->1107.5K(128.0M)]
       第二次：[Eden: 40.0M(40.0M)->0.0B(74.0M) Survivors: 2048.0K->2048.0K Heap: 41.1M(128.0M)->1070.9K(128.0M)]
       第三次：[Eden: 74.0M(74.0M)->0.0B(75.0M) Survivors: 2048.0K->1024.0K Heap: 75.0M(128.0M)->985.3K(128.0M)]
       第四次：[Eden: 75.0M(75.0M)->0.0B(75.0M) Survivors: 1024.0K->1024.0K Heap: 75.9M(128.0M)->1007.0K(128.0M)]

        测试调整新生代占比
        -Xmx128M -XX:NewRatio=6 -XX:+UseG1GC -XX:+PrintGCTimeStamps -XX:+PrintGCDetails
        128/(1+6) = 18MB

        测试 Region 大小
        -XX:G1HeapRegionSize=5M

        测试调节堆内存
        -Xmx128M -Xms128M -XX:+UseG1GC -XX:+PrintGCTimeStamps -XX:+PrintGCDetails

     */
    public static void main(String[] args) {
        byte[] data = new byte[1024 * 256];
        for (int i = 0; i < 1000; i++) {
            data = new byte[1024 * 256];
        }
    }

    private static final int MB = 1024 * 1024;
    private static final byte[] MB2 = new byte[4 * 1024 * 1024];
}
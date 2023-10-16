package org.newrain.jvm.gc;

public class FullGcTest {
    private static final int _1MB = 1024 * 1024;

    public static void testAllocation(){
        byte[] bytes1 = new byte[5 * _1MB];
        byte[] bytes2 = new byte[5 * _1MB];
        byte[] bytes3 = new byte[5 * _1MB];
    }

    public static void main(String[] args) {
        testAllocation();
    }
}

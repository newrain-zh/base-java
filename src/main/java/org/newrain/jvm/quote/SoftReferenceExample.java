package org.newrain.jvm.quote;

import java.lang.ref.SoftReference;

/**
 * 软引用
 * 启动参数 -Xmx20M
 */
public class SoftReferenceExample {
    public static void main(String[] args) {
        SoftReference<byte[]> m = new SoftReference<>(new byte[1025 * 1024 * 10]);
        System.out.println(m.get());//not null
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(m.get());//not null
        byte[] b = new byte[1024 * 1024 * 10];
        System.out.println(m.get());//堆内存不够 null 否则 not null
    }
}

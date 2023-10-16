package org.newrain.jvm.quote;

import java.lang.ref.WeakReference;

/**
 * 弱引用
 */
public class WeakReferenceExample {
    public static void main(String[] args) {
        WeakReference<String> sr = new WeakReference<String>(new String("hello"));
        System.out.println(sr.get());
        System.gc();                //通知JVM的gc进行垃圾回收
        System.out.println(sr.get());//null
    }
}

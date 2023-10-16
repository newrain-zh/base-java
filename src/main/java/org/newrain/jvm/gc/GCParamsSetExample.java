package org.newrain.jvm.gc;

/**
 * -Xms20M
 * -Xmx20M
 * -Xmn10M
 * -XX:SurvivorRatio=8
 * <p>
 * 垃圾回收器设置
 * -XX:+UseSerialGC  虚拟机运行在Client模式的默认值，打开此开关参数后，使用Serial+Serial Old收集器组合进行垃圾收集。
 * -XX:UseParNewGC 使用ParNew+Serial Old收集器组合进行垃圾收集
 * -XX:UseConcMarkSweepGC 使用ParNew+CMS+Serial Old收集器组合进行垃圾收集。Serial Old作为CMS收集器出现Concurrent Mode Failure的备用垃圾收集器。
 * -XX:UseParallelGC 虚拟机运行在Server模式的默认值，打开此开关参数后，使用Parallel Scavenge+Serial Old收集器组合进行垃圾收集。
 * -XX:UseParallelOldGC 打开此开关参数后，使用Parallel Scavenge+Parallel Old收集器组合进行垃圾收集。
 *  GC日志设置
 * -XX:+PrintGC  打印GC日志
 * -XX:+PrintGCDetails 打印详细的GC日志。还会在退出前打印堆的详细信息。
 * -XX:+PrintHeapAtGC 每次GC前后打印堆信息。
 * -XX:+PrintGCTimeStamps 打印GC发生的时间。
 * -XX:+PrintGCDateStamps 打印GC发生的时间日期类型。
 * -XX:+PrintGCApplicationConcurrentTime 打印应用程序的执行时间
 * -XX:+PrintGCApplicationStoppedTime 打印应用由于GC而产生的停顿时间
 * -Xloggc:gc.log 将GC日志以文件形式输出
 * -XX:+PrintReferenceGC 跟踪软引用、弱引用、虚引用和Finallize队列。
 *
 * @author zhangzhiqin
 */
public class GCParamsSetExample {

    public static void main(String[] args) {
        // allocate 4M space
        byte[] b = new byte[4 * 1024 * 1024];
//        System.out.println("first allocate");
        // allocate 4M space
        b = new byte[4 * 1024 * 1024];
//        System.out.println("second allocate");
    }
}

package org.newrain.jvm.rsort;

/**
 * 1,2,3,4步骤之间没有强依赖关系 有可能存在重排序
 * 使用volatile 关键字可以实现线程之间的可见 以及防止重排序
 *
 * @author newrain
 */
public class SortExample {

    private /*volatile*/ static int num = 0;
    private /*volatile*/ static boolean ready = false;

    public static class ReadThread extends Thread {
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                if (ready) {//(1)
                    System.out.println(num + num);//(2)
                }
                System.out.println("read thread...");
            }
        }
    }

    public static class WriteThread extends Thread {
        @Override
        public void run() {
            num = 2;//(3)
            ready = true;//(4)
            System.out.println("WriteThread set over...");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReadThread readThread = new ReadThread();
        readThread.start();
        WriteThread writeThread = new WriteThread();
        writeThread.start();
        Thread.sleep(10);
        readThread.interrupt();
        System.out.println("main thread over...");
    }
}



package org.newrain.jvm;

import sun.nio.ch.DirectBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.CountDownLatch;

public class NativeHeapTest {

    public static void main(String[] args) throws InterruptedException, IOException {
      /*  List<ByteBuffer> byteBufferList = new ArrayList<>();
        while (true) {
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(10240);
            byteBufferList.add(byteBuffer);
            Thread.sleep(100);
        }
*/
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024 * 1024 * 1024);
        System.in.read();
        ((DirectBuffer) buffer).cleaner().clean();
        new CountDownLatch(1).await();
    }


}

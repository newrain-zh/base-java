package org.newrain.base.zerocpoy;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Scanner;

/**
 * linux之mmap实现 零拷贝
 */
public class MmapExample {

    public static void main(String[] args) throws IOException {
        File file = new File("/Users/newrain/Downloads/alipay_record_20211220_1212_1.csv");
        long len = file.length();
        byte[] ds = new byte[(int) len];
        try {
            MappedByteBuffer mappedByteBuffer = new RandomAccessFile(file, "r")
                    .getChannel()
                    .map(FileChannel.MapMode.READ_ONLY, 0, len);
            for (int offset = 0; offset < len; offset++) {
                byte b = mappedByteBuffer.get();
                ds[offset] = b;
            }
            Scanner scan = new Scanner(new ByteArrayInputStream(ds)).useDelimiter(" ");
            while (scan.hasNext()) {
                System.out.print(scan.next() + " ");
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}

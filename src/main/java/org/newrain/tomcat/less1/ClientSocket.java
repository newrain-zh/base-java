package org.newrain.tomcat.less1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author newRain
 * @description 模拟发送请求
 */
public class ClientSocket {

    public static void main(String[] args) throws IOException, InterruptedException {

        Socket socket = new Socket("127.0.0.1", 8080);
        boolean autoFlush = true;
        PrintWriter out = new PrintWriter(socket.getOutputStream(), autoFlush);
        // send an HTTP request to the web server
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out.println("GET /index.html HTTP/1.1");
        out.println("Host: localhost:8080");
        out.println("Connection: Close");
        out.println();
        // read the response
        boolean loop = true;
        StringBuffer sb = new StringBuffer(8096);
        while (loop) {
            if (in.ready()) {
                int i = 0;
                while (i != -1) {
                    i = in.read();
                    sb.append((char) i);
                }
                loop = false;
            }
            Thread.sleep(50);
        }
        // display the response to the out console
        System.out.printf("read the response:{%s}%n", sb);
        socket.close();
        out.close();
        in.close();
    }
}

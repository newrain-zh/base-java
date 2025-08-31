package org.newrain.base.socket.nio.example;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

@Slf4j
public class NIOClient {

    private static final String SERVER_HOST = "127.0.0.1";
    private static final int    SERVER_PORT = 8000;
    private static final int    BUFF_SIZE   = 1024;

    public static void main(String[] args) {
        try {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false); // 非阻塞模式
            socketChannel.connect(new InetSocketAddress(SERVER_HOST, SERVER_PORT));
            log.info("客户端正在连接服务端...");
            Selector selector = Selector.open();
            // 将 SocketChannel 注册到 Selector 中，并监听连接就绪事件
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
            while (true) {
                selector.select(); // 阻塞直到有就绪事件;
                Set<SelectionKey>      selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator      = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey next = iterator.next();
                    iterator.remove();
                    if (next.isConnectable()) {
                        handleConnect(next, selector);
                    }
                    if (next.isConnectable()){
                        handleRead(next, selector);
                        return;
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void handleConnect(SelectionKey key, Selector selector) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        // 完成连接 非阻塞式连接需显示调用 finishConnect()
        if (socketChannel.isConnectionPending()) {
            socketChannel.finishConnect();
        }
        log.info("客户端连接客户端成功!");
        socketChannel.configureBlocking(false);
        String     msg         = "hello world";
        ByteBuffer writeBuffer = ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8));
        socketChannel.write(writeBuffer);
        log.info("客户端发送消息：{}", msg);
        // 重新注册通道到 Selector，关注“读就绪”事件（准备接收服务端响应）
        socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(BUFF_SIZE));
    }

    // 处理度就绪事件：读取服务端响应
    private static void handleRead(SelectionKey key, Selector selector) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        ByteBuffer    buffer        = (ByteBuffer) key.attachment();
        int           read          = socketChannel.read(buffer);
        if (read > 0) {
            buffer.flip(); // 切换为读模式
            byte[] bytes = new byte[buffer.remaining()];
            buffer.get(bytes);
            String serverResponse = new String(bytes, StandardCharsets.UTF_8);
            log.info("收到服务端响应:{}", serverResponse);
        }
        socketChannel.close();
        key.cancel();
    }
}
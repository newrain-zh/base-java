package org.newrain.base.socket.nio.input;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedDeque;

@Slf4j
public class NIOInputClient {


    private static final int    PORT = 8000;
    private static final String HOST = "127.0.0.1";

    private static final int BUFFER_SIZE = 1024;

    private static final Queue<String> msgQueue = new ConcurrentLinkedDeque<>();

    public static void main(String[] args) {

        try (Selector selector = Selector.open(); SocketChannel socketChannel = SocketChannel.open()) {
            socketChannel.configureBlocking(false);
            startInputThread(selector);
            socketChannel.connect(new java.net.InetSocketAddress(HOST, PORT));
            log.info("准备连接到服务端");
            socketChannel.register(selector, SelectionKey.OP_CONNECT); // 注册连接就绪事件
            for (; ; ) {
                selector.select();
                Set<SelectionKey>      selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> keyIterator   = selectionKeys.iterator();
                while (keyIterator.hasNext()) {
                    SelectionKey selectionKey = keyIterator.next();
                    log.info("selectionKey:{}", selectionKey);
                    keyIterator.remove();
                    if (selectionKey.isConnectable()) {
                        handleConnect(selectionKey, selector);
                    }
                    if (selectionKey.isReadable()) {
                        handleServerRead(selectionKey, selector); // 处理服务端响应
                    }
                    if (selectionKey.isWritable()) {
                        handleWrite(socketChannel, selector, selectionKey);
                    }
                }
            }
        } catch (Exception e) {

        }
    }

    private static void handleServerRead(SelectionKey selectionKey, Selector selector) throws IOException {
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        ByteBuffer    buffer        = (ByteBuffer) selectionKey.attachment();
        int           readBytes     = socketChannel.read(buffer);
        if (readBytes > 0) {
            buffer.flip();
            byte[] bytes = new byte[buffer.remaining()];
            buffer.get(bytes);
            String serverResponse = new String(bytes, StandardCharsets.UTF_8);
            log.info("服务端响应：{}", serverResponse);
            // 如果是退出响应，关闭连接
            if (serverResponse.contains("已收到退出命令")) {
                socketChannel.close();
                selectionKey.cancel();
                log.info("已与服务端断开连接");
                System.exit(0); // 退出程序
            }
            log.info("请输入消息：");
        }

        buffer.clear();
    }

    private static void handleConnect(SelectionKey selectionKey, Selector selector) throws IOException {
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        if (socketChannel.isConnectionPending()) {
            socketChannel.finishConnect();
        }
        log.info("连接服务端成功!");
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_WRITE | SelectionKey.OP_READ, ByteBuffer.allocate(BUFFER_SIZE));

    }

    /**
     * 处理写事件（发送消息到服务端）
     */
    private static void handleWrite(SocketChannel socketChannel,
                                    Selector selector,
                                    SelectionKey key) throws IOException {
        ByteBuffer buffer = (ByteBuffer) key.attachment();
        // 检查是否有消息需要发送
        String message;
        while ((message = msgQueue.poll()) != null) {
            // 将消息写入缓冲区并发送
            buffer.clear();
            buffer.put(message.getBytes(StandardCharsets.UTF_8));
            buffer.flip();
            socketChannel.write(buffer);
            log.info("已发送：{}", message);
            socketChannel.register(selector, SelectionKey.OP_WRITE, ByteBuffer.allocate(1024));
        }
        // 如果队列中没有消息了，暂时取消写事件关注，避免空轮询
/*        if (msgQueue.isEmpty()) {
            key.interestOps(SelectionKey.OP_READ);
        }*/
    }

    private static void startInputThread(Selector selector) {
        Thread inputThread = new Thread(() -> {
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
                String input;
                while ((input = bufferedReader.readLine()) != null) {
                    // 输入内容
                    log.info("控制台输入:{}", input);
                    msgQueue.add(input);
                    if ("exit".equals(input)) {
                        log.info("准备退出");
                    }
                }
            } catch (Exception e) {
                log.error("输入异常", e);
            }

        });
        inputThread.setDaemon(true);
        inputThread.start();
    }


}
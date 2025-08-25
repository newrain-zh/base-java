package org.newrain.spi;


/**
 * 消息服务接口 - 示例SPI服务
 */
@ServiceProviderInterface("email") // 指定默认实现为email
public interface MessageService {
    String sendMessage(String content);
}
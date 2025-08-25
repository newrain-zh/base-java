package org.newrain.spi;


/**
 * SPI机制演示
 * 多个ServiceProvider实现
 */
public class SPIDemo {
    public static void main(String[] args) {
        // 加载消息服务的所有实现
        ServiceLoader<MessageService> serviceLoader = ServiceLoader.load(MessageService.class);

        // 获取所有服务并使用
        System.out.println("=== 所有服务实现 ===");
        for (MessageService service : serviceLoader.getAllServices()) {
            System.out.println(service.sendMessage("Hello SPI"));
        }

        // 获取指定服务
        System.out.println("\n=== 指定服务(sms) ===");
        MessageService smsService = serviceLoader.getService("sms");
        System.out.println(smsService.sendMessage("Hello SMS"));

        // 获取默认服务
        System.out.println("\n=== 默认服务(email) ===");
        MessageService defaultService = serviceLoader.getDefaultService();
        System.out.println(defaultService.sendMessage("Hello Default"));
    }
}
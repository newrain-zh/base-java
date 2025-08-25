package org.newrain.spi;

/**
 * 短信服务实现
 */
public class SmsService implements MessageService {
    @Override
    public String sendMessage(String content) {
        return "SMS sent: " + content;
    }
}
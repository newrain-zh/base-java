package org.newrain.spi;

/**
 * 邮件服务实现
 */
public class EmailService implements MessageService {
    @Override
    public String sendMessage(String content) {
        return "Email sent: " + content;
    }
}
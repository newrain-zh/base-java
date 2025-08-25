package org.newrain.design;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 发布-订阅模式实现
 * 包含事件发布、订阅、取消订阅等功能
 */
public class PubSubPatternExample {

    /**
     * 事件接口 - 所有事件的基类
     */
    public interface Event {
        String getType();
        long getTimestamp();
    }

    /**
     * 事件处理器接口
     */
    public interface EventHandler<T extends Event> {
        void handle(T event);
    }

    /**
     * 消息代理 - 核心发布订阅组件
     */
    public static class MessageBroker {
        // 存储主题与订阅者的映射关系
        private final ConcurrentMap<String, List<EventHandler<? extends Event>>> subscribers =
                new ConcurrentHashMap<>();

        // 事件ID生成器
        private final AtomicLong eventIdGenerator = new AtomicLong(0);

        // 线程池用于异步处理事件
        private final ExecutorService executorService =
                Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        /**
         * 订阅特定类型的事件
         * @param eventType 事件类型
         * @param handler 事件处理器
         * @param <T> 事件类型
         * @return 订阅ID，可用于取消订阅
         */
        public <T extends Event> String subscribe(String eventType, EventHandler<T> handler) {
            String subscriptionId = UUID.randomUUID().toString();

            // 包装处理器，添加订阅ID信息
            EventHandlerWrapper<T> wrapper = new EventHandlerWrapper<>(subscriptionId, handler);

            subscribers.computeIfAbsent(eventType, k -> new CopyOnWriteArrayList<>())
                    .add(wrapper);

            System.out.println("订阅成功: " + eventType + ", 订阅ID: " + subscriptionId);
            return subscriptionId;
        }

        /**
         * 取消订阅
         * @param subscriptionId 订阅ID
         */
        public void unsubscribe(String subscriptionId) {
            for (List<EventHandler<? extends Event>> handlers : subscribers.values()) {
                handlers.removeIf(handler ->
                        handler instanceof EventHandlerWrapper &&
                                ((EventHandlerWrapper<?>) handler).subscriptionId.equals(subscriptionId)
                );
            }
            System.out.println("取消订阅: " + subscriptionId);
        }

        /**
         * 发布事件
         * @param event 事件对象
         */
        public void publish(Event event) {
            String eventType = event.getType();
            List<EventHandler<? extends Event>> handlers = subscribers.get(eventType);

            if (handlers != null && !handlers.isEmpty()) {
                // 异步处理事件
                executorService.submit(() -> {
                    for (EventHandler<? extends Event> handler : handlers) {
                        try {
                            // 调用处理器处理事件
                            @SuppressWarnings("unchecked")
                            EventHandler<Event> eventHandler = (EventHandler<Event>) handler;
                            eventHandler.handle(event);
                        } catch (Exception e) {
                            System.err.println("处理事件时出错: " + e.getMessage());
                        }
                    }
                });
            }
        }

        /**
         * 关闭消息代理，释放资源
         */
        public void shutdown() {
            executorService.shutdown();
            try {
                if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                    executorService.shutdownNow();
                }
            } catch (InterruptedException e) {
                executorService.shutdownNow();
                Thread.currentThread().interrupt();
            }
            System.out.println("消息代理已关闭");
        }

        /**
         * 包装事件处理器，添加订阅ID
         */
        private static class EventHandlerWrapper<T extends Event> implements EventHandler<T> {

            private final String subscriptionId;

            private final EventHandler<T> delegate;

            public EventHandlerWrapper(String subscriptionId, EventHandler<T> delegate) {
                this.subscriptionId = subscriptionId;
                this.delegate = delegate;
            }

            @Override
            public void handle(T event) {
                delegate.handle(event);
            }
        }
    }

    /**
     * 用户注册事件
     */
    public static class UserRegisteredEvent implements Event {
        private final long id;
        private final long timestamp;
        private final String username;
        private final String email;

        public UserRegisteredEvent(String username, String email) {
            this.id = UUID.randomUUID().hashCode() & Long.MAX_VALUE;
            this.timestamp = System.currentTimeMillis();
            this.username = username;
            this.email = email;
        }

        @Override
        public String getType() {
            return "user.registered";
        }

        @Override
        public long getTimestamp() {
            return timestamp;
        }

        public long getId() {
            return id;
        }

        public String getUsername() {
            return username;
        }

        public String getEmail() {
            return email;
        }

        @Override
        public String toString() {
            return "UserRegisteredEvent{" +
                    "id=" + id +
                    ", timestamp=" + timestamp +
                    ", username='" + username + '\'' +
                    ", email='" + email + '\'' +
                    '}';
        }
    }

    /**
     * 订单创建事件
     */
    public static class OrderCreatedEvent implements Event {
        private final long id;
        private final long timestamp;
        private final String orderId;
        private final double amount;

        public OrderCreatedEvent(String orderId, double amount) {
            this.id = UUID.randomUUID().hashCode() & Long.MAX_VALUE;
            this.timestamp = System.currentTimeMillis();
            this.orderId = orderId;
            this.amount = amount;
        }

        @Override
        public String getType() {
            return "order.created";
        }

        @Override
        public long getTimestamp() {
            return timestamp;
        }

        public long getId() {
            return id;
        }

        public String getOrderId() {
            return orderId;
        }

        public double getAmount() {
            return amount;
        }

        @Override
        public String toString() {
            return "OrderCreatedEvent{" +
                    "id=" + id +
                    ", timestamp=" + timestamp +
                    ", orderId='" + orderId + '\'' +
                    ", amount=" + amount +
                    '}';
        }
    }

    /**
     * 邮件服务 - 处理用户注册事件
     */
    public static class EmailService implements EventHandler<UserRegisteredEvent> {
        @Override
        public void handle(UserRegisteredEvent event) {
            System.out.println("【邮件服务】发送欢迎邮件给: " + event.getEmail() +
                    ", 用户名: " + event.getUsername());
            // 模拟邮件发送延迟
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("【邮件服务】欢迎邮件已发送: " + event.getEmail());
        }
    }

    /**
     * 数据统计服务 - 处理所有事件
     */
    public static class AnalyticsService implements EventHandler<Event> {
        @Override
        public void handle(Event event) {
            System.out.println("【数据分析】记录事件: " + event.getType() +
                    ", 时间: " + new Date(event.getTimestamp()));
            // 模拟数据分析处理
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * 订单处理服务 - 处理订单创建事件
     */
    public static class OrderService implements EventHandler<OrderCreatedEvent> {
        @Override
        public void handle(OrderCreatedEvent event) {
            System.out.println("【订单服务】处理订单: " + event.getOrderId() +
                    ", 金额: " + event.getAmount());
            // 模拟订单处理
            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("【订单服务】订单处理完成: " + event.getOrderId());
        }
    }

    /**
     * 演示发布-订阅模式的使用
     */
    public static void main(String[] args) throws InterruptedException {
        // 创建消息代理
        MessageBroker broker = new MessageBroker();

        // 创建服务实例
        EmailService emailService = new EmailService();
        AnalyticsService analyticsService = new AnalyticsService();
        OrderService orderService = new OrderService();

        // 订阅事件
        String sub1 = broker.subscribe("user.registered", emailService);
        String sub2 = broker.subscribe("user.registered", analyticsService);
        String sub3 = broker.subscribe("order.created", orderService);
        String sub4 = broker.subscribe("order.created", analyticsService);
        String sub5 = broker.subscribe("user.registered", analyticsService); // 重复订阅同一事件

        System.out.println("\n=== 开始发布事件 ===\n");

        // 发布用户注册事件
        broker.publish(new UserRegisteredEvent("张三", "zhangsan@example.com"));
        broker.publish(new UserRegisteredEvent("李四", "lisi@example.com"));

        // 发布订单创建事件
        broker.publish(new OrderCreatedEvent("ORDER_001", 199.99));
        broker.publish(new OrderCreatedEvent("ORDER_002", 299.99));

        // 等待异步处理完成
        Thread.sleep(1000);

        System.out.println("\n=== 取消一个订阅 ===\n");

        // 取消邮件服务的订阅
        broker.unsubscribe(sub1);

        // 发布新事件，邮件服务不会再收到
        broker.publish(new UserRegisteredEvent("王五", "wangwu@example.com"));

        // 等待异步处理完成
        Thread.sleep(1000);

        // 关闭消息代理
        broker.shutdown();
    }
}
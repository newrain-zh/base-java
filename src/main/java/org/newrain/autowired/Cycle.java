package org.newrain.autowired;

import org.newrain.autowired.service.OrderService;
import org.newrain.autowired.service.UserService;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author newRain
 * @description 模拟单例模式下Spring bean 初始化是如何解决循环依赖问你题
 */
public class Cycle {

    /**
     * 单例池，里面放的是完整的bean，已完成填充属性
     */
    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>();

    /**
     * 存放的是提前暴露出来的bean，没有经历过spring完整的生命周期，没有填充属性
     */
    private final Map<String, Object> earlySingletonObjects = new HashMap<>();

    /**
     * 在Spring中，这个map存放的是beanNam和beanDefinition的映射关系
     */
    static Map<String, Class<?>> map = new HashMap<>();

    static {
        map.put("orderService", OrderService.class);
        map.put("userService", UserService.class);
    }

    /**
     * 如果先调用init方法，就是预加载，如果直接调用getBean就是懒加载，两者的循环依赖问题都解决了
     */
    public void init() {
        for (Map.Entry<String, Class<?>> stringClassEntry : map.entrySet()) {
            createBean(stringClassEntry.getKey());
        }
    }

    /**
     * 获取Bean 对象
     *
     * @param beanName
     * @return
     */
    public Object getBean(String beanName) {
        // 尝试从singletonObjects中取，
        Object singletonObject = this.singletonObjects.get(beanName);
        if (singletonObject != null) {
            return singletonObject;
        }

        // 尝试从earlySingletonObjects取
        singletonObject = this.earlySingletonObjects.get(beanName);
        if (singletonObject != null) {
            return singletonObject;
        }

        return createBean(beanName);
    }

    private Object createBean(String beanName) {
        Object singletonObject;

        try {
            // 创建对象
            singletonObject = map.get(beanName).getConstructor().newInstance();

            // 把没有完成填充属性的半成品 bean 放入earlySingletonObjects
            earlySingletonObjects.put(beanName, singletonObject);

            // 填充属性
            populateBean(singletonObject);

            // bean创建成功，放入singletonObjects
            this.singletonObjects.put(beanName, singletonObject);

            return singletonObject;
        } catch (Exception ignore) {
        }
        return null;
    }

    private void populateBean(Object object) {
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getAnnotation(CodeBearAutowired.class) != null) {
                Object value = getBean(field.getName());
                try {
                    field.setAccessible(true);
                    field.set(object, value);
                } catch (IllegalAccessException ignored) {
                }
            }
        }
    }

    public static void main(String[] args) {
        Cycle cycle = new Cycle();
        cycle.init();
        UserService userService = (UserService) cycle.getBean("userService");
        OrderService orderService = (OrderService) cycle.getBean("orderService");
        System.out.println(userService.orderService);
        System.out.println(orderService.userService);

    }
}

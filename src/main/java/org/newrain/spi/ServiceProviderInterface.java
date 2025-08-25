package org.newrain.spi;

import java.lang.annotation.*;

/**
 * 标记接口为SPI服务接口
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ServiceProviderInterface {
    // 可选：指定默认实现类的名称
    String value() default "";
}
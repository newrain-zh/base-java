package org.newrain.base.ioc;

/**
 * @author newRain
 * @description bean工厂接口
 */
public interface BeanFactory {
    /**
     * 根据id获取Bean
     * @param id
     * @return
     */
    Object getBean(String id);
}

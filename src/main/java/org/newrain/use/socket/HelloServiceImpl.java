package org.newrain.use.socket;

/**
 * @author newRain
 * @description 远程接口实现
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(String name) {
        return "hello" + name;
    }
}

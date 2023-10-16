package org.newrain.base.question;

import lombok.extern.slf4j.Slf4j;

/**
 * 创建对象的几种方式
 *
 * @author alex
 */
@Slf4j
public class CreateCLassMethod {

    public static void main(String[] args) {
        //1 new关键字
        Example example = new Example();
        //2 class
        Class<Example> clazz = Example.class;
        try {
            Example exampleTest = (Example) clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            log.error("create obj error",e);
        }
    }


}
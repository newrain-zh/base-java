package org.newrain.base.copy;

import org.newrain.base.copy.entity.Address;

/**
 * @author newRain
 * @description copy字符串示例
 *
 * 若变量为 String 字符串，则拷贝其地址引用。
 * 但是在修改时，它会从字符串池中重新生成一个新的字符串，原有对象保持不变。
 */
public class CopyStringExample {

    public static void main(String[] args) {
        Address address = new Address();
        address.setCity("shanghai");

        Address cpAddress = (Address) address.clone();
        cpAddress.setCity("beijing");

        System.out.println(address == cpAddress);
        System.out.println(address.getCity());//shanghai
        System.out.println(cpAddress.getCity());//beijing

    }

}

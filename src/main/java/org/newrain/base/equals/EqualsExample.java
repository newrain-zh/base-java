package org.newrain.base.equals;

import java.util.HashSet;
import java.util.Set;

/**
 * @author newRain
 * @description hashCode 和 equals 方法使用示例
 */
public class EqualsExample {

    public int x;
    public int y;

    public EqualsExample(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EqualsExample other = (EqualsExample) obj;
        if (x != other.x) {
            return false;
        }
        if (y != other.y) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        test1();
        test2();
    }

    /**
     * hashCode、equals方法测试
     */
    public static void test1() {
        Set<EqualsExample> set = new HashSet<>();
        EqualsExample r1 = new EqualsExample(3, 3);
        EqualsExample r2 = new EqualsExample(5, 5);
        EqualsExample r3 = new EqualsExample(3, 3);
        set.add(r1);
        set.add(r2);
        set.add(r3);
        set.add(r1);
        //使用默认的hashCode、equals方法 size为3
        //使用重写的hashCode、equals方法 size为2
        System.out.println("size:" + set.size());
    }

    /**
     * 属性参与计算时，更改属性值引发的问题
     */
    public static void test2() {
        //将对象的属性值参与了hashCode的运算中，在进行删除的时候，就不能对其属性值进行修改
        //否则会造成内存泄漏
        Set<EqualsExample> set = new HashSet<>();
        EqualsExample r1 = new EqualsExample(3, 3);
        EqualsExample r2 = new EqualsExample(5, 5);
        EqualsExample r3 = new EqualsExample(3, 3);
        set.add(r1);
        set.add(r2);
        set.add(r3);
        System.out.println(r3.hashCode());//r3 修改前hashcode
        r3.y = 7;
        System.out.println(r3.hashCode());
        System.out.println("删除前的大小size:" + set.size());
        set.remove(r3);
        System.out.println("删除后的大小size:" + set.size());
    }
}
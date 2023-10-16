package org.newrain.jdk8;

@FunctionalInterface
public interface IConverter<F, T> {

    T covert(F from);

    //只能添加一个抽象方法
    //T reset(F from);
    //default是可以添加的
    default void reset(T from) {

    }


    public static void main(String[] args) {
        //原始写法
        IConverter<String, Integer> converter01 = new IConverter<String, Integer>() {
            @Override
            public Integer covert(String from) {
                return Integer.valueOf(from);
            }
        };
        //简化版本
        IConverter<String, Integer> converter02 = from -> Integer.valueOf(from);
        IConverter<String, Integer> converter03 = (from) -> {
            return Integer.valueOf(from);
        };
        IConverter<String, Integer> converter04 = Integer::valueOf;
        //how to use
        System.out.println(converter04.covert("45"));

    }

}

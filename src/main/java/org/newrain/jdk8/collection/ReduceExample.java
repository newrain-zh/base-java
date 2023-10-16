package org.newrain.jdk8.collection;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * reduce 能够将集合归成一个值
 */
public class ReduceExample {

    public static void main(String[] args) {
        List<String> collect = Stream.of("hello", "world").collect(Collectors.toList());
        Optional<String> reduce = collect.stream().reduce((s1, s2) -> s1 + "#" + s2);
        reduce.ifPresent(System.out::println);
    }
}

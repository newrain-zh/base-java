package org.newrain.base.lambda.base.predicte;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;


/**
 * @author newRain
 * @description Predicate使用示例
 */
public class PredicateExample {
    public static void main(String[] args) {
        //
        List<Integer> list = new ArrayList<>();
        int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        for (int i : numbers) {
            list.add(i);
        }
        Predicate<Integer> p1 = i -> i > 5;
        Predicate<Integer> p2 = i -> i < 20;
        Predicate<Integer> p3 = i -> i % 2 == 0;

        List<Integer> test = list.stream().filter(p1.and(p2).and(p3)).collect(Collectors.toList());
        List<Integer> test1 = list.stream().filter(p1.or(p2).or(p3)).collect(Collectors.toList());
        List<Integer> collect = list.stream().filter(p3.negate()).collect(Collectors.toList());
        System.out.println(test);
        System.out.println(test1);
        System.out.println(collect);
        System.out.println(p3.test(1));
    }
}

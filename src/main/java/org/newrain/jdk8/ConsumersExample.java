package org.newrain.jdk8;

import java.util.function.Consumer;

public class ConsumersExample {
    public static void main(String[] args) {
        Consumer<Person> consumer = new Consumer<Person>() {
            @Override
            public void accept(Person person) {
                System.out.println("hello" + person.getName());
            }
        };
        Consumer<Person> consumer2 = (p) -> System.out.println("hello " + p.getName());
        consumer2.accept(new Person("zhangshang"));
    }
}

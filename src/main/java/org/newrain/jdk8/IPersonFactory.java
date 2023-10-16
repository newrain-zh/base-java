package org.newrain.jdk8;

@FunctionalInterface
public interface IPersonFactory<P extends Person> {

    P create(String name);

    public static void main(String[] args) {
        IPersonFactory<Person> personFactory = Person::new;
        Person zhangsan = personFactory.create("zhangsan");
    }
}

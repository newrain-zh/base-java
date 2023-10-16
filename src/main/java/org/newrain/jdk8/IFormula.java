package org.newrain.jdk8;

public interface IFormula {

    double caculate(int a);

    default double sqrt(int a) {
        return Math.sqrt(a);
    }

    public static void main(String[] args) {
        IFormula iFormula = a -> a * a;

    }
}

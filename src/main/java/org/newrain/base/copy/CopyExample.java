package org.newrain.base.copy;

/**
 * Created by newrain on 2017-3-6.
 */
public class CopyExample implements Cloneable {

    public static void main(String[] args) {
        CopyExample demo = new CopyExample();
        CopyExample demo1 = null;
        try {
            demo1 = (CopyExample) demo.clone();//false
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        System.out.println(demo);
        System.out.println(demo1);
        System.out.println(demo == demo1); //false
        System.out.println(demo.equals(demo1)); //false
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
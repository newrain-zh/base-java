package org.newrain.base.proxy.staticproxy;

/**
 * Created by zzqno on 2017-3-27.
 */
public class HelloImpl implements Hello {
    @Override
    public void say(String name) {
        System.out.println("Hello" + name);
    }
}

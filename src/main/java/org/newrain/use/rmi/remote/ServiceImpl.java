package org.newrain.use.rmi.remote;

/**
 * Created by zzqno on 2016-9-23.
 */
public class ServiceImpl implements Service {

    @Override
    public void service(String name) {
        System.out.println(name);
    }
}

package org.newrain.use.rmi.demo;

import org.newrain.use.rmi.remote.HelloService;
import org.newrain.use.rmi.remote.HelloServiceImpl;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

/**
 * Created by zzqno on 2016-9-23.
 */
public class HelloServer {
    public static void main(String[] args) {
        try {
            HelloService h = new HelloServiceImpl();
            LocateRegistry.createRegistry(12312);
            Naming.bind("rmi://127.0.0.1:12312/FleeceCountQuestion", h);
            System.out.println("HelloServer启动成功");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

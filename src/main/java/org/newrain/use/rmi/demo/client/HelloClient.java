package org.newrain.use.rmi.demo.client;

import org.newrain.use.rmi.remote.HelloService;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * @author newRain
 * @description rmi client
 */
public class HelloClient {
    public static void main(String[] args) {
        try {
            HelloService h = (HelloService) Naming.lookup("rmi://127.0.0.1:12312/FleeceCountQuestion");
            System.out.println(h.sayHello("zx"));
        } catch (MalformedURLException e) {
            System.out.println("url格式异常");
        } catch (RemoteException e) {
            System.out.println("创建对象异常");
        } catch (NotBoundException e) {
            System.out.println("对象未绑定");
        }
    }
}

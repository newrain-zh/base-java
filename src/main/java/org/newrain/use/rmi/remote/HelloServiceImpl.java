package org.newrain.use.rmi.remote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by zzqno on 2016-9-23.
 */

/**
 * UnicastRemoteObject类的构造函数抛出了RemoteException，故其继承类不能使用默认构造函数，继承类的构造函数必须也抛出RemoteException
 * 由于方法参数与返回值最终都将在网络上传输，故必须是可序列化的
 */
public class HelloServiceImpl extends UnicastRemoteObject implements HelloService {

    public HelloServiceImpl() throws RemoteException {
    }

    @Override
    public String sayHello(String name) throws RemoteException {
        return "sayHello" + name;
    }
}

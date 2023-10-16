package org.newrain.use.socket;

/**
 * @author newRain
 * @description RPC服务启动
 */
public class RpcProvider {

    public static void main(String[] args) throws Exception {
        HelloService service = new HelloServiceImpl();
        RpcFramework.export(service, 1234);
    }
}

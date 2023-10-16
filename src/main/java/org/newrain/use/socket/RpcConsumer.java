package org.newrain.use.socket;

/**
 * Created by zzqno on 2017-4-9.
 * 服务消费者
 *
 * @author newRain
 * @description RPC消费者
 */
public class RpcConsumer {

    public static void main(String[] args) throws Exception {
        HelloService service = RpcFramework.refer(HelloService.class, "127.0.0.1", 1234);
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            String hello = service.hello("World" + i);
            System.out.println(hello);
            Thread.sleep(1000);
        }
    }
}

package org.newrain.base.ioc;


import org.junit.Test;
import org.newrain.base.ioc.bean.User;
import org.newrain.base.ioc.bean.UserService;

/**
 * Created by zzqno on 2016-12-26.
 */
public class IocTest {

    @Test
    public void testAdd() throws Exception {
 /*       String path = Class.class.getClass().getResource("/").getPath();
        System.out.println(System.getProperty("user.dir"));*/
        BeanFactory applicationContext = (BeanFactory) new ClassPathXmlApplicationContext(); // 获取上下文
        UserService service = (UserService) applicationContext.getBean("userService"); // Spring装配Bean
        User user = new User();
        user.setUserName("zhangsan");
        user.setUserId(1);
        service.addUser(user); // 将user保存入库
    }
}

package org.newrain.base.copy;

import org.newrain.base.copy.entity.Address;
import org.newrain.base.copy.entity.User;

/**
 * Created by zzqno on 2017-3-6.
 */
public class CopyBySerializableExample {
    public static void main(String[] args) {
        User user = new User();
        user.setName("hello");
        Address address = new Address("shanghai");
        user.setAddress(address);

        User cpUser =  CloneUtils.clone(user);
        cpUser.setAddress(new Address("beijing"));

        System.out.println(cpUser == user);//false
        System.out.println(address == cpUser.getAddress());//false

        System.out.println(user.getAddress().getCity());//shanghai
        System.out.println(cpUser.getAddress().getCity());//beijing

    }
}

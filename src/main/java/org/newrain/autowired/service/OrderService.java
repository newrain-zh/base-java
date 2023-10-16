package org.newrain.autowired.service;

import org.newrain.autowired.CodeBearAutowired;

/**
 * @author newRain
 */
public class OrderService {

    @CodeBearAutowired
    public UserService userService;
}

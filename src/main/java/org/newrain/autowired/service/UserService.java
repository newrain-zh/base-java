package org.newrain.autowired.service;

import org.newrain.autowired.CodeBearAutowired;

/**
 * @author newRain
 */
public class UserService {
    @CodeBearAutowired
    public OrderService orderService;
}

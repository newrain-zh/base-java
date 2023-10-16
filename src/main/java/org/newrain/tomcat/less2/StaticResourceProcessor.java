package org.newrain.tomcat.less2;

import java.io.IOException;

/**
 * @author newRain
 * @description 静态资源处理
 */
public class StaticResourceProcessor {


    public void process(Request request, Response response) {
        try {
            response.sendStaticResource();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

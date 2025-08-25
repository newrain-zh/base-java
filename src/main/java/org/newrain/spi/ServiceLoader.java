package org.newrain.spi;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 自定义SPI服务加载器
 */
@Slf4j
public class ServiceLoader<S> {
    // 服务接口
    private final Class<S>                        service;
    // 类加载器
    private final ClassLoader                     classLoader;
    // 缓存已加载的服务实例
    private final Map<String, S>                  serviceInstances = new ConcurrentHashMap<>();
    // 缓存服务名称与类的映射
    private final Map<String, Class<? extends S>> serviceClasses   = new ConcurrentHashMap<>();

    private ServiceLoader(Class<S> service, ClassLoader classLoader) {
        this.service     = service;
        this.classLoader = classLoader;
        loadServices();
    }

    /**
     * 获取指定服务接口的加载器
     */
    public static <S> ServiceLoader<S> load(Class<S> service) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        return new ServiceLoader<>(service, classLoader);
    }

    /**
     * 加载所有服务实现
     */
    private void loadServices() {
        //
        String configFileName = "META-INF/services/" + service.getName();

        try {
            // 查找所有资源
            Enumeration<URL> urls  = classLoader.getResources(configFileName);
            int              count = 0;
            while (urls.hasMoreElements()) {
                count++;
                URL url = urls.nextElement();
                loadFromUrl(url);
            }
            if (count == 0) {
                log.warn("未能找到配置文件");
            }
        } catch (IOException e) {
            throw new RuntimeException("Error loading SPI configuration for " + service.getName(), e);
        }
    }

    /**
     * 从指定URL加载服务配置
     */
    private void loadFromUrl(URL url) {
        try (InputStream is = url.openStream(); BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

            String line;
            while ((line = reader.readLine()) != null) {
                // 处理注释和空行
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                // 格式：服务名称=实现类全限定名
                String[] parts = line.split("=", 2);
                if (parts.length != 2) {
                    continue;
                }

                String serviceName = parts[0].trim();
                String className   = parts[1].trim();

                try {
                    // 加载服务类
                    Class<?> clazz = classLoader.loadClass(className);
                    if (service.isAssignableFrom(clazz)) {
                        @SuppressWarnings("unchecked") Class<? extends S> serviceClass = (Class<? extends S>) clazz;
                        serviceClasses.put(serviceName, serviceClass);
                    }
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException("Service class not found: " + className, e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading SPI configuration from " + url, e);
        }
    }

    /**
     * 获取所有服务实例
     */
    public List<S> getAllServices() {
        List<S> services = new ArrayList<>();
        for (String serviceName : serviceClasses.keySet()) {
            services.add(getService(serviceName));
        }
        return services;
    }

    /**
     * 根据名称获取服务实例
     */
    public S getService(String serviceName) {
        // 先从缓存获取
        S instance = serviceInstances.get(serviceName);
        if (instance != null) {
            return instance;
        }

        // 加载并实例化服务
        Class<? extends S> serviceClass = serviceClasses.get(serviceName);
        if (serviceClass == null) {
            throw new IllegalArgumentException("No service found with name: " + serviceName);
        }

        try {
            instance = serviceClass.getDeclaredConstructor().newInstance();
            serviceInstances.put(serviceName, instance);
            return instance;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create instance of " + serviceClass.getName(), e);
        }
    }

    /**
     * 获取默认服务实例（基于@ServiceProviderInterface注解的value）
     */
    public S getDefaultService() {
        ServiceProviderInterface annotation = service.getAnnotation(ServiceProviderInterface.class);
        if (annotation == null || annotation.value().isEmpty()) {
            throw new IllegalStateException("No default service specified for " + service.getName());
        }
        return getService(annotation.value());
    }
}
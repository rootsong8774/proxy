package hello.proxy.config.v2_dynamicproxy.handler;

import hello.proxy.app.v1.OrderControllerV1;
import hello.proxy.app.v1.OrderControllerV1Impl;
import hello.proxy.app.v1.OrderRepositoryV2;
import hello.proxy.app.v1.OrderRepositoryV1Impl;
import hello.proxy.app.v1.OrderServiceV1;
import hello.proxy.app.v1.OrderServiceV2;
import hello.proxy.trace.logtrace.LogTrace;
import java.lang.reflect.Proxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DynamicProxyBasicConfig {
    
    @Bean
    public OrderControllerV1 orderControllerV1(LogTrace logTrace) {
        OrderControllerV1 orderController = new OrderControllerV1Impl(
            orderServiceV1(logTrace));
    
        return  (OrderControllerV1) Proxy.newProxyInstance(OrderControllerV1.class.getClassLoader(),
            new Class[]{OrderControllerV1.class},
            new LogTraceBasicHandler(orderController, logTrace));
    }
    
    @Bean
    public OrderServiceV1 orderServiceV1(LogTrace logTrace) {
        OrderServiceV1 orderService = new OrderServiceV2(orderRepositoryV1(logTrace));
    
        return (OrderServiceV1) Proxy.newProxyInstance(
            OrderServiceV1.class.getClassLoader(),
            new Class[]{OrderServiceV1.class}, new LogTraceBasicHandler(orderService, logTrace));
    }
    
    @Bean
    public OrderRepositoryV2 orderRepositoryV1(LogTrace logTrace) {
        OrderRepositoryV2 orderRepository = new OrderRepositoryV1Impl();
    
        return (OrderRepositoryV2) Proxy.newProxyInstance(OrderRepositoryV2.class.getClassLoader(),
            new Class[]{OrderRepositoryV2.class},
            new LogTraceBasicHandler(orderRepository, logTrace));
    }
    
}

package com.example.basic.socket.netty5;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

public class ContextRefreshedListener implements ApplicationListener<ApplicationStartedEvent> {

    @Override
    public void onApplicationEvent(ApplicationStartedEvent applicationStartedEvent) {

        Map<String, Object> map = new HashMap<>();

        Map<String, Object> bizMap = applicationStartedEvent.getApplicationContext().getBeansWithAnnotation(Biz.class);
        for (Map.Entry<String, Object> entry : bizMap.entrySet()) {
            Object object = entry.getValue();
            Class c = object.getClass();
            System.err.println(c + "===>");
            Annotation[] annotations = c.getDeclaredAnnotations();

            for (Annotation annotation : annotations) {
                if (annotation.annotationType().equals(Biz.class)) {
                    Biz biz = (Biz) annotation;
                    map.put(biz.value(), object);
                }
            }
        }


        TcpDispacher tcpDispacher = (TcpDispacher) applicationStartedEvent.getApplicationContext().getBean("tcpDispacher");
        tcpDispacher.setCourses(map);

    }
}
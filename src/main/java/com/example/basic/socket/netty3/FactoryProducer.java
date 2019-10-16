package com.example.basic.socket.netty3;

/**
 * 采用单例模式创建工厂类
 * */
public class FactoryProducer {
    private static volatile MessageFactory factory = null;

    private FactoryProducer(){}

    public static MessageFactory getInstance(){
        synchronized(MessageFactory.class){
            if(factory == null){
                factory = new MessageFactory();
            }
        }
        return factory;
    }
}
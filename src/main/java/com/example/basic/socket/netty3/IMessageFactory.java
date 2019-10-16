package com.example.basic.socket.netty3;
public interface IMessageFactory {
    public abstract IMessage getMessage(String type);
}
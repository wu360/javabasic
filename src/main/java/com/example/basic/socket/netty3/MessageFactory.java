package com.example.basic.socket.netty3;

public class MessageFactory implements IMessageFactory {

    public IMessage getMessage(String type) {
        if (type.equalsIgnoreCase("email")) {
            return new Email();
        } else if (type.equalsIgnoreCase("text")) {
            return (IMessage) new Text();
        }
        return null;
    }

}

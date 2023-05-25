package org.wicketstuff.examples;

import org.apache.wicket.protocol.ws.api.message.IWebSocketPushMessage;

public class CounterMessage implements IWebSocketPushMessage {

    private int counter;

    public int getCounter(){
        return counter;
    }

    public CounterMessage(final int counter){
        this.counter=counter;
    }
}

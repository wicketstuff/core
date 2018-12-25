package org.wicketstuff.examples;

import org.apache.wicket.protocol.ws.api.message.IWebSocketPushMessage;

import java.time.LocalDateTime;

public class CounterMessage implements IWebSocketPushMessage {

    private int counter;

    public int getCounter(){
        return counter;
    }

    public CounterMessage(final int counter){
        this.counter=counter;
    }


}

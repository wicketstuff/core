/**
 * Copyright 2012 Vineet Semwal
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.examples;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.wicket.Application;
import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.ws.WebSocketSettings;
import org.apache.wicket.protocol.ws.api.IWebSocketConnection;
import org.apache.wicket.protocol.ws.api.message.ConnectedMessage;
import org.apache.wicket.protocol.ws.api.registry.IWebSocketConnectionRegistry;

public class WicketApplication extends WebApplication {
    /**
     * @see org.apache.wicket.Application#getHomePage()
     */
    @Override
    public Class<HomePage> getHomePage() {
        return HomePage.class;
    }

    @Override
    public RuntimeConfigurationType getConfigurationType() {
        return RuntimeConfigurationType.DEVELOPMENT;
    }

    /**
     * @see org.apache.wicket.Application#init()
     */
    @Override
    public void init() {
        getCspSettings().blocking().disabled();
        super.init();


        // add your configuration here
        Runnable incrementTask = new IncrementCounterTask();
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(incrementTask, 2, 2, TimeUnit.SECONDS);
        Runnable decrementTask=new DecrementCounterTask();
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(decrementTask,2,2,TimeUnit.SECONDS);
        Runnable rowIncrementTask=new IncrementRowCounterTask();
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(rowIncrementTask,2,2,TimeUnit.SECONDS);
    }

    public static WicketApplication get(){
       return (WicketApplication) Application.get();
    }

    private Set<ConnectedMessage> incrementConnected = new HashSet<>();

    public void addIncrementConnectMessage(final ConnectedMessage connectedMessage) {
        incrementConnected.add(connectedMessage);
    }

    private volatile int incrementCounter = 0;

    private class IncrementCounterTask implements Runnable {

        @Override
        public void run() {
            if(incrementConnected.isEmpty()){
                return;
            }
            incrementCounter++;
            WebSocketSettings socketSettings = WebSocketSettings.Holder.get(WicketApplication.this);
            IWebSocketConnectionRegistry connectionRegistry = socketSettings.getConnectionRegistry();
            for (ConnectedMessage connected : incrementConnected) {
                IWebSocketConnection connection = connectionRegistry.getConnection(
                        WicketApplication.this, connected.getSessionId(), connected.getKey());
                CounterMessage counterMessage = new CounterMessage(incrementCounter);
                try {
                    if(connection!=null) {
                        connection.sendMessage(counterMessage);
                    }
                }catch (Throwable e){
                    e.printStackTrace();
                }
            }
        }
    }


    private Set<ConnectedMessage>decrementConnected=new HashSet<>();

    public void addDecrementConnectMessage(final ConnectedMessage connectedMessage){
        decrementConnected.add(connectedMessage);
    }



    private volatile int decrementCounter = 30;

    private class DecrementCounterTask implements Runnable {

        @Override
        public void run() {
            if(decrementConnected.isEmpty()){
                return;
            }
            if(decrementCounter<0) {
               return;
            }
            decrementCounter--;
            WebSocketSettings socketSettings = WebSocketSettings.Holder.get(WicketApplication.this);
            IWebSocketConnectionRegistry connectionRegistry = socketSettings.getConnectionRegistry();
            for (ConnectedMessage connected : decrementConnected) {
                IWebSocketConnection connection = connectionRegistry.getConnection(
                        WicketApplication.this, connected.getSessionId(), connected.getKey());
                CounterMessage counterMessage = new CounterMessage(decrementCounter);
                try {
                    if(connection!=null) {
                        connection.sendMessage(counterMessage);
                    }
                }catch (Throwable e){
                    e.printStackTrace();
                }
            }
        }
    }


    private Set<ConnectedMessage> incrementGridRowConnected =new HashSet<>();

    public void addGridRowIncrementConnect(final ConnectedMessage connectedMessage){
        incrementGridRowConnected.add(connectedMessage);
    }


    private volatile int incrementRowCounter = -1;

    private class IncrementRowCounterTask implements Runnable {

        @Override
        public void run() {
            if(incrementGridRowConnected.isEmpty()){
                return;
            }
            incrementRowCounter=incrementRowCounter+2;
            WebSocketSettings socketSettings = WebSocketSettings.Holder.get(WicketApplication.this);
            IWebSocketConnectionRegistry connectionRegistry = socketSettings.getConnectionRegistry();
            for (ConnectedMessage connected : incrementGridRowConnected) {
                IWebSocketConnection connection = connectionRegistry.getConnection(
                        WicketApplication.this, connected.getSessionId(), connected.getKey());
                CounterMessage counterMessage = new CounterMessage(incrementRowCounter);
                try {
                    if(connection!=null) {
                        connection.sendMessage(counterMessage);
                    }
                }catch (Throwable e){
                    e.printStackTrace();
                }
            }
        }
    }


}

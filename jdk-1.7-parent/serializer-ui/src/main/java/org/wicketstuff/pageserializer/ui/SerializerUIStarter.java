/*
 * Copyright 2015 mosmann.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.pageserializer.ui;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.concurrent.Callable;

import org.fuin.utils4j.Utils4J;

/**
 *
 * @author mosmann
 */
public abstract class SerializerUIStarter {

    public static void main(final String[] args) {
        UI ui=startUI();
    }

    public static UI startUI() {
    	
        return JavaFXStarter.withJavaFX(new Callable<UI>() {
            
        	@Override
        	public UI call() throws Exception {
                return SerializerUI.start(new String[]{});
        	}
        });
    }
        
}

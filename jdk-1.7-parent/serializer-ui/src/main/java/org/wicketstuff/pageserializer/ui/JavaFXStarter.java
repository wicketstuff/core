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
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.fuin.utils4j.Utils4J;

/**
 *
 * @author mosmann
 */
public class JavaFXStarter {
    
    public static <V> V withJavaFX(Callable<V> runable) {
    	Lock lock=new ReentrantLock();
    	
    	try {
	        String jfxJar=System.getProperty("java.home") + File.separator + "lib" + File.separator + "jfxrt.jar";
	//        System.out.println("Jar: "+jfxJar);
	        
	        if (!new File(jfxJar).exists()) {
	            throw new IllegalArgumentException("File do NOT exist: "+jfxJar);
	        }
	//        final URL jfxurl = new URL("file:///" + jfxJar);
	        
	        Utils4J.addToClasspath("file:///" + jfxJar);
	        return runable.call();
    	} catch (Exception ex) {
    		throw new RuntimeException(ex);
    	}
    }
}

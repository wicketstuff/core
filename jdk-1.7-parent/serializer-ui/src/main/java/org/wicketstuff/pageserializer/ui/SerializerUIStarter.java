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
import org.fuin.utils4j.Utils4J;

/**
 *
 * @author mosmann
 */
public abstract class SerializerUIStarter {

    public static void main(String[] args) throws ClassNotFoundException, MalformedURLException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String jfxJar=System.getProperty("java.home") + File.separator + "lib" + File.separator + "jfxrt.jar";
        System.out.println("Jar: "+jfxJar);
        
        if (!new File(jfxJar).exists()) {
            throw new IllegalArgumentException("File do NOT exist: "+jfxJar);
        }
        final URL jfxurl = new URL("file:///" + jfxJar);
        Utils4J.addToClasspath("file:///" + jfxJar);

//        ClassLoader cl=new URLClassLoader(new URL[]{jfxurl}, SerializerUIStarter.class.getClassLoader());
//        cl=SerializerUIStarter.class.getClassLoader();
//        
//        cl.loadClass(SerializerUIStarter.class.getName());
//        cl.loadClass("javafx.application.Application");
//        final Class<?> ui = cl.loadClass(SerializerUIStarter.class.getPackage().getName()+".SerializerUI");
//        
//        final Method main = ui.getMethod("main", new String[]{}.getClass());
//        System.out.println("Method:"+main);
//        main.invoke(null,new Object[]{new String[]{}});
        
        SerializerUI.main(new String[]{});
//        SerializerUI.main(args);
    }
}

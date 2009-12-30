/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.javaee.injection;

import org.apache.wicket.injection.ConfigurableInjector;
import org.apache.wicket.injection.IFieldValueFactory;
import org.wicketstuff.javaee.naming.IJndiNamingStrategy;
import org.wicketstuff.javaee.naming.StandardJndiNamingStrategy;

/**
 * Injector that injects ejb references based on {@link javax.ejb.EJB}
 * annotation
 *
 * @author Filippo Diotalevi
 */
public class AnnotJavaEEInjector extends ConfigurableInjector {

    IFieldValueFactory factory;

    /**
     * Constructor
     */
    public AnnotJavaEEInjector() {
        initFactory(new StandardJndiNamingStrategy());
    }

    /**
     * Constructor
     * @param namingStrategy - naming strategy
     */
    public AnnotJavaEEInjector(IJndiNamingStrategy namingStrategy) {
        initFactory(namingStrategy);
    }

    private void initFactory(IJndiNamingStrategy namingStrategy) {
        factory = new JavaEEProxyFieldValueFactory(namingStrategy);
    }

    @Override
    protected IFieldValueFactory getFieldValueFactory() {
        return factory;
    }
}

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
package org.wicketstuff.javaee.naming.global;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 * The container has to register one JNDI global entry for every local and remote business interface implemented by the EJB and its no-interface view.
 * <pre>
 *      java:global[/<app-name>]/<module-name>/<bean-name>[!<interface-FQN>]
 * </pre>
 *
 * If an EJB implements only one business interface or only has a no-interface view, the container is also required to register such a view with the following JNDI name:
 * <pre>
 *      java:global[/<app-name>]/<module-name>/<bean-name>
 * </pre>
 */
public class GlobalJndiNamingStrategyTest {

    private GlobalJndiNamingStrategy namingStrategy = new GlobalJndiNamingStrategy("appname", "modulename");

    @Test
    public void nameAccordingToSpecification() {
        assertEquals(namingStrategy.calculateName(null, String.class),
                "java:global/appname/modulename/java.lang.String");
        assertEquals(namingStrategy.calculateName("beanname", String.class),
                "java:global/appname/modulename/beanname!java.lang.String");
    }
}

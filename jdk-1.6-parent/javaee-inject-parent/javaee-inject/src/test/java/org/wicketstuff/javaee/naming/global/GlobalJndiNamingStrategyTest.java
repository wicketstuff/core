package org.wicketstuff.javaee.naming.global;


import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;


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
        assertThat(namingStrategy.calculateName(null, String.class)).isEqualTo("java:global/appname/modulename/java.lang.String");
        assertThat(namingStrategy.calculateName("beanname", String.class)).isEqualTo("java:global/appname/modulename/beanname!java.lang.String");
    }

}
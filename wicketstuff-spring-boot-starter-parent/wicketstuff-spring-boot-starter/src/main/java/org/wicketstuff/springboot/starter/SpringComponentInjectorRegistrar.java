/*
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
package org.wicketstuff.springboot.starter;

import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Registers a {@link SpringComponentInjector} on every {@link WebApplication} bean as soon as the
 * bean is created.
 * <p>
 * Spring Boot initialises the embedded servlet container's filters, and with them the Wicket
 * application, while the application context is still refreshing and before ordinary singletons
 * exist. Registering the injector at bean creation is what makes {@code @SpringBean} (and
 * {@link org.apache.wicket.injection.Injector#get()}) available inside
 * {@link WebApplication#init()}, whichever filter registration ends up initialising the
 * application.
 * </p>
 *
 * @author Daniel Bartl
 */
class SpringComponentInjectorRegistrar implements BeanPostProcessor, ApplicationContextAware {

	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) {
		if (bean instanceof WebApplication webApplication) {
			webApplication.getComponentInstantiationListeners()
					.add(new SpringComponentInjector(webApplication, applicationContext));
		}
		return bean;
	}
}

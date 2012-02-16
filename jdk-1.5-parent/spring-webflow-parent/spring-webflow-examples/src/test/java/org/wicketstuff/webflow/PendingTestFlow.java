/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributed by United Services Automotive Association (USAA)
 */
package org.wicketstuff.webflow;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Test;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.webflow.config.FlowDefinitionResource;
import org.springframework.webflow.config.FlowDefinitionResourceFactory;
import org.springframework.webflow.test.MockExternalContext;
import org.springframework.webflow.test.MockFlowBuilderContext;
import org.springframework.webflow.test.execution.AbstractXmlFlowExecutionTests;

public class PendingTestFlow extends AbstractXmlFlowExecutionTests {

	@Override
	protected FlowDefinitionResource getResource(
			FlowDefinitionResourceFactory resourceFactory) {
		FlowDefinitionResource resource = resourceFactory
				.createResource("foo-flow.xml");
		assertNotNull(resource);

		return resource;
	}

//	BazService mockedBazService;

	@Override
	 protected void registerMockFlowBeans(ConfigurableBeanFactory flowBeanFactory){
//	 mockedBazService=mock(BazService.class);
//	flowBeanFactory.registerSingleton("bazService",mockedBazService);
//	 flowBeanFactory.registerSingleton("MyExceptionHandler", mock(MyExceptionHandler.class));
	 }


MockExternalContext context = new MockExternalContext();
@Test
public void testFlowShouldEnterStartState()
{
 this.startFlow(context);
 assertCurrentStateEquals("myFirstState");

}

//private final String[] configLocations = new String[] {	"./src/main/webapp/WEB-INF/myapp-webflow.xml" };
//
//protected void configureFlowBuilderContext(
//		MockFlowBuilderContext builderContext) {
//
//	for (String name : context.getBeanDefinitionNames()) {
//		if (Arrays.asList(beansToInject).contains(name)) {
//			if (LOGGER.isDebugEnabled()) {
//				LOGGER.debug("Bean being injected = " + name);
//			}
//
//			builderContext.registerBean(name, context.getBean(name));
//		}
//	}
//}




@Test
public void testStateWhenFooBarProcessingEventTriggered()
{

  this.startFlow(context);
  context.setEventId("startProcessingFooBar");
  resumeFlow(context);
  assertCurrentStateEquals("fooBarProcessor");
}

@Test
public void testProcessingDetails()
{
   this.startFlow(context);
//   when(mockedBazService
//    .isDetailsAvailable(anyString())
//    ).thenReturn(true);
   context.setEventId("processDetails");
   resumeFlow(context);
   assertCurrentStateEquals("processDetails");

}


}

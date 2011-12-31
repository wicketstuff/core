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

import org.apache.wicket.util.tester.WicketTester;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class TestAppContext {
	
	ApplicationContext ctx;
	WicketTester tester;
	
	
	@Before
	public void setup(){
		ctx = new FileSystemXmlApplicationContext("/WEB-INF/web-application-config.xml");
		tester = new WicketTester(new SimpleWebFlowApplication(ctx));
		
		
//		
//		ApplicationContext context = mock(ApplicationContext.class);
//		
//		WicketViewFactoryCreator viewFactoryCreator = new WicketViewFactoryCreator();
//		when(context.getBean("viewFactoryCreator")).thenReturn(viewFactoryCreator);
//		
//		PageFlowConversationManager flowConversationManager = new PageFlowConversationManager();
//		when(context.getBean("flowConversationManager")).thenReturn(flowConversationManager);
//		
//		FlowDefinitionRegistryImpl flowRegistry = new FlowDefinitionRegistryImpl(); //Load the flow definition
//		
//		FlowDefinitionResourceFactory resourceFactory = new FlowDefinitionResourceFactory();
//		
//		FlowModelBuilder fb = new XmlFlowModelBuilder(new FileSystemResource(new File("src/main/webapp/WEB-INF/config/ShortSimpleFlow.xml")));
////		FlowAssembler assem = new FlowAssembler(fb, null);
////		
////		FlowDefinitionHolder defHolder = new DefaultFlowHolder();
////		
////		flowRegistry.registerFlowDefinition(definition)
//		when(context.getBean("flowRegistry")).thenReturn(flowRegistry);
//
//		FlowExecutionImplFactory flowExecutionFactory = new FlowExecutionImplFactory();
//		when(context.getBean("flowExecutionFactory")).thenReturn(flowExecutionFactory);
//		
//		SerializedFlowExecutionSnapshotFactory flowSnapshotFactory = new SerializedFlowExecutionSnapshotFactory(flowExecutionFactory,flowRegistry);
//		when(context.getBean("flowSnapshotFactory")).thenReturn(flowSnapshotFactory);
//		
//		DefaultFlowExecutionRepository flowExecutionRepository = new DefaultFlowExecutionRepository(flowConversationManager,flowSnapshotFactory);
//		flowExecutionFactory.setExecutionKeyFactory(flowExecutionRepository);
//		when(context.getBean("flowExecutionRepository")).thenReturn(flowExecutionRepository);
//		
//		FlowExecutorImpl flowExecutor = new FlowExecutorImpl(flowRegistry, flowExecutionFactory, flowExecutionRepository);
//		when(context.getBean("flowExecutor")).thenReturn(flowExecutor);
	}
	
//	@Autowired
//    private ApplicationContext applicationContext;
	
	@Test
	public void appContextShouldBeavailable(){
		System.out.println("Context:"+ctx);
		System.out.println("flowExecutor:"+ctx.getBean("flowExecutor"));
		
		tester.startPage(tester.getApplication().getHomePage());
		tester.assertRenderedPage(Page1.class);
		
	}

}

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

import org.springframework.webflow.config.FlowDefinitionResource;
import org.springframework.webflow.config.FlowDefinitionResourceFactory;
import org.springframework.webflow.context.ExternalContext;
import org.springframework.webflow.test.MockExternalContext;
import org.springframework.webflow.test.MockFlowBuilderContext;
import org.springframework.webflow.test.execution.AbstractXmlFlowExecutionTests;

public class PendingTestFlow2 extends AbstractXmlFlowExecutionTests {
	 
    protected FlowDefinitionResource getResource(FlowDefinitionResourceFactory resourceFactory) {
//            return resourceFactory.createClassPathResource("ShortSimpleFlow.xml", getClass());
            return resourceFactory.createFileResource("src/main/webapp/WEB-INF/config/ShortSimpleFlow.xml");
    }

    public void testStartFlow() {
            ExternalContext context = new MockExternalContext();
            startFlow(context);
            assertCurrentStateEquals("Page1");
    }

//    protected void configureFlowBuilderContext(MockFlowBuilderContext builderContext) {
//            builderContext.registerBean("searchService", new TestSearchService());
//    }

}
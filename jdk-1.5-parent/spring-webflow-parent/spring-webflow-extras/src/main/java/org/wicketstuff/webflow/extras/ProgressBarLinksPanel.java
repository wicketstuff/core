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
package org.wicketstuff.webflow.extras;

import java.util.List;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.webflow.definition.FlowDefinition;
import org.springframework.webflow.definition.StateDefinition;
import org.springframework.webflow.engine.Flow;
import org.springframework.webflow.engine.TransitionableState;
import org.springframework.webflow.executor.FlowExecutorImpl;
import org.wicketstuff.webflow.app.PageFlowWebApplication;
import org.wicketstuff.webflow.components.WebFlowLink;
import org.wicketstuff.webflow.session.PageFlowSession;
import org.wicketstuff.webflow.session.ProgressLink;

/**
 * <p>ProgressBarLinksPanel class.</p>
 *
 * @author Clint Checketts, Florian Braun, Doug Hall
 * @version $Id: $
 */
public class ProgressBarLinksPanel extends Panel {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(ProgressBarLinksPanel.class);

	/**
	 * <p>Constructor for ProgressBarLinksPanel.</p>
	 *
	 * @param id a {@link java.lang.String} object.
	 * @param model a {@link org.apache.wicket.model.IModel} object.
	 */
	public ProgressBarLinksPanel(String id, IModel<? extends List<? extends ProgressLink>> model) {
		super(id, model);

		ListView<ProgressLink> linksRepeater = new ListView<ProgressLink>("linksRepeater",model) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<ProgressLink> item) {
				ProgressLink linkDetails = item.getModelObject();
				IModel<String> stepText = linkDetails.getLabelModel();
				int stepNumber = linkDetails.getFlowStepNumber();
				LOG.debug("Creating link: {} {}",stepText,linkDetails);
				
				item.add(new ProgressBarFlowLink("progressBarLink", stepText, stepNumber));
			}
		};
		add(linksRepeater);
		
	}
	
	private class ProgressBarFlowLink extends WebFlowLink<String>{
		private static final long serialVersionUID = 1L;

		public ProgressBarFlowLink(String id, IModel<String> model, int stepNumber) {
			super(id, model);
			setEventId("progressBarStep"+stepNumber);
		}
		
		@Override
		protected void onComponentTagBody(MarkupStream markupStream,
				ComponentTag openTag) {
			replaceComponentTagBody(markupStream, openTag, getModelObject());
		}
		
		@Override
		public boolean isEnabled() {
			boolean transitionExistsForCurrentView = false;
			
			FlowDefinition flowDef = ((FlowExecutorImpl)PageFlowWebApplication.get().getFlowExecutor()).getDefinitionLocator().getFlowDefinition(PageFlowWebApplication.get().getFlowId());
			
			// Retrieve the flow instance for the input view state
			Flow flow = (Flow)flowDef;
			StateDefinition currState = flow.getState(PageFlowSession.get().getFlowState().getCurrentViewStateId());
			TransitionableState transitionState = null;
			if(currState instanceof TransitionableState){
				transitionState = (TransitionableState)currState;
			} else {
				//End states aren't transitional and so none of the links should be enabled
				return false;
			}
			
			if(transitionState.getTransition(getEventId()) != null || flow.getGlobalTransition(getEventId()) != null){
				transitionExistsForCurrentView = true;
			}
			
			return transitionExistsForCurrentView;
		}
		
		
	}
	

}

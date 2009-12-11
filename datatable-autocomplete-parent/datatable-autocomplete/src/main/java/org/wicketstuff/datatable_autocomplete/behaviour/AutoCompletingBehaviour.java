/*
 * 
 * ==============================================================================
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.wicketstuff.datatable_autocomplete.behaviour;

import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.Request;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.HiddenField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.util.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.datatable_autocomplete.panel.AbstractAutoCompleteDependencyProcessor;
import org.wicketstuff.datatable_autocomplete.panel.AutoCompletingPanel;
import org.wicketstuff.datatable_autocomplete.panel.AJAXAutoCompleteBehaviour;

/**
 * @author mocleiri
 * 
 */
public class AutoCompletingBehaviour extends AJAXAutoCompleteBehaviour {

	/**
	 * 
	 */
	private static final long		serialVersionUID	= 7722548233439995560L;
	private static final Logger		log					= LoggerFactory
																.getLogger(AutoCompletingBehaviour.class);
	private TextField<String>				textModel;
	private final HiddenField<?>	selectedContextField;

	private class AutoCompletingDependencyProcessor extends
			AbstractAutoCompleteDependencyProcessor {

		/**
		 * 
		 */
		private static final long				serialVersionUID	= 5497206492287083574L;
		private final AutoCompletingPanel<?>	autoCompletingPanel;

		private final String					callbackName;

		/**
		 * @param names
		 * @param components
		 * @param duration
		 */
		public AutoCompletingDependencyProcessor(String callbackName,
				TextField<?> textField,
				AutoCompletingPanel<?> autoCompletingPanel, Duration duration) {

			super(new String[] { callbackName }, new Component[] { textField },
					duration);
			this.callbackName = callbackName;
			this.autoCompletingPanel = autoCompletingPanel;
		}

		
		public void onAjaxUpdate(Request request, AjaxRequestTarget target) {

			String value = request.getParameter(callbackName);

			textModel.setModelObject(value);

			// enable since this is an ajax update
			autoCompletingPanel.setInitialRenderDisabledMode(false);

			// will be revaluated during rendering
			autoCompletingPanel.setVisible(true);

			target.addComponent(autoCompletingPanel);

		}

	};

	/**
	 * A special behaviour to generate a GET request for changes to the
	 * textComponent provided.
	 * 
	 * @param selectedContextField
	 *            the field where the id value of the selected row is placed
	 * @param textComponent
	 *            the component whose value is updated on the request (this
	 *            behavoir should be added to textComponent).
	 * @param referenceAutoCompletingPanel
	 *            the completing panel that needs to be updated when the
	 *            textfield value changes.
	 * @param milisecondDurationBetweenRequests
	 *            the number of mmiliseconds that must elapse before a
	 *            subsequent get request will be sent from the client.
	 */
	public AutoCompletingBehaviour(HiddenField<?> selectedContextField,
			TextField<String> textComponent,
			AutoCompletingPanel<?> referenceAutoCompletingPanel,
			long milisecondDurationBetweenRequests) {

		super("onkeyup");
		this.selectedContextField = selectedContextField;

		this.textModel = textComponent;

		super.setDependencyProcessor(new AutoCompletingDependencyProcessor(
				"value", textComponent, referenceAutoCompletingPanel, Duration
						.milliseconds(milisecondDurationBetweenRequests)));

	}

	public AutoCompletingBehaviour(TextField<String> textComponent,
			AutoCompletingPanel<?> referenceAutoCompletingPanel,
			long milisecondDurationBetweenRequests) {

		this(null, textComponent, referenceAutoCompletingPanel,
				milisecondDurationBetweenRequests);

	}

	@Override
	protected void addAdditionalJavaScript(List<String> eventScripts) {

		if (selectedContextField != null) {
			// add in the clear script to be the first part of the element.
			eventScripts.add(0, "Wicket.$("
					+ selectedContextField.getMarkupId() + ").value='CLEAR'");
		}

	}

}

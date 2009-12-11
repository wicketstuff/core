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
 * 
 */
package org.wicketstuff.datatable_autocomplete.button;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxCallDecorator;
import org.apache.wicket.ajax.IAjaxIndicatorAware;
import org.apache.wicket.ajax.calldecorator.AjaxCallDecorator;
import org.apache.wicket.ajax.markup.html.form.AjaxFallbackButton;
import org.apache.wicket.extensions.ajax.markup.html.AjaxIndicatorAppender;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.datatable_autocomplete.form.action.IFormOnSubmitAction;

/**
 * @author mocleiri
 * 
 *         A special form submitting button.
 * 
 *         It will only render if the action is defined.
 * 
 *         It also allows for appending an onclick string. useful for poping an
 *         alert is state is missing on the client side.
 * 
 *         Will by default handle the refresh on the invoking form.
 * 
 */
public class DTAAjaxFallbackButton extends AjaxFallbackButton implements
		DTAButtonProvider, IAjaxIndicatorAware {

	/**
	 * 
	 */
	private static final long					serialVersionUID			= 1829825558587963971L;

	private static final Logger					log							= LoggerFactory
																					.getLogger(DTAAjaxFallbackButton.class);

	private IFormOnSubmitAction				submitAction				= null;

	private IAjaxCallDecorator					ajaxCallDecorator			= null;

	private IModel<String>								preAjaxScriptModel			= null;

	private Form								form						= null;

	private boolean								actionDeterminesVisibility	= true;

	private int									callCounter					= 0;

	private final AjaxIndicatorAppender	indicatorAppender			= new AjaxIndicatorAppender();

	class DTAButtonAjaxCallDecorator extends AjaxCallDecorator {

		/**
		 * 
		 */
		private static final long serialVersionUID = -8601609694500314263L;
		private final IModel<String>	decoratingStringModel;

		/**
		 * @param delegate
		 */
		public DTAButtonAjaxCallDecorator(IModel<String> decoratingStringModel) {

			this.decoratingStringModel = decoratingStringModel;

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.apache.wicket.ajax.calldecorator.AjaxCallDecorator#decorateScript
		 * (java.lang.CharSequence)
		 */
		@Override
		public CharSequence decorateScript(CharSequence script) {

			String preDecorator = decoratingStringModel.getObject();

			if (preDecorator != null)
				return preDecorator + ";" + script;
			else
				return script;
		}

	}

	/**
	 * @param actionDeterminesVisibility
	 *            the actionDeterminesVisibility to set
	 * 
	 *            Normally the existance of an action is used to control the
	 *            visiblilty of the button.
	 * 
	 *            setting this to true will have the default behaviour. Setting
	 *            to false will allow visibilty to be controlled externally.
	 * 
	 */
	public void setActionDeterminesVisibility(boolean actionDeterminesVisibility) {

		this.actionDeterminesVisibility = actionDeterminesVisibility;
	}

	/**
	 * @param id
	 */
	public DTAAjaxFallbackButton(String id, String label, Form form,
			IFormOnSubmitAction submitAction) {

		super(id, new Model(label), form);
		this.form = form;
		this.submitAction = submitAction;

		add(indicatorAppender);

	}

	public DTAAjaxFallbackButton(String id, String label, Form form,
			IModel preAjaxScriptModel) {

		this(id, label, form);
		this.form = form;
		this.preAjaxScriptModel = preAjaxScriptModel;
		add(indicatorAppender);

	}

	public DTAAjaxFallbackButton(String id, IModel labelModel, Form form) {

		super(id, labelModel, form);
		this.form = form;
		add(indicatorAppender);
	}

	public DTAAjaxFallbackButton(String id, String label, Form form) {

		this(id, new Model(label), form);
		add(indicatorAppender);
	}

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.wicket.ajax.markup.html.form.AjaxFallbackButton#getForm()
	 */
	@Override
	public Form getForm() {

		return this.form;

	}

	/**
	 * @param form
	 *            the form to set
	 */
	public void setForm(Form form) {

		this.form = form;
		this.form.setOutputMarkupId(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.wicket.ajax.markup.html.form.AjaxFallbackButton#onSubmit(org
	 * .apache.wicket.ajax.AjaxRequestTarget,
	 * org.apache.wicket.markup.html.form.Form)
	 */
	@Override
	protected final void onSubmit(AjaxRequestTarget target, Form form) {

		callCounter++;

		if (submitAction != null)
			submitAction.onSubmit(target, form);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.apache.wicket.ajax.markup.html.form.AjaxFallbackButton#
	 * getAjaxCallDecorator()
	 */
	@Override
	protected IAjaxCallDecorator getAjaxCallDecorator() {

		if (this.ajaxCallDecorator == null) {
			if (this.preAjaxScriptModel != null) {
				this.ajaxCallDecorator = new DTAButtonAjaxCallDecorator(
						this.preAjaxScriptModel);
			}
		}
		return this.ajaxCallDecorator;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.wicket.Component#onBeforeRender()
	 */
	@Override
	protected void onBeforeRender() {

		if (actionDeterminesVisibility) {
			if (this.submitAction == null)
				setVisible(false);
			else
				setVisible(true);
		}

		this.callCounter = 0;

		super.onBeforeRender();
	}

	/**
	 * @param submitAction
	 *            the submitAction to set
	 */
	public void setSubmitAction(IFormOnSubmitAction submitAction) {

		this.submitAction = submitAction;
	}


	public DTAAjaxFallbackButton getButton() {

		if (!getId().equals(DTAButtonProvider.BUTTON_ID)) {
			// this is a problem
			throw new RuntimeException("ID of button (" + getId()
					+ " != DTAButtonProvider.BUTTON_ID");
		}

		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.wicket.ajax.IAjaxIndicatorAware#getAjaxIndicatorMarkupId()
	 */
	public String getAjaxIndicatorMarkupId() {

		return indicatorAppender.getMarkupId();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.wicket.markup.html.form.FormComponent#onDetach()
	 */
	@Override
	protected void onDetach() {

		super.onDetach();

		if (callCounter > 0)
			log.debug("button called " + callCounter + " times");

	}

	
}

package org.wicketstuff.foundation.revealmodal;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.wicketstuff.foundation.component.FoundationJsPanel;
import org.wicketstuff.foundation.util.Attribute;

/**
 * 
 * Reveal modal component.
 *
 */
public class FoundationRevealModal extends FoundationJsPanel {

	private static final long serialVersionUID = 1L;
	
	private IModel<String> contentModel;

	/**
	 * Constructor with no content model. You should override {@link #createContentPanel(String)}
	 * to create the content you need.
	 * @param id wicket id
	 * @param linkTextModel model for link text
	 */
	public FoundationRevealModal(String id, IModel<String> linkTextModel) {
		this(id, linkTextModel, null);
	}
	
	/**
	 * Constructor with content model.
	 * @param id wicket id
	 * @param linkTextModel model for link text
	 * @param contentModel model for html content
	 */
	public FoundationRevealModal(String id, IModel<String> linkTextModel, IModel<String> contentModel) {
		super(id);
		this.contentModel = contentModel;
		final WebMarkupContainer content = createContentPanel("modalContent");
		content.add(new AttributeModifier("data-reveal", AttributeModifier.VALUELESS_ATTRIBUTE_ADD));
		content.add(new AttributeAppender("class", "reveal-modal", " "));
		add(content);
		
		AjaxLink<Void> openLink = new AjaxLink<Void>("openLink") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onComponentTag(ComponentTag tag) {
				Attribute.addAttribute(tag, "data-reveal-id", content.getMarkupId());
				super.onComponentTag(tag);
			}
			
			@Override
			public void onClick(AjaxRequestTarget arg0) {
			}
		};
		add(openLink);
		openLink.add(new Label("openLinkText", linkTextModel));
	}

	/**
	 * Creates content panel. Override this as necessary.
	 * @param id
	 * @return
	 */
	public WebMarkupContainer createContentPanel(String id) {
		return new RevealModalContent(id, contentModel);
	}

	/**
	 * 
	 * Default content panel.
	 *
	 */
	private static class RevealModalContent extends WebMarkupContainer {

		private static final long serialVersionUID = 1L;

		public RevealModalContent(String id, IModel<String> contentModel) {
			super(id, contentModel);
			this.setEscapeModelStrings(false);
		}
		
		@Override
		public void onComponentTagBody(MarkupStream markupStream,
				ComponentTag openTag) {
			this.replaceComponentTagBody(markupStream, openTag, getDefaultModelObjectAsString());
			super.onComponentTagBody(markupStream, openTag);
		}
	}
}

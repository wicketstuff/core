package org.wicketstuff.foundation.tab;

import java.util.List;
import java.util.Optional;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxFallbackLink;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;

/**
 *
 * Ajaxified version of {@link FoundationTab} component.
 *
 */
public class AjaxFoundationTab<T extends ITab> extends FoundationTab<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor
	 * 
	 * @param id
	 *            component id
	 * @param tabs
	 *            list of ITab objects used to represent tabs
	 * @param model
	 *            model holding the index of the selected tab
	 */
	public AjaxFoundationTab(String id, List<T> tabs, IModel<Integer> model) {
		super(id, tabs, model);
		setOutputMarkupId(true);

		setVersioned(false);
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 *            component id
	 * @param tabs
	 *            list of ITab objects used to represent tabss
	 */
	public AjaxFoundationTab(String id, List<T> tabs) {
		this(id, tabs, null);
	}

	@Override
	protected WebMarkupContainer newLink(final String linkId, final int index) {
		return new IndicatingAjaxFallbackLink<Void>(linkId) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(final Optional<AjaxRequestTarget> targetOptional) {
				setSelectedTab(index);
				targetOptional.ifPresent(target -> target.add(AjaxFoundationTab.this));
				onAjaxUpdate(targetOptional);
			}
		};
	}

	/**
	 * A template method that lets users add additional behavior when ajax
	 * update occurs. This method is called after the current tab has been set
	 * so access to it can be obtained via {@link #getSelectedTab()}.
	 * <p>
	 * <strong>Note</strong> Since an {@link AjaxFallbackLink} is used to back
	 * the ajax update the <code>target</code> argument can be null when the
	 * client browser does not support ajax and the fallback mode is used. See
	 * {@link AjaxFallbackLink} for details.
	 * 
	 * @param targetOptional
	 *            ajax target used to update this component
	 */
	protected void onAjaxUpdate(final Optional<AjaxRequestTarget> targetOptional) {
	}
}

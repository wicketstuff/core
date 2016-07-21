package org.wicketstuff.foundation.progressbar;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.foundation.component.FoundationBasePanel;
import org.wicketstuff.foundation.util.Attribute;
import org.wicketstuff.foundation.util.StringUtil;

/**
 * A simple way to add progress bars to your layouts.
 * http://foundation.zurb.com/docs/components/progress_bars.html
 * @author ilkka
 *
 */
public class FoundationProgressBar extends FoundationBasePanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create FoundationProgressBar.
	 * @param id - Wicket id.
	 * @param percent - Percent value to display.
	 */
	public FoundationProgressBar(String id, int percent) {
		this(id, Model.of(new ProgressBarOptions()), Model.of(percent));
	}
	
	/**
	 * Create FoundationProgressBar.
	 * @param id - Wicket id.
	 * @param options - Options for the progress bar.
	 * @param percent - Percent value to display.
	 */
	public FoundationProgressBar(String id, ProgressBarOptions options, int percent) {
		this(id, Model.of(options), Model.of(percent));
	}

	/**
	 * Create FoundationProgressBar.
	 * @param id - Wicket id.
	 * @param optionsModel - Model for the progress bar options.
	 * @param percentModel - Model for the percent value.
	 */
	public FoundationProgressBar(String id, IModel<ProgressBarOptions> optionsModel, IModel<Integer> percentModel) {
		super(id);
		ProgressContainer progressContainer = new ProgressContainer("progressContainer", optionsModel);
		add(progressContainer);
		Progress progress = new Progress("progress", percentModel);
		progressContainer.add(progress);
	}
	
	private static class ProgressContainer extends WebMarkupContainer {

		private static final long serialVersionUID = 1L;
		
		private IModel<ProgressBarOptions> optionsModel;

		public ProgressContainer(String id, IModel<ProgressBarOptions> optionsModel) {
			super(id);
			this.optionsModel = optionsModel;
		}
		
		@Override
		protected void onComponentTag(ComponentTag tag) {
			Attribute.addClass(tag, "progress");
			ProgressBarOptions options = optionsModel.getObject();
			if (options.getColor() != null) {
				Attribute.addClass(tag, StringUtil.EnumNameToCssClassName(options.getColor().name()));
			}
			if (options.getRadius() != null) {
				Attribute.addClass(tag, StringUtil.EnumNameToCssClassName(options.getRadius().name()));
			}
			super.onComponentTag(tag);
		}
		
		@Override
		protected void onDetach() {
			optionsModel.detach();
			super.onDetach();
		}
	}
	
	private static class Progress extends WebMarkupContainer {

		private static final long serialVersionUID = 1L;
		
		private IModel<Integer> percentModel;

		public Progress(String id, IModel<Integer> percentModel) {
			super(id);
			this.percentModel = percentModel;
		}
		
		@Override
		protected void onComponentTag(ComponentTag tag) {
			Attribute.addClass(tag, "meter");
			Integer percent = percentModel.getObject();
			if (percent == null || percent < 0 || percent > 100) {
				percent = 100;
			}
			Attribute.addAttribute(tag, "style", String.format("width:%d%%", percent));
			super.onComponentTag(tag);
		}
		
		@Override
		protected void onDetach() {
			percentModel.detach();
			super.onDetach();
		}
	}
}

package com.googlecode.wicket.jquery.ui.samples.jqueryui.slider;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.ui.form.slider.AjaxSlider;
import com.googlecode.wicket.jquery.ui.form.slider.Slider.Range;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;

public class ColorPickerPage extends AbstractSliderPage
{
	private static final long serialVersionUID = 1L;

	// Models //
	private final Model<String> model;

	public ColorPickerPage()
	{
		this.model = Model.of("#336699");
		this.initialize();
	}

	private void initialize()
	{
		final Form<Void> form = new Form<Void>("form");
		this.add(form);

		// FeedbackPanel //
		final FeedbackPanel feedback = new JQueryFeedbackPanel("feedback");
		form.add(feedback.setOutputMarkupId(true));

		// Color panel //
		final EmptyPanel colorPanel = new EmptyPanel("color");
		colorPanel.add(this.newBackgroundAttributeModifier());
		form.add(colorPanel.setOutputMarkupId(true));

		// Color Slider(s) //
		form.add(new ColorPicker("picker", this.model) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onColorChanged(IPartialPageRequestHandler handler)
			{
				// change the color of the color-panel //
				colorPanel.add(ColorPickerPage.this.newBackgroundAttributeModifier());
				handler.add(colorPanel);

				ColorPickerPage.this.info(this);
				handler.add(feedback);
			}
		});
	}

	private Behavior newBackgroundAttributeModifier()
	{
		return AttributeModifier.replace("style", "background-color: " + this.model.getObject());
	}

	private void info(Component component)
	{
		this.info(component.getMarkupId() + " has been clicked");
		this.info("The model object is: " + this.model.getObject());
	}

	/**
	 * A FormComponentFragment would have been perfect here, but... it does not exists :s
	 *
	 */
	abstract class ColorPicker extends Fragment
	{
		private static final long serialVersionUID = 1L;
		private static final int INDEX_R = 1; // #RRxxxx
		private static final int INDEX_G = 3; // #xxGGxx
		private static final int INDEX_B = 5; // #xxxxBB

		private final IModel<Integer> modelR;
		private final IModel<Integer> modelG;
		private final IModel<Integer> modelB;

		public ColorPicker(String id, IModel<String> model)
		{
			super(id, "color-picker", ColorPickerPage.this, model);

			this.modelR = this.newColorModel(INDEX_R);
			this.modelG = this.newColorModel(INDEX_G);
			this.modelB = this.newColorModel(INDEX_B);

			this.initialize();
		}

		private void initialize()
		{
			this.add(this.newAjaxSlider("r", this.modelR)); // Slider: Red
			this.add(this.newAjaxSlider("g", this.modelG)); // Slider: Green
			this.add(this.newAjaxSlider("b", this.modelB)); // Slider: Blue
		}

		/**
		 * Updates the model with the new color.
		 * 
		 * @param handler the {@link IPartialPageRequestHandler}
		 */
		private void changeColor(IPartialPageRequestHandler handler)
		{
			Integer r = this.modelR.getObject();
			Integer g = this.modelG.getObject();
			Integer b = this.modelB.getObject();

			this.setDefaultModelObject(String.format("#%02x%02x%02x", r, g, b));
			this.onColorChanged(handler);
		}

		// Events //
		/**
		 * Event which will be fired when the color has been changed.
		 * 
		 * @param handler the {@link IPartialPageRequestHandler}
		 */
		protected abstract void onColorChanged(IPartialPageRequestHandler handler);

		// Factories //
		/**
		 * Gets a new {@link AjaxSlider} for the specified color model
		 * 
		 * @param id the markup id
		 * @param model the (R|G|B) color model
		 * @return the {@link AjaxSlider}
		 */
		private AjaxSlider newAjaxSlider(String id, IModel<Integer> model)
		{
			AjaxSlider slider = new AjaxSlider(id, model) {

				private static final long serialVersionUID = 1L;

				@Override
				public void onValueChanged(IPartialPageRequestHandler handler)
				{
					ColorPicker.this.changeColor(handler);
				}
			};
			
			slider.setMax(255);
			slider.setRange(Range.MIN);

			return slider;
		}

		/**
		 * Gets a new one-color-model based on the rdb-color-model<br>
		 * The code is not defensive (ie: no check on string length)
		 */
		private IModel<Integer> newColorModel(int index)
		{
			String color = this.getDefaultModelObjectAsString().substring(index, index + 2);
			return Model.of(Integer.parseInt(color, 16));
		}
	}
}

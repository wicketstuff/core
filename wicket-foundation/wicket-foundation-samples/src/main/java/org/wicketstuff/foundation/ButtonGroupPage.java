package org.wicketstuff.foundation;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.foundation.button.ButtonColor;
import org.wicketstuff.foundation.button.ButtonOptions;
import org.wicketstuff.foundation.button.ButtonRadius;
import org.wicketstuff.foundation.button.FoundationLink;
import org.wicketstuff.foundation.buttongroup.ButtonGroupOptions;
import org.wicketstuff.foundation.buttongroup.ButtonGroupStacking;
import org.wicketstuff.foundation.buttongroup.FoundationButtonBarBorder;
import org.wicketstuff.foundation.buttongroup.FoundationButtonGroup;

public class ButtonGroupPage extends BasePage {

	private static final long serialVersionUID = 1L;

	public ButtonGroupPage(PageParameters params) {
		super(params);
		createAndAddBasicButtonGroup();
		createAndAddAdvancedButtonGroup();
		createAndAddStackButtonGroup();
		createAndAddButtonBar();
	}
	
	private void createAndAddBasicButtonGroup() {
		List<ButtonOptions> btnOptions = Arrays.asList(
				new ButtonOptions(), new ButtonOptions(), new ButtonOptions());
		add(new FoundationButtonGroup("basic", new ButtonGroupOptions(), btnOptions) {

			private static final long serialVersionUID = 1L;

			@Override
			protected WebMarkupContainer createButton(int idx, String id,
					IModel<ButtonOptions> optionsModel) {
				return new FoundationLink<String>(id, Model.of(String.format("Button %d", idx+1)), optionsModel.getObject()) {

					private static final long serialVersionUID = 1L;

					@Override
					public void onClick() {
						// NOP
					}
				};
			}
		});
	}
	
	private void createAndAddAdvancedButtonGroup() {
		List<ButtonOptions> btnOptions = Arrays.asList(
				new ButtonOptions(ButtonColor.ALERT), 
				new ButtonOptions(ButtonColor.ALERT), 
				new ButtonOptions(ButtonColor.ALERT));
		add(new FoundationButtonGroup("advanced", new ButtonGroupOptions(ButtonRadius.ROUND), btnOptions) {

			private static final long serialVersionUID = 1L;

			@Override
			protected WebMarkupContainer createButton(int idx, String id,
					IModel<ButtonOptions> optionsModel) {
				return new FoundationLink<String>(id, Model.of(String.format("Button %d", idx+1)), optionsModel.getObject()) {

					private static final long serialVersionUID = 1L;

					@Override
					public void onClick() {
						// NOP
					}
				};
			}
		});
	}
	
	private void createAndAddStackButtonGroup() {
		List<ButtonOptions> btnOptions = Arrays.asList(new ButtonOptions(), 
				new ButtonOptions(), new ButtonOptions());
		add(new FoundationButtonGroup("stack", new ButtonGroupOptions(ButtonGroupStacking.STACK), btnOptions) {

			private static final long serialVersionUID = 1L;

			@Override
			protected WebMarkupContainer createButton(int idx, String id,
					IModel<ButtonOptions> optionsModel) {
				return new FoundationLink<String>(id, Model.of(String.format("Button %d", idx+1)), optionsModel.getObject()) {

					private static final long serialVersionUID = 1L;

					@Override
					public void onClick() {
						// NOP
					}
				};
			}
		});
	}
	
	private void createAndAddButtonBar() {
		FoundationButtonBarBorder buttonbar = new FoundationButtonBarBorder("buttonbar");
		add(buttonbar);
		buttonbar.add(createButtonGroup("first"));
		buttonbar.add(createButtonGroup("second"));
	}
	
	private FoundationButtonGroup createButtonGroup(String id) {
		List<ButtonOptions> btnOptions = Arrays.asList(
				new ButtonOptions(), new ButtonOptions(), new ButtonOptions());
		return new FoundationButtonGroup(id, new ButtonGroupOptions(), btnOptions) {

			private static final long serialVersionUID = 1L;

			@Override
			protected WebMarkupContainer createButton(int idx, String id,
					IModel<ButtonOptions> optionsModel) {
				return new FoundationLink<String>(id, Model.of(String.format("Button %d", idx+1)), optionsModel.getObject()) {

					private static final long serialVersionUID = 1L;

					@Override
					public void onClick() {
						// NOP
					}
				};
			}
		};
	}	
}

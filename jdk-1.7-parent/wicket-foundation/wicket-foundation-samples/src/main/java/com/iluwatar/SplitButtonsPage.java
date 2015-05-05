package com.iluwatar;

import java.util.Arrays;

import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.iluwatar.foundation.button.ButtonColor;
import com.iluwatar.foundation.button.ButtonRadius;
import com.iluwatar.foundation.button.ButtonSize;
import com.iluwatar.foundation.splitbutton.FoundationSplitButton;
import com.iluwatar.foundation.splitbutton.SplitButtonOptions;

public class SplitButtonsPage extends BasePage {

	private static final long serialVersionUID = 1L;

	public SplitButtonsPage(PageParameters params) {
		super(params);
		
		add(new FoundationSplitButton("basic", "Split Button", Arrays.asList("This is a link", "This is another", "Yet another")) {

			private static final long serialVersionUID = 1L;

			@Override
			public AbstractLink createButton(String id) {
				return new Link<Void>(id) {

					private static final long serialVersionUID = 1L;

					@Override
					public void onClick() {
					}
				};
			}

			@Override
			public AbstractLink createDropdownLink(String id, int idx) {
				return new Link<Void>(id) {

					private static final long serialVersionUID = 1L;

					@Override
					public void onClick() {
					}
				};
			}
		});
		
		SplitButtonOptions options = new SplitButtonOptions(ButtonSize.SMALL).setColor(ButtonColor.ALERT).setRadius(ButtonRadius.ROUND);
		add(new FoundationSplitButton("advanced", "Split Button", Arrays.asList("This is a link", "This is another", "Yet another"), options) {

			private static final long serialVersionUID = 1L;

			@Override
			public AbstractLink createButton(String id) {
				return new Link<Void>(id) {

					private static final long serialVersionUID = 1L;

					@Override
					public void onClick() {
					}
				};
			}

			@Override
			public AbstractLink createDropdownLink(String id, int idx) {
				return new Link<Void>(id) {

					private static final long serialVersionUID = 1L;

					@Override
					public void onClick() {
					}
				};
			}
		});
	}
}

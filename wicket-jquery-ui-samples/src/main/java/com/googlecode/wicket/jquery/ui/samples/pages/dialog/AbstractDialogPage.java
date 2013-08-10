package com.googlecode.wicket.jquery.ui.samples.pages.dialog;

import java.util.Arrays;
import java.util.List;

import com.googlecode.wicket.jquery.ui.samples.SamplePage;

abstract class AbstractDialogPage extends SamplePage
{
	private static final long serialVersionUID = 1L;

	public AbstractDialogPage()
	{

	}

	@Override
	protected List<DemoLink> getDemoLinks()
	{
		return Arrays.asList(
				new DemoLink(MessageDialogPage.class, "Message Dialogs"),
				new DemoLink(CustomDialogPage.class, "Custom Dialog"),
				new DemoLink(FragmentDialogPage.class, "Fragment Dialog"),

				new DemoLink(InputDialogPage.class, "Input Dialog"),
				new DemoLink(FormDialogPage.class, "Form Dialog (Slider sample)"),
				new DemoLink(UploadDialogPage.class, "Form Dialog (Upload sample)"),
				new DemoLink(UserDialogPage.class, "<b>Demo:</b> User Accounts")
			);
	}
}

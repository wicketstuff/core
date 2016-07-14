package com.googlecode.wicket.jquery.ui.samples.jqueryui.dialog;

import java.util.Arrays;
import java.util.List;

import com.googlecode.wicket.jquery.ui.samples.JQuerySamplePage;

abstract class AbstractDialogPage extends JQuerySamplePage
{
	private static final long serialVersionUID = 1L;

	@Override
	protected List<DemoLink> getDemoLinks()
	{
		return Arrays.asList( // lf
				new DemoLink(MessageDialogPage.class, "Message Dialogs"), // lf
				new DemoLink(CustomDialogPage.class, "Custom Dialog"), // lf
				new DemoLink(FragmentDialogPage.class, "Fragment Dialog"), // lf
				new DemoLink(InputDialogPage.class, "Input Dialog"), // lf
				new DemoLink(FormDialogPage.class, "Form Dialog (Slider sample)"), // lf
				new DemoLink(UploadDialogPage.class, "Form Dialog (Upload sample)"), // lf
				new DemoLink(UserDialogPage.class, "<b>Demo:</b> User Accounts") // lf
		);
	}
}

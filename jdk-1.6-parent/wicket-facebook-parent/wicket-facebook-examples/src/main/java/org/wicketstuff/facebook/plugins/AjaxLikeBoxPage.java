package org.wicketstuff.facebook.plugins;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.wicketstuff.facebook.FacebookSdk;

/**
 * 
 * @author Till Freier
 * 
 */
public class AjaxLikeBoxPage extends WebPage
{
	/**
	 * 
	 */
	public AjaxLikeBoxPage()
	{
		add(new FacebookSdk("fb-root"));

		final Model<String> urlModel = new Model<String>();

		final Form<Void> form = new Form<Void>("form");
		final TextField<String> textField = new TextField<String>("urlInput", urlModel);
		form.add(textField);
		add(form);

		final LikeBox box = new LikeBox("likebox", urlModel);
		box.setOutputMarkupId(true);

		add(box);

		textField.add(new AjaxFormComponentUpdatingBehavior("onkeyup")
		{

			@Override
			protected void onUpdate(final AjaxRequestTarget target)
			{
				target.add(box);
			}
		});
	}

}

package org.wicketstuff.select2;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.form.AbstractChoice;

/**
 * Behavior that adds a select2 to a dropdown choice.
 * 
 * @author Ernesto Reinaldo Barreiro (reiern70@gmail.com)
 */
public class Select2Behavior extends Behavior 
{
	private static final long serialVersionUID = 1L;

	private Settings settings = new Settings();

	public static Select2Behavior forSingleChoice()
	{
		return new Select2Behavior();
	}

	public static Select2Behavior forMultiChoice()
	{
		Select2Behavior select2Behavior = new Select2Behavior();
		select2Behavior.getSettings().setMultiple(true);
		return select2Behavior;
	}

	@Override
	public void renderHead(Component component, IHeaderResponse response)
	{
		new Select2ResourcesBehavior()
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void renderHead(Component component, IHeaderResponse response)
			{
				super.renderHead(component, response);

				// render theme related resources if any
				if(settings.getTheme() != null)
				{
					settings.getTheme().renderHead(component, response);
				}

				// include i18n resource file
				response.render(JavaScriptHeaderItem.forReference(
						new Select2LanguageResourceReference(settings.getLanguage())));
			}
		}.renderHead(component, response);

		response.render(OnDomReadyHeaderItem.forScript(JQuery.execute("$('#%s').select2(%s);",
				component.getMarkupId(), getSettings().toJson())));
	}

	@Override
	public void bind(Component component)
	{
		component.setOutputMarkupId(true);
		if(!(component instanceof AbstractSelect2Choice) && !(component instanceof AbstractChoice))
		{
			throw new IllegalArgumentException("This behavior should only be used on AbstractSelect2Choice or AbstractChoice components");
		}
	}

	public Settings getSettings()
	{
		return settings;
	}

	public void setSettings(Settings settings)
	{
		this.settings = settings;
	}

}

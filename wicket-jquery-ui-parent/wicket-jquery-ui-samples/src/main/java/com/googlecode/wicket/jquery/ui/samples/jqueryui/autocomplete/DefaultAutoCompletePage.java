package com.googlecode.wicket.jquery.ui.samples.jqueryui.autocomplete;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.lang.Generics;

import com.googlecode.wicket.jquery.core.resource.StyleSheetPackageHeaderItem;
import com.googlecode.wicket.jquery.ui.form.autocomplete.AutoCompleteTextField;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;

public class DefaultAutoCompletePage extends AbstractAutoCompletePage // NOSONAR
{
	private static final long serialVersionUID = 1L;
	private static final List<String> CHOICES = Arrays.asList("Acid rock", "Alternative metal", "Alternative rock", "Anarcho punk", "Art punk", "Art rock", "Beat music", "Black metal", "Blues-rock", "Britpop", "Canterbury scene",
			"Chinese rock", "Christian metal", "Crossover Thrash Metal", "Crust punk", "Crustgrind", "Dark cabaret", "Death metal", "Deathcore", "Deathrock", "Desert rock", "Djent", "Doom metal", "Dream pop", "Drone metal", "Dunedin Sound",
			"Electronic rock", "Emo", "Experimental rock", "Folk metal", "Folk rock", "Freakbeat", "Funk metal", "Garage punk", "Garage rock", "Glam metal", "Glam rock", "Goregrind", "Gothic metal", "Gothic rock", "Grindcore",
			"Groove metal", "Grunge", "Hard rock", "Hardcore punk", "Heavy metal", "Indie pop", "Indie rock", "Industrial metal", "Industrial rock", "J-Rock", "Jazz-Rock", "Krautrock", "Math rock", "Mathcore", "Melodic Death Metal",
			"Melodic metalcore", "Metalcore", "Neo-psychedelia", "New Prog", "New Wave", "No Wave", "Noise pop", "Noise rock", "Noisegrind", "Nu metal", "Paisley Underground", "Pop punk", "Pop rock", "Pornogrind", "Post-Britpop",
			"Post-grunge", "Post-hardcore", "Post-metal", "Post-punk", "Post-punk revival", "Post-rock", "Power metal", "Power pop", "Progressive metal", "Progressive rock", "Psychedelic rock", "Psychobilly", "Punk rock", "Raga rock",
			"Rap metal", "Rap rock", "Rapcore", "Riot grrrl", "Rock and roll", "Rock en Español", "Rock in Opposition", "Sadcore", "Screamo", "Shoegazer", "Slowcore", "Sludge metal", "Soft rock", "Southern rock", "Space Rock",
			"Speed metal", "Stoner rock", "Sufi rock", "Surf rock", "Symphonic metal", "Technical Death Metal", "Thrash metal", "Thrashcore", "Twee Pop", "Unblack metal", "World Fusion");

	public DefaultAutoCompletePage()
	{
		// Form //
		final Form<Void> form = new Form<Void>("form");
		this.add(form);

		// FeedbackPanel //
		final FeedbackPanel feedback = new JQueryFeedbackPanel("feedback");
		form.add(feedback.setOutputMarkupId(true));

		// Auto-complete //
		form.add(new AutoCompleteTextField<String>("autocomplete", new Model<String>()) { // NOSONAR

			private static final long serialVersionUID = 1L;

			@Override
			protected List<String> getChoices(String input)
			{
				List<String> choices = Generics.newArrayList();
				String inputLowerCase = input.toLowerCase();

				int count = 0;
				for (String choice : CHOICES)
				{
					if (choice.toLowerCase().startsWith(inputLowerCase))
					{
						choices.add(choice);

						// limits the number of results
						if (++count == 20)
						{
							break;
						}
					}
				}

				return choices;

				//
				// Equivalent to:
				// return ListUtils.startsWith(input, CHOICES);
				//
			}

			@Override
			protected void onSelected(AjaxRequestTarget target)
			{
				info("Your favorite rock genre is: " + this.getModelObject());
				target.add(feedback);
			}
		});
	}

	// Methods //

	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);

		response.render(new StyleSheetPackageHeaderItem(DefaultAutoCompletePage.class));
	}
}

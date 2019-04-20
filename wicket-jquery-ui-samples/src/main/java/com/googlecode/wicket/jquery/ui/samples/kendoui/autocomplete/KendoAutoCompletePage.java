package com.googlecode.wicket.jquery.ui.samples.kendoui.autocomplete;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.core.utils.ListUtils;
import com.googlecode.wicket.kendo.ui.form.autocomplete.AutoCompleteTextField;
import com.googlecode.wicket.kendo.ui.form.button.AjaxButton;
import com.googlecode.wicket.kendo.ui.form.button.Button;
import com.googlecode.wicket.kendo.ui.panel.KendoFeedbackPanel;

public class KendoAutoCompletePage extends AbstractAutoCompletePage
{
	private static final long serialVersionUID = 1L;
	private static final List<String> CHOICES = Arrays.asList("Acid rock", "Alternative metal", "Alternative rock", "Anarcho punk", "Art punk", "Art rock", "Beat music", "Black metal", "Blues-rock", "Britpop", "Canterbury scene",
			"Chinese rock", "Christian metal", "Crossover Thrash Metal", "Crust punk", "Crustgrind", "Dark cabaret", "Death metal", "Deathcore", "Deathrock", "Desert rock", "Djent", "Doom metal", "Dream pop", "Drone metal",
			"Dunedin Sound", "Electronic rock", "Emo", "Experimental rock", "Folk metal", "Folk rock", "Freakbeat", "Funk metal", "Garage punk", "Garage rock", "Glam metal", "Glam rock", "Goregrind", "Gothic metal", "Gothic rock",
			"Grindcore", "Groove metal", "Grunge", "Hard rock", "Hardcore punk", "Heavy metal", "Indie pop", "Indie rock", "Industrial metal", "Industrial rock", "J-Rock", "Jazz-Rock", "Krautrock", "Math rock", "Mathcore",
			"Melodic Death Metal", "Melodic metalcore", "Metalcore", "Neo-psychedelia", "New Prog", "New Wave", "No Wave", "Noise pop", "Noise rock", "Noisegrind", "Nu metal", "Paisley Underground", "Pop punk", "Pop rock", "Pornogrind",
			"Post-Britpop", "Post-grunge", "Post-hardcore", "Post-metal", "Post-punk", "Post-punk revival", "Post-rock", "Power metal", "Power pop", "Progressive metal", "Progressive rock", "Psychedelic rock", "Psychobilly", "Punk rock",
			"Raga rock", "Rap metal", "Rap rock", "Rapcore", "Riot grrrl", "Rock and roll", "Rock en Español", "Rock in Opposition", "Sadcore", "Screamo", "Shoegazer", "Slowcore", "Sludge metal", "Soft rock", "Southern rock", "Space Rock",
			"Speed metal", "Stoner rock", "Sufi rock", "Surf rock", "Symphonic metal", "Technical Death Metal", "Thrash metal", "Thrashcore", "Twee Pop", "Unblack metal", "World Fusion");

	public KendoAutoCompletePage()
	{
		// Form //
		final Form<Void> form = new Form<>("form");
		this.add(form);

		// FeedbackPanel //
		final KendoFeedbackPanel feedback = new KendoFeedbackPanel("feedback");
		form.add(feedback);

		// Auto-complete //
		final AutoCompleteTextField<String> autocomplete = new AutoCompleteTextField<>("autocomplete", Model.of("Heavy metal")) {

			private static final long serialVersionUID = 1L;

			@Override
			protected List<String> getChoices(String input)
			{
				return ListUtils.startsWith(input, CHOICES);
			}
		};

		form.add(autocomplete);

		// Buttons //
		form.add(new Button("submit") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit()
			{
				KendoAutoCompletePage.this.info(autocomplete);
			}
		});

		form.add(new AjaxButton("button") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target)
			{
				KendoAutoCompletePage.this.info(autocomplete);
				target.add(feedback);
			}
		});
	}

	private void info(AutoCompleteTextField<?> autocomplete)
	{
		Object choice =  autocomplete.getModelObject();

		this.info(choice != null ? choice.toString() : "no choice");
	}
}

package org.wicketstuff.closurecompiler.testapp;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.closurecompiler.ClosureCompilationException;
import org.wicketstuff.closurecompiler.ClosureCompilerJavaScriptCompressor;

import com.google.javascript.jscomp.CompilationLevel;
import com.google.javascript.jscomp.JSError;

public class HomePage extends WebPage
{
	private static final long serialVersionUID = -6623549607771622662L;
	private static final Logger log = LoggerFactory.getLogger(HomePage.class);

	private String source;
	private String result;
	private CompilationLevel level;

	public HomePage()
	{
		add(form("form"));
		add(new FeedbackPanel("status"));

		level = CompilationLevel.ADVANCED_OPTIMIZATIONS;
	}

	private Form<Void> form(String id)
	{
		Form<Void> form = new Form<Void>(id)
		{
			private static final long serialVersionUID = -3175260671078394969L;

			@Override
			protected void onSubmit()
			{
				try
				{
					result = compress(source);
				}
				catch (ClosureCompilationException e)
				{
					for (JSError message : e.getErrors())
					{
						error(message.toString());
					}
				}
				catch (Exception e)
				{
					error(e.getMessage());
					log.error(e.getMessage(), e);
				}
			}
		};

		// source text area
		TextArea<String> sourceText = new TextArea<String>("source", new PropertyModel<String>(
			this, "source"));
		sourceText.setOutputMarkupId(true);
		sourceText.setRequired(true);
		form.add(sourceText);

		// compression result text area
		TextArea<String> resultText = new TextArea<String>("result", new PropertyModel<String>(
			this, "result"));
		resultText.setOutputMarkupId(true);
		form.add(resultText);

		// compression level
		form.add(compressionLevel("level"));

		return form;
	}

	private DropDownChoice<CompilationLevel> compressionLevel(String id)
	{
		final IModel<CompilationLevel> model = new PropertyModel<CompilationLevel>(this, "level");

		final List<CompilationLevel> choices = Arrays.asList(CompilationLevel.values());

		final ChoiceRenderer<CompilationLevel> renderer = new ChoiceRenderer<CompilationLevel>()
		{
			private static final long serialVersionUID = 1958044399469404529L;

			public Object getDisplayValue(CompilationLevel object)
			{
				return object.name();
			}

			public String getIdValue(CompilationLevel object, int index)
			{
				return String.valueOf(object.ordinal());
			}
		};
		DropDownChoice<CompilationLevel> choice = new DropDownChoice<CompilationLevel>(id, model,
			choices, renderer);
		choice.setRequired(true);
		return choice;
	}

	private String compress(String source) throws Exception
	{
		ClosureCompilerJavaScriptCompressor compressor = new ClosureCompilerJavaScriptCompressor();
		compressor.setLevel(level);

		final String compressed = compressor.compressSource(source);
		final float ratio = (float)compressed.length() / (float)source.length() * 100.0f;

		success(String.format("original=%d bytes, compressed=%d bytes (%.2f%%)", source.length(),
			compressed.length(), ratio));

		return compressed;
	}
}

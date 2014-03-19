package org.wicketstuff.tagit;

import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;

/**
 * A text field that acts as autocomplete field and collects the selected choices in the input
 * field.
 * 
 * @param <T>
 *            the type of the possible input
 * 
 * @see <a href="https://github.com/aehlke/tag-it">Tag It</a>
 */
public abstract class TagItTextField<T> extends TextField<T>
{

	private static final long serialVersionUID = 1L;

	/**
	 * @param id
	 *            the component id
	 */
	public TagItTextField(final String id)
	{
		this(id, null, null);
	}

	/**
	 * @param id
	 *            the component id
	 * @param type
	 *            Type for field validation
	 */
	public TagItTextField(final String id, final Class<T> type)
	{
		this(id, null, type);
	}

	/**
	 * @param id
	 *            the component id
	 * @param model
	 *            the component default model
	 * @see org.apache.wicket.Component#Component(String, IModel)
	 */
	public TagItTextField(final String id, final IModel<T> model)
	{
		this(id, model, null);
	}

	/**
	 * @param id
	 *            the component id
	 * @param model
	 *            the component default model
	 * @param type
	 *            The type to use when updating the model for this text field
	 * @see org.apache.wicket.Component#Component(String, IModel)
	 */
	public TagItTextField(final String id, final IModel<T> model, final Class<T> type)
	{
		super(id, model, type);

		add(newTagItAjaxBehavior());
	}

	/**
	 * Creates the {@link TagItAjaxBehavior} that listens for the user's input.
	 * <p>
	 * Override this method you need to tweak the TagIt configuration.
	 * </p>
	 * 
	 * @return the ajax behavior that listens for the user's input.
	 */
	protected TagItAjaxBehavior<T> newTagItAjaxBehavior()
	{
		return new TagItAjaxBehavior<T>()
		{

			private static final long serialVersionUID = 1L;

			@Override
			protected Iterable<T> getChoices(final String value)
			{
				return TagItTextField.this.getChoices(value);
			}
		};
	}

	/**
	 * Finds the possible choices for the provided input
	 * 
	 * @param input
	 *            the term provided by the user.
	 * @return a collection of all possible choices for this input
	 */
	protected abstract Iterable<T> getChoices(final String input);
}

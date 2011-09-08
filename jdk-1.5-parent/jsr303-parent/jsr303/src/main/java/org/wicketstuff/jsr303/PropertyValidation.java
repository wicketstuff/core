package org.wicketstuff.jsr303;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.AbstractPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;

/**
 * Behavior to be added to either a FormComponent or Form. If used on a FormComponent, it has to be
 * bound to an AbstractPropertyModel. When used on a Form, this behavior will be added to all
 * appropriate FormComponents.
 */
public class PropertyValidation extends Behavior
{

	private static final long serialVersionUID = 1L;

	static class JSR303ValidatorFormComponentVisitor implements IVisitor<Component, Void>
	{


		public void component(Component component, IVisit<Void> visit)
		{

			if (component instanceof FormComponent<?>)
			{
				final FormComponent<?> fc = (FormComponent<?>)component;
				addValidator(fc, true);
			}
		}
	}

	private boolean assigned = false;

	@Override
	public void onConfigure(Component context)
	{
		if (!assigned)
		{
			assigned = true;
			if (context instanceof Form<?>)
			{
				final Form<?> form = (Form<?>)context;
				form.visitChildren(new JSR303ValidatorFormComponentVisitor());
			}
			else
			{
				if (context instanceof FormComponent<?>)
				{
					final FormComponent<?> fc = (FormComponent<?>)context;
					addValidator(fc, false);
				}
				else
				{
					throw new IllegalStateException(
						"Can only be applied to Forms or FormComponents");
				}
			}
		}
		super.onConfigure(context);
	}

	private static <T> void addValidator(FormComponent<T> fc, boolean ignoreIncompatibleModel)
	{
		final IModel<T> model = fc.getModel();
		if (model != null)
		{
			if (model instanceof AbstractPropertyModel<?>)
			{
				final AbstractPropertyModel<T> pm = (AbstractPropertyModel<T>)model;
				PropertyValidator<T> validator = new PropertyValidator<T>(pm, fc);
				fc.add(validator);
			}
			else if (!ignoreIncompatibleModel)
			{
				throw new IllegalArgumentException(
					"Expected something that provides an AbstractPropertyModel");
			}
		}
	}
}
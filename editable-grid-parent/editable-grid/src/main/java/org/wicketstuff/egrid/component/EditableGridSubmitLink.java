package org.wicketstuff.egrid.component;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;
/**
 * 
 * @author Nadeem Mohammad
 *
 */
public abstract class EditableGridSubmitLink extends AjaxSubmitLink
{

	private static final long serialVersionUID = 1L;
	private final WebMarkupContainer encapsulatingContainer;

	protected abstract void onSuccess(AjaxRequestTarget target);
	protected abstract void onError(AjaxRequestTarget target);

	public EditableGridSubmitLink(final String id, final WebMarkupContainer newEncapsulatingComponent)
	{
		super(id);
		this.encapsulatingContainer = newEncapsulatingComponent;
	}

	@Override
	protected final void onSubmit(AjaxRequestTarget target)
	{

		if (isFormComponentsValid())
		{
			updateFormComponentsModel();			
			EditableGridSubmitLink.this.onSuccess(target);
		} 
		else
		{
			EditableGridSubmitLink.this.onError(target);
		}
	}

	private boolean isFormComponentsValid()
	{
		final Boolean[] error = {false};
		this.encapsulatingContainer.visitChildren(FormComponent.class, new IVisitor<FormComponent<?>, Void>()
		{
			@Override
			public void component(FormComponent<?> formComponent, IVisit<Void> visit)
			{

				if (formComponentActive(formComponent))
				{
					formComponent.validate();
					if (formComponent.isValid())
					{
						if (!formComponent.processChildren())
						{
							visit.dontGoDeeper();
						}
					} 
					else {
						error[0] = true;
						visit.dontGoDeeper();
					}
				}
				visit.dontGoDeeper();
				
			}
		});
		return !error[0];
	}

	private void updateFormComponentsModel()
	{
		this.encapsulatingContainer.visitChildren(FormComponent.class, new IVisitor<FormComponent<?>, Void>()
		{				

			@Override
			public void component(FormComponent<?> formComponent, IVisit<Void> visit)
			{
				if (formComponentActive(formComponent))
				{

					formComponent.updateModel();

					if (!formComponent.processChildren())
					{
						visit.dontGoDeeper();
					}
				}
				visit.dontGoDeeper();
			}
		});
	}

	private boolean formComponentActive(FormComponent<?> formComponent)
	{
		return formComponent.isVisibleInHierarchy()
				&& formComponent.isValid()
				&& formComponent.isEnabled()
				&& formComponent.isEnableAllowed();
	}
}

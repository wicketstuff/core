package com.inmethod.grid.column.editable;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;

import com.inmethod.grid.common.AbstractGrid;
import com.inmethod.icon.Icon;
import com.inmethod.icon.IconImage;

abstract class SubmitCancelPanel<M, I> extends Panel
{

	private static final long serialVersionUID = 1L;

	protected AbstractGrid<M, I, ?> getGrid()
	{
		return grid;
	}

	private final AbstractGrid<M, I, ?> grid;

	SubmitCancelPanel(String id, final IModel<I> model, AbstractGrid<M, I, ?> grid)
	{
		super(id);

		this.grid = grid;

		AjaxSubmitLink submit = new SubmitLink("submit")
		{
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isVisible()
			{
				return getGrid().isItemEdited(model);
			}
		};

		submit.setDefaultFormProcessing(false);
		add(submit);

		submit.add(new IconImage("icon", getSubmitIcon()));

		AjaxLink<Void> cancel = new AjaxLink<Void>("cancel")
		{

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target)
			{
				onCancel(target);
			}

			@Override
			public boolean isVisible()
			{
				return getGrid().isItemEdited(model);
			}
		};

		add(cancel);

		cancel.add(new IconImage("icon", getCancelIcon()));

	}

	protected abstract void onSubmitted(AjaxRequestTarget target);

	protected abstract void onError(AjaxRequestTarget target);

	protected abstract void onCancel(AjaxRequestTarget target);

	protected abstract Icon getSubmitIcon();

	protected abstract Icon getCancelIcon();

	private class SubmitLink extends AjaxSubmitLink
	{

		public SubmitLink(String id)
		{
			super(id, getGrid().getForm());
		}

		private static final long serialVersionUID = 1L;

		private boolean formComponentActive(FormComponent<?> formComponent)
		{
			return formComponent.isVisibleInHierarchy() && formComponent.isValid() &&
				formComponent.isEnabled() && formComponent.isEnableAllowed();
		}

		@Override
		protected void onSubmit(AjaxRequestTarget target)
		{
			WebMarkupContainer gridRow = getGrid().findParentRow(SubmitCancelPanel.this);
			final Boolean[] error = { false };

			// first iteration - validate components
			gridRow.visitChildren(FormComponent.class, new IVisitor<FormComponent<?>, Void>()
			{
				public void component(FormComponent<?> formComponent, IVisit<Void> visit)
				{

					if (formComponentActive(formComponent))
					{
						formComponent.validate();
						if (formComponent.isValid())
						{
							if (formComponent.processChildren())
							{
								return;
							}
							else
							{
								visit.dontGoDeeper();
								return;
							}
						}
						else
						{
							error[0] = true;
							visit.dontGoDeeper();
						}
					}
				}
			});

			// second iteration - update models if the validation passed
			if (error[0] == false)
			{
				gridRow.visitChildren(FormComponent.class, new IVisitor<FormComponent<?>, Void>()
				{
					public void component(FormComponent<?> formComponent, IVisit<Void> visit)
					{

						if (formComponentActive(formComponent))
						{

							formComponent.updateModel();

							if (formComponent.processChildren())
							{
								return;
							}
							else
							{
								visit.dontGoDeeper();
								return;
							}
						}
					}
				});

				onSubmitted(target);
			}
			else
			{
				SubmitCancelPanel.this.onError(target);
			}
		}
	};


}

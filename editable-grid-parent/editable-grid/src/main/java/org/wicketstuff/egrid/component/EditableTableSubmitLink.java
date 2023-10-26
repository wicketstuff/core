package org.wicketstuff.egrid.component;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.util.visit.IVisitor;

import java.io.Serial;

/**
 * AjaxSubmitLink that validates all components of the {@link #encapsulatingContainer} and updates the models on success.
 *
 * @author Nadeem Mohammad
 * @see org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink
 */
public abstract class EditableTableSubmitLink extends AjaxSubmitLink {
    @Serial
    private static final long serialVersionUID = 1L;

    private final WebMarkupContainer encapsulatingContainer;

    /**
     * Constructor
     *
     * @param id                     the component id
     * @param encapsulatingComponent a WebMarkupContainer that contains different {@link org.apache.wicket.markup.html.form.FormComponent FormComponents}
     */
    public EditableTableSubmitLink(final String id, final WebMarkupContainer encapsulatingComponent) {
        super(id);
        this.encapsulatingContainer = encapsulatingComponent;
    }

    /**
     * This method will validate all FormComponents on click of the link and update the models on success.
     *
     * @param target the {@link org.apache.wicket.ajax.AjaxRequestTarget}
     */
    @Override
    protected void onSubmit(final AjaxRequestTarget target) {
        if (areAllFormComponentsValid()) {
            try {
                updateFormComponentModels();
                EditableTableSubmitLink.this.onSuccess(target);
            } catch (RuntimeException e) {
                error(getString("editableTableSubmitLink.updateModelsError"));
                EditableTableSubmitLink.this.onError(target);
            }
        } else {
            EditableTableSubmitLink.this.onError(target);
        }
    }

    /**
     * Manual validation of all FormComponents instead of the form to validate only a subset of the form.
     * The subset is most likely a row of an {@link org.wicketstuff.egrid.component.EditableDataTable}.
     *
     * @return whether all components are valid or a problem exists
     */
    protected boolean areAllFormComponentsValid() {
        final boolean[] error = {false};
        encapsulatingContainer.visitChildren(FormComponent.class, (IVisitor<FormComponent<?>, Void>) (formComponent, visit) -> {
            if (!error[0] && isFormComponentActive(formComponent)) {
                formComponent.validate();
                if (formComponent.isValid()) {
                    if (!formComponent.processChildren()) {
                        visit.dontGoDeeper();
                    }
                } else {
                    error[0] = true;
                    visit.dontGoDeeper();
                }
            }
            visit.dontGoDeeper();

        });
        return !error[0];
    }

    /**
     * As a result of the manual validation, a manual update of the model of each FormComponent is also required.
     */
    protected void updateFormComponentModels() {
        encapsulatingContainer.visitChildren(FormComponent.class, (IVisitor<FormComponent<?>, Void>) (formComponent, visit) -> {
            if (isFormComponentActive(formComponent)) {
                formComponent.updateModel();
                if (!formComponent.processChildren()) {
                    visit.dontGoDeeper();
                }
            }
            visit.dontGoDeeper();
        });
    }

    /**
     * @param formComponent a FormComponent
     * @return whether the formComponent is visible and enabled
     */
    protected boolean isFormComponentActive(final FormComponent<?> formComponent) {
        return formComponent.isVisibleInHierarchy() && formComponent.isEnabledInHierarchy();
    }

    /**
     * Listener method invoked if the submission was successful.
     *
     * @param target
     */
    protected abstract void onSuccess(AjaxRequestTarget target);

    /**
     * Listener method invoked if there was an error during validation.
     *
     * @param target
     */
    protected abstract void onError(AjaxRequestTarget target);

    /**
     * @return the encapsulatingContainer
     */
    protected WebMarkupContainer getEncapsulatingContainer() {
        return encapsulatingContainer;
    }
}

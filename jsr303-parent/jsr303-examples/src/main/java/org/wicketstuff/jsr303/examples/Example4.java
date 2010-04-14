/**
 * 
 */
package org.wicketstuff.jsr303.examples;

import java.io.Serializable;

import javax.validation.Valid;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.wicketstuff.jsr303.PropertyValidation;

public class Example4 extends WebPage
{
    static class Data implements Serializable
    {
        @Valid
        ValidatedBean validatableBean = new ValidatedBean();

        NonValidatedBean nonValidatableBean = new NonValidatedBean();
    }
    static class ValidatedBean implements Serializable
    {
        @Email
        @NotBlank
        String email;
    }
    static class NonValidatedBean implements Serializable
    {
        @Email
        @NotBlank
        String email;
    }

    private final Data dummy = new Data();

    public Example4()
    {
        final Form form = new Form("form");
        add(form);
        add(new FeedbackPanel("fb"));
        add(new WebMarkupContainer("message")
        {
            @Override
            public boolean isVisible()
            {
                return form.isSubmitted() && (!form.hasError());
            }
        });
        form.add(new PropertyValidation());
        form.add(new TextField("email1", new PropertyModel<String>(this.dummy, "validatableBean.email")));
        form.add(new TextField("email2", new PropertyModel<String>(this.dummy, "nonValidatableBean.email")));
    }
}

/**
 * 
 */
package org.wicketstuff.jsr303.examples;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.wicketstuff.jsr303.PropertyValidation;

public class Example2 extends WebPage
{
    static class Data implements Serializable
    {
        @Email
        @NotNull
        @NotBlank
        String email = "";
    }

    private final Data dummy = new Data();

    public Example2()
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
        form.add(new TextField("email", new PropertyModel<String>(this.dummy, "email")));
    }
}

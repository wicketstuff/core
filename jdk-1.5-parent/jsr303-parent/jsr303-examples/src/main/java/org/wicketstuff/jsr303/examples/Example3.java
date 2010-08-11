/**
 * 
 */
package org.wicketstuff.jsr303.examples;

import java.io.Serializable;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Range;
import org.wicketstuff.jsr303.PropertyValidation;

public class Example3 extends WebPage
{
    static class Data implements Serializable
    {
        @Email
        String email;

        @Range(min = 18, max = 99)
        int age;
    }

    private final Data dummy = new Data();

    public Example3()
    {
        final Form form = new Form("form", new CompoundPropertyModel(this.dummy));
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
        form.add(new TextField("email"));
        form.add(new TextField("age"));
    }
}

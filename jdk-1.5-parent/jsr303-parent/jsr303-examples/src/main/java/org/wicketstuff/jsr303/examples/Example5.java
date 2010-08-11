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
import org.wicketstuff.jsr303.BeanValidator;

public class Example5 extends WebPage
{
    @FooConstraint
    static class Data implements Serializable, FieldBundle
    {
        public String getField1()
        {
            return this.field1;
        }

        public String getField2()
        {
            return this.field2;
        }

        String field1;
        String field2;
    }

    private final Data dummy = new Data();

    public Example5()
    {
        final Form form = new Form("form", new CompoundPropertyModel(this.dummy))
        {
            @Override
            protected void onSubmit()
            {
                super.onSubmit();

                if (!new BeanValidator(this).isValid(Example5.this.dummy))
                {
                    // execute...
                }
                else
                {
                    // stay here...
                }
            }
        };

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

        form.add(new TextField("field1"));
        form.add(new TextField("field2"));
    }
}

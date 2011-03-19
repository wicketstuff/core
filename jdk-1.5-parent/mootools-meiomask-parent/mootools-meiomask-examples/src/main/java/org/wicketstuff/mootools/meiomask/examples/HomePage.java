package org.wicketstuff.mootools.meiomask.examples;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.mootools.meiomask.MaskType;
import org.wicketstuff.mootools.meiomask.MeioMaskField;


public class HomePage extends WebPage {

    private static final long serialVersionUID = 1L;
    private TestModel testModel = new TestModel();

    public HomePage(final PageParameters parameters) {

        FeedbackPanel feedbackPanel = new FeedbackPanel("feedBack");
        add(feedbackPanel);

        Form<TestModel> form = new Form<TestModel>("form", new CompoundPropertyModel<TestModel>(testModel)) {

            @Override
            protected void onSubmit() {
                info("fixed-phone: " + getModelObject().getFixedPhone());
                info("fixed-phone-us: " + getModelObject().getFixedPhoneUs());

            }
        };

        add(form);

        form.add(new MeioMaskField<Long>("fixedPhoneUs", MaskType.FixedPhoneUs));
        form.add(new MeioMaskField<String>("fixedPhone", MaskType.FixedPhone));

    }
}

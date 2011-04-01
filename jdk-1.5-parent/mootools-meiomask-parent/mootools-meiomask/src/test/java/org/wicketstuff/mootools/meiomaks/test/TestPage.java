/*
 * Copyright 2011 henrique.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.mootools.meiomaks.test;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.junit.Ignore;
import org.wicketstuff.mootools.meiomask.MaskType;
import org.wicketstuff.mootools.meiomask.MeioMaskField;

/**
 *
 * @author henrique
 */
@Ignore
public class TestPage extends WebPage {

    private static final long serialVersionUID = 1L;
    private TestModel testModel = new TestModel();

    public TestPage() {

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

        form.add(new MeioMaskField<String>("fixedPhone", MaskType.FixedPhone));
        form.add(new MeioMaskField<Long>("fixedPhoneUs", MaskType.FixedPhoneUs));

    }

    private class TestModel {

        private String fixed;
        private String fixedPhone;
        private Long fixedPhoneUs;
        private String fixedCpf;

        /**
         * @return the fixed
         */
        public String getFixed() {
            return fixed;
        }

        /**
         * @param fixed the fixed to set
         */
        public void setFixed(String fixed) {
            this.fixed = fixed;
        }

        /**
         * @return the fixedPhone
         */
        public String getFixedPhone() {
            return fixedPhone;
        }

        /**
         * @param fixedPhone the fixedPhone to set
         */
        public void setFixedPhone(String fixedPhone) {
            this.fixedPhone = fixedPhone;
        }

        /**
         * @return the fixedCpf
         */
        public String getFixedCpf() {
            return fixedCpf;
        }

        /**
         * @param fixedCpf the fixedCpf to set
         */
        public void setFixedCpf(String fixedCpf) {
            this.fixedCpf = fixedCpf;
        }

        /**
         * @return the fixedPhoneUs
         */
        public Long getFixedPhoneUs() {
            return fixedPhoneUs;
        }

        /**
         * @param fixedPhoneUs the fixedPhoneUs to set
         */
        public void setFixedPhoneUs(Long fixedPhoneUs) {
            this.fixedPhoneUs = fixedPhoneUs;
        }
    }
}

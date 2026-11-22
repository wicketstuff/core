/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License") +  you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.wicketstuff.select2;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.wicket.IGenericComponent;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.head.CssReferenceHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

public class Issue1569Page extends WebPage implements IGenericComponent<Collection<String>, Issue1569Page> {
	private static final long serialVersionUID = 1L;
	FeedbackPanel feedback = new FeedbackPanel("feedback");

	public static final List<String> KNOWN_USERS = Arrays.asList("bob", "alice", "evil");

	@Override
	protected void onInitialize() {
		super.onInitialize();

		Form<Void> frm = new Form<>("frm");
		add(frm.add(feedback.setOutputMarkupId(true)));

		StringTextChoiceProvider provider = new StringTextChoiceProvider() {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isDisabled(String object) {
				return object == null || object.endsWith("evil");
			}

			@Override
			public void query(String term, int page, Response<String> response) {
				response.addAll(KNOWN_USERS);
			}
		};
		final Select2MultiChoice<String> s2mc = new Select2MultiChoice<>("s2mc", getModel(), provider);
		s2mc.getSettings().setTags(true);
		frm.add(s2mc);
		frm.add(new AjaxButton("sbmt") {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target) {
				feedback.info("INFO:: " + s2mc.getModelObject().size());
				target.add(feedback);
				Issue1569Page.this.onSubmit(Issue1569Page.this.getModelObject());
			}
		});
	}

	protected void onSubmit(Collection<String> selection) {
		selection.forEach(System.out::println);
	}

	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);

		response.render(CssReferenceHeaderItem.forReference(ApplicationSettings.get().getCssReference()));
	}
}

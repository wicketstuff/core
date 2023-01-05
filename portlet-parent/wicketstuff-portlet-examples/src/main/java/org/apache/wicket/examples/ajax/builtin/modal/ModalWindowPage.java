/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.wicket.examples.ajax.builtin.modal;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.examples.ajax.builtin.BasePage;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalDialog;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.PropertyModel;


/**
 * @author Matej Knopp
 */
public class ModalWindowPage extends BasePage
{
	private static final long serialVersionUID = 1L;

	public ModalWindowPage()
	{
		final Label result;
		add(result = new Label("result", new PropertyModel<>(this, "result")));
		result.setOutputMarkupId(true);

		/*
		 * First modal window
		 */

		final ModalDialog modal1;
		add(modal1 = new ModalDialog("modal1"){

                    @Override
                    public ModalDialog close(AjaxRequestTarget target) {
			setResult("Modal window 1 - close button");
                        return super.close(target);
                    }
                    
                });

		add(new AjaxLink<Void>("showModal1")
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target)
			{
				modal1.open(target);
			}
		});

		/*
		 * Second modal window
		 */

		final ModalDialog modal2;
		add(modal2 = new ModalDialog("modal2"){

                    @Override
                    public ModalDialog close(AjaxRequestTarget target) {
			setResult("Modal window 2 - close button");
                        target.add(result);
                        return super.close(target);
                    }
                });

		modal2.setContent(new ModalPanel1(ModalDialog.CONTENT_ID));

		add(new AjaxLink<Void>("showModal2")
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target)
			{
				modal2.open(target);
			}
		});
	}

	/**
	 * @return the result
	 */
	public String getResult()
	{
		return result;
	}

	/**
	 * @param result
	 *            the result to set
	 */
	public void setResult(String result)
	{
		this.result = result;
	}

	private String result;

}

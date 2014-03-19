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
package org.wicketstuff.objectautocomplete.example;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.objectautocomplete.AutoCompletionChoicesProvider;
import org.wicketstuff.objectautocomplete.ObjectAutoCompleteBuilder;
import org.wicketstuff.objectautocomplete.ObjectAutoCompleteField;
import org.wicketstuff.objectautocomplete.ObjectAutoCompleteSelectionChangeListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Homepage
 */
public class ListenerExamplePage extends BaseExamplePage<Car, Integer>
{

	private static final long serialVersionUID = 1L;

	public ListenerExamplePage()
	{
		super(new Model<Integer>());

	}

	@Override
	List<Car> getAllChoices()
	{
		return CarRepository.allCars();
	}

	@Override
	String getHtmlSample()
	{
		return "<p>\n" + "  Selected id is: <span wicket:id=\"selectedId\"></span>\n" + "</p>\n\n"
			+ "<form wicket:id=\"form\">\n"
			+ "   Brand: <input type=\"text\" wicket:id=\"acField\"/>\n" + "</form>";
	}

	@Override
	String getCodeSample()
	{
		return "Label label = new Label(\"selectedId\", getModel());\n"
			+ "label.setOutputMarkupId(true);\n"
			+ "Form form = new Form(\"form\");\n"
			+ "ObjectAutoCompleteField acField =\n"
			+ "        new ObjectAutoCompleteBuilder<Car,Integer>(getCarChoiceProvider())\n"
			+ "                .updateOnSelectionChange(new ObjectAutoCompleteSelectioChangeListener<Integer>() {\n"
			+ "                     public void selectionChanged(AjaxRequestTarget pTarget, IModel<Integer> pIntegerIModel) {\n"
			+ "                          label.setDefaultModel(pIntegerIModel);\n"
			+ "                          pTarget.addComponent(label);\n"
			+ "                     }\n " + "                })\n"
			+ "                .build(\"acField\", new Model<Integer>());\n"
			+ "acField.registerForUpdateOnSelectionChange(label);\n" + "\n" + "form.add(label);\n"
			+ "form.add(acField);";
	}

	private void test()
	{
		Form<Void> form = new Form<Void>("form");
		ObjectAutoCompleteField<Car, Integer> acField = new ObjectAutoCompleteBuilder<Car, Integer>(
			getCarChoiceProvider()).build("acField", new Model<Integer>());
		Label label1 = new Label("selectedId", getModel());
		label1.setOutputMarkupId(true);
		Label label = label1;
		add(label);
		form.add(acField);
	}

	private AutoCompletionChoicesProvider<Car> getCarChoiceProvider()
	{
		return new AutoCompletionChoicesProvider<Car>()
		{
			private static final long serialVersionUID = 1L;

			public Iterator<Car> getChoices(String input)
			{
				List<Car> ret = new ArrayList<Car>();
				for (Car car : CarRepository.allCars())
				{
					if (car.getName().toLowerCase().startsWith(input.toLowerCase()))
					{
						ret.add(car);
					}
				}
				return ret.iterator();
			}
		};
	}

	@Override
	protected void initBuilder(
		ObjectAutoCompleteBuilder<Car, Integer> pCarIntegerObjectAutoCompleteBuilder)
	{

		// Read-only view of the current id
		final Label label = new Label("selectedId", getModel());
		label.setOutputMarkupId(true);
		add(label);

		ObjectAutoCompleteSelectionChangeListener<Integer> objectAutoCompleteSelectionChangeListener = new ObjectAutoCompleteSelectionChangeListener<Integer>()
		{
			private static final long serialVersionUID = 1L;

			public void selectionChanged(AjaxRequestTarget pTarget, IModel<Integer> pIntegerIModel)
			{
				label.setDefaultModel(pIntegerIModel);
				pTarget.add(label);
			}
		};
		pCarIntegerObjectAutoCompleteBuilder.updateOnSelectionChange(
			objectAutoCompleteSelectionChangeListener).idType(Integer.class);

	}

	@Override
	protected void addIfMatch(List<Car> pCars, Car pCar, String pInput)
	{
		if (pCar.getName().toLowerCase().startsWith(pInput.toLowerCase()))
		{
			pCars.add(pCar);
		}
	}

}
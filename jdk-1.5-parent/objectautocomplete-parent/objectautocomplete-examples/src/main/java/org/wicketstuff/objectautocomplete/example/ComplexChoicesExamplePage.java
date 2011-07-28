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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.http.WebResponse;
import org.wicketstuff.objectautocomplete.AutoCompletionChoicesProvider;
import org.wicketstuff.objectautocomplete.ObjectAutoCompleteBuilder;
import org.wicketstuff.objectautocomplete.ObjectAutoCompleteField;
import org.wicketstuff.objectautocomplete.ObjectAutoCompleteResponseRenderer;

/**
 * Homepage
 */
public class ComplexChoicesExamplePage extends BaseExamplePage<Car, Integer>
{

	private static final long serialVersionUID = 1L;

	public ComplexChoicesExamplePage()
	{
		super(new Model<Integer>());
	}

	@Override
	List<Car> getAllChoices()
	{
		return CarRepository.allCars();
	}

	@Override
	protected void initBuilder(ObjectAutoCompleteBuilder<Car, Integer> pBuilder)
	{
		super.initBuilder(pBuilder);
		pBuilder.autoCompleteResponseRenderer(getAutoCompleteResponseRenderer());
	}

	@Override
	String getCodeSample()
	{
		return "ObjectAutoCompleteBuilder<Car,Integer> builder =\n"
			+ "       new ObjectAutoCompleteBuilder<Car,Integer>( ... ).\n\n"
			+ "       autoCompleteResponseRenderer(new ObjectAutoCompleteResponseRenderer<Car>() {\n"
			+ "\n"
			+ "            public void onRequest(Iterator<Car> pComps, WebResponse pResponse,String pInput) {\n"
			+ "                List<Car> without = new ArrayList<Car>();\n"
			+ "                pResponse.write(\"<div>\");\n"
			+ "                pResponse.write(\"<div>With 'a':</div><ul>\");\n"
			+ "                while (pComps.hasNext()) {\n"
			+ "                    Car car = pComps.next();\n"
			+ "                    if (car.getName().contains(\"a\")) {\n"
			+ "                        renderObject(car,pResponse,pInput);\n"
			+ "                    } else {\n" + "                        without.add(car);\n"
			+ "                    }\n" + "                }\n"
			+ "                pResponse.write(\"</ul>\");\n"
			+ "                if (without.size() > 0) {\n"
			+ "                    pResponse.write(\"<div>Without 'a':</div><ul>\");\n"
			+ "                    for (Car car : without) {\n"
			+ "                        renderObject(car,pResponse,pInput);\n"
			+ "                    }\n" + "                    pResponse.write(\"</ul>\");\n"
			+ "                }\n" + "                pResponse.write(\"</div>\");\n"
			+ "            }\n" + "        }\n" + "\n" + "ObjectAutoCompleteField acField = \n"
			+ "       builder.build(\"acField\", new Model<Integer>());\n" + "form.add(acField);";
	}

	// Used for C&P in the string above
	private void test()
	{
		Form<Void> form = new Form<Void>("form");
		ObjectAutoCompleteBuilder<Car, Integer> builder = new ObjectAutoCompleteBuilder<Car, Integer>(
			new AutoCompletionChoicesProvider<Car>()
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
			}).autoCompleteResponseRenderer(getAutoCompleteResponseRenderer());
		ObjectAutoCompleteField<Car, Integer> acField = builder.build("acField",
			new Model<Integer>());
		form.add(acField);
	}

	private ObjectAutoCompleteResponseRenderer<Car> getAutoCompleteResponseRenderer()
	{
		return new ObjectAutoCompleteResponseRenderer<Car>()
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void onRequest(Iterator<Car> pComps, WebResponse pResponse, String pInput)
			{
				List<Car> without = new ArrayList<Car>();
				pResponse.write("<div>");
				pResponse.write("<div class=\"choiceHeader\">With 'a':</div><ul>");
				while (pComps.hasNext())
				{
					Car car = pComps.next();
					if (car.getName().contains("a"))
					{
						renderObject(car, pResponse, pInput);
					}
					else
					{
						without.add(car);
					}
				}
				pResponse.write("</ul>");
				if (without.size() > 0)
				{
					pResponse.write("<div class=\"choiceHeader\">Without 'a':</div><ul>");
					for (Car car : without)
					{
						renderObject(car, pResponse, pInput);
					}
					pResponse.write("</ul>");
				}
				pResponse.write("</div>");
			}
		};
	}

	@Override
	String getHtmlSample()
	{
		return "<form wicket:id=\"form\">\n"
			+ " Brand: <input type=\"text\" wicket:id=\"acField\"/>\n" + "</form>";
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
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

import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.objectautocomplete.ObjectAutoCompleteBuilder;
import org.wicketstuff.objectautocomplete.ObjectReadOnlyRenderer;

/**
 * Homepage
 */
public class ReadOnlyObjectRendererExamplePage extends BaseExamplePage<Car, Integer>
{

	private static final long serialVersionUID = 1L;

	public ReadOnlyObjectRendererExamplePage()
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
		pBuilder.readOnlyRenderer(new ObjectReadOnlyRenderer<Integer>()
		{
			private static final long serialVersionUID = 1L;

			public Component getObjectRenderer(String id, final IModel<Integer> pIModel,
				IModel<String> pSearchTextModel)
			{
				Fragment frag = new Fragment(id, "readOnlyView",
					ReadOnlyObjectRendererExamplePage.this);
				frag.add(new Label("search", pSearchTextModel));
				frag.add(new Label("id", pIModel));
				frag.add(new Label("object", new IModel<String>()
				{
					private static final long serialVersionUID = 1L;

					@Override
					public String getObject()
					{
						if (pIModel != null && pIModel.getObject() != null)
						{
							for (Car car : CarRepository.allCars())
							{
								if (car.getId() == pIModel.getObject())
								{
									return "[id = " + car.getId() + ",name = " + car.getName() +
										"]";
								}
							}
						}
						return null;
					}
				}));
				return frag;
			}
		})
			.searchOnClick()
			.idType(Integer.class);
	}

	@Override
	String getCodeSample()
	{
		return "ObjectAutoCompleteField<Car,Integer> acField =\n"
			+ "        new ObjectAutoCompleteBuilder<Car,Integer>(getAcChoicesProvider()) \n"
			+ "                .readOnlyRenderer(new ReadOnlyObjectRenderer<Integer>() {\n"
			+ "                    public Component getObjectRenderer(String id, IModel<Integer> pIModel,\n"
			+ "                                                       Model<String> pSearchTextModel) {\n"
			+ "                        Fragment frag =  new Fragment(id,\"readOnlyView\");\n"
			+ "                        frag.add(new Label(\"search\",pSearchTextModel));\n"
			+ "                        frag.add(new Label(\"id\",pIModel));\n"
			+ "                        frag.add(new Label(\"object\",new IModel() {\n"
			+ "                              public Object getObject() {\n"
			+ "                                  if (pIModel != null && pIModel.getObject() != null) {\n"
			+ "                                       for (Car car : CarRepository.allCars()) {\n"
			+ "                                           if (car.getId() == pIModel.getObject()) {\n"
			+ "                                              return \"[id = \" + car.getId()\n"
			+ "                                                   + \",name = \" + car.getName() + \"]\";\n"
			+ "                                           }\n"
			+ "                                       }\n"
			+ "                                  }\n"
			+ "                                  return null;\n"
			+ "                              }\n" + "                        }));"
			+ "                        return frag;\n" + "                    }\n"
			+ "               })\n" + "                .searchOnClick()\n"
			+ "                .build();\n" + "form.add(acField);";
	}

	@Override
	String getHtmlSample()
	{
		return "<form wicket:id=\"form\">\n"
			+ "  Brand: <input type=\"text\" wicket:id=\"acField\" />\n" + "</form>\n" + "\n"
			+ "<wicket:fragment wicket:id=\"readOnlyView\">\n"
			+ "  Name: <span wicket:id=\"search\">[search]</span>\n"
			+ "  Id:   <span wicket:id=\"id\">[id]</span>\n" + "</wicket:fragment>";
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
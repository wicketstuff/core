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
package org.apache.wicket.extensions.markup.html.tree;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import org.apache.wicket.extensions.markup.html.tree.TreeTablePage.Person;

/**
 * Test fixture with the same family as {@link TreeTablePage}:
 *
 * <pre>
 * Opa Puk
 * +- Pietje Puk
 * +- Janneke Puk
 *    +- Pino Puk
 * </pre>
 */
public class FamilyTree
{
	public final FamilyTreeNode opa;

	public final FamilyTreeNode pietje;

	public final FamilyTreeNode janneke;

	public final FamilyTreeNode pino;

	public final TreeModel model;

	public FamilyTree()
	{
		opa = new FamilyTreeNode(person("Opa Puk"));
		pietje = opa.addChild(person("Pietje Puk"));
		janneke = opa.addChild(person("Janneke Puk"));
		pino = janneke.addChild(person("Pino Puk"));
		model = new DefaultTreeModel(opa);
	}

	private static Person person(String name)
	{
		Person person = new Person();
		person.setName(name);
		return person;
	}
}

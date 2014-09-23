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
package org.wicketstuff.jquery.demo.dnd;

import java.util.ArrayList;
import java.util.List;

public class MyFactory
{

	public static MyItem newMyItem(String base) throws Exception
	{
		MyItem back = new MyItem();
		back.label = "label of " + base;
		back.description = "description of " + base +
			"\norem ipsum dolor sit amet, consectetuer adipiscing elit";
		return back;
	}

	public static List<MyItem> newMyItemList(String base, int length) throws Exception
	{
		ArrayList<MyItem> back = new ArrayList<MyItem>(length);
		for (int i = 0; i < length; i++)
		{
			back.add(newMyItem(base + " " + i));
		}
		return back;
	}

	public static MyGroup newMyGroup(String base, int length) throws Exception
	{
		MyGroup back = new MyGroup();
		back.label = "label of " + base;
		back.items = newMyItemList(base + "Item", length);
		return back;
	}

	public static List<MyGroup> newMyGroupList(String base, int length, int nbItemPerGroup)
		throws Exception
	{
		ArrayList<MyGroup> back = new ArrayList<MyGroup>(length);
		for (int i = 0; i < length; i++)
		{
			back.add(newMyGroup(base + " " + i, nbItemPerGroup));
		}
		return back;
	}
}

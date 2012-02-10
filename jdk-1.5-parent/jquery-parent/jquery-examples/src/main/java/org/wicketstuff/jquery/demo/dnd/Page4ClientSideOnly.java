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

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.wicketstuff.jquery.Options;
import org.wicketstuff.jquery.demo.PageSupport;
import org.wicketstuff.jquery.dnd.DnDSortableHandler;

@SuppressWarnings("serial")
public class Page4ClientSideOnly extends PageSupport
{
	public Page4ClientSideOnly() throws Exception
	{
		super();
		add(new DnDSortableHandler("dnd0", new Options().set("accept", "groupItem").set(
			"containerclass", "groupWrapper")
		// .set("helperclass", "sortHelper")
		// .set("activeclass", "sortableactive")
		// .set("hoverclass", "sortablehover")
		// .set("handle", "div.itemHeader")
		// .set("tolerance", "pointer")
		)
		{
			@Override
			public boolean onDnD(AjaxRequestTarget target, MarkupContainer srcContainer,
				int srcPos, MarkupContainer destContainer, int destPos)
			{
				System.out.format("srcContainer=%s, srcPos=%s, destContainer=%s, destPos=%s\n",
					String.valueOf(srcContainer), String.valueOf(srcPos),
					String.valueOf(destContainer), String.valueOf(destPos));
				return false;
			}
		});
	}
}

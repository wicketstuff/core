/**
 * Copyright (C)
 * 	2008 Jeremy Thomerson <jeremy@thomersonfamily.com>
 * 	2012 Michael Mosmann <michael@mosmann.de>
 *
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
package org.wicketstuff.pageserializer.kryo2;

import org.apache.wicket.Component;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.lang.Bytes;
import org.wicketstuff.pageserializer.kryo2.inspecting.InspectingKryoSerializer;
import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.AnalyzingSerializationListener;
import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.IObjectLabelizer;
import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.ISerializedObjectTree;
import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.ISerializedObjectTreeProcessor;
import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.TreeProcessors;
import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.report.Level;
import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.report.SimilarNodeTreeTransformator;
import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.report.SortedTreeSizeReport;
import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.report.TreeTransformator;
import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.report.TypeSizeReport;
import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.report.filter.ITreeFilter;
import org.wicketstuff.pageserializer.kryo2.inspecting.listener.ISerializationListener;
import org.wicketstuff.pageserializer.kryo2.inspecting.listener.SerializationListeners;
import org.wicketstuff.pageserializer.kryo2.inspecting.validation.DefaultJavaSerializationValidator;

/**
 * Application object for your web application. If you want to run this application without
 * deploying, run the Start class.
 * 
 * @see org.wicketstuff.pageserializer.kryo.mycompany.Start#main(String[])
 */
public class WicketApplication extends WebApplication
{
	@Override
	public Class<HomePage> getHomePage()
	{
		return HomePage.class;
	}

	@Override
	public void init()
	{
		super.init();
		
		IObjectLabelizer labelizer = new IObjectLabelizer()
		{

			@Override
			public String labelFor(Object object)
			{
				if (object instanceof Component) {
					return ((Component) object).getId();
				}
				return null;
			}
		};
		ISerializedObjectTreeProcessor treeProcessor = TreeProcessors.listOf(new TypeSizeReport(),
		 new SortedTreeSizeReport(), new SimilarNodeTreeTransformator(
				new SortedTreeSizeReport()));
		ITreeFilter filter = new ITreeFilter()
		{
			@Override
			public boolean accept(ISerializedObjectTree source, Level current)
			{
				return source.type() != Class.class;
			}
		};
		ISerializedObjectTreeProcessor cleanedTreeProcessor = new TreeTransformator(treeProcessor,
			TreeTransformator.strip(filter));
		ISerializationListener listener = SerializationListeners.listOf(
			new DefaultJavaSerializationValidator(), 
			new AnalyzingSerializationListener(labelizer, cleanedTreeProcessor));


		getFrameworkSettings().setSerializer(new InspectingKryoSerializer(Bytes.bytes(1024*1024),listener));
	}
}

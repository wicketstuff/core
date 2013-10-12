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
package org.wicketstuff.pageserializer.fast;

import java.io.File;
import java.util.UUID;

import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.lang.Bytes;
import org.wicketstuff.pageserializer.fast.FastWicketSerializer;

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
		
		getFrameworkSettings().setSerializer(new FastWicketSerializer(Bytes.bytes(1024*1024)));
	}

	private File tempDirectory(String prefix) {
		File baseDir = new File(System.getProperty("java.io.tmpdir"));
		String baseName = prefix + "-" + UUID.randomUUID().toString();

		File tempDir = new File(baseDir, baseName);
		if (tempDir.mkdir()) {
			return tempDir;
		}

		throw new RuntimeException("Could not create tempdir " + baseName + " in " + baseDir);
	}
}

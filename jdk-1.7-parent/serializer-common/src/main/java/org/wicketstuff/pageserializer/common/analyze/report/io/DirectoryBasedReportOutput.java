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
package org.wicketstuff.pageserializer.common.analyze.report.io;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.wicket.util.file.Files;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.pageserializer.common.analyze.ISerializedObjectTree;
import org.wicketstuff.pageserializer.common.analyze.report.IReportOutput;
import org.wicketstuff.pageserializer.common.analyze.report.IReportRenderer;


public class DirectoryBasedReportOutput {
	
	private final static Logger LOG = LoggerFactory.getLogger(DirectoryBasedReportOutput.class);

	private final File _directory;

	public DirectoryBasedReportOutput(File directory) {
		if (!directory.isDirectory()) throw new RuntimeException("not a directory: "+directory);
		_directory = directory;
		
		LOG.info("write reports into "+directory);
	}
	
	protected void write(IReportKeyGenerator keyGenerator, ISerializedObjectTree tree, IReportRenderer renderer) {
		String report = renderer.render(tree);
		File output=new File(_directory,keyGenerator.keyOf(tree));
		try {
			Files.writeTo(output, new ByteArrayInputStream(report.getBytes(Charset.forName("UTF-8"))));
		} catch (IOException e) {
			throw new RuntimeException("write report to "+output,e);
		}
	}
	
	public IReportOutput with(IReportKeyGenerator keyGenerator) {
		return new KeyReportOutputAdapter(keyGenerator);
	}
	
	class KeyReportOutputAdapter implements IReportOutput {

		private final IReportKeyGenerator _keyGenerator;

		public KeyReportOutputAdapter(IReportKeyGenerator keyGenerator) {
			_keyGenerator = keyGenerator;
		}

		@Override
		public void write(ISerializedObjectTree tree, IReportRenderer renderer) {
			DirectoryBasedReportOutput.this.write(_keyGenerator,tree,renderer);
		}
		
	}
}

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
package org.wicketstuff.poi.excel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.wicket.util.resource.AbstractResourceStream;
import org.apache.wicket.util.resource.ResourceStreamNotFoundException;
import org.apache.wicket.util.time.Time;

/**
 * The XLS file resource for the specified {@link Workbook}
 * 
 * @author Pedro Santos
 */
public class XlsStream extends AbstractResourceStream
{
	/** */
	private static final long serialVersionUID = 1L;
	private transient Workbook wb;
	private transient InputStream inputStream;

	public XlsStream(Workbook wb)
	{
		this.wb = wb;
	}

	public InputStream getInputStream() throws ResourceStreamNotFoundException
	{
		if (inputStream == null)
		{
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			try
			{
				wb.write(out);
				out.close();
				inputStream = new ByteArrayInputStream(out.toByteArray());
			}
			catch (IOException e)
			{
				throw new RuntimeException(e);
			}
		}
		return inputStream;
	}

	public void close() throws IOException
	{
		if (inputStream != null)
		{
			inputStream.close();
			inputStream = null;
		}
	}

	@Override
	public String getContentType()
	{
		return "application/vnd.ms-excel";
	}

	@Override
	public Time lastModifiedTime()
	{
		return Time.now();
	}
}

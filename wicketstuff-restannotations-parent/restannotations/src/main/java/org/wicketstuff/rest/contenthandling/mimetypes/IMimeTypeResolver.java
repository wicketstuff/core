/**
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.wicketstuff.rest.contenthandling.mimetypes;

/**
 * A generic resolver to retrieve the MIME types to use for a mapped method in input and output.
 * 
 * @author andrea del bene
 * 
 */
public interface IMimeTypeResolver
{
	/**
	 * Get the MIME type to use in input.
	 * 
	 * @return The MIME type to use in input.
	 */
	public String getInputFormat();

	/**
	 * Get the MIME type to use in output.
	 * 
	 * @return The MIME type to use in output
	 */
	public String getOutputFormat();
}

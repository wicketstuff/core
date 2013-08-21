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

import org.apache.wicket.util.lang.Args;
import org.wicketstuff.rest.annotations.MethodMapping;

/**
 * Default implementation for interface {@link IMimeTypeResolver} that uses
 * annotation {@link MethodMapping}.
 * 
 * @author andrea del bene
 * 
 */
public class DefaultMimeTypeResolver implements IMimeTypeResolver {
	/** The MIME type to use in input. */
	private final String inputFormat;
	/** The MIME type to use in output. */
	private final String outputFormat;

	public DefaultMimeTypeResolver(MethodMapping methodMapped) {
		Args.notNull(methodMapped, "methodMapped");
				
		this.inputFormat = methodMapped.consumes();
		this.outputFormat = methodMapped.produces();
	}

	@Override
	public String getInputFormat() {
		return inputFormat;
	}

	@Override
	public String getOutputFormat() {
		return outputFormat;
	}

}

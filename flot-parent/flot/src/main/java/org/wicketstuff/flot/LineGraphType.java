/*
 * Copyright 2009 Michael Würtinger (mwuertinger@users.sourceforge.net)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 		http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.flot;

public class LineGraphType extends GraphType
{
	/** Required by {@link Serializable} */
	private static final long serialVersionUID = 1L;

	public LineGraphType(Double lineWidth, Boolean fill, Color fillColor)
	{
		super(lineWidth, fill, fillColor);
	}

	@Override
	public String toString()
	{
		StringBuffer str = new StringBuffer();

		str.append("lines: {");
		str.append(super.toString());
		str.append("}");

		return str.toString();
	}
}

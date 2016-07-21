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

import java.io.Serializable;

public abstract class GraphType implements Serializable
{
	/** Required by {@link Serializable} */
	private static final long serialVersionUID = 1L;

	private Double lineWidth;
	private Boolean fill;
	private Color fillColor;

	public GraphType(Double lineWidth, Boolean fill, Color fillColor)
	{
		this.lineWidth = lineWidth;
		this.fill = fill;
		this.fillColor = fillColor;
	}

	@Override
	public String toString()
	{
		StringBuffer str = new StringBuffer();

		str.append("show: true");

		if (lineWidth != null)
		{
			str.append(", lineWidth: ");
			str.append(lineWidth);
		}

		if (fill != null)
		{
			str.append(", fill: ");
			str.append(fill);
		}

		if (fillColor != null)
		{
			str.append(", fillColor: ");
			str.append(fillColor);
		}

		return str.toString();
	}
}

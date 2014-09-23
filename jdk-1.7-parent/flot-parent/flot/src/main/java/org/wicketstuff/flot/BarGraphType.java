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


/**
 * 
 * @author Michael Würtinger (mwuertinger@users.sourceforge.net)
 * 
 */
public class BarGraphType extends GraphType
{
	/** Required by {@link Serializable} */
	private static final long serialVersionUID = 1L;

	public enum Align
	{
		LEFT, CENTER
	};

	private Double barWidth;
	private Align align;

	public BarGraphType(Double lineWidth, Boolean fill, Color fillColor, Double barWidth,
		Align align)
	{
		super(lineWidth, fill, fillColor);
		this.barWidth = barWidth;
		this.align = align;
	}

	@Override
	public String toString()
	{
		StringBuffer str = new StringBuffer();

		str.append("bars: {");

		String superStr = super.toString();

		if (barWidth != null)
		{
			str.append("barWidth: ");
			str.append(barWidth);
			if (align != null || superStr.length() > 0)
				str.append(", ");
		}

		if (align != null)
		{
			str.append("align: ");
			switch (align)
			{
				case LEFT :
					str.append("\"left\"");
					break;
				case CENTER :
					str.append("\"center\"");
					break;
			}

			if (superStr.length() > 0)
				str.append(", ");
		}

		str.append(superStr);

		str.append("}");

		return str.toString();
	}
}

/*
 * Copyright 2009 Michael W�rtinger (mwuertinger@users.sourceforge.net)
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
import java.util.Locale;

public class TickDataSet implements Serializable
{
	/** Required by {@link Serializable} */
	private static final long serialVersionUID = 1L;

	private double x;
	private String label;

	public TickDataSet(double x, String label)
	{
		this.x = x;
		this.label = label;
	}

	public double getX()
	{
		return x;
	}

	public String getLabel()
	{
		return label;
	}

	@Override
	public String toString()
	{
		return String.format(Locale.US, "[%f, \"%s\"]", x, label);
	}
}
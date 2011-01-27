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
package org.wicketstuff.dojo11.application;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.wicket.IClusterable;
import org.apache.wicket.ResourceReference;

/**
 * @author stf
 */
public class DojoLayer implements IClusterable
{
	private String _name;
	private ResourceReference _resourceReference;
	private Set<String> _layerDependencies;
	
	/**
	 * Construct.
	 * @param name
	 * @param ref
	 * @param layerDependencies
	 */
	public DojoLayer(String name, ResourceReference ref, String ... layerDependencies) {
		_name = name;
		_resourceReference = ref;
		_layerDependencies = new HashSet<String>();
		_layerDependencies.addAll(Arrays.asList(layerDependencies));
	}

	/**
	 * @return layer name
	 */
	public String getName()
	{
		return _name;
	}

	/**
	 * @return layer resourceReference
	 */
	public ResourceReference getResourceReference()
	{
		return _resourceReference;
	}

	/**
	 * @return layer dependencies
	 */
	public Set<String> geLayerDependencies()
	{
		return Collections.unmodifiableSet(_layerDependencies);
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_name == null) ? 0 : _name.hashCode());
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DojoLayer other = (DojoLayer)obj;
		if (_name == null)
		{
			if (other._name != null)
				return false;
		}
		else if (!_name.equals(other._name))
			return false;
		return true;
	}
}

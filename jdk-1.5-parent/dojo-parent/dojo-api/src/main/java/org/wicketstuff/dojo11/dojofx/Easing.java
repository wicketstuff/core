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
package org.wicketstuff.dojo11.dojofx;

import java.util.Arrays;

import org.apache.wicket.IClusterable;
import org.wicketstuff.dojo11.AbstractRequireDojoBehavior.RequireDojoLibs;

/**
 * @author stf
 */
public class Easing implements IClusterable
{
	/** dojox.fx.easing.backIn */
	public static final Easing BACK_IN = new Easing("dojox.fx.easing.backIn", "dojox.fx.easing");
	/** dojox.fx.easing.backInOut */
	public static final Easing BACK_IN_OUT = new Easing("dojox.fx.easing.backInOut", "dojox.fx.easing");
	/** dojox.fx.easing.backOut */
	public static final Easing BACK_OUT = new Easing("dojox.fx.easing.backOut", "dojox.fx.easing");
	
	/** dojox.fx.easing.bounceIn */
	public static final Easing BOUNCE_IN = new Easing("dojox.fx.easing.bounceIn", "dojox.fx.easing");
	/** dojox.fx.easing.bounceInOut */
	public static final Easing BOUNCE_IN_OUT = new Easing("dojox.fx.easing.bounceInOut", "dojox.fx.easing");
	/** dojox.fx.easing.bounceOut */
	public static final Easing BOUNCE_OUT = new Easing("dojox.fx.easing.bounceOut", "dojox.fx.easing");
	
	/** dojox.fx.easing.elasticIn */
	public static final Easing ELASTIC_IN = new Easing("dojox.fx.easing.elasticIn", "dojox.fx.easing");
	/** dojox.fx.easing.elasticInOut */
	public static final Easing ELASTIC_IN_OUT = new Easing("dojox.fx.easing.elasticInOut", "dojox.fx.easing");
	/** dojox.fx.easing.elasticOut */
	public static final Easing ELASTIC_OUT = new Easing("dojox.fx.easing.elasticOut", "dojox.fx.easing");
	
	/** dojox.fx.easing.quartIn */
	public static final Easing QUART_IN = new Easing("dojox.fx.easing.quartIn", "dojox.fx.easing");
	/** dojox.fx.easing.quartInOut */
	public static final Easing QUART_IN_OUT = new Easing("dojox.fx.easing.quartInOut", "dojox.fx.easing");
	/** dojox.fx.easing.quartOut */
	public static final Easing QUART_OUT = new Easing("dojox.fx.easing.quartOut", "dojox.fx.easing");
	
	private String _name;
	private String[] _libs;

	/**
	 * 
	 * Construct.
	 * @param name name of easing functions
	 * @param libs required dojo libs
	 */
	public Easing(String name, String ... libs) {
		_name = name;
		_libs = libs;
	}
	
	/**
	 * @return name of easing function
	 */
	public String getName() {
		return _name;
	}

	/**
	 * @param libs
	 */
	public void setRequire(RequireDojoLibs libs)
	{
		libs.addAll(Arrays.asList(_libs));
	}
}

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
package org.wicketstuff.minis.behavior.prototip;

import java.io.Serializable;

/**
 * This is a class for creating settings to be used with the prototip lib (the optional third
 * argument)
 * 
 * in all cases you do not need to worry about enclosing your strings in ' '
 * 
 * There is logic in place so that if you enter false or true (ie the boolean), or a number it will
 * not enclose your string in '', where as any other string it will
 * 
 * see http://www.nickstakenburg.com/projects/prototip/ for a better description of the options that
 * prototip uses
 * 
 * @author Richard Wilkinson
 * 
 */
public class PrototipSettings implements Serializable
{
	private static final long serialVersionUID = 1L;

	private String className;
	private String closeButton;
	private String duration;
	private String delay;
	private String effect;
	private String hideAfter;
	private String hideOn;
	private String hook;
	private String offset_x;
	private String offset_y;
	private String showOn;
	private String target;
	private String title;
	private String viewpoint;
	private String extraOptions;
	private String fixed;

	/**
	 * Generates the correct Javascript code to add as the third parameter to a prototip tooltip
	 * 
	 * any title passed in here will override any title which is set in this object eg:
	 * 
	 * PrototipBehaviour title > PrototipSettings title
	 * 
	 * @return Javascript String
	 */
	public String getOptionsString(String title)
	{
		StringBuilder options = new StringBuilder();
		if (className != null)
		{
			options.append("className").append(": ").append(className);
		}
		if (closeButton != null)
		{
			if (options.length() > 0)
				options.append(", ");
			options.append("closeButton").append(":").append(closeButton);
		}
		if (duration != null)
		{
			if (options.length() > 0)
				options.append(", ");
			options.append("duration").append(":").append(duration);
		}
		if (delay != null)
		{
			if (options.length() > 0)
				options.append(", ");
			options.append("delay").append(":").append(delay);
		}
		if (effect != null)
		{
			if (options.length() > 0)
				options.append(", ");
			options.append("effect").append(": ").append(effect);
			;
		}
		if (hideAfter != null)
		{
			if (options.length() > 0)
				options.append(", ");
			options.append("hideAfter").append(":").append(hideAfter);
		}
		if (hideOn != null)
		{
			if (options.length() > 0)
				options.append(", ");
			options.append("hideOn").append(":").append(hideOn);
		}
		if (hook != null)
		{
			if (options.length() > 0)
				options.append(", ");
			options.append("hook").append(":").append(hook);
		}
		if (offset_x != null && offset_y != null)
		{
			if (options.length() > 0)
				options.append(", ");
			options.append("offset")
				.append(": { x:")
				.append(offset_x)
				.append(", y: ")
				.append(offset_y)
				.append("}");
		}
		if (showOn != null)
		{
			if (options.length() > 0)
				options.append(", ");
			options.append("showOn").append(": ").append(showOn);
		}
		if (target != null)
		{
			if (options.length() > 0)
				options.append(", ");
			options.append("target").append(": ").append(target);
		}
		if (title != null)
		{
			if (options.length() > 0)
				options.append(", ");
			options.append("title").append(": '").append(title).append("'");
		}
		else if (this.title != null)
		{
			if (options.length() > 0)
				options.append(", ");
			options.append("title").append(": ").append(this.title);
		}
		if (viewpoint != null)
		{
			if (options.length() > 0)
				options.append(", ");
			options.append("viewpoint").append(":").append(viewpoint);
		}
		if (fixed != null)
		{
			if (options.length() > 0)
				options.append(", ");
			options.append("fixed").append(":").append(fixed);
		}
		if (extraOptions != null)
		{
			if (options.length() > 0)
				options.append(", ");
			options.append(extraOptions);
		}
		if (options.length() > 0)
		{
			options.insert(0, "{ ");
			options.append(" }");
		}

		return options.toString();
	}

	/**
	 * @return the className
	 */
	public String getClassName()
	{
		return className;
	}

	/**
	 * you do not need to include the ' '
	 * 
	 * @param className
	 *            the className to set
	 * @return this
	 */
	public PrototipSettings setClassName(String className)
	{
		this.className = "'" + className + "'";
		return this;
	}

	/**
	 * @return the closeButton
	 */
	public String getCloseButton()
	{
		return closeButton;
	}

	/**
	 * either false or true
	 * 
	 * @param closeButton
	 *            the closeButton to set
	 * @return this
	 */
	public PrototipSettings setCloseButton(String closeButton)
	{
		this.closeButton = closeButton;
		return this;
	}

	/**
	 * @return the duration
	 */
	public String getDuration()
	{
		return duration;
	}

	/**
	 * duration of the effect, if used eg 0.3
	 * 
	 * @param duration
	 *            the duration to set
	 * @return this
	 */
	public PrototipSettings setDuration(String duration)
	{
		this.duration = duration;
		return this;
	}

	/**
	 * @return the fixed
	 */
	public String getFixed()
	{
		return fixed;
	}

	/**
	 * eg false or true
	 * 
	 * @param fixed
	 *            the fixed to set
	 * @return this
	 */
	public PrototipSettings setFixed(String fixed)
	{
		this.fixed = fixed;
		return this;
	}

	/**
	 * @return the delay
	 */
	public String getDelay()
	{
		return delay;
	}

	/**
	 * seconds before tooltip appears eg 0.2
	 * 
	 * @param delay
	 *            the delay to set
	 * @return this
	 */
	public PrototipSettings setDelay(String delay)
	{
		this.delay = delay;
		return this;
	}

	/**
	 * @return the effect
	 */
	public String getEffect()
	{
		return effect;
	}

	/**
	 * you do not need to include the ' '
	 * 
	 * false, appear or blind, or others if they get enabled
	 * 
	 * @param effect
	 *            the effect to set
	 * @return this
	 */
	public PrototipSettings setEffect(String effect)
	{
		if (!effect.equals("false"))
		{
			effect = "'" + effect + "'";
		}
		this.effect = effect;
		return this;
	}

	/**
	 * @return the hideAfter
	 */
	public String getHideAfter()
	{
		return hideAfter;
	}

	/**
	 * false or a number eg 1.5
	 * 
	 * @param hideAfter
	 *            the hideAfter to set
	 * @return this
	 */
	public PrototipSettings setHideAfter(String hideAfter)
	{
		this.hideAfter = hideAfter;
		return this;
	}

	/**
	 * @return the hideOn
	 */
	public String getHideOn()
	{
		return hideOn;
	}

	/**
	 * any event eg mouseout or false
	 * 
	 * @param hideOn
	 *            the hideOn to set
	 * @return this
	 */
	public PrototipSettings setHideOn(String hideOn)
	{
		if (!hideOn.equals("false"))
		{
			hideOn = "'" + hideOn + "'";
		}
		this.hideOn = hideOn;
		return this;
	}

	/**
	 * eg: { element: 'element|target|tip|closeButton|.close', event: 'click|mouseover|mousemove' }
	 * 
	 * @param element
	 * @param event
	 * @return this
	 */
	public PrototipSettings setHideOn(String element, String event)
	{
		hideOn = new StringBuilder("{ ").append("element: '")
			.append(element)
			.append("', event: '")
			.append(event)
			.append("' }")
			.toString();
		return this;
	}

	/**
	 * @return the hook
	 */
	public String getHook()
	{
		return hook;
	}

	/**
	 * @param hook
	 *            the hook to set
	 * @return this
	 */
	public PrototipSettings setHookFalse()
	{
		hook = "false";
		return this;
	}

	/**
	 * Set the hook, where you want
	 * 
	 * eg:
	 * 
	 * { target: 'topLeft|topRight|bottomLeft|bottomRight|
	 * topMiddle|bottomMiddle|leftMiddle|rightMiddle', tip:
	 * 'topLeft|topRight|bottomLeft|bottomRight| topMiddle|bottomMiddle|leftMiddle|rightMiddle' }
	 * 
	 * for false use setHookFalse()
	 * 
	 * @param target
	 * @param tip
	 * @return this
	 */
	public PrototipSettings setHook(String target, String tip)
	{
		hook = new StringBuilder("{ ").append("target: '")
			.append(target)
			.append("', tip: '")
			.append(tip)
			.append("' }")
			.toString();
		return this;
	}

	/**
	 * @return the offset_x
	 */
	public String getOffset_x()
	{
		return offset_x;
	}

	/**
	 * @param offset_x
	 *            the offset_x to set
	 * @return this
	 */
	public PrototipSettings setOffset_x(String offset_x)
	{
		this.offset_x = offset_x;
		return this;
	}

	/**
	 * @return the offset_y
	 */
	public String getOffset_y()
	{
		return offset_y;
	}

	/**
	 * @param offset_y
	 *            the offset_y to set
	 * @return this
	 */
	public PrototipSettings setOffset_y(String offset_y)
	{
		this.offset_y = offset_y;
		return this;
	}

	/**
	 * Set both x and y offsets
	 * 
	 * @param offset_x
	 * @param offset_y
	 * @return this
	 */
	public PrototipSettings setOffset(String offset_x, String offset_y)
	{
		this.offset_x = offset_x;
		this.offset_y = offset_y;
		return this;
	}

	/**
	 * @return the showOn
	 */
	public String getShowOn()
	{
		return showOn;
	}

	/**
	 * @param showOn
	 *            the showOn to set
	 * @return this
	 */
	public PrototipSettings setShowOn(String showOn)
	{
		this.showOn = "'" + showOn + "'";
		return this;
	}

	/**
	 * @return the target
	 */
	public String getTarget()
	{
		return target;
	}

	/**
	 * @param target
	 *            the target to set
	 * @return this
	 */
	public PrototipSettings setTarget(String target)
	{
		this.target = "'" + target + "'";
		return this;
	}

	/**
	 * @return the title
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public PrototipSettings setTitle(String title)
	{
		if (!title.equals("false"))
		{
			title = "'" + title + "'";
		}
		this.title = title;
		return this;
	}

	/**
	 * @return the viewpoint
	 */
	public String getViewpoint()
	{
		return viewpoint;
	}

	/**
	 * @param viewpoint
	 *            the viewpoint to set
	 * @return this
	 */
	public PrototipSettings setViewpoint(String viewpoint)
	{
		this.viewpoint = viewpoint;
		return this;
	}

	/**
	 * @return the extraOptions
	 */
	public String getExtraOptions()
	{
		return extraOptions;
	}

	/**
	 * Futureproofing - this allows you at add any string as an option (note you will need to take
	 * care of ' and { } yourself
	 * 
	 * @param extraOptions
	 *            the extraOptions to set
	 * @return this
	 */
	public PrototipSettings setExtraOptions(String extraOptions)
	{
		this.extraOptions = extraOptions;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((className == null) ? 0 : className.hashCode());
		result = prime * result + ((closeButton == null) ? 0 : closeButton.hashCode());
		result = prime * result + ((delay == null) ? 0 : delay.hashCode());
		result = prime * result + ((duration == null) ? 0 : duration.hashCode());
		result = prime * result + ((effect == null) ? 0 : effect.hashCode());
		result = prime * result + ((extraOptions == null) ? 0 : extraOptions.hashCode());
		result = prime * result + ((fixed == null) ? 0 : fixed.hashCode());
		result = prime * result + ((hideAfter == null) ? 0 : hideAfter.hashCode());
		result = prime * result + ((hideOn == null) ? 0 : hideOn.hashCode());
		result = prime * result + ((hook == null) ? 0 : hook.hashCode());
		result = prime * result + ((offset_x == null) ? 0 : offset_x.hashCode());
		result = prime * result + ((offset_y == null) ? 0 : offset_y.hashCode());
		result = prime * result + ((showOn == null) ? 0 : showOn.hashCode());
		result = prime * result + ((target == null) ? 0 : target.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((viewpoint == null) ? 0 : viewpoint.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
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
		final PrototipSettings other = (PrototipSettings)obj;
		if (className == null)
		{
			if (other.className != null)
				return false;
		}
		else if (!className.equals(other.className))
			return false;
		if (closeButton == null)
		{
			if (other.closeButton != null)
				return false;
		}
		else if (!closeButton.equals(other.closeButton))
			return false;
		if (delay == null)
		{
			if (other.delay != null)
				return false;
		}
		else if (!delay.equals(other.delay))
			return false;
		if (duration == null)
		{
			if (other.duration != null)
				return false;
		}
		else if (!duration.equals(other.duration))
			return false;
		if (effect == null)
		{
			if (other.effect != null)
				return false;
		}
		else if (!effect.equals(other.effect))
			return false;
		if (extraOptions == null)
		{
			if (other.extraOptions != null)
				return false;
		}
		else if (!extraOptions.equals(other.extraOptions))
			return false;
		if (fixed == null)
		{
			if (other.fixed != null)
				return false;
		}
		else if (!fixed.equals(other.fixed))
			return false;
		if (hideAfter == null)
		{
			if (other.hideAfter != null)
				return false;
		}
		else if (!hideAfter.equals(other.hideAfter))
			return false;
		if (hideOn == null)
		{
			if (other.hideOn != null)
				return false;
		}
		else if (!hideOn.equals(other.hideOn))
			return false;
		if (hook == null)
		{
			if (other.hook != null)
				return false;
		}
		else if (!hook.equals(other.hook))
			return false;
		if (offset_x == null)
		{
			if (other.offset_x != null)
				return false;
		}
		else if (!offset_x.equals(other.offset_x))
			return false;
		if (offset_y == null)
		{
			if (other.offset_y != null)
				return false;
		}
		else if (!offset_y.equals(other.offset_y))
			return false;
		if (showOn == null)
		{
			if (other.showOn != null)
				return false;
		}
		else if (!showOn.equals(other.showOn))
			return false;
		if (target == null)
		{
			if (other.target != null)
				return false;
		}
		else if (!target.equals(other.target))
			return false;
		if (title == null)
		{
			if (other.title != null)
				return false;
		}
		else if (!title.equals(other.title))
			return false;
		if (viewpoint == null)
		{
			if (other.viewpoint != null)
				return false;
		}
		else if (!viewpoint.equals(other.viewpoint))
			return false;
		return true;
	}
}

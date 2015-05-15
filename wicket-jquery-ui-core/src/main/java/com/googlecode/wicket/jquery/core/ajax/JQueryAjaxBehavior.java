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
package com.googlecode.wicket.jquery.core.ajax;

import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.attributes.CallbackParameter;
import org.apache.wicket.ajax.attributes.ThrottlingSettings;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.util.time.Duration;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.JQueryEvent;

/**
 * Base class for implementing AJAX GET calls to a {@link IJQueryAjaxAware} source, which is usually a {@link JQueryBehavior}<br />
 * <br />
 * <b>Example</b>
 * 
 * <pre>
 * interface IMyJQueryListener
 * {
 * 	void onMyEvent(AjaxRequestTarget target);
 * }
 * 
 * public class MyJQueryLabel extends Label implements IJQueryWidget, IMyJQueryListener
 * {
 * 	private static final long serialVersionUID = 1L;
 * 
 * 	public MyJQueryLabel(String id)
 * 	{
 * 		super(id);
 * 	}
 * 
 * 	// Events //
 * 	protected void onInitialize()
 * 	{
 * 		super.onInitialize();
 * 
 * 		this.add(JQueryWidget.newWidgetBehavior(this));
 * 	}
 * 
 * 	public void onMyEvent(AjaxRequestTarget target)
 * 	{
 * 		// do something here
 * 	}
 * 
 * 	public JQueryBehavior newWidgetBehavior(String selector)
 * 	{
 * 		return new MyJQueryBehavior(selector, &quot;jquerymethod&quot;) {
 * 
 * 			private static final long serialVersionUID = 1L;
 * 
 * 			public void onMyEvent(AjaxRequestTarget target)
 * 			{
 * 				MyJQueryLabel.this.onMyEvent(target);
 * 			}
 * 		};
 * 	}
 * 
 * 	static abstract class MyJQueryBehavior extends JQueryBehavior implements IJQueryAjaxAware, IMyJQueryListener
 * 	{
 * 		private static final long serialVersionUID = 1L;
 * 		private JQueryAjaxBehavior onMyEventAjaxBehavior;
 * 
 * 		public MyJQueryBehavior(String selector, String method)
 * 		{
 * 			super(selector, method);
 * 		}
 * 
 * 		public void bind(Component component)
 * 		{
 * 			super.bind(component);
 * 
 * 			component.add(this.onMyEventAjaxBehavior = this.newJQueryAjaxBehavior(this));
 * 		}
 * 
 * 		// Events //
 * 		public void onConfigure(Component component)
 * 		{
 * 			super.onConfigure(component);
 * 
 * 			this.setOption(&quot;jqueryevent&quot;, this.onMyEventAjaxBehavior.getCallbackFunction());
 * 		}
 * 
 * 		public void onAjax(AjaxRequestTarget target, JQueryEvent event)
 * 		{
 * 			if (event instanceof MyEvent)
 * 			{
 * 				this.onMyEvent(target);
 * 			}
 * 		}
 * 
 * 		// Factory //
 * 		protected JQueryAjaxBehavior newJQueryAjaxBehavior(IJQueryAjaxAware source)
 * 		{
 * 			return new JQueryAjaxBehavior(source) {
 * 
 * 				private static final long serialVersionUID = 1L;
 * 
 * 				protected CallbackParameter[] getCallbackParameters()
 * 				{
 * 					return new CallbackParameter[] { CallbackParameter.context(&quot;event&quot;), CallbackParameter.context(&quot;ui&quot;) };
 * 				}
 * 
 * 				protected JQueryEvent newEvent()
 * 				{
 * 					return new MyEvent();
 * 				}
 * 			};
 * 		}
 * 
 * 		// Event Class //
 * 		protected static class MyEvent extends JQueryEvent
 * 		{
 * 		}
 * 	}
 * }
 * </pre>
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public abstract class JQueryAjaxBehavior extends AbstractDefaultAjaxBehavior
{
	private static final long serialVersionUID = 1L;

	private final IJQueryAjaxAware source;
	private final Duration duration;

	/**
	 * Constructor
	 * 
	 * @param source {@link Behavior} to which the event - returned by {@link #newEvent()} - will be broadcasted.
	 */
	public JQueryAjaxBehavior(IJQueryAjaxAware source)
	{
		this(source, Duration.NONE);
	}

	/**
	 * Constructor
	 * 
	 * @param source {@link Behavior} to which the event - returned by {@link #newEvent()} - will be broadcasted.
	 * @param duration {@link Duration}. If different than {@link Duration#NONE}, an {@link ThrottlingSettings} will be added with the specified {@link Duration}.
	 */
	public JQueryAjaxBehavior(IJQueryAjaxAware source, Duration duration)
	{
		this.source = source;
		this.duration = duration;
	}

	@Override
	protected void respond(AjaxRequestTarget target)
	{
		if (this.source != null)
		{
			this.source.onAjax(target, this.newEvent());
		}
	}

	/**
	 * Gets the {@link JQueryEvent} to be broadcasted to the {@link IJQueryAjaxAware} source when the behavior will respond
	 * 
	 * @return the {@link JQueryEvent}
	 */
	protected abstract JQueryEvent newEvent();

	// wicket 6.x //
	@Override
	protected void updateAjaxAttributes(AjaxRequestAttributes attributes)
	{
		super.updateAjaxAttributes(attributes);

		if (this.duration.compareTo(Duration.NONE) > 0)
		{
			attributes.setThrottlingSettings(new ThrottlingSettings("jquery-throttle", this.duration));
		}
	}

	/**
	 * Gets the {@link CallbackParameter}{@code s} that *may* be passed to {@link #getCallbackFunction(CallbackParameter...)}<br/>
	 * This is a convenience method that allows to define {@link CallbackParameter}{@code s} before the invocation of {@link #getCallbackFunction(CallbackParameter...)}.
	 *
	 * @return an array of {@link CallbackParameter}
	 * @see #getCallbackFunction()
	 */
	protected CallbackParameter[] getCallbackParameters()
	{
		return new CallbackParameter[] {};
	}

	/**
	 * Calls {@link #getCallbackFunction(CallbackParameter...)} by passing {@link CallbackParameter}{@code s} from {@link #getCallbackParameters()}
	 *
	 * @return the javascript function.
	 */
	public String getCallbackFunction()
	{
		return super.getCallbackFunction(this.getCallbackParameters()).toString();
	}

}

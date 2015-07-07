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
package org.wicketstuff.stateless.components;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.ajax.markup.html.IAjaxLink;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.link.StatelessLink;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.stateless.behaviors.StatelessAjaxEventBehavior;

/**
 * Just like {@link AjaxFallbackLink}, but stateless.
 * 
 * @author jfk
 */
public abstract class StatelessAjaxFallbackLink<T> extends StatelessLink<T>
        implements IAjaxLink
{

    private static final long serialVersionUID = -133600842398684777L;

    public StatelessAjaxFallbackLink(final String id)
    {
        super(id);

        add(new StatelessAjaxEventBehavior("click")
        {
            private static final long serialVersionUID = -8445395501430605953L;

            @Override
            protected void updateAjaxAttributes(AjaxRequestAttributes attributes)
            {
                super.updateAjaxAttributes(attributes);
                attributes.setPreventDefault(true);
                StatelessAjaxFallbackLink.this.updateAjaxAttributes(attributes);
            }

            @Override
            protected void onComponentTag(final ComponentTag tag)
            {
                // only render handler if link is enabled
                if (isEnabledInHierarchy())
                {
                    super.onComponentTag(tag);
                }
            }

            @Override
            protected void onEvent(final AjaxRequestTarget target)
            {
                onClick(target);
                target.add(StatelessAjaxFallbackLink.this);
            }

			@Override
			protected PageParameters getPageParameters()
			{
				return getPage().getPageParameters();
			}
        });
    }

    /**
     * 
     * @return call decorator to use or null if none
     */
    protected void updateAjaxAttributes(AjaxRequestAttributes attributes)
    {
    }
    
    /**
     * @see Link#onClick()
     */
    @Override
    public final void onClick()
    {
        onClick(null);
    }

    @Override
    protected void onComponentTag(ComponentTag tag)
    {
    	super.onComponentTag(tag);
    	
    	tag.remove("onclick");
    }
    
    /**
     * Callback for the onClick event. If ajax failed and this event was
     * generated via a normal link the target argument will be null
     * 
     * @param target
     *            ajax target if this linked was invoked using ajax, null
     *            otherwise
     */
    public abstract void onClick(final AjaxRequestTarget target);
}

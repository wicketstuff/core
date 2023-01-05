/**
 * Copyright 2012 Vineet Semwal
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;

import java.util.Map;

/**
 * Synchronizer basically adds components(repeater's items) and scripts to the the AjaxRequestTarget after
 * checking parent is not added to AjaxRequestTarget .If parent is added scripts and
 * items are not added to the AjaxRequestTarget
 *
 * @author Vineet Semwal
 */
public class DefaultSynchronizer extends Synchronizer implements AjaxRequestTarget.IListener {
    public DefaultSynchronizer(final MarkupContainer parent,
                               final IPartialPageRequestHandler requestHandler) {
        super(parent, requestHandler);
    }

    @Override
    public void onBeforeRespond(final Map<String, Component> map, final AjaxRequestTarget target) {
        submit();
    }

    @Override
    public void onAfterRespond(Map<String, Component> map, AjaxRequestTarget target) {
    }

    @Override
    public void updateAjaxAttributes(AbstractDefaultAjaxBehavior behavior, AjaxRequestAttributes attributes) {
    }


}
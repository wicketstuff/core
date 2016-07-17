/*
 * Copyright 2014 Decebal Suiu
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this work except in compliance with
 * the License. You may obtain a copy of the License in the LICENSE file, or at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.wicketstuff.dashboard.web;

import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.resource.ContextRelativeResource;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.util.lang.Args;
import org.wicketstuff.dashboard.Widget;
import org.wicketstuff.dashboard.WidgetAction;

/**
 * @author Decebal Suiu
 */
public abstract class AbstractWidgetAction implements WidgetAction {
	private static final long serialVersionUID = 1L;
	protected Widget widget;
    protected IModel<String> tooltip;
    private Class<?> imageScope;
    private String imageName;

    public AbstractWidgetAction(Widget widget) {
        this.widget = widget;
    }

    public Widget getWidget() {
        return widget;
    }

    @Override
    public IModel<String> getTooltip() {
        return tooltip;
    }

    public void setTooltip(IModel<String> tooltip) {
        this.tooltip = tooltip;
    }

    /**
     * If you use this method than the image name is relative to scope.
     *
     * @param scope
     * @param name
     */
    public void setImage(Class<?> scope, String name) {
        imageScope = scope;
        imageName = name;
    }

    /**
     * If you use this method than the image name is relative to context.
     *
     * @param name
     */
    public void setImage(String name) {
        setImage(null, name);
    }

    @Override
    public Image getImage(String id) {
        Args.notNull(imageName, "imageName");

        if (imageScope != null) {
            return new Image(id, new PackageResourceReference(imageScope, imageName));
        }

        return new Image(id, new ContextRelativeResource(imageName));
    }

}

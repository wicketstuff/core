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

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxCallListener;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.StringResourceModel;
import org.wicketstuff.dashboard.Widget;
import org.wicketstuff.dashboard.web.util.AjaxConfirmLink;

/**
 * Utility class which includes set of default widgets
 * @author Decebal Suiu
 */
public class DefaultWidgetActions {

    public static class Refresh extends AbstractWidgetAction {

        private static final long serialVersionUID = 1L;

        public Refresh(Widget widget) {
            super(widget);

            tooltip = new ResourceModel("refresh");

            setImage(DefaultWidgetActions.class, "res/refresh.gif");
        }

        @Override
        public AbstractLink getLink(String id) {
            return new AjaxLink<Void>(id) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onClick(AjaxRequestTarget target) {
                    WidgetView widgetView = findParent(WidgetPanel.class).getWidgetView();
                    target.add(widgetView);
                }

            };
        }

    }

    public static class Delete extends AbstractWidgetAction {

        private static final long serialVersionUID = 1L;

        public Delete(Widget widget) {
            super(widget);

            tooltip = new ResourceModel("delete");

            setImage(DefaultWidgetActions.class, "res/delete.gif");
        }

        @Override
        public AbstractLink getLink(String id) {
            AjaxConfirmLink<Void> deleteLink = new AjaxConfirmLink<Void>(id) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onClick(AjaxRequestTarget target) {
                    send(getPage(), Broadcast.BREADTH, new DashboardEvent(target, DashboardEvent.EventType.WIDGET_REMOVED, widget));
                    // the widget is removed from ui with javascript (with a IAjaxCallListener) -> see getAjaxCallListener()
                }

                @Override
                protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
                    super.updateAjaxAttributes(attributes);

                    attributes.getAjaxCallListeners().add(new AjaxCallListener() {

                        private static final long serialVersionUID = 1L;

                        @Override
                        public CharSequence getSuccessHandler(Component component) {
                            return "$('#widget-" + widget.getId() + "').remove();";
                        }

                    });
                }

                @Override
                protected void onInitialize() {
                	super.onInitialize();
                    IModel<String> resourceModel = new StringResourceModel("deleteAsk", this, Model.of(widget.getTitle()));
                    setConfirmMessage(resourceModel.getObject());
                }
            };

            return deleteLink;
        }

    }

    public static class Settings extends AbstractWidgetAction {

        private static final long serialVersionUID = 1L;

        public Settings(Widget widget) {
            super(widget);

            tooltip = new ResourceModel("settings");

            setImage(DefaultWidgetActions.class, "res/edit.png");
        }

        @Override
        public AbstractLink getLink(String id) {
            return new AjaxLink<Void>(id) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onClick(AjaxRequestTarget target) {
                    if (widget.hasSettings()) {
                        WidgetPanel widgetPanel = findParent(WidgetPanel.class);
                        Panel settingsPanel = widgetPanel.getSettingsPanel();
                        settingsPanel.setVisible(true);
                        target.add(settingsPanel);
                    }
                }

            };
        }

    }

}

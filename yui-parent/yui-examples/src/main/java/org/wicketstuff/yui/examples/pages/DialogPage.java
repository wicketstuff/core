/*
 * $Id$
 * $Date$
 * 
 * ==============================================================================
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.wicketstuff.yui.examples.pages;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.wicketstuff.yui.examples.WicketExamplePage;
import org.wicketstuff.yui.markup.html.dialog.Dialog;
import org.wicketstuff.yui.markup.html.dialog.DialogSettings;
import org.wicketstuff.yui.markup.html.dialog.DialogSettings.UnderlayType;

/**
 * Page that displays the calendar component of the Yahoo UI library.
 * 
 * @author Eelco Hillenius
 * @author Josh
 */
public class DialogPage extends WicketExamplePage
{
    private Dialog d;
	/**
	 * Construct.
	 */
	public DialogPage()
	{
        DialogSettings settings = new DialogSettings();
        settings.setModal(true);
        settings.setDraggable(true);
        settings.setVisible(true);
        settings.setUnderlay(UnderlayType.SHADOW);
        settings.setVisible(false);
        add(d = new Dialog("dialog", null, settings){

            @Override
            public Panel createContent(String panelId) {
                return new DialogPanel(panelId);
            }
            
            @Override
            public Model getTitle() {
                return new Model("My title");
            }
            
        });
        
        add(new AjaxLink("show"){

            @Override
            public void onClick(AjaxRequestTarget target) {
                d.show(target);
            }
            
        });
        
        add(new AjaxLink("hide"){

            @Override
            public void onClick(AjaxRequestTarget target) {
                d.hide(target);
            }
            
        });
	}
    
    
    public class DialogPanel extends Panel{
        public DialogPanel(String id) {
            super(id, new Model());
            add(new Label("label", new Model("a value in my label")));
        }
    }

}

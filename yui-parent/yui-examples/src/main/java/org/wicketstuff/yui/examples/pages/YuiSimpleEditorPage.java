/*
 *  Copyright 2008 korbinianbachl.
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  under the License.
 */

package org.wicketstuff.yui.examples.pages;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;
import org.wicketstuff.yui.examples.WicketExamplePage;
import org.wicketstuff.yui.markup.html.editor.YuiSimpleEditor;

/**
 *
 * @author korbinianbachl
 */
public class YuiSimpleEditorPage extends WicketExamplePage {
    
    public YuiSimpleEditorPage() {
        Model model = new Model("This <i>is</i> some <b>content</b> - feel free to edit it!");
        Form form = new Form("someForm");
        form.add(new YuiSimpleEditor("yuiSimpleEditor", model));
        add(form);
    }

}

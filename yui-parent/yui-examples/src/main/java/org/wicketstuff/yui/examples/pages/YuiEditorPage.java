/*
 * YuiEditorPage.java
 *
 * 
 */

package org.wicketstuff.yui.examples.pages;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;
import org.wicketstuff.yui.examples.WicketExamplePage;
import org.wicketstuff.yui.markup.html.editor.YuiEditor;

/**
 *
 * @author korbinianbachl
 */
public class YuiEditorPage extends WicketExamplePage {
    
    /**
     * Creates a new instance of YuiEditorPage
     */
    public YuiEditorPage() {
        Model model = new Model("This is some <b>content</b> - feel free to edit it!");
        Form form = new Form("someForm");
        form.add(new YuiEditor("yuiEditor", model));
        add(form);
    }
    
}

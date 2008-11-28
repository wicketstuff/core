/*
    This file is part of Wicket-Contrib-TinyMce. See
    <http://http://wicketstuff.org/confluence/display/STUFFWIKI/wicket-contrib-tinymce>

    Wicket-Contrib-TinyMce is free software: you can redistribute it and/
    or modify it under the terms of the GNU Lesser General Public License
    as published by the Free Software Foundation, either version 3 of the
    License, or (at your option) any later version.

    Wicket-Contrib-TinyMce is distributed in the hope that it will be
    useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with Wicket-Contrib-TinyMce.  If not, see
    <http://www.gnu.org/licenses/>.
 */
package wicket.contrib.tinymce;

import java.util.UUID;

import org.apache.wicket.Component;
import org.apache.wicket.Request;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import wicket.contrib.tinymce.settings.WicketSavePlugin;

/**
 * This behavior adds saving functionality to an editor for in-place editing of content. In most cases you will want to
 * use {@link InPlaceEditComponent} instead of this class directly.
 */
public class InPlaceSaveBehavior extends AbstractDefaultAjaxBehavior {
    private static final Logger LOG = LoggerFactory.getLogger(InPlaceSaveBehavior.class);

    private static final String PARAM_HTMLCONT = "htmlcont";
    private String saveEditorScriptName;
    private String cancelEditorScriptName;
    private String additionalJavaScript;

    public InPlaceSaveBehavior() {}

    /**
     * @param additionalJavaScript
     *            Additional javascript code that will be appended to the save and cancel callback functions. You can
     *            use this to e.g. show or hide buttons based on the state of the in-place-edit component.
     */
    public void setAdditionalJavaScript(String additionalJavaScript) {
        this.additionalJavaScript = additionalJavaScript;
    }

    protected final void respond(AjaxRequestTarget target) {
        Request request = RequestCycle.get().getRequest();
        String newContent = request.getParameter(PARAM_HTMLCONT);
        newContent = onSave(newContent);
        newContent = onSave(target, newContent);
        Component component = getComponent();
        component.getModel().setObject(newContent);
        target.addComponent(component);
    }

    /**
     * Returns the name of the JavaScript function that handles the save event. (Replace the editor with the saved
     * content in the original component).
     * 
     * @return Name of the javascript function, used by WicketSave plugin, see {@link WicketSavePlugin}
     */
    public final String getSaveCallbackName() {
        if (saveEditorScriptName == null) {
            String uuid = UUID.randomUUID().toString().replace('-', '_');
            saveEditorScriptName = "savemce_" + uuid;
        }
        return saveEditorScriptName;
    }

    public final String getCancelCallbackName() {
        if (cancelEditorScriptName == null) {
            String uuid = UUID.randomUUID().toString().replace('-', '_');
            cancelEditorScriptName = "cancelmce_" + uuid;
        }
        return cancelEditorScriptName;
    }

    public Component getTheComponent() {
        Component result = getComponent();
        if (result == null)
            throw new IllegalArgumentException("save behavior not yet bound to a component");
        return result;
    }

    /**
     * @deprecated Override onSave(AjaxRequestTarget,String) instead
     */
    protected String onSave(String newContent) {
        return newContent;
    }

    /**
     * This method gets called before the new content as received from the TinyMce editor is pushed to the website.
     * Override it to add additional processing to the content.
     * 
     * @param newContent
     *            The content as received from the editor.
     * @return The content that will be pushed back to your website.
     */
    protected String onSave(AjaxRequestTarget target, String newContent) {
        return newContent;
    }

    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        // Don't pass an id, since every EditableComponent will have its own
        // submit script:
        response.renderJavascript(createSaveScript(), null);
        response.renderJavascript(createCancelScript(), null);
    }

    private final String createSaveScript() {
        String callback = getWicketPostScript();
        String markupId = getComponent().getMarkupId();
        return "function " + getSaveCallbackName() + "(inst) {\n" //
                + " var content = inst.getContent();\n" //
                + " inst.setContent(inst.settings.wicket_updating_mess);\n" //
                + " tinyMCE.execCommand('mceRemoveControl',false,'" + markupId + "');\n" //
                + " " + callback + "\n" //
                + (additionalJavaScript == null ? "" : (additionalJavaScript + "\n"))//
                + "}";
    }

    private final String createCancelScript() {
        String markupId = getComponent().getMarkupId();
        return "function " + getCancelCallbackName() + "(inst) {\n" //
                + (additionalJavaScript == null ? "" : (additionalJavaScript + "\n"))//
                + "}";
    }

    private final String getWicketPostScript() {
        return generateCallbackScript(
                "wicketAjaxPost('" + getCallbackUrl(false) + "', Wicket.Form.encode('" + PARAM_HTMLCONT
                        + "') + '=' + Wicket.Form.encode(content) + '&'").toString();
    }
}

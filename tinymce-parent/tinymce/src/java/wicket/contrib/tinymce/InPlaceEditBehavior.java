/**
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

import java.util.Collections;
import java.util.UUID;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.behavior.IBehavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.IHeaderResponse;

import wicket.contrib.tinymce.settings.TinyMCESettings;
import wicket.contrib.tinymce.settings.TinyMCESettings.Mode;

/**
 * This behavior adds in-place editing functionality to wicket components. In most cases you will want to use
 * {@link InPlaceEditComponent} instead of this class directly.
 */
public class InPlaceEditBehavior extends TinyMceBehavior {
    private String startEditorScriptName;

    /**
     * Construct in-place-editing behavior to a component. It makes the content of the component editable with a TinyMce
     * WYSIWYG editor.
     * 
     * @param settings
     *            TinyMceSettings for the editor when opened.
     * @param triggerComponent
     *            Component that will get an onclick event to make the component that this behavior is added to
     *            editable. Can be the editable component itself, but can also be another component, e.g. a button. If
     *            set to null, you will have to start the editable state via a call to the javascriptfunction with name:
     *            {@link #getStartEditorScriptName()}
     */
    public InPlaceEditBehavior(TinyMCESettings settings, Component triggerComponent) {
        super(settings);
        if (triggerComponent != null)
            triggerComponent.add(createTriggerBehavior());
    }

    private IBehavior createTriggerBehavior() {
        return new AbstractBehavior() {
            public void onComponentTag(Component component, ComponentTag tag) {
                super.onComponentTag(component, tag);
                tag.put("onclick", getStartEditorScriptName() + "();");
            }
        };
    }

    protected String getRenderOnDomReadyJavascript(IHeaderResponse response) {
        return null;
    }

    protected String getRenderJavascript(IHeaderResponse response) {
        return "" //
                + "function " + getStartEditorScriptName() + "() {" //
                + getAddTinyMceSettingsScript(Mode.none, Collections.EMPTY_LIST) //
                + " tinyMCE.execCommand('mceAddControl',true,'" + getComponent().getMarkupId(true) + "');" //
                + "}";
    }

    /**
     * @return The name of the (no-argument) JavaScript that will replace the component that is bound to this behavior
     *         with a TinyMce editor.
     */
    public final String getStartEditorScriptName() {
        if (startEditorScriptName == null) {
            String uuid = UUID.randomUUID().toString().replace('-', '_');
            startEditorScriptName = "startmce_" + uuid;
        }
        return startEditorScriptName;
    }
}

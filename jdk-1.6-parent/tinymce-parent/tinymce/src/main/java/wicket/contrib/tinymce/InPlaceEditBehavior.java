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
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnEventHeaderItem;

import wicket.contrib.tinymce.settings.TinyMCESettings;
import wicket.contrib.tinymce.settings.TinyMCESettings.Mode;

/**
 * This behavior adds in-place editing functionality to wicket components. In
 * most cases you will want to use {@link InPlaceEditComponent} instead of this
 * class directly.
 */
public class InPlaceEditBehavior extends TinyMceBehavior
{
	private static final long serialVersionUID = 1L;	
	
	@Override
	protected HeaderItem wrapTinyMceSettingsScript(String settingScript,
			Component component) {
		OnEventHeaderItem headerItem = new OnEventHeaderItem("'" + component.getMarkupId() + "'", "click", settingScript);
		return headerItem;
	}
}

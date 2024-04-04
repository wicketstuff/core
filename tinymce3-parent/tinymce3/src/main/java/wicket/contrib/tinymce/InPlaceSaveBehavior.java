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
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.attributes.CallbackParameter;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.cycle.RequestCycle;

import wicket.contrib.tinymce.settings.WicketSavePlugin;

/**
 * This behavior adds saving functionality to an editor for in-place editing of content. In most cases you will want to use
 * {@link InPlaceEditComponent} instead of this class directly.
 */
public class InPlaceSaveBehavior extends AbstractDefaultAjaxBehavior
{

	private static final long serialVersionUID = 1L;
	private static final String PARAM_HTMLCONT = "htmlcont";
	private String saveEditorScriptName;
	private String cancelEditorScriptName;
	private String additionalJavaScript;

	public InPlaceSaveBehavior()
	{
	}

	/**
	 * @param additionalJavaScript Additional javascript code that will be appended to the save and cancel callback functions. You
	 * can use this to e.g. show or hide buttons based on the state of the in-place-edit component.
	 */
	public void setAdditionalJavaScript(String additionalJavaScript)
	{
		this.additionalJavaScript = additionalJavaScript;
	}

	@Override
	protected final void respond(AjaxRequestTarget target)
	{
		Request request = RequestCycle.get().getRequest();
		String newContent = request.getRequestParameters()
			.getParameterValue(PARAM_HTMLCONT).toString();
		newContent = onSave(target, newContent);
		Component component = getComponent();
		IModel defaultModel = component.getDefaultModel();
		defaultModel.setObject(newContent);
		target.add(component);
	}

	/**
	 * Returns the name of the JavaScript function that handles the save event. (Replace the editor with the saved content in the
	 * original component).
	 *
	 * @return Name of the javascript function, used by WicketSave plugin, see {@link WicketSavePlugin}
	 */
	public final String getSaveCallbackName()
	{
		if (saveEditorScriptName == null)
		{
			String uuid = UUID.randomUUID().toString().replace('-', '_');
			saveEditorScriptName = "savemce_" + uuid;
		}
		return saveEditorScriptName;
	}

	public final String getCancelCallbackName()
	{
		if (cancelEditorScriptName == null)
		{
			String uuid = UUID.randomUUID().toString().replace('-', '_');
			cancelEditorScriptName = "cancelmce_" + uuid;
		}
		return cancelEditorScriptName;
	}

	public Component getTheComponent()
	{
		Component result = getComponent();
		if (result == null)
		{
			throw new IllegalArgumentException(
				"save behavior not yet bound to a component");
		}
		return result;
	}

	/**
	 * This method gets called before the new content as received from the TinyMce editor is pushed to the website. Override it to
	 * add additional processing to the content.
	 *
	 * @param target the current ajax request handler
	 * @param newContent The content as received from the editor.
	 * @return The content that will be pushed back to your website.
	 */
	protected String onSave(AjaxRequestTarget target, String newContent)
	{
		return newContent;
	}

	public String getSaveCallback()
	{
		CharSequence callback = getWicketPostScript();
		String markupId = getComponent().getMarkupId();
		return "function "
			+ getSaveCallbackName()
			+ "(inst) {\n" //
			+ " var content = inst.getContent();\n" //
			+ " inst.setContent(inst.settings.wicket_updating_mess);\n" //
			+ " tinyMCE.execCommand('mceRemoveControl',false,'"
			+ markupId
			+ "');\n" //
			+ " "
			+ callback
			+ "\n" //
			+ (additionalJavaScript == null ? ""
			: (additionalJavaScript + "\n"))//
			+ "}";
	}

	public String getCancelCallback()
	{
		return "function " + getCancelCallbackName()
			+ "(inst) {\n" //
			+ (additionalJavaScript == null ? ""
			: (additionalJavaScript + "\n"))//
			+ "}";
	}

	private CharSequence getWicketPostScript()
	{
		return "var sendContent = " + getCallbackFunction(CallbackParameter.explicit("content")) + "sendContent(content);";
	}

	@Override
	protected void updateAjaxAttributes(AjaxRequestAttributes attributes)
	{
		super.updateAjaxAttributes(attributes);
		attributes.setMethod(AjaxRequestAttributes.Method.POST);
		attributes.getDynamicExtraParameters()
			.add("return {'" + PARAM_HTMLCONT + "': attrs.ep.content}");
	}
}

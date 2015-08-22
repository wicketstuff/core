/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.yui.markup.html.cropp;

import org.apache.wicket.Component;
import org.apache.wicket.Response;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.util.string.JavascriptUtils;

/**
 * Yui imagecropper is a based on {@link http
 * ://developer.yahoo.com/yui/imagecropper/}
 * 
 * @author wickeria at gmail.com
 * 
 */
public abstract class ImageCropper extends Panel {

	private static final long serialVersionUID = 1L;

	private final CallbackBehavior callbackBehavior;

	public ImageCropper(String id, IModel<String> model, final YuiImageCropperSettings settings) {
		super(id, model);

		setOutputMarkupId(true);

		add(new AbstractBehavior() {
			private static final long serialVersionUID = 1L;

			@Override
			public void onRendered(Component component) {
				StringBuffer buffer = new StringBuffer();
				buffer.append("top = " + settings.getTop() + ";");
				buffer.append("left = " + settings.getLeft() + ";");
				buffer.append("height = " + settings.getHeight() + ";");
				buffer.append("width = " + settings.getWidth() + ";");
				buffer.append("var " + getCropperVar() + " = new YAHOO.widget.ImageCropper('" + getImageId() + "', {");
				buffer.append("initialXY: [left,top],");
				buffer.append("keyTick: " + settings.getKeyTick() + ",");
				buffer.append("shiftKeyTick: " + settings.getShiftKeyTick() + ",");
				buffer.append("minHeight: " + settings.getMinHeight() + ",");
				buffer.append("minWidth: " + settings.getMinWidth() + ",");
				buffer.append("ratio: " + settings.isRatio() + ",");
				buffer.append("initHeight: height,");
				buffer.append("initWidth: width");
				buffer.append("});");

				buffer.append(getCropperVar() + ".on('moveEvent', function() {");
				buffer.append("var region = " + getCropperVar() + ".getCropCoords();");
				buffer.append("top = region.top;");
				buffer.append("left = region.left;");
				buffer.append("height = region.height;");
				buffer.append("width = region.width;");
				buffer.append("});");

				Response response = component.getResponse();
				response.write(JavascriptUtils.SCRIPT_OPEN_TAG);
				response.write("YAHOO.util.Event.onContentReady('" + getImageId() + "',function() {"
						+ buffer.toString() + "});");
				response.write(JavascriptUtils.SCRIPT_CLOSE_TAG);
			}
		});

		add(callbackBehavior = new CallbackBehavior() {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onCropInternal(int top, int left, int height, int width, AjaxRequestTarget target) {
				onCrop(top, left, height, width, target);
			}
		});
		add(newLinkComponent("link").add(newLabelComponent("linkLabel")));
		add(new AjaxLink<Void>("cancel") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				onCancel(target);
			}
		});
	}

	protected IModel<String> getLinkLabelModel() {
		return new ResourceModel("apply");
	}

	protected Component newLabelComponent(String id) {
		return new Label(id, getLinkLabelModel());
	}

	protected WebMarkupContainer newLinkComponent(String id) {
		return new AjaxLink<Void>(id) {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				target.prependJavascript("" + callbackBehavior.getEventHandler());
			}
		};
	}

	protected abstract void onCrop(int top, int left, int height, int width, AjaxRequestTarget target);

	protected abstract void onCancel(AjaxRequestTarget target);

	protected String getCropperVar() {
		return "var_" + getMarkupId();
	}

	protected String getImageId() {
		return getDefaultModelObjectAsString();
	}

	private static abstract class CallbackBehavior extends AjaxEventBehavior {

		private static final long serialVersionUID = 1L;

		public CallbackBehavior() {
			super("callbackBehavior");
		}

		@Override
		protected void onComponentTag(final ComponentTag tag) {
		}

		@Override
		public CharSequence getEventHandler() {
			return super.getEventHandler();
		}

		@Override
		protected CharSequence generateCallbackScript(CharSequence partialCall) {
			CharSequence script = super.generateCallbackScript("wicketAjaxGet('" + getCallbackUrl(false)
					+ "&top='+top+'&left='+left+'&height='+height+'&width='+width");
			// + "'");

			return script;
		}

		@Override
		protected final void onEvent(AjaxRequestTarget target) {
			String top = getComponent().getRequest().getParameter("top");
			String left = getComponent().getRequest().getParameter("left");
			String height = getComponent().getRequest().getParameter("height");
			String width = getComponent().getRequest().getParameter("width");
			onCropInternal(Integer.parseInt(top), Integer.parseInt(left), Integer.parseInt(height), Integer
					.parseInt(width), target);
		}

		protected abstract void onCropInternal(int top, int left, int height, int width, AjaxRequestTarget target);

	}

}

/**
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
package org.wicketstuff.yui.markup.html.container;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.util.string.Strings;
import org.wicketstuff.yui.markup.html.container.YuiPanel;

/**
 * Facilitates displaying OS-like windows in the browser based on YUI panels.
 * <p>
 * Typical usage pattern:
 * 
 * <pre>
 * HTML
 * &lt;div wicket:id=&quot;windows&quot;&gt;&lt;/div&gt;
 * </pre>
 * 
 * It is probably best to add the div at the end of the page.
 * 
 * <pre>
 * JAVA
 * Windows windows = new Windows(&quot;windows&quot;);
 * add(windows);
 * 
 * windows.addWindow(new Window(&quot;SomePanelTitleKey&quot;) {
 * 	public WindowDimension getDimension() {
 * 		return new WindowDimension(760, 300);
 * 	}
 * 
 * 	public Component newBody(String id) {
 * 		return new SomePanel(id);
 * 	}
 * });
 * </pre>
 * 
 * Optionally add an override of {@link YuiWindow#getAdditionalOpts()},
 * {@link YuiWindow#newFooter(String)} or {@link YuiWindow#newHeader(String)}. Or,
 * instead of extending {@link YuiWindow} implement {@link IYuiWindow} directly.
 * <p>
 * 
 * @author Erik van Oosten
 */
public class YuiWindows extends Panel {

	/**
	 * @param id component id
	 */
	public YuiWindows(String id) {
		super(id);
		setOutputMarkupId(true);
		add(new RepeatingView("windows"));
	}

	/**
	 * Add a window.
	 * <p>
	 * If this is called during an AJAX call, the changes are automatically
	 * added to the ajax request target.
	 * 
	 * @param window the new window (not null)
	 * @return the component id of the new window (used to remove it)
	 */
	public String addWindow(final IYuiWindow window) {
		YuiPanel yuiPanel = new YuiPanel(getRepeater().newChildId()) {
			@Override
			protected String getOpts() {
				return newOptions(window.getDimension(), window.getAdditionalOpts());
			}

			@Override
			protected String getResizeOpts() {
				return "{handles : ['br'], proxy: true, status: true, animate: false}";
			}

			@Override
			protected Component newBodyPanel(String id) {
				return window.newBody(id);
			}

			@Override
			protected Component newFooterPanel(String id) {
				return window.newFooter(id);
			}

			@Override
			protected Component newHeaderPanel(String id) {
				return window.newHeader(id);
			}

			@Override
			protected void onHide(AjaxRequestTarget target, String type) {
				getRepeater().remove(this);
				removeElementFromDom(target, getMarkupId());
			}
		};
		yuiPanel.setOutputMarkupId(true);
		yuiPanel.setUsesOverlayManager(true);
		getRepeater().add(yuiPanel);

		AjaxRequestTarget target = AjaxRequestTarget.get();
		if (target != null) {
			String createDiv = "(function(){var c=document.createElement('div');c.id='%s';Wicket.$('%s').appendChild(c)})();";
			target.prependJavascript(String.format(createDiv, yuiPanel.getMarkupId(), getMarkupId()));
			target.addComponent(yuiPanel);
			// TODO: find out why javascript of yuiPanel's header contributor
			// fails during the AJAX update in FireFox, works fine in IE.
		}

		return yuiPanel.getId();
	}

	/**
	 * Removes a window.
	 * <p>
	 * If this is called during an AJAX call, the changes are automatically
	 * added to the ajax request target.
	 */
	public void removeWindow(String id) {
		String markupId = getRepeater().get(id).getMarkupId();
		getRepeater().remove(id);

		AjaxRequestTarget target = AjaxRequestTarget.get();
		if (target != null) {
			removeElementFromDom(target, markupId);
		}
	}

	private RepeatingView getRepeater() {
		// There is only 1 child.
		return (RepeatingView)get(0);
	}

	private void removeElementFromDom(AjaxRequestTarget target, String yuiPanelMarkupId) {
		target.appendJavascript(String.format("Wicket.$('%s').removeChild(Wicket.$('%s'));", getMarkupId(), yuiPanelMarkupId));
	}

	/**
	 * @param windowDimension initial dimension/position (not null)
	 * @param otherOpts a comma separated string of options in JSON notation,
	 *            see http://developer.yahoo.com/yui/container/panel/#config or
	 *            null for none
	 * @return an options string
	 */
	private String newOptions(YuiWindowDimension windowDimension, String otherOpts) {
		return String.format(OPTS, newSizeOptions(windowDimension) + (Strings.isEmpty(otherOpts) ? "" : ", " + otherOpts));
	}

	private String newSizeOptions(YuiWindowDimension windowDimension) {
		if (windowDimension == null) {
			throw new NullPointerException("windowDimension must not be null");
		}
		StringBuilder sb = new StringBuilder();
		int[] topLeft = windowDimension.getTopLeft();
		if (topLeft != null) {
			sb.append(" y: '").append(topLeft[0]).append("px', x: '").append(topLeft[1]).append("px', ");
		}
		int[] widthHeight = windowDimension.getWidthHeight();
		sb.append("width: '").append(widthHeight[0]).append("px', height: '").append(widthHeight[1]).append("px'");
		return sb.toString();
	}

	private static final String OPTS = "{"
			+ "close : true, "
			+ "draggable : true, "
			+ "underlay : 'shadow', "
			+ "modal : false, "
			+ "visible : true, "
			+ "effect : {effect:YAHOO.widget.ContainerEffect.FADE,duration:0.25}, "
			+ "monitorresize : true, "
			+ "constraintoviewport : true, "
			+ "iframe : false, "
			+ "autofillheight :'body', "
			// Additional options
			+ "%s "
			+ "}";
}

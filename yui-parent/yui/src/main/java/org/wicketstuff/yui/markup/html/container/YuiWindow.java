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
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;

/**
 * A simple implementation of IWindow.
 * <p>
 * Assumes that the header and footer are simple strings that can be retrieved
 * from some model (e.g. a ResourceModel).
 * <p>
 * Implement {@link IYuiWindow} directly for more advanced options.
 * 
 * @author Erik van Oosten
 */
public abstract class YuiWindow implements IYuiWindow {

	private IModel<String> titleModel;
	private IModel<String> footerModel;

	/**
	 * Constructor for footer less window with fixed title.
	 * 
	 * @param titleKey resource key for the title
	 */
	public YuiWindow(String titleKey) {
		this(new ResourceModel(titleKey), null);
	}

	/**
	 * Ctor.
	 * 
	 * @param titleModel model for the title or null for no title
	 * @param footerModel model for the footer or null for no footer
	 */
	public YuiWindow(IModel<String> titleModel, IModel<String> footerModel) {
		this.titleModel = titleModel;
		this.footerModel = footerModel;
	}

	/**
	 * @see IYuiWindow.chess.wicket.yui.IWindow#getAdditionalOpts()
	 */
	public String getAdditionalOpts() {
		return null;
	}

	/**
	 * @see IYuiWindow.chess.wicket.yui.IWindow#newFooter(java.lang.String)
	 */
	public Component newFooter(String id) {
		return footerModel == null ? new EmptyPanel(id) : new Label(id, footerModel);
	}

	/**
	 * @see IYuiWindow.chess.wicket.yui.IWindow#newHeader(java.lang.String)
	 */
	public Component newHeader(String id) {
		return titleModel == null ? new EmptyPanel(id) : new Label(id, titleModel);
	}

}

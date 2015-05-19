package org.wicketstuff.foundation.tab;

import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.extensions.markup.html.tabs.TabbedPanel;
import org.apache.wicket.model.IModel;

public class FoundationTab<T extends ITab> extends TabbedPanel<T> {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private boolean verticalTab = false;

	public FoundationTab(String id, List<T> tabs) {
        super(id, tabs);
    }

    public FoundationTab(String id, List<T> tabs, IModel<Integer> model) {
        super(id, tabs, model);
    }

    @Override
    protected String getSelectedTabCssClass() {
        return "active";
    }
    
    @Override
    protected String getTabContainerCssClass() {
    	return "tabs";
    }
    
    @Override
    protected void onInitialize() {
    	super.onInitialize();
    	
    	if (verticalTab) {
			get("tabs-container").add(AttributeModifier.append("class", "vertical"));
		}
    }

	/**
	 * @return the verticalTab
	 */
	public boolean isVerticalTab() {
		return verticalTab;
	}

	/**
	 * @param verticalTab the verticalTab to set
	 * @return 
	 */
	public FoundationTab<T> setVerticalTab(boolean verticalTab) {
		this.verticalTab = verticalTab;
		return this;
	}
}

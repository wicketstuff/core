/*
 * 
 * ==============================================================================
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
package org.wicketstuff.openlayers.api.control;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mocleiri
 *
 */
public class DefaultSelectFeatureControlOptions implements
		ISelectFeatureControlOptions {
	private static final Logger log = LoggerFactory
			.getLogger(DefaultSelectFeatureControlOptions.class);
	private boolean multipleSelect;
	private boolean clickout;
	private boolean hover;
	private boolean highlightOnly;
	private boolean boxSelection;

	
	/**
	 * @param multipleSelect
	 * @param clickout
	 * @param hover
	 * @param highlightOnly
	 * @param boxSelection
	 */
	public DefaultSelectFeatureControlOptions(boolean multipleSelect,
			boolean clickout, boolean hover, boolean highlightOnly,
			boolean boxSelection) {
		super();
		this.multipleSelect = multipleSelect;
		this.clickout = clickout;
		this.hover = hover;
		this.highlightOnly = highlightOnly;
		this.boxSelection = boxSelection;
	}

	/* (non-Javadoc)
	 * @see org.wicketstuff.openlayers.api.control.ISelectFeatureControlOptions#isMultipleSelectEnabled()
	 */
	public boolean isMultipleSelectEnabled() {
		return multipleSelect;
	}

	
	/* (non-Javadoc)
	 * @see org.wicketstuff.openlayers.api.control.ISelectFeatureControlOptions#isClickoutEnabled()
	 */
	public boolean isClickoutEnabled() {
		return clickout;
	}

	/* (non-Javadoc)
	 * @see org.wicketstuff.openlayers.api.control.ISelectFeatureControlOptions#isToggleByMouseEnabled()
	 */
	public boolean isToggleByMouseEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.wicketstuff.openlayers.api.control.ISelectFeatureControlOptions#isHoverEnabled()
	 */
	public boolean isHoverEnabled() {
		return hover;
	}

	/* (non-Javadoc)
	 * @see org.wicketstuff.openlayers.api.control.ISelectFeatureControlOptions#isHighlightOnlyEnabled()
	 */
	public boolean isHighlightOnlyEnabled() {
		return highlightOnly;
	}

	/* (non-Javadoc)
	 * @see org.wicketstuff.openlayers.api.control.ISelectFeatureControlOptions#isBoxSelectionEnabled()
	 */
	public boolean isBoxSelectionEnabled() {
		return boxSelection;
	}

	
}

/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributed by United Services Automotive Association (USAA)
 */
package org.wicketstuff.webflow.state;
/*******************************************************************************
 * File: WicketEndState
 ******************************************************************************/

import org.springframework.webflow.engine.EndState;
import org.springframework.webflow.engine.Flow;
import org.springframework.webflow.engine.RequestControlContext;
import org.springframework.webflow.execution.FlowExecutionException;
import org.wicketstuff.webflow.FlowUtils;


/**
 * End State that includes attributes necessary for the display of a progress bar in page flow /panel flow
 * based application.
 *
 * @author Clint Checketts, Florian Braun, Doug Hall, Subramanian Murali
 * @version $Id: $
 */
public class WicketEndState extends EndState implements IProgressBarState
{
	//Attribute that represents the text to be displayed in the progress bar for this view state
	private String progressBarText;
	
	//Attribute that represents the step number for this view state in the progress bar
	private int stepNumber;
	
	/**
	 * Constructor.
	 *
	 * @param flow - Owning flow.
	 * @param id - State identifier that is unique to the flow.
	 * @throws java.lang.IllegalArgumentException if any.
	 * @param progressText a {@link java.lang.String} object.
	 * @param stepNumber a int.
	 */
	public WicketEndState(Flow flow, String id, String progressText, int stepNumber) throws IllegalArgumentException
	{
		super(flow, id);
		this.progressBarText = progressText;
		this.stepNumber = stepNumber;
	}
	
	/** {@inheritDoc} */
	@Override
	protected void doEnter(RequestControlContext context) throws FlowExecutionException
	{
		FlowUtils.doEnterHelper(context, stepNumber);
		
		super.doEnter(context);
	}

	/**
	 * Method that returns the progress bar text for this view state.
	 *
	 * @return String - Progress bar text for this view state.
	 */
	public String getProgressBarText()
	{
		return progressBarText;
	}

	/**
	 * Method that returns the step number for this view state.
	 *
	 * @return int - Step number for this view state
	 */
	public int getStepNumber()
	{
		return stepNumber;
	}
}


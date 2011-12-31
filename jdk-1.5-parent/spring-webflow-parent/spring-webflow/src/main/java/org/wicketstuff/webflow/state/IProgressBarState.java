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
/**
 *****************************************************************************
 * File: IProgressBarState.java
 *****************************************************************************
 *
 * @author Clint Checketts, Florian Braun, Doug Hall, Subramanian Murali
 * @version $Id: $
 */
public interface IProgressBarState
{
	/**
	 * Method that returns the progress bar text for this view state.
	 *
	 * @return String - Progress bar text for this view state.
	 */
	public String getProgressBarText();

	/**
	 * Method that returns the step number for this view state.
	 *
	 * @return int - Step number for this view state
	 */
	public int getStepNumber();
	
}


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
package org.wicketstuff.webflow.session;

import java.io.Serializable;

import org.apache.wicket.Page;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * This class holds all the information for a progress link step
 *
 * @author Clint Checketts, Florian Braun, Doug Hall, Subramanian Murali
 * @version $Id: $
 */
public class ProgressLink implements Serializable
{
	private static final long	serialVersionUID	= 1L;
	
	private IModel<String> labelModel;
	private int stepNumber;
	private int flowStepNumber;  // the position of the corresponding step in webflow
	private Class<? extends Page> resultPage;

	/**
	 * Default constructor
	 */
	public ProgressLink()
	{
	}
	
	/**
	 * <p>Constructor for ProgressLink.</p>
	 *
	 * @param resultPage a {@link java.lang.Class} object.
	 * @param stepNumber a int.
	 * @param label a {@link java.lang.String} object.
	 */
	public ProgressLink(Class<? extends Page> resultPage, int stepNumber, String label)
	{
		this(resultPage, stepNumber, new Model<String>(label));
	}
	
	/**
	 * <p>Constructor for ProgressLink.</p>
	 *
	 * @param resultPage a {@link java.lang.Class} object.
	 * @param label a {@link java.lang.String} object.
	 */
	public ProgressLink(Class<? extends Page> resultPage, String label)
	{
		this(resultPage, 0, new Model<String>(label));
	}
	
	/**
	 * <p>Constructor for ProgressLink.</p>
	 *
	 * @param resultPage a {@link java.lang.Class} object.
	 * @param stepNumber a int.
	 * @param labelModel a {@link org.apache.wicket.model.IModel} object.
	 */
	public ProgressLink(Class<? extends Page> resultPage, int stepNumber, IModel<String> labelModel)
	{
		super();
		this.stepNumber = stepNumber;
		this.resultPage = resultPage;
		this.labelModel = labelModel;
	}
	
	/**
	 * <p>Getter for the field <code>resultPage</code>.</p>
	 *
	 * @return Class<? extends Page> The resultPage
	 */
	public Class<? extends Page> getResultPage()
	{
		return resultPage;
	}
	
	/**
	 * <p>Getter for the field <code>labelModel</code>.</p>
	 *
	 * @return IModel<String> labelModel
	 */
	public IModel<String> getLabelModel()
	{
		return labelModel;
	}

	/**
	 * <p>Getter for the field <code>stepNumber</code>.</p>
	 *
	 * @return int stepNumber
	 */
	public int getStepNumber()
	{
		return stepNumber;
	}

	/**
	 * <p>Setter for the field <code>stepNumber</code>.</p>
	 *
	 * @param stepNumber a int.
	 */
	public void setStepNumber(int stepNumber)
	{
		this.stepNumber = stepNumber;
	}

	/**
	 * <p>Getter for the field <code>flowStepNumber</code>.</p>
	 *
	 * @return the flowStepNumber
	 */
	public int getFlowStepNumber()
	{
		return flowStepNumber;
	}

	/**
	 * <p>Setter for the field <code>flowStepNumber</code>.</p>
	 *
	 * @param flowStepNumber the flowStepNumber to set
	 */
	public void setFlowStepNumber(int flowStepNumber)
	{
		this.flowStepNumber = flowStepNumber;
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString() {
		String stepText = labelModel == null ? "" : labelModel.getObject();
		
		return  "Step #:"+flowStepNumber+" Text:"+	stepText+" for page:"+resultPage;
	}
}

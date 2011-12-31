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
/* ***************************************************************************
 * File: WicketViewFactoryCreator
 *****************************************************************************/
package org.wicketstuff.webflow.view;

import org.apache.wicket.Component;
import org.apache.wicket.WicketRuntimeException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.binding.convert.ConversionService;
import org.springframework.binding.expression.Expression;
import org.springframework.binding.expression.ExpressionParser;
import org.springframework.validation.Validator;
import org.springframework.webflow.engine.builder.BinderConfiguration;
import org.springframework.webflow.engine.builder.ViewFactoryCreator;
import org.springframework.webflow.execution.ViewFactory;

/**
 * Returns view factorys that create wicket views for spring web flow.
 * This implementation is meant for wicket environment, and returns instances of the wicket view factory implementations.
 *
 * @author Clint Checketts, Florian Braun, Doug Hall, Subramanian Murali
 * @version $Id: $
 */
public class WicketViewFactoryCreator implements ViewFactoryCreator
{
	private static final Logger LOG = LoggerFactory.getLogger(WicketViewFactoryCreator.class);
	
	/* (non-Javadoc)
	 * @see org.springframework.webflow.engine.builder.ViewFactoryCreator#createViewFactory(org.springframework.binding.expression.Expression, org.springframework.binding.expression.ExpressionParser, org.springframework.binding.convert.ConversionService, org.springframework.webflow.engine.builder.BinderConfiguration)
	 */
	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	public ViewFactory createViewFactory(Expression viewId, ExpressionParser expressionParser,
			ConversionService conversionService, BinderConfiguration binderConfiguration, Validator validator)
	{
		Class<? extends Component> viewClass;
		
		try 
		{
			Class<?> parsedClass = Class.forName(viewId.toString());
			
			
			if(Component.class.isAssignableFrom(parsedClass)){
				viewClass = (Class<? extends Component>) parsedClass;
			} else {
				throw new WicketRuntimeException("Component " +viewId.toString() + " must implement the class "+ Component.class.getName() + " for it to be used in a WebFlow.");
			}
			
		} 
		catch (ClassNotFoundException classNotFoundException) 
		{
			
			LOG.error("An exception occurred while trying to load the view state class retrieved from Spring Web Flow " + viewId.toString(),
				classNotFoundException);
			throw new RuntimeException(classNotFoundException);
		}
		
		return new WicketViewFactory(viewClass);	
	}

	/* (non-Javadoc)
	 * @see org.springframework.webflow.engine.builder.ViewFactoryCreator#getViewIdByConvention(java.lang.String)
	 */
	/** {@inheritDoc} */
	public String getViewIdByConvention(String viewStateId)
	{
		return viewStateId;
	}


}

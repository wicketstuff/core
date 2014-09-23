package org.apache.wicket.security.examples.springsecurity.security.authorization;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.security.actions.RegistrationException;
import org.apache.wicket.security.swarm.actions.SwarmAction;
import org.apache.wicket.security.swarm.actions.SwarmActionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Custom action factory. Registers {@link Department} and {@link Organization} actions.
 * 
 * @author marrink
 */
public class MyActionFactory extends SwarmActionFactory
{
	private static final Logger log = LoggerFactory.getLogger(MyActionFactory.class);

	/**
	 * Constructs a new ActionFactory with in addition to the default actions,
	 * organisation and department actions.
	 * 
	 * @param key
	 *            key to store this factory
	 */
	public MyActionFactory(Object key)
	{
		super(key);
		try
		{
			// note none of the actions registered this way will implement the
			// interface defined here, you will simply get the default action.
			// that's just the way swarm implements wasp
			register(Department.class, "department");
			// registering an action this way will return the actual
			// implementation specified here
			// however the reason we are using a custom implementation here is
			// because we need to inherit the department action not because we
			// want our actions to be a certain subclass.
			register(Organization.class, new DepartmentInheritor(nextPowerOf2(), "organization",
				this));

		}
		catch (RegistrationException e)
		{
			throw new WicketRuntimeException("actionfactory was not setup correctly", e);
		}
		if (log.isDebugEnabled())
		{
			log.debug("created for key:" + key.toString());
		}
	}

	/**
	 * Custom class for all actions implying the department action.
	 * 
	 * @author marrink
	 */
	private static final class DepartmentInheritor extends SwarmAction
	{
		private static final long serialVersionUID = 1L;

		/**
		 * 
		 * Construct.
		 * 
		 * @param actions
		 *            power of two, to be used as base value for the action
		 * @param name
		 *            action name
		 * @param factory
		 *            the factory
		 */
		protected DepartmentInheritor(int actions, String name, SwarmActionFactory factory)
		{
			// bitwise or to inherit department action
			super(actions | factory.getAction(Department.class).actions(), name, factory);
		}
	}
}
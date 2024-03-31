package org.wicketstuff.springreference;

import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.io.IClusterable;
import org.springframework.context.ApplicationContext;

/**
 * <p>
 * This class together with {@link SpringReferenceSupporter} makes it possible to use spring
 * annotation driven injection with wicket (for example <code>&#64;Autowired</code> and
 * <code>&#64;Qualifier</code> annotated fields). It has most of the benefits of
 * {@link SpringReference} (except lazy lookup) so this can be used too where
 * <code>&#64;SpringBean</code> from wicket-spring fails (no dynamic proxies used, serialization
 * supported).
 * </p>
 * <p>
 * <b>Instances of this class use the {@link SpringReferenceSupporter} for bean lookup, so it must
 * be registered in your wicket {@link WebApplication} init() method (
 * <code>SpringReferenceSupporter.register(this);</code>). Otherwise you will get
 * {@link NullPointerException} during instantiation. See {@link SpringReferenceSupporter} for more
 * information.</b>
 * </p>
 * <p>
 * Probably this is the most ugly looking in code from the spring-wicket integration alternatives,
 * however this is the closest to the vanilla spring injection.<br />
 * The base idea is that the spring dependencies are encapsulated into a subclass of this class.
 * Dependencies are (re)injected during instantiation, deserialization and cloning.
 * </p>
 * <p>
 * Usage:
 * <ul>
 * <li>you can declare it in your wicket components, models or your custom classes as well</li>
 * <li>see the custom Deps subclass in the example below</li>
 * <li>note that injected dependencies are marked as transient so they are not serialized by wicket
 * (they will be reinjected during deserialization)</li>
 * </ul>
 * 
 * <pre>
 * <code>
 * public class MySession extends AuthenticatedWebSession
 * {
 * 	private static final long serialVersionUID = 1L;
 * 	
 * 	<b>// This nested class holds all our spring dependencies. Annotated in spring style.
 * 	static class Deps extends AbstractSpringDependencies
 * 	{
 * 		private static final long serialVersionUID = 1L;
 * 	
 * 		&#64;Autowired
 * 		&#64;Qualifier("authenticationManager")
 * 		transient AuthenticationManager authenticationManager;
 * 
 * 		&#64;Autowired
 * 		transient AbstractRememberMeServices abstractRememberMeServices;
 * 	}
 * 	
 * 	private final Deps deps = new Deps();</b>
 * 	
 * 	// ...
 * 
 * 	public boolean authenticate(String username, String password)
 * 	{
 * 		// ...
 * 		<b>deps.authenticationManager.authenticate(token);
 * 		AbstractRememberMeServices rememberMeServices = deps.abstractRememberMeServices;</b>
 * 		// ...
 * 	}
 * }
 * </code>
 * </pre>
 * 
 * </p>
 * 
 * @author akiraly
 */
public abstract class AbstractSpringDependencies implements IClusterable, Cloneable
{
	private static final long serialVersionUID = 1L;

	private transient boolean inited;

	/**
	 * Constructor. Calls {@link #init()}.
	 */
	protected AbstractSpringDependencies()
	{
		init();
	}

	/**
	 * Injects dependencies.
	 */
	protected void init()
	{
		if (inited)
			return;
		inited = true;
		ApplicationContext applicationContext = SpringReferenceSupporter.get()
			.getApplicationContext();
		applicationContext.getAutowireCapableBeanFactory().autowireBean(this);
	}

	/**
	 * Called during deserialization by the JVM. Calls {@link #init()}.
	 * 
	 * @return this
	 */
	protected Object readResolve()
	{
		init();
		return this;
	}

	@Override
	public AbstractSpringDependencies clone()
	{
		try
		{
			return (AbstractSpringDependencies)super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			// should not happen
			throw new IllegalStateException(e);
		}
	}
}

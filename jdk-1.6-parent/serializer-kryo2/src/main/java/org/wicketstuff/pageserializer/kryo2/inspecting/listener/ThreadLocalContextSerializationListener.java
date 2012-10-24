package org.wicketstuff.pageserializer.kryo2.inspecting.listener;

/**
 * serialization listener which handles a thread local context 
 * @author mosmann
 *
 * @param <T> context type
 */
public abstract class ThreadLocalContextSerializationListener<T> implements ISerializationListener
{
	private final ThreadLocal<T> contextContainer = new ThreadLocal<T>();

	/**
	 * creates a context for one serialization process
	 * @param object object to be serialized
	 * @return context based on this
	 */
	protected abstract T createContext(Object object);

	@Override
	public final void begin(Object object)
	{
		T context = createContext(object);
		contextContainer.set(context);
		begin(context, object);
	}

	/**
	 * @see ISerializationListener#begin(Object)
	 */
	protected abstract void begin(T context, Object object);

	@Override
	public final void before(int position, Object object)
	{
		before(contextContainer.get(), position, object);
	}

	/**
	 * @see ISerializationListener#before(int, Object)
	 */
	protected abstract void before(T context, int position, Object object);

	@Override
	public final void after(int position, Object object)
	{
		after(contextContainer.get(), position, object);
	}

	/**
	 * @see ISerializationListener#after(int, Object)
	 */
	protected abstract void after(T context, int position, Object object);

	@Override
	public void end(Object object, RuntimeException exception)
	{
		T context = contextContainer.get();
		contextContainer.remove();
		end(context, object, exception);
	}

	/**
	 * @see ISerializationListener#end(Object, RuntimeException)
	 */
	protected abstract void end(T context, Object object, RuntimeException exception);
}

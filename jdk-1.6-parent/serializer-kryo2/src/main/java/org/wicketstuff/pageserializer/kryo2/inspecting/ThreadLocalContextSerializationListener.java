package org.wicketstuff.pageserializer.kryo2.inspecting;

public abstract class ThreadLocalContextSerializationListener<T> implements ISerializationListener
{
	private final ThreadLocal<T> contextContainer=new ThreadLocal<T>();
	
	protected abstract T createContext(Object object);

	@Override
	public final void begin(Object object)
	{
		T context = createContext(object);
		contextContainer.set(context);
		begin(context,object);
	}

	protected abstract void begin(T context, Object object);

	@Override
	public final void before(int position, Object object)
	{
		before(contextContainer.get(),position,object);
	}

	protected abstract void before(T context, int position, Object object);
	
	@Override
	public final void after(int position, Object object)
	{
		after(contextContainer.get(),position,object);
	}

	protected abstract void after(T context, int position, Object object);
	
	@Override
	public void end(Object object)
	{
		T context=contextContainer.get();
		contextContainer.remove();
		end(context,object);
	}

	protected abstract void end(T context, Object object);
}

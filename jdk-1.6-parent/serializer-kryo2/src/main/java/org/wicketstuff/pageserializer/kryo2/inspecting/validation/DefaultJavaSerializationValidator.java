package org.wicketstuff.pageserializer.kryo2.inspecting.validation;

import java.io.Serializable;

import org.apache.wicket.WicketRuntimeException;
import org.wicketstuff.pageserializer.kryo2.inspecting.ISerializationListener;

public class DefaultJavaSerializationValidator implements ISerializationListener
{

	@Override
	public void begin(Object object)
	{
		checkSerializable(object);
	}

	@Override
	public void before(int position, Object object)
	{
		checkSerializable(object);
	}

	@Override
	public void after(int position, Object object)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void end(Object object)
	{
		// TODO Auto-generated method stub

	}

	private void checkSerializable(Object object)
	{
		if (object==null) return;
		
		if (!(object instanceof Serializable)) {
			throw new WicketRuntimeException(""+object.getClass()+" is not Serializable");
		}
	}

}

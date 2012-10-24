package org.wicketstuff.pageserializer.kryo2.inspecting.validation;

import java.io.Serializable;

import org.wicketstuff.pageserializer.kryo2.inspecting.listener.ISerializationListener;

import com.esotericsoftware.kryo.KryoException;

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

	}

	@Override
	public void end(Object object,RuntimeException ex)
	{
		
	}

	private void checkSerializable(Object object)
	{
		if (object==null) return;
		
		if (!(object instanceof Serializable)) {
			throw new NotSerializableException(""+object.getClass()+" is not Serializable");
		}
	}

	static class NotSerializableException extends KryoException {

		public NotSerializableException(String message)
		{
			super(message);
		}
		
	}
}

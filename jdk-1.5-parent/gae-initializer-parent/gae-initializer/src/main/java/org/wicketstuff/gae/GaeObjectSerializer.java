package org.wicketstuff.gae;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.wicket.serialize.ISerializer;
import org.apache.wicket.serialize.java.JavaSerializer;
import org.apache.wicket.util.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * use plain JDK Object(Input|Output)Stream
 */
public class GaeObjectSerializer implements ISerializer
{
	private static final Logger log = LoggerFactory.getLogger(JavaSerializer.class);

	public byte[] serialize(Object object)
	{
		try
		{
			final ByteArrayOutputStream out = new ByteArrayOutputStream();
			ObjectOutputStream oos = null;
			try
			{
				oos = new ObjectOutputStream(out);
				oos.writeObject(object);
			}
			finally
			{
				try
				{
					IOUtils.close(oos);
				}
				finally
				{
					out.close();
				}
			}
			return out.toByteArray();
		}
		catch (Exception e)
		{
			log.error("Error serializing object " + object.getClass() + " [object=" + object + "]",
				e);
		}
		return null;
	}

	public Object deserialize(byte[] data)
	{
		try
		{
			final ByteArrayInputStream in = new ByteArrayInputStream(data);
			ObjectInputStream ois = null;
			try
			{
				ois = new ObjectInputStream(in);
				return ois.readObject();
			}
			finally
			{
				try
				{
					IOUtils.close(ois);
				}
				finally
				{
					in.close();
				}
			}
		}
		catch (ClassNotFoundException e)
		{
			throw new RuntimeException("Could not deserialize object using GaeObjectSerializer", e);
		}
		catch (IOException e)
		{
			throw new RuntimeException("Could not deserialize object using GaeObjectSerializer", e);
		}
	}

}

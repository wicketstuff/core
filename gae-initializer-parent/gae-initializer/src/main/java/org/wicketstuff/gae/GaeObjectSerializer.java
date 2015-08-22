package org.wicketstuff.gae;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import org.apache.wicket.serialize.java.JavaSerializer;

/**
 * Use plain JDK Object(Input|Output)Stream
 */
public class GaeObjectSerializer extends JavaSerializer
{
	public GaeObjectSerializer(String applicationKey)
	{
		super(applicationKey);
	}

	@Override
	protected ObjectInputStream newObjectInputStream(InputStream in) throws IOException
	{
		return new ObjectInputStream(in);
	}

	@Override
	protected ObjectOutputStream newObjectOutputStream(OutputStream out) throws IOException
	{
		return new ObjectOutputStream(out);
	}
}

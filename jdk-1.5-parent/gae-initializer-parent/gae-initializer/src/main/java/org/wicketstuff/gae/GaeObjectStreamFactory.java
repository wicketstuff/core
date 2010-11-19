package org.wicketstuff.gae;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import org.apache.wicket.util.io.IObjectStreamFactory;

public class GaeObjectStreamFactory implements IObjectStreamFactory {

	public ObjectInputStream newObjectInputStream(InputStream in)
			throws IOException {

		return new ObjectInputStream(in);
	}

	public ObjectOutputStream newObjectOutputStream(OutputStream out)
			throws IOException {
		
		return new ObjectOutputStream(out);
	}

}

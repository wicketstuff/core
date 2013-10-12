package org.wicketstuff.pageserializer.fast;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import org.apache.wicket.serialize.ISerializer;
import org.apache.wicket.util.io.ByteArrayOutputStream;
import org.apache.wicket.util.lang.Args;
import org.apache.wicket.util.lang.Bytes;

import de.ruedigermoeller.serialization.FSTConfiguration;
import de.ruedigermoeller.serialization.FSTObjectInput;
import de.ruedigermoeller.serialization.FSTObjectOutput;


public class FastWicketSerializer implements ISerializer{

	/**
	 * The size of the {@link ByteBuffer} that is used to hold the serialized page
	 */
	private static final Bytes DEFAULT_BUFFER_SIZE = Bytes.megabytes(10L);

	private final Bytes bufferSize;
	
	static final FSTConfiguration fastSerialConfig = FSTConfiguration.createDefaultConfiguration();
	
	public FastWicketSerializer(Bytes bufferSize) {
		this.bufferSize = Args.notNull(bufferSize, "bufferSize");
	}


	
	@Override
	public byte[] serialize(Object object) {
		
    try {
  		ByteArrayOutputStream buffer = new ByteArrayOutputStream((int) this.bufferSize.bytes());
    	FSTObjectOutput out = fastSerialConfig.getObjectOutput(buffer);
			out.writeObject(object);
	    // DON'T out.close() when using factory method;
	    out.flush();
	    buffer.close();
			return buffer.toByteArray();
    } catch (RuntimeException e) {
    	throw new FastWicketSerialException("serialize",e);
		} catch (IOException e) {
			throw new FastWicketSerialException("serialize",e);
		}
	}

	@Override
	public Object deserialize(byte[] data) {
		try {
			ByteArrayInputStream buffer=new ByteArrayInputStream(data);
			
	    FSTObjectInput in = fastSerialConfig.getObjectInput(buffer);
	    Object result = in.readObject();
	    // DON'T: in.close(); here prevents reuse and will result in an exception      
	    buffer.close();
	    return result;
	    
		} catch (IOException e) {
			throw new FastWicketSerialException("deserialize", e);
		} catch (ClassNotFoundException e) {
			throw new FastWicketSerialException("deserialize", e);
		}
	}

}

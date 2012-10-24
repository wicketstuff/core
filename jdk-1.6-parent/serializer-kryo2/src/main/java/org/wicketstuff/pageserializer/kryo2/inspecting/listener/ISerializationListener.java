package org.wicketstuff.pageserializer.kryo2.inspecting.listener;

/**
 * serialization listener
 * 
 * @author mosmann
 *
 */
public interface ISerializationListener
{

	/**
	 * called when serialization begins
	 * @param object to be serialized
	 */
	void begin(Object object);

	/**
	 * before an object is written to output, this hook is called
	 * @param position output stream position 
	 * @param object
	 */
	void before(int position, Object object);

	/**
	 * after an object is written to output, this hook is called
	 * if any exception is thrown, then this hook is NOT called
	 * @param position
	 * @param object
	 */
	void after(int position, Object object);

	/**
	 * callend when serialization ends
	 * @param object the object it started with
	 * @param exceptionIfAny if somethings throws an exception, this parameter is set
	 */
	void end(Object object, RuntimeException exceptionIfAny);
}

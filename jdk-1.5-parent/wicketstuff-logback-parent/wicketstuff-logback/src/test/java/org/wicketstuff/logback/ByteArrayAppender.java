package org.wicketstuff.logback;

import java.io.ByteArrayOutputStream;

import ch.qos.logback.core.Appender;
import ch.qos.logback.core.OutputStreamAppender;

/**
 * Test {@link Appender} implementation to capture the log messages in a byte array. Used by
 * {@link LogbackTest}.
 * 
 * @param <E>
 *            type of the event.
 * 
 * @author akiraly
 */
public class ByteArrayAppender<E> extends OutputStreamAppender<E>
{
	@Override
	public void start()
	{
		setOutputStream(new ByteArrayOutputStream());
		super.start();
	}

	@Override
	public ByteArrayOutputStream getOutputStream()
	{
		return (ByteArrayOutputStream)super.getOutputStream();
	}
}

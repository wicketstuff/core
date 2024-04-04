package org.wicketstuff.htmlcompressor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;

import org.apache.wicket.markup.parser.IXmlPullParser;
import org.apache.wicket.markup.parser.XmlTag;
import org.apache.wicket.util.crypt.CharEncoding;
import org.apache.wicket.util.io.Streams;

import com.googlecode.htmlcompressor.compressor.HtmlCompressor;

/**
 * {@link IXmlPullParser} implementation with the delegation pattern. Compresses the markup before
 * passing it to the delegated parser. It is constructed by {@link HtmlCompressingMarkupFactory}.
 * 
 * @author akiraly
 */
public class HtmlCompressingXmlPullParser implements IXmlPullParser
{
	private final IXmlPullParser delegate;
	private final HtmlCompressor compressor;

	/**
	 * Constructor.
	 * 
	 * @param delegate
	 *            the wrapped, "real" parser, not null
	 * @param compressor
	 *            the html compressor, not null
	 */
	public HtmlCompressingXmlPullParser(IXmlPullParser delegate, HtmlCompressor compressor)
	{
		this.delegate = delegate;
		this.compressor = compressor;
	}

	public void parse(CharSequence string) throws IOException
	{
		parse(new ByteArrayInputStream(string.toString().getBytes()), null);
	}

	public void parse(InputStream inputStream) throws IOException
	{
		parse(inputStream, CharEncoding.UTF_8);
	}

	public void parse(InputStream inputStream, String encoding) throws IOException
	{
		String markup;
		if (encoding != null)
			markup = Streams.readString(inputStream, encoding);
		else
			markup = Streams.readString(inputStream);

		String compressed = compressor.compress(markup);

		byte[] bytes;
		if (encoding != null)
			bytes = compressed.getBytes(encoding);
		else
			bytes = compressed.getBytes();

		delegate.parse(new ByteArrayInputStream(bytes), encoding);
	}

	public String getEncoding()
	{
		return delegate.getEncoding();
	}

	public CharSequence getDoctype()
	{
		return delegate.getDoctype();
	}

	public CharSequence getInputFromPositionMarker(int toPos)
	{
		return delegate.getInputFromPositionMarker(toPos);
	}

	public CharSequence getInput(int fromPos, int toPos)
	{
		return delegate.getInput(fromPos, toPos);
	}

	public HttpTagType next() throws ParseException
	{
		return delegate.next();
	}

	public XmlTag getElement()
	{
		return delegate.getElement();
	}

	public CharSequence getString()
	{
		return delegate.getString();
	}

	public void setPositionMarker()
	{
		delegate.setPositionMarker();
	}

	public void setPositionMarker(int pos)
	{
		delegate.setPositionMarker(pos);
	}

	/**
	 * Getter for the "real" xml pull parser.
	 * 
	 * @return wrapped parser, not null
	 */
	public IXmlPullParser getDelegate()
	{
		return delegate;
	}

	/**
	 * Getter for the html compressor.
	 * 
	 * @return html compressor, not null
	 */
	public HtmlCompressor getCompressor()
	{
		return compressor;
	}
}

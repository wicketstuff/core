package org.wicketstuff.htmlcompressor;

import org.apache.wicket.markup.MarkupFactory;
import org.apache.wicket.markup.parser.IXmlPullParser;

import com.googlecode.htmlcompressor.compressor.HtmlCompressor;

/**
 * Overrides {@link #newXmlPullParser()} to use {@link HtmlCompressingXmlPullParser}.
 * 
 * @author akiraly
 */
public class HtmlCompressingMarkupFactory extends MarkupFactory
{
	private final HtmlCompressor compressor;

	/**
	 * Constructor.
	 */
	public HtmlCompressingMarkupFactory()
	{
		this(null);
	}

	/**
	 * Constructor.
	 * 
	 * @param compressor
	 *            used for markup compressing, can be null
	 */
	public HtmlCompressingMarkupFactory(HtmlCompressor compressor)
	{
		this.compressor = compressor != null ? compressor : new HtmlCompressor();
	}

	@Override
	protected IXmlPullParser newXmlPullParser()
	{
		IXmlPullParser parser = super.newXmlPullParser();

		return new HtmlCompressingXmlPullParser(parser, compressor);
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

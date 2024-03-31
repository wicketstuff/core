package org.wicketstuff.htmlcompressor;

import org.apache.wicket.markup.MarkupFactory;
import org.apache.wicket.markup.parser.IXmlPullParser;

import com.googlecode.htmlcompressor.compressor.HtmlCompressor;

/**
 * <p>
 * This markup factory can be used to compress (minify) html markup.
 * </p>
 * 
 * <p>
 * To use it do the followings in your wicket Application:
 * </p>
 * 
 * <pre>
 * <code>
 * 	&#64;Override
 * 	protected void init()
 * 	{
 * 		super.init();
 * 		getMarkupSettings().setMarkupFactory(new HtmlCompressingMarkupFactory());
 * 	}
 * </code>
 * </pre>
 * 
 * <p>
 * Or if we want to preconfigure the compressor used:
 * </p>
 * 
 * <pre>
 * <code>
 * 	&#64;Override
 * 	protected void init()
 * 	{
 * 		super.init();
 * 		HtmlCompressor compressor = new HtmlCompressor();
 * 		compressor.setPreserveLineBreaks(true);
 * 		getMarkupSettings().setMarkupFactory(new HtmlCompressingMarkupFactory(compressor));
 * 	}
 * </code>
 * </pre>
 * 
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
		if (compressor == null)
		{
			compressor = new HtmlCompressor();
			compressor.setRemoveIntertagSpaces(true);
			compressor.setRemoveSurroundingSpaces(HtmlCompressor.BLOCK_TAGS_MIN);
		}
		this.compressor = compressor;
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

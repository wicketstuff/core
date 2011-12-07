package org.wicketstuff.jquery.codepress;

import org.wicketstuff.jquery.Options;

/**
 * 
 * @author Edvin Syse <edvin@sysedata.no>
 * 
 */
public class CodepressOptions extends Options
{
	private static final long serialVersionUID = 1L;
	private String fileType;
	private boolean autoComplete;
	private boolean lineNumbers;

	public CodepressOptions()
	{
		set("path", "/resources/org.wicketstuff.jquery.codepress.CodepressBehaviour/");
	}

	public CodepressOptions height(int height)
	{
		set("height", height);
		return this;
	}

	public CodepressOptions lineNumbers(boolean lineNumbers)
	{
		this.lineNumbers = lineNumbers;
		return this;
	}

	public CodepressOptions autoComplete(boolean autoComplete)
	{
		if (!autoComplete)
			set("autocomplete", "off");
		this.autoComplete = autoComplete;
		return this;
	}

	public CodepressOptions fileType(String fileType)
	{
		this.fileType = fileType;
		return this;
	}

	public String getFileType()
	{
		return fileType;
	}

	public boolean isAutoComplete()
	{
		return autoComplete;
	}

	public boolean isLineNumbers()
	{
		return lineNumbers;
	}

}

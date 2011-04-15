package org.wicketstuff.jquery.block;

import org.wicketstuff.jquery.Options;

public class BlockOptions extends Options
{

	private static final long serialVersionUID = 1L;

	public BlockOptions setMessage(String message)
	{
		set("message", message);
		return this;
	}

	public BlockOptions setCSS(Options css)
	{
		set("css", css);
		return this;
	}

	public BlockOptions setFadeIn(int ms)
	{
		set("fadeIn", ms);
		return this;
	}

	public BlockOptions setFadeOut(int ms)
	{
		set("fadeOut", ms);
		return this;
	}

	public BlockOptions setTimeout(int ms)
	{
		set("timeout", ms);
		return this;
	}

	public BlockOptions setShowOverlay(boolean v)
	{
		set("showOverlay", v);
		return this;
	}
}

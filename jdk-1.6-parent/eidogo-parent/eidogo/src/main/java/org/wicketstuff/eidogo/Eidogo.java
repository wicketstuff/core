/* *******************************************************************************
 * This file is part of Wicket-EidoGo.
 *
 * Wicket-EidoGo is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Wicket-EidoGo is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with Foobar.  If not, see <http://www.gnu.org/licenses/agpl.html>.
 ********************************************************************************/
package org.wicketstuff.eidogo;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.parser.XmlTag;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.util.string.JavaScriptUtils;

/**
 * Component to render a EidoGo Player
 * <p>
 * Page.html :
 * 
 * <pre>
 * &lt;div wicket:id="eidogo"&gt;[[EidoGo player here]]&lt;/div&gt;
 * </pre>
 * 
 * Nota : the markup MUST be &lt;div&gt;.
 * </p>
 * <p>
 * Page.java :
 * 
 * <pre>
 * public class Page extends WebPage
 * {
 * 	public Page()
 * 	{
 * 		add(new Eidogo(&quot;eidogo&quot;));
 * 	}
 * }
 * </pre>
 * 
 * </p>
 * 
 * <p>
 * Source : <a href="http://eidogo.com/source">http://eidogo.com/source</a>
 * </p>
 * 
 * @author Isammoc
 */
public class Eidogo extends WebComponent
{

	/** */
	private static final long serialVersionUID = 1L;

	/** Reference to the javascript resource. */
	private static final ResourceReference JS = new JavaScriptResourceReference(Eidogo.class,
		"js/all.compressed.js");
	/** Reference to the CSS resource. */
	private static final ResourceReference CSS = new PackageResourceReference(Eidogo.class,
		"css/player.css");

	/** <code>true</code> to show comments in the player. */
	private boolean showComments;
	/** <code>true</code> to show information of the players. */
	private boolean showPlayerInfo;
	/** <code>true</code> to show game info. */
	private boolean showGameInfo;
	/** <code>true</code> to show tools. */
	private boolean showTools;
	/** <code>true</code> to show options. */
	private boolean showOptions;
	/** <code>true</code> to mark last played stone. */
	private boolean markCurrent;
	/** <code>true</code> to mark position of variations. */
	private boolean markVariation;
	/** <code>true</code> to mark the position of next move. */
	private boolean markNext;
	/** <code>true</code> to enable keyboard shortcuts. */
	private boolean enableShortcuts;
	/** <code>true</code> to enable problem mode. */
	private boolean problemMode;
	/** The theme to apply. */
	private final Theme theme = Theme.STANDARD;
	/** The current mode. */
	private final Mode mode = Mode.PLAY;
	/** URL where to load SGF file. */
	private final String sgfUrl;

	/**
	 * Possible theme.
	 * 
	 * @author Isammoc
	 */
	public enum Theme
	{
		/** Standard theme. */
		STANDARD("standard");
		/** The value in the JSon. */
		private String value;

		/**
		 * Private constructor of the enum.
		 * 
		 * @param value
		 *            Value to store.
		 */
		private Theme(String value)
		{
			this.value = value;
		}

		/**
		 * Used to transform into JSon value.
		 * 
		 * @return the JSon value
		 */
		@Override
		public String toString()
		{
			return value;
		}
	}

	/**
	 * Mode of the player.
	 * 
	 * @author Isammoc
	 */
	public enum Mode
	{
		/** Play mode. Allows user to play the game. */
		PLAY("play"),
		/**
		 * Add black stone mode. Allows user to add as many black stones as (s)he wants.
		 */
		ADD_BLACK("add_b"),
		/**
		 * Add white stone mode. Allows user to add as many white stones as (s)he wants.
		 */
		ADD_WHITE("add_w"),
		/** Region mode. Allows user to mark a region. */
		REGION("region"),
		/**
		 * Triangle mode. Allows user to mark stone and/or intersection with a triangle.
		 */
		TRIANGLE("tr"),
		/**
		 * Square mode. Allows user to mark stone and/or intersection with a square.
		 */
		SQUARE("sq"),
		/**
		 * Circle mode. Allows user to mark stone and/or intersection with a circle.
		 */
		CIRCLE("cr"),
		/**
		 * Label mode. Allows user to mark stone and/or intersection with a letter.
		 */
		LABEL("label"),
		/**
		 * Number mode. Allows user to mark stone and/or intersection with a number.
		 */
		NUMBER("number"),
		/** Score mode. Allows user to view score. */
		SCORE("score");

		/** The value in the JSon. */
		private String value;

		/**
		 * Private constructor of the enum.
		 * 
		 * @param value
		 *            The JSon value to store.
		 */
		private Mode(String value)
		{
			this.value = value;
		}

		/**
		 * Used to transform into JSon value.
		 * 
		 * @return the JSon value
		 */
		@Override
		public String toString()
		{
			return value;
		}
	}

	/**
	 * Minimalist constructor.
	 * 
	 * @param id
	 *            Wicket ID of the component
	 */
	public Eidogo(String id)
	{
		this(id, null);
	}

	/**
	 * Constructor with URL to the SGF file.
	 * 
	 * @param id
	 *            Wicket ID of the component
	 * @param sgfUrl
	 *            URL to the SGF file
	 */
	public Eidogo(String id, String sgfUrl)
	{
		super(id);
		this.sgfUrl = sgfUrl;
	}

	/**
	 * Renders links to the needed javascript and the CSS in the head of response.
	 * 
	 * @param response
	 *            The header response to contribute to.
	 */
	@Override
	public void renderHead(IHeaderResponse response)
	{
		response.render(JavaScriptHeaderItem.forReference(JS));
		response.render(CssHeaderItem.forReference(CSS));
		super.renderHead(response);
	}

	/**
	 * Checks that component tag is a &lt;div&gt; and adds a markup id.
	 */
	@Override
	protected void onComponentTag(ComponentTag tag)
	{
		checkComponentTag(tag, "div");
		setOutputMarkupId(true);
		if (tag.isOpenClose())
		{
			tag.setType(XmlTag.TagType.OPEN);
		}
		super.onComponentTag(tag);
	}

	/**
	 * Renders a javascript component to create the player.
	 */
	@Override
	public void onComponentTagBody(MarkupStream markupStream, ComponentTag openTag)
	{
		super.onComponentTagBody(markupStream, openTag);
		replaceComponentTagBody(markupStream, openTag, null);
		JavaScriptUtils.writeJavaScript(getResponse(),
			"new eidogo.Player({container:\"" + getMarkupId() + "\",theme:\"" + theme + "\"," +
				"sgfUrl:\"" + getResponse().encodeURL(sgfUrl) + "\"," + "loadPath:[0, 0]" +
				",mode:\"" + mode + "\"," + "showComments:" + showComments + "," +
				"showPlayerInfo:" + showPlayerInfo + "," + "showGameInfo:" + showGameInfo + "," +
				"showTools:" + showTools + "," + "showOptions:" + showOptions + "," +
				"markCurrent:" + markCurrent + "," + "markVariations:" + markVariation + "," +
				"markNext:" + markNext + "," + "enableShortcuts:" + enableShortcuts + "," +
				"problemMode:" + problemMode + "});");
	}

	/**
	 * @return <code>true</code> if comments must be shown, <code>false</code> otherwise.
	 */
	public boolean isShowComments()
	{
		return showComments;
	}

	/**
	 * Set the visibility of the comments.
	 * 
	 * @param showComments
	 *            <code>true</code> if comments must be shown, <code>false</code> otherwise.
	 */
	public void setShowComments(boolean showComments)
	{
		this.showComments = showComments;
	}

	/**
	 * @return <code>true</code> if players information must be shown, <code>false</code> otherwise
	 */
	public boolean isShowPlayerInfo()
	{
		return showPlayerInfo;
	}

	/**
	 * Set the visibility of players information.
	 * 
	 * @param showPlayerInfo
	 *            <code>true</code> if players information must be shown, <code>false</code>
	 *            otherwise
	 */
	public void setShowPlayerInfo(boolean showPlayerInfo)
	{
		this.showPlayerInfo = showPlayerInfo;
	}

	/**
	 * @return <code>true</code> if game information must be shown, <code>false</code> otherwise
	 */
	public boolean isShowGameInfo()
	{
		return showGameInfo;
	}

	/**
	 * Set the visibility of game information.
	 * 
	 * @param showGameInfo
	 *            <code>true</code> if game information must be shown, <code>false</code> otherwise
	 */
	public void setShowGameInfo(boolean showGameInfo)
	{
		this.showGameInfo = showGameInfo;
	}

	/**
	 * @return <code>true</code> if tools must be shown, <code>false</code> otherwise
	 */
	public boolean isShowTools()
	{
		return showTools;
	}

	/**
	 * Set the visibility of the tools.
	 * 
	 * @param showTools
	 *            <code>true</code> if tools must be shown, <code>false</code> otherwise
	 */
	public void setShowTools(boolean showTools)
	{
		this.showTools = showTools;
	}

	/**
	 * @return <code>true</code> if options must be shown, <code>false</code> otherwise
	 */
	public boolean isShowOtions()
	{
		return showOptions;
	}

	/**
	 * Set the visibility of the options.
	 * 
	 * @param showOtions
	 *            <code>true</code> if options must be shown, <code>false</code> otherwise
	 */
	public void setShowOtions(boolean showOtions)
	{
		showOptions = showOtions;
	}

	/**
	 * @return <code>true</code> if current stone must be marked, <code>false</code> otherwise
	 */
	public boolean isMarkCurrent()
	{
		return markCurrent;
	}

	/**
	 * Set the visibility of the mark on the last played stone.
	 * 
	 * @param markCurrent
	 *            <code>true</code> if current stone must be marked, <code>false</code> otherwise
	 */
	public void setMarkCurrent(boolean markCurrent)
	{
		this.markCurrent = markCurrent;
	}

	/**
	 * @return <code>true</code> if variation positions must be marked, <code>false</code> otherwise
	 */
	public boolean isMarkVariation()
	{
		return markVariation;
	}

	/**
	 * Enable or disable the mark on variation positions.
	 * 
	 * @param markVariation
	 *            <code>true</code> if variation positions must be marked, <code>false</code>
	 *            otherwise
	 */
	public void setMarkVariation(boolean markVariation)
	{
		this.markVariation = markVariation;
	}

	/**
	 * @return <code>true</code> if the next position must be marked, <code>false</code> otherwise
	 */
	public boolean isMarkNext()
	{
		return markNext;
	}

	/**
	 * Enable or disable the mark on the position of the next move.
	 * 
	 * @param markNext
	 *            <code>true</code> if the next position must be marked, <code>false</code>
	 *            otherwise
	 */
	public void setMarkNext(boolean markNext)
	{
		this.markNext = markNext;
	}

	/**
	 * @return <code>true</code> if shortcuts are enabled, <code>false</code> otherwise
	 */
	public boolean isEnableShortcuts()
	{
		return enableShortcuts;
	}

	/**
	 * Enable or disable the keyboard shortcuts.
	 * 
	 * @param enableShortcuts
	 *            <code>true</code> if shortcuts are enabled, <code>false</code> otherwise
	 */
	public void setEnableShortcuts(boolean enableShortcuts)
	{
		this.enableShortcuts = enableShortcuts;
	}

	/**
	 * @return <code>true</code> if eidogo player is in problem mode, <code>false</code> otherwise
	 */
	public boolean isProblemMode()
	{
		return problemMode;
	}

	/**
	 * Enable or disable problem mode
	 * 
	 * @param problemMode
	 *            <code>true</code> if eidogo player is in problem mode, <code>false</code>
	 *            otherwise
	 */
	public void setProblemMode(boolean problemMode)
	{
		this.problemMode = problemMode;
	}
}

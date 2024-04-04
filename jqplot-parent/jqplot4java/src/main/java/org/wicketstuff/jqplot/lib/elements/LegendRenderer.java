package org.wicketstuff.jqplot.lib.elements;

public class LegendRenderer implements Element
{

	private static final long serialVersionUID = 8595187092890943392L;

	private String background;
	private String border;
	private String fontSize;
	private String fontFamily;
	private String textColor;
	private String marginTop;
	private String marginBotton;
	private String marginLeft;
	private String marginRight;

	private Integer numberRows;
	private Integer numberColumns;

	public String getBackground()
	{
		return background;
	}

	public LegendRenderer setBackground(String background)
	{
		this.background = background;
		return this;
	}

	public String getBorder()
	{
		return border;
	}

	public LegendRenderer setBorder(String border)
	{
		this.border = border;
		return this;
	}

	public String getFontSize()
	{
		return fontSize;
	}

	public LegendRenderer setFontSize(String fontSize)
	{
		this.fontSize = fontSize;
		return this;
	}

	public String getFontFamily()
	{
		return fontFamily;
	}

	public LegendRenderer setFontFamily(String fontFamily)
	{
		this.fontFamily = fontFamily;
		return this;
	}

	public String getTextColor()
	{
		return textColor;
	}

	public LegendRenderer setTextColor(String textColor)
	{
		this.textColor = textColor;
		return this;
	}

	public String getMarginTop()
	{
		return marginTop;
	}

	public LegendRenderer setMarginTop(String marginTop)
	{
		this.marginTop = marginTop;
		return this;
	}

	public String getMarginBotton()
	{
		return marginBotton;
	}

	public LegendRenderer setMarginBotton(String marginBotton)
	{
		this.marginBotton = marginBotton;
		return this;
	}

	public String getMarginLeft()
	{
		return marginLeft;
	}

	public LegendRenderer setMarginLeft(String marginLeft)
	{
		this.marginLeft = marginLeft;
		return this;
	}

	public String getMarginRight()
	{
		return marginRight;
	}

	public LegendRenderer setMarginRight(String marginRight)
	{
		this.marginRight = marginRight;
		return this;
	}

	public Integer getNumberRows()
	{
		return numberRows;
	}

	public LegendRenderer setNumberRows(Integer numberRows)
	{
		this.numberRows = numberRows;
		return this;
	}

	public Integer getNumberColumns()
	{
		return numberColumns;
	}

	public LegendRenderer setNumberColumns(Integer numberColumns)
	{
		this.numberColumns = numberColumns;
		return this;
	}

}

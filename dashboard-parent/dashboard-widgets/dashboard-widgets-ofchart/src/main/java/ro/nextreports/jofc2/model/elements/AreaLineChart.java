package ro.nextreports.jofc2.model.elements;

import ro.nextreports.jofc2.model.metadata.Alias;

/**
 * -------------------------------------------------------------
 * 22.09.2011 
 * Patched "area_line" string in constructor must be "area"
 * Mihai Dinca-Panaitescu
 * -------------------------------------------------------------
 */
public class AreaLineChart extends LineChart {
	
	private static final long serialVersionUID = 1L;

	private static transient final Float DEFAULT_ALPHA = 0.35f;
    
	@Alias("fill-alpha") 
	private Float fillAlpha;

        @Alias("fill") 
	private String fillColor;
    

	public String getFillColor() {
		return fillColor;
	}

	public void setFillColor(String fillColor) {
		this.fillColor = fillColor;
	}

	public AreaLineChart() {
	        super("area");
        	setFillAlpha(DEFAULT_ALPHA);
	}

	public Float getFillAlpha() {
        	return fillAlpha;
	}

  	public AreaLineChart setFillAlpha(Float fillAlpha) {
        	this.fillAlpha = fillAlpha;
	        return this;
	}
}
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.digilabs.jqplot.chart.elements;

/**
 *
 * @author bernardo.moura
 */
public class Legend implements Element {
	
	private static final long serialVersionUID = 7228235274262615669L;

	private String options;
    private String placment;
    private String location;
    private String border;
    private String background;
    private String fontFamily;
    private String marginTop;
    private String marginRight;
    private String marginBottom;
    private String marginLeft;
    
    private Renderer renderer;
    
    private Double xoffset;
    private Double yoffset;
                
    private Boolean show;
    private Boolean showLables;
    private Boolean showSwatches;
    private Boolean preDraw;
    private Boolean escapeHtml; 

    private String[] lables;
    public Legend (){

    }
    public Legend (boolean show, String location){
        this.show = show;
        this.location = location;
    }

    public Legend (String options){
        this.options = options;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getBorder() {
        return border;
    }

    public void setBorder(String border) {
        this.border = border;
    }

    public Boolean getEscapeHtml() {
        return escapeHtml;
    }

    public void setEscapeHtml(Boolean escapeHtml) {
        this.escapeHtml = escapeHtml;
    }

    public String getFontFamily() {
        return fontFamily;
    }

    public void setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
    }

    public String[] getLables() {
        return lables;
    }

    public void setLables(String[] lables) {
        this.lables = lables;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMarginBottom() {
        return marginBottom;
    }

    public void setMarginBottom(String marginBottom) {
        this.marginBottom = marginBottom;
    }

    public String getMarginLeft() {
        return marginLeft;
    }

    public void setMarginLeft(String marginLeft) {
        this.marginLeft = marginLeft;
    }

    public String getMarginRight() {
        return marginRight;
    }

    public void setMarginRight(String marginRight) {
        this.marginRight = marginRight;
    }

    public String getMarginTop() {
        return marginTop;
    }

    public void setMarginTop(String marginTop) {
        this.marginTop = marginTop;
    }

    public String getPlacment() {
        return placment;
    }

    public void setPlacment(String placment) {
        this.placment = placment;
    }

    public Boolean getPreDraw() {
        return preDraw;
    }

    public void setPreDraw(Boolean preDraw) {
        this.preDraw = preDraw;
    }

    public Renderer getRenderer() {
        return renderer;
    }

    public void setRenderer(Renderer renderer) {
        this.renderer = renderer;
    }

    public Boolean getShow() {
        return show;
    }

    public void setShow(Boolean show) {
        this.show = show;
    }

    public Boolean getShowLables() {
        return showLables;
    }

    public void setShowLables(Boolean showLables) {
        this.showLables = showLables;
    }

    public Boolean getShowSwatches() {
        return showSwatches;
    }

    public void setShowSwatches(Boolean showSwatches) {
        this.showSwatches = showSwatches;
    }

    public Double getXoffset() {
        return xoffset;
    }

    public void setXoffset(Double xoffset) {
        this.xoffset = xoffset;
    }

    public Double getYoffset() {
        return yoffset;
    }

    public void setYoffset(Double yoffset) {
        this.yoffset = yoffset;
    }
   
    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }
    
    
}

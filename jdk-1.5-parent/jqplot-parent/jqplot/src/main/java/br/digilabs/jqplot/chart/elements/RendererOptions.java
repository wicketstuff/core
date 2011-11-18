/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.digilabs.jqplot.chart.elements;

/**
 *
 * @author bernardo.moura
 */
public class RendererOptions implements Element {

	private static final long serialVersionUID = -3555383297912526665L;

	private Boolean showDataLabels;
    private String dataLabels;
    private Integer sliceMargin;
    private Integer startAngle;

    /**
     * @return the showDataLabels
     */
    public Boolean getShowDataLabels() {
        return showDataLabels;
    }

    /**
     * @param showDataLabels the showDataLabels to set
     */
    public void setShowDataLabels(Boolean showDataLabels) {
        this.showDataLabels = showDataLabels;
    }

    /**
     * @return the dataLabels
     */
    public String getDataLabels() {
        return dataLabels;
    }

    /**
     * @param dataLabels the dataLabels to set
     */
    public void setDataLabels(String dataLabels) {
        this.dataLabels = dataLabels;
    }

    /**
     * @return the sliceMargin
     */
    public Integer getSliceMargin() {
        return sliceMargin;
    }

    /**
     * @param sliceMargin the sliceMargin to set
     */
    public void setSliceMargin(Integer sliceMargin) {
        this.sliceMargin = sliceMargin;
    }

    /**
     * @return the startAngle
     */
    public Integer getStartAngle() {
        return startAngle;
    }

    /**
     * @param startAngle the startAngle to set
     */
    public void setStartAngle(Integer startAngle) {
        this.startAngle = startAngle;
    }
}

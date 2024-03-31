package org.wicketstuff.jqplot.lib.elements;

/**
 *
 * @author sbalamaci
 */
public class Animation implements Element {

	private static final long serialVersionUID = -4476584768663107303L;

	private Integer speed;

    /**
     * @return animation speed
     */
    public Integer getSpeed() {
        return speed;
    }

    /**
     * set animation speed in ms
     * @param speed in
	 * @return  Animation
     */
    public Animation setSpeed(Integer speed) {
        this.speed = speed;
        return this;
    }
}

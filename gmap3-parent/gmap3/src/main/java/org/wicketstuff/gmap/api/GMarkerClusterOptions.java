package org.wicketstuff.gmap.api;

import java.util.ArrayList;
import java.util.List;
import org.wicketstuff.gmap.GMapMarkerClustererHeaderContributor;
import org.wicketstuff.gmap.js.Array;
import org.wicketstuff.gmap.js.ObjectLiteral;

/**
 * Options object for using specific settings in marker clustering. 
 * Like custom icons or zoom restrictions.
 * 
 * https://googlemaps.github.io/js-marker-clusterer/docs/reference.html
 * 
 * Docs from the js file:
 *  'gridSize': (number) The grid size of a cluster in pixels.
 *  'maxZoom': (number) The maximum zoom level that a marker can be part of a cluster.
 *  'zoomOnClick': (boolean) Whether the default behaviour of clicking on a cluster is to zoom into it.
 *  'averageCenter': (boolean) Wether the center of each cluster should be
 *                   the average of all markers in the cluster.
 *  'minimumClusterSize': (number) The minimum number of markers to be in a
 *                        cluster before the markers are hidden and a count is shown.
 *  'styles': (object) An object that has style properties:
 *    'url': (string) The image url.
 *    'height': (number) The image height.
 *    'width': (number) The image width.
 *    'anchor': (Array) The anchor position of the label text.
 *    'textColor': (string) The text color.
 *    'textSize': (number) The text size.
 *    'backgroundPosition': (string) The position of the backgound x, y.
 * 
 * @author Rob Sonke
 */
public class GMarkerClusterOptions implements GValue
{
    private static final long serialVersionUID = 1L;
    
    private Integer gridSize = 60;
    private Integer maxZoom = 100;
    private boolean zoomOnClick = true;
    private boolean averageCenter = false;
    private Integer minimumClusterSize = 2;
    private List<GMarkerClusterStyle> styles = new ArrayList<GMarkerClusterStyle>();

    public GMarkerClusterOptions()
    {
        
    }

    /**
     * @see GValue#getJSconstructor()
     */
    @Override
    public String getJSconstructor()
    {
        ObjectLiteral literal = new ObjectLiteral();
        // we need to overwrite the imagePath so that the script can find the PNGs in the org.wicketstuff.gmap package.
        literal.set("imagePath", "\"wicket/resource/" + GMapMarkerClustererHeaderContributor.INSTANCE.getResource().getScope().getCanonicalName() + "/m\"");
        literal.set("gridSize", getGridSize().toString());
        literal.set("maxZoom", getMaxZoom().toString());
        literal.setString("zoomOnClick", (isZoomOnClick())?"true":"false");
        literal.setString("averageCenter", (isAverageCenter())?"true":"false");
        literal.set("minimumClusterSize", getMinimumClusterSize().toString());

        if(getStyles().isEmpty())
        	literal.set("styles", "[]");
        else
        {
        	Array array = new Array();
            for (GMarkerClusterStyle style : getStyles())
            {
                array.add(style.getJSconstructor());
            }
        	literal.set("styles", array.toJS());
        }

        return literal.toJS();
    }
    
    public Integer getGridSize() 
    {
		return gridSize;
	}

	public GMarkerClusterOptions setGridSize(Integer gridSize) 
	{
		this.gridSize = gridSize;
		return this;
	}

	public Integer getMaxZoom() 
	{
		return maxZoom;
	}

	public GMarkerClusterOptions setMaxZoom(Integer maxZoom) 
	{
		this.maxZoom = maxZoom;
		return this;
	}

	public boolean isZoomOnClick() 
	{
		return zoomOnClick;
	}

	public GMarkerClusterOptions setZoomOnClick(boolean zoomOnClick) 
	{
		this.zoomOnClick = zoomOnClick;
		return this;
	}

	public boolean isAverageCenter() 
	{
		return averageCenter;
	}

	public GMarkerClusterOptions setAverageCenter(boolean averageCenter) 
	{
		this.averageCenter = averageCenter;
		return this;
	}

	public Integer getMinimumClusterSize() 
	{
		return minimumClusterSize;
	}

	public GMarkerClusterOptions setMinimumClusterSize(Integer minimumClusterSize) 
	{
		this.minimumClusterSize = minimumClusterSize;
		return this;
	}

	public List<GMarkerClusterStyle> getStyles() 
	{
		return styles;
	}

	public GMarkerClusterOptions setStyles(List<GMarkerClusterStyle> styles) 
	{
		this.styles = styles;
		return this;
	}
}

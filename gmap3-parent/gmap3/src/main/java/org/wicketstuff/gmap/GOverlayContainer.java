/*
 * $Id: org.eclipse.jdt.ui.prefs 5004 2006-03-17 20:47:08 -0800 (Fri, 17 Mar 2006) eelco12 $
 * $Revision: 5004 $
 * $Date: 2006-03-17 20:47:08 -0800 (Fri, 17 Mar 2006) $
 * 
 * ==============================================================================
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.wicketstuff.gmap;

import java.util.List;
import org.wicketstuff.gmap.api.GOverlay;

public interface GOverlayContainer
{

    /**
     * Adds a child component to this container.
     *
     * @param child
     * The child
     * @throws IllegalArgumentException
     * Thrown if a child with the same id is replaced by the add operation.
     * @return This
     */
    public GOverlayContainer addOverlay(final GOverlay child);

    /**
     * Remove an overlay.
     *
     * @param overlay
     * overlay to remove
     * @return This
     */
    public GOverlayContainer removeOverlay(GOverlay overlay);

    /**
     * Clear all overlays.
     *
     * @return This
     */
    public GOverlayContainer removeAllOverlays();

    /**
     * @return all overlay's
     */
    public List<GOverlay> getOverlays();
}

/*
 * $Id: GMapExampleApplication.java 1722 2007-02-02 10:03:45Z syca $
 * $Revision: 1722 $
 * $Date: 2007-02-02 11:03:45 +0100 (Fr, 02 Feb 2007) $
 *
 * ====================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.examples.gmap.ggroundOverlay;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;

public class GGroundOverlayApplication extends WebApplication
{

    @Override
    public Class<? extends Page> getHomePage()
    {
        return GGroundOverlayPage.class;
    }
}

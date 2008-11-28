/*
 *  Copyright (C) 2005  Iulian-Corneliu Costan
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package wicket.contrib.tinymce.settings;

/**
 * Base class for tinymce button. There ae two types of buttons:
 * <ul>
 * <li>default button - these buttons are defined in TinyMCESettings class.</li>
 * <li>plugin button - these buttons are defined by individual plugins.</li>
 * </ul>
 *
 * @author Iulian-Corneliu COSTAN
 */
public class Button extends wicket.contrib.tinymce.settings.Enum
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected Button(String name)
    {
        super(name);
    }
}

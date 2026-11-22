/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.hugerte;

import java.util.Locale;

/**
 * The plugins that ship with HugeRTE.
 */
public enum HugeRtePlugin
{
	ACCORDION,
	ADVLIST,
	ANCHOR,
	AUTOLINK,
	AUTORESIZE,
	AUTOSAVE,
	CHARMAP,
	CODE,
	CODESAMPLE,
	DIRECTIONALITY,
	EMOTICONS,
	FULLSCREEN,
	HELP,
	IMAGE,
	IMPORTCSS,
	INSERTDATETIME,
	LINK,
	LISTS,
	MEDIA,
	NONBREAKING,
	PAGEBREAK,
	PREVIEW,
	QUICKBARS,
	SAVE,
	SEARCHREPLACE,
	TABLE,
	TEMPLATE,
	VISUALBLOCKS,
	VISUALCHARS,
	WORDCOUNT;

	/**
	 * @return the name HugeRTE uses for this plugin in the {@code plugins} option
	 */
	public String id()
	{
		return name().toLowerCase(Locale.ROOT);
	}
}

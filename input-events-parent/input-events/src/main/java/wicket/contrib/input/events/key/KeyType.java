/*
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
package wicket.contrib.input.events.key;

/**
 * 
 * @author Nino Martinez (nino.martinez.wael *at* gmail *dot* com remember no stars)
 * 
 */
public enum KeyType
{

	Ctrl, Shift, Alt, Meta, a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z, zero(
		"0"), one("1"), two("2"), three("3"), four("4"), five("5"), six("6"), seven("7"), eight("8"), nine(
		"9"), Escape, Tab, Space, Return, Enter, Backspace, Scroll_lock, Caps_lock, Num_lock, Pause, Insert, Delete, Home, End, Page_up, Page_down, Left, Up, Right, Down, F1, F2, F3, F4, F5, F6, F7, F8, F9, F10, F11, F12;

	private final String keyCode;

	/**
	 * future thing if keyCode should be used.
	 * 
	 * @param keyCode
	 */
	private KeyType(String keyCode)
	{
		this.keyCode = keyCode;
	}

	private KeyType()
	{
		keyCode = name();
	}

	public String getKeyCode()
	{
		return keyCode;
	}

}

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.html5.media.webvtt;

import org.wicketstuff.html5.media.webvtt.VttCue.Alignment;
import org.wicketstuff.html5.media.webvtt.VttCue.Vertical;
import org.wicketstuff.html5.media.webvtt.VttRegion.Scroll;

public class VttGeneratorMain
{

	public static void main(String[] args)
	{
		WebVtt webvtt = new WebVtt();
		webvtt.setDescription("this is the description");
		webvtt.setNote("This is the note");
		VttRegion vttRegion = new VttRegion("fred", "40%", 3, "0%,100%", "10%,90%", Scroll.up);
		webvtt.addRegion(vttRegion);
		VttRegion vttRegion2 = new VttRegion("fred", "40%", 3, "0%,100%", "10%,90%", Scroll.up);
		webvtt.addRegion(vttRegion2);

		webvtt.addCue(new VttCue("1", new VttTime().setS(1), new VttTime().setS(2), "Hello").setAlignment(
			Alignment.start)
			.setVoiceSpan("test")
			.setNote("This is a note\nvdsvsd"));
		webvtt.addCue(new VttCue("2", new VttTime().setS(2), new VttTime().setS(3), "Hello2").setVoiceSpan(
			".test2")
			.setRegion(vttRegion));
		webvtt.addCue(new VttCue("3", new VttTime().setS(4), new VttTime().setS(5), "Hello3").setVertical(
			Vertical.rl)
			.setPosition("20%")
			.setSize("60%")
			.setLine("0"));
		System.out.println(webvtt.create());

	}
}

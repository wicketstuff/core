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

import org.apache.wicket.markup.html.media.Track;
import org.apache.wicket.markup.html.media.Track.Kind;
import org.apache.wicket.markup.html.media.video.Video;
import org.wicketstuff.html5.BasePage;

public class WebVttDemo extends BasePage
{

	private static final long serialVersionUID = 1L;

	public WebVttDemo()
	{
		final WebVtt webVtt = new WebVtt();
		webVtt.addCue(new VttCue("1", new VttTime().setS(1), new VttTime().setS(5),
			getString("first")));
		webVtt.addCue(new VttCue("2", new VttTime().setS(6), new VttTime().setS(10),
			getString("second")));
		Video video = new Video("video", "http://media.w3.org/2010/05/video/movie_300.mp4");
		Track track = new Track("track", new VttResourceReference("VTT")
		{

			private static final long serialVersionUID = -8739122871674506741L;

			@Override
			public WebVtt getWebVtt()
			{
				return webVtt;
			}
		});
		track.setDefaultTrack(true);
		track.setKind(Kind.SUBTITLES);
		video.add(track);
		add(video);
	}

}

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
package org.wicketstuff.html5.media.webrtc;

import java.util.UUID;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.link.ResourceLink;
import org.apache.wicket.markup.html.media.video.Video;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.wicketstuff.html5.BasePage;

public class WebRTCDemo extends BasePage
{

	private static final long serialVersionUID = 1L;

	public WebRTCDemo(PageParameters pageParameters)
	{

		String stringValue = pageParameters.get("roomName").toString();
		String roomName = "";
		if (stringValue != null)
		{
			roomName = stringValue;
		}
		else
		{
			roomName = UUID.randomUUID().toString();
		}

		final String finalRoomName = roomName;

		add(new ResourceLink<Void>("signalserver", new PackageResourceReference(WebRTC.class,
			"server.js")));

		add(new ResourceLink<Void>("signalserverconfig", new PackageResourceReference(WebRTC.class,
			"dev_config.json")));

		final Video video = new Video("video");
		video.setControls(false);
		this.add(video);

		WebRTC webrtc = new WebRTC("webrtc")
		{
			private static final long serialVersionUID = 1L;

			@Override
			public Video getLocalVideo()
			{
				return video;
			}

			@Override
			public String getRoomName()
			{
				return finalRoomName;
			}

			@Override
			public String getSocketIOUrl()
			{
				return "https://signaling.simplewebrtc.com:443/";
			}

			// can be overridden to provide an error page
			@Override
			public Class<? extends Page> getErrorPage()
			{
				return super.getErrorPage();
			}

			// can be overridden to provide a dummy
			// picture if no video is available
			@Override
			protected ResourceReference getNoVideoResourceReference()
			{
				return super.getNoVideoResourceReference();
			}
		};
		webrtc.setVolumeBars(false);
		webrtc.setDebug(false);
		webrtc.setFramesPerSeconds(10);
		webrtc.setMaxWidth(320);
		webrtc.setMaxHeight(240);
		this.add(webrtc);
	}
}
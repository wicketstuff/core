---
title: HTML5
category: ui
description: Simple components and behaviors for HTML5 features.
artifactId: wicketstuff-html5
tags: media video
---

The wicket-html5 module contains classes that give Wicket support for using HTML5 features like **FileApi** and **Geolocation**. The `<video>` and `<audio>` components are part of Wicket core.

## Maven

```xml
<dependency>
    <groupId>org.wicketstuff</groupId>
    <artifactId>wicketstuff-html5</artifactId>
</dependency>
```

Import `org.wicketstuff:wicketstuff-bom` in your `dependencyManagement` section to manage the version.

## Fileapi package
This package contains support for the Html5 FileApi.

### Brief info about Html5 FileApi
Gives limited access for javascript to read information (name, file size, type, last modified date) about the files chosen for a file upload field. It is supported by all modern browsers.

### FileFieldSizeCheckBehavior
This behavior can be added to FileUploadField-s to provide a convenient and fast size checking for users using FileApi supporting browsers. Using this has the benefit that the user does not have to upload the whole file data to receive an error about it exceeding the size limit. By default an ajax call is triggered when the user changes the value of the upload field (selects/deselects files). For browsers not implementing the FileApi this behavior does not change or break anything (so it is safe for IE users).

Usage is very simple (similar to an AjaxButton), just add it to the field and specify what should happen when validation passes or fails:

```java
final FileUploadField uploadField = new FileUploadField("file");

// add our FileApi based size check, errors are reported trough a
// feedback panel
uploadField.add(new FileFieldSizeCheckBehavior() {
	private static final long serialVersionUID = 7228537141239670625L;

	@Override
	protected void onSubmit(AjaxRequestTarget target, FileList fileList) {
		target.add(feedback);
	}

	@Override
	protected void onError(AjaxRequestTarget target, FileList fileList) {
		target.add(feedback);
	}
});
```

If you want to do something different than check for size you can subclass **FileFieldChangeBehavior**.

## Geolocation package
This package contains support for the HTML5 GeoLocation API.

### Brief info about HTML5 GeoLocation API
The GeoLocation API tries to determine the position of the hosting device. E.g via GPS if a signal is available (on smartphones) or via network signals like the IP address. Therefore no guarantee is given that the location is accurate.

### AjaxGeolocationBehavior
Usage is very simple, just add it to a WebMarkupContainer and specify what should happen when you received a location (or no location can't be received):

```java
        final WebMarkupContainer geoLocator = new WebMarkupContainer("geolocator");

        AjaxGeolocationBehavior geolocationBehaviour = new AjaxGeolocationBehavior()
        {

            private static final long serialVersionUID = 1L;

            // onGeoAvailable is abstract so you have to implement it
            @Override
            protected void onGeoAvailable(AjaxRequestTarget target, String latitude,
                    String longitude)
            {
                // permission to locate user was granted
                // you can do now whatever you want with the longitude and latitude values.
                String result = "We received a location. Latitude: "+ latitude+" Longitude: " + longitude +"\n";
            }

            // you don't need to implement this method if you don't want to react on failure
            @Override
            protected void onNotAvailable(AjaxRequestTarget target, String errorCode, String errorMessage)
            {
                // if the location is not available, show the reason why
                String result = "An error occurred. Error code: "+ errorCode + " Error message:" + errorMessage;
            }
        };
        // if the user does not react on the request to share his location with
        // us within 5 seconds we want the onNotAvailable-method to get triggered
        geolocationBehaviour.setTimeout(5000);

        geoLocator.add(geolocationBehaviour);


        add(geoLocator);
```

```html
    <wicket:extend>
                <span wicket:id="geolocator"></span>
    </wicket:extend>
```

### Reacting when no location is available
You can react on the event that no location can be obtained. The methods are **onNotAvailable** and **setTimeout**.
If the user is ignoring the request for sharing his location or postpones his decision (in Firefox the "Not now" option) no callback method is called. This is intended by design (see <https://bugzilla.mozilla.org/show_bug.cgi?id=650247>). For those cases we can set a timeout. After the specified amount of time the **onNotAvailable** method is called.

The [specification](https://www.w3.org/TR/geolocation/#position_error_interface) defines also a timeout attribute:
"The length of time specified by the timeout property has elapsed before the implementation could successfully acquire a new Position object."

So it applies only for the case the user grants permission to share his location and is NOT the timeout which is set in the **setTimeout**-method!

It MIGHT happen that the determination of the location takes a bit longer, even though the user granted permission to share his location.
In this case first the method **onNotAvailable** will be called and as soon as the location could be obtained the method **onGeoAvailable** will be called. So don't set the timeout too low.

If onNotAvailable is called you can determine the cause via the **errorCode** parameter:
* "1" indicates a "permission denied"
* "2" indicates that the position could not be determined
* "3" indicates a timeout

The **errorMessage** parameter is mainly for debugging purposes since it does not have to contain a meaningful message which can be directly delegated to the user.

## HTML5 Shapes

CSS shapes are used to let inline elements like text move along them - for example around one side of a background picture.

Java:
```java
	add(new ShapeBuilder("shapeleft").shape(new Circle("30%"))
		.transition(new Circle("50%"))
		.useWidth("500px")
		.useHeight("500px"));
	add(new ShapeBuilder("shaperight").shape(new Circle("30%"))
		.transition(new Circle("50%"))
		.useWidth("500px")
		.useHeight("500px")
		.orientation(Orientation.right));
```

HTML:
```html
<div>
	<div wicket:id="shapeleft"></div>
	<div wicket:id="shaperight"></div>
	... some text...
</div>
```

Further information:
* <http://www.html5rocks.com/en/tutorials/shapes/getting-started/>
* <http://dev.w3.org/csswg/css-shapes/>

## WebVTT API

The WebVTT API is used to generate dynamically internationalized subtitles in Wicket videos. So you only have to record a video once and apply subtitles for each language. Note if you want to use cue-spans that closes in the middle set it as text (with #setString(String)) Example: `<v fred>Hi everyone</v> <b>HIII</b>`

Styling Examples:
```css
::cue(.red){ color: red; }
::cue(.blue){ color: blue; }
::cue(.green){ color: green; }
::cue(.yellow){ color: yellow; }
::cue(.background-red){ background-color: red; }
::cue(.background-blue){ background-color: blue; }
::cue(.background-green){ background-color: green; }
::cue(.background-yellow){ background-color: yellow; }
```

```html
<c.red.background-yellow>Red text with a yellow background.</c>
```

```java
final WebVtt webVtt = new WebVtt();

webVtt.addCue(new VttCue("1", new VttTime().setS(1),
new VttTime().setS(5), getString("first")));

webVtt.addCue(new VttCue("2", new VttTime().setS(6),
new VttTime().setS(10), getString("second")));

Video video = new Video("video",
"http://media.w3.org/2010/05/video/movie_300.mp4");
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
```

## WebRTC API

In order to use the integration of WebRTC you have to process the following steps which are required for the data transfer / the communication layer. There are two servers required to communicate. The first is the STUN / TURN server so that the clients can communicate even if they are behind a NAT. The second is a signal server which provides a websocket layer so that the client can update data of other clients. For default the public google STUN server is used stun.l.google.com:19302.

Installing the Signal Server:
* Download and install nodejs (<https://nodejs.org>)
* Copy the "server.js" and the "dev_config.json" file to the directory where you want the signal server to be started from - you can download the files from sources (jar) or starting wicketstuff-html5-examples and download it from the WebRTC demo page
* Install socket.io / node-uuid / getconfig / fs - `npm install socket.io@0.9.16 node-uuid@1.2.0 getconfig@0.3.0 fs@0.0.2` (run this command in the same directory which contains the "server.js" and the "dev_config.json")
* Start the signal server with `node server.js`
* The listening port is 8000

Installing STUN server:
* Follow the instructions at <http://www.stunprotocol.org/>

Wicket Java Integration:
* Use the WebRTC class to display the video conference
* Override getLocalVideo and put in the local video instantiation
* getRoomName() is used to configure the room name people are able to join to and - this name can be dynamically provided from the wicket application
* Use getSocketIOUrl() to configure the url to the signal server (example: `http://192.168.2.110:8000`)

Java:
```java
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
			return "roomname";
		}

		@Override
		public String getSocketIOUrl()
		{
			return "http://192.168.2.110:8000";
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
```

HTML:
```html
<div class="videocontainer">
	<video wicket:id="video" />
</div>
<div wicket:id="webrtc"></div>
```

To change the look and positions of the videos you can do this via CSS. The classes are:
- videocontainer - the container which holds the video and the volume bar
- video - the video element in the videocontainer
- volumebar - the volume bar

Further information:
* <http://www.html5rocks.com/en/tutorials/webrtc/basics/>
* <https://github.com/andyet/signalmaster>
* <https://github.com/HenrikJoreteg/SimpleWebRTC>
* <https://github.com/HenrikJoreteg/webrtc.js>

Special thanks to:
* Jörn Zaefferer who helped me out with NodeJS. :-)

## InlineImage
The inline image allows you to render the image content as base64 encoded content into the html page. (Example: src="data:image/jpeg;base64,......") The benefit is for example, that you could create HTML EMails without external image resources.

Java:
```java
add(new InlineImage("inlineimage",
new PackageResourceReference(this.getClass(), "test.png")));
```
HTML:
```html
<img wicket:id="inlineimage" />
```

Further information:
* <http://www.html5rocks.com/en/tutorials/canvas/integrating/>

The [`wicket-html5-examples`](https://github.com/wicketstuff/core/tree/wicket-10.x/wicket-html5-parent/wicket-html5-examples) module contains a runnable example application.

Source: [`wicket-html5-parent`](https://github.com/wicketstuff/core/tree/wicket-10.x/wicket-html5-parent)

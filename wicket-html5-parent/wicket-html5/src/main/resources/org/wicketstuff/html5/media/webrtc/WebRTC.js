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
$(function(){
	// creates the volume bar
	function createVolumeBar(id){
		var volumebar = document.createElement('meter');
		volumebar.id = id;
		volumebar.className = 'volumebar';
		volumebar.min = -45;
		volumebar.max = -20;
		volumebar.low = -40;
		volumebar.high = -25;
		return volumebar;
	}
	
	var webrtc = new SimpleWebRTC({
	    // the id/element dom element that will hold "our" video
	    localVideoEl: '%(localvideoid)',
	    // the id/element dom element that will hold remote videos
	    remoteVideosEl: '',
	    // immediately ask for camera access
	    autoRequestMedia: true,
	    detectSpeakingEvents: true,
	    autoAdjustMic: false,
	    media: {
	    	video: {
		    	mandatory: {
			    	maxFrameRate: %(framesperseconds),
			    	maxWidth: %(maxwidth),
			    	maxHeight: %(maxheight)
		    	}
	    	},
	    	audio: true
	    },
	    // url for the socket.io server
	    url: '%(socketiourl)',
	    socketio: { 'force new connection':true }
	});
	
	// show local volume bar
	if(%(volumebars)){
		var localvideo = document.getElementById('%(localvideoid)');
		localvideo.parentElement.appendChild(createVolumeBar('localvolume'));	
	}
	
    // we did not get access to the camera
    webrtc.on('localMediaError', function (err) {
    	if('%(errorurl)' === ''){    		
    		document.getElementById('localvolume').style.display='none';
    	}else{
    		window.location.href='%(errorurl)';
    	}
    });

	
	//we have to wait until it's ready
	webrtc.on('readyToCall', function () {
	    // you can name it anything
	    webrtc.joinRoom('%(roomname)');
	}); 
	
	// Shows the volume bar in the video elements
	function showVolume(el, volume) {
	    if (!el) return;
		if (volume < -45) volume = -45; // -45 to -20 is
		if (volume > -20) volume = -20; // a good range
		el.value = volume;
	}
	
	// If a new user joins another video element is added and a volume bar is created for the video element
	webrtc.on('videoAdded', function (video, peer) {
		video.poster="%(poster)";
		var remotes = document.getElementById('%(markupid)');
	    if (remotes) {
	        var remoteContainer = document.createElement('div');
	        remoteContainer.className = 'videocontainer';
	        remoteContainer.id = 'container_' + webrtc.getDomId(peer);
	        remoteContainer.appendChild(video);
	        
			// suppress context menu
			video.oncontextmenu = function () { return false; };

			// resize the video on click
			video.onclick = function () {
				remoteContainer.style.width = video.videoWidth + 'px';
				remoteContainer.style.height = video.videoHeight + 'px';
			};			

			// show volume bars if requested
	        if(%(volumebars)){
				remoteContainer.appendChild(createVolumeBar('volume_' + peer.id));
	        }
	        remotes.appendChild(remoteContainer);
	    }
	});
	
	// If a user left the room the video is going to be removed
	webrtc.on('videoRemoved', function (video, peer) {
	    var remotes = document.getElementById('%(markupid)');
	    var el = document.getElementById('container_' + webrtc.getDomId(peer));
	    if (remotes && el) {
	        remotes.removeChild(el);
	    }
	});
	
	// If the volume changed the current volume should be displayed
	webrtc.on('volumeChange', function (volume, treshold) {
	    showVolume(document.getElementById('localvolume'), volume);
	});

	// remote volume has changed
	webrtc.on('remoteVolumeChange', function (peer, volume) {
		showVolume(document.getElementById('volume_' + peer.id), volume);
	});
});
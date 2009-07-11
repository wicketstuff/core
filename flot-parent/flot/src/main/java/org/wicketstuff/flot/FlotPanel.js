/*
 * Copyright 2009 Michael Würtinger (mwuertinger@users.sourceforge.net)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * 		http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
var flotTarget = $("#FlotPanel_drawingArea");
var flotData = %s;
var flotOptions = %s;

$(function () {
    var plot = $.plot(flotTarget, flotData, flotOptions);

    $(window).bind("resize", function () {
    	// I don't know why but we have to set the parent div's size manually!
    	flotTarget.width(flotTarget.parent().width());
    	flotTarget.height(flotTarget.parent().height());

    	// Redraw the graph with the right size
    	plot = $.plot(flotTarget, flotData, flotOptions);
    });
});

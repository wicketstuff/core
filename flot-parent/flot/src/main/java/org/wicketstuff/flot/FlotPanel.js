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
var showTooltip = %s;

$(function () {
    var plot = $.plot(flotTarget, flotData, flotOptions);

    $(window).bind("resize", function () {
    	// I don't know why but we have to set the parent div's size manually!
    	flotTarget.width(flotTarget.parent().width());
    	flotTarget.height(flotTarget.parent().height());

    	// Redraw the graph with the right size
    	plot = $.plot(flotTarget, flotData, flotOptions);
    });
    
    $(flotTarget).bind("plotclick", function (event, pos, item) {
        alert("You clicked at " + pos.x + ", " + pos.y);
        // secondary axis coordinates if present are in pos.x2, pos.y2,
        // if you need global screen coordinates, they are pos.pageX, pos.pageY

        if (item) {
          highlight(item.series, item.datapoint);
          alert("You clicked a point!");
        }
    });

    function showTooltip(x, y, contents) {
        $('<div id="tooltip">' + contents + '</div>').css( {
            position: 'absolute',
            display: 'none',
            top: y + 5,
            left: x + 5,
            border: '1px solid #fdd',
            padding: '2px',
            'background-color': '#fee',
            opacity: 0.80
        }).appendTo("body").fadeIn(200);
    }

    var previousPoint = null;
    $(flotTarget).bind("plothover", function (event, pos, item) {
        $("#x").text(pos.x.toFixed(2));
        $("#y").text(pos.y.toFixed(2));

        //if ($("#enableTooltip:checked").length > 0) {
        if (showTooltip) {
            if (item) {
                if (previousPoint != item.datapoint) {
                    previousPoint = item.datapoint;

                    $("#tooltip").remove();
                    var x = item.datapoint[0].toFixed(2),
                            y = item.datapoint[1].toFixed(2);

                    //showTooltip(item.pageX, item.pageY, item.series.label + " of " + x + " = " + y);
                    showTooltip(item.pageX, item.pageY, y);
                }
            }
            else {
                $("#tooltip").remove();
                previousPoint = null;
            }
        }
    });


});

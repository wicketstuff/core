function showSplitButtonMenu(opener){
	//first lets hide all open SplitButton menus
	jQuery('.split-button-marker')/* every buttonset-container-span gets this class / see markup file */
	.next()/* tags for menus (auto-iterating) */
	.hide();/* lets hide it now (auto-iterating) */
	
	jQuery(opener)
	.parent()/* buttonset-container-span */
	.next()/* tag for menu */
	.show()/* has display:none by default, lets show it now */
	.position({/* and lets the position nearby the opening element */
				my: "left top",
				at: "left bottom",
				of: jQuery(opener)
			  }
	);
	
	//register one single click to the document (somewhere else click) to hide the menu again
	jQuery(document).one( "click", function() {/* just one time, then die; no further clicks for onDocument */
		jQuery(opener)
		.parent()/* buttonset-container-span */
		.next()/* tag for menu */
		.hide();/* lets hide it now */
	});
	
	return false;//prevent default click handling of button
}

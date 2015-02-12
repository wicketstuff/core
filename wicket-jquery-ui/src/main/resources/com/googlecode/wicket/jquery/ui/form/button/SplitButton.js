function openMenuReturnFalse(opener){
	
<<<<<<< HEAD
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
=======
	//first lets hide all open SplitButton menu-divs
	jQuery('.split-button-marker')/* every buttonset-container-span gets this class / see markup file */
	.next()/* menu-div (auto-iterating) */
	.hide();/* lets hide it now (auto-iterating) */
	
	jQuery(opener)
	.parent()/* buttonset-container-span */
	.next()/* menu-div */
	.show()/* Menu holds DisplayNoneBehavior on construction-time, lets show it now */
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
		.next()/* menu-div */
>>>>>>> branch 'splitbutton-branch' of https://github.com/Patrick1701/wicket-jquery-ui.git
		.hide();/* lets hide it now */
	});
	
	return false;//prevent default click handling of button
}

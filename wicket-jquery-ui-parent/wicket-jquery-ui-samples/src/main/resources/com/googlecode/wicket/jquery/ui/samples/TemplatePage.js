$(function() {
	$('.linkZone').collapse({
		persist : true,
		query : 'h2.tButton',
		open : function() {
			this.slideDown(200);
		},
		close : function() {
			this.slideUp(200);
		}
	});
});

$(function(){
	var selectizeConfig = %(selectizeConfig);
	var defaultSelectizeConfig = {
		    persist: false,
		    create: function(input) {
		        return {
		            value: input,
		            text: input
		        }
		    }
		};
	$.extend(defaultSelectizeConfig,selectizeConfig);
	$("#"+selectizeConfig.selectizeMarkupId).selectize(defaultSelectizeConfig);
});
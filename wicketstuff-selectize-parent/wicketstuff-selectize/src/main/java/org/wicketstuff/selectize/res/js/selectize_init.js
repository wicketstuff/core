$(function(){
	var selectizeConfig = ${selectizeConfig};
	var defaultSelectizeConfig = {
		    persist: false
		   
		};
	$.extend(defaultSelectizeConfig,selectizeConfig);
	if(selectizeConfig.createAvailable){
		$.extend(defaultSelectizeConfig, {
			create: function(input) {
				return {
					value: input,
					text: input
				}
			}
		});
	}
	var selectize = $("#"+selectizeConfig.selectizeMarkupId).selectize(defaultSelectizeConfig);
	var selectizeRef = selectize[0].selectize;
	$(selectizeConfig.optionsToAdd).each(function(){
		selectizeRef.addOption(this);
	});
	$(selectizeConfig.optgroupsToAdd).each(function(){
		selectizeRef.addOptionGroup(this.groupId,this);
	});
});
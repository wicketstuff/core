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
	if(selectizeConfig.ajaxcallback){
		$.extend(defaultSelectizeConfig,{
			load: function(query, callback) {
				if (!query.length) return callback();
				$.ajax({
					url: selectizeConfig.ajaxcallback+'&search='+ encodeURIComponent(query),
					type: 'GET',
					headers:{
						"Wicket-Ajax":"true",
						"Wicket-Ajax-BaseURL":Wicket.Ajax.baseUrl
					},
					error: function() {
						callback();
					},
					success: function(res) {
						callback(res);
					}
				});
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
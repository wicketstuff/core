$(function() {
	var selectizeConfig = ${selectizeConfig};
	var defaultSelectizeConfig = {
		persist : false
	};
	
	$.extend(defaultSelectizeConfig, selectizeConfig);
	
	if (selectizeConfig.createAvailable) {
		$.extend(defaultSelectizeConfig, {
			create : function(input) {
				return {
					value : input,
					text : input
				}
			}
		});
	}
	
	if (selectizeConfig.ajaxCallback) {
		$.extend(defaultSelectizeConfig, {
			load : function(query, callback) {
				if (!query.length)
					return callback();
				$.ajax({
					url : selectizeConfig.ajaxCallback + '&search='
							+ encodeURIComponent(query),
					cache : false,
					type : 'GET',
					headers : {
						"Wicket-Ajax" : "true",
						"Wicket-Ajax-BaseURL" : Wicket.Ajax.baseUrl
					},
					error : function() {
						callback();
					},
					success : function(res) {
						callback(res.options);
					}
				});
			},
			render : {
				option : function(item, escape) {
					var ajaxTemplate = selectizeConfig.ajaxTemplate;
					var croppedAjaxTemplate = $("div:first",ajaxTemplate).parent().html();
					return Handlebars.compile(croppedAjaxTemplate)(item);
				}
			},
			onChange:function(value){
				Wicket.Ajax.get({
					'u' : selectizeConfig.ajaxChangeCallback,
					'ep' : {
						'search' : encodeURIComponent(value)
					}
				});
			}
		});
	}
	var selectize = $("#" + selectizeConfig.selectizeMarkupId).selectize(defaultSelectizeConfig);
	var selectizeRef = selectize[0].selectize;
	$(selectizeConfig.optionsToAdd).each(function() {
		selectizeRef.addOption(this);
	});
	$(selectizeConfig.optgroupsToAdd).each(function() {
		selectizeRef.addOptionGroup(this.groupId, this);
	});
});
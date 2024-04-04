jQuery.widget("ui.timespinner", jQuery.ui.spinner, {
	options : {
		// seconds
		step : 60 * 1000,
		// hours
		page : 60
	},

	_parse : function(value) {
		if (typeof value === "string") {
			if (Number(value) == value) {
				// already a timestamp
				return Number(value);
			}

			return +Globalize.parseDate(value, ['t'], this.options.culture);
		}

		return value;
	},

	_format : function(value) {
		return Globalize.format(new Date(value), "t", this.options.culture);
	}
});

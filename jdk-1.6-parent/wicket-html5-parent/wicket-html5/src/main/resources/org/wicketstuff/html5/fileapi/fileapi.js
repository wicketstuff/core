if (typeof Wicketstuff === "undefined")
	Wicketstuff = {};

Wicketstuff.fileapi = {
	supports : function(field) {
		return field != null && typeof field.files === "object";
	},
	fileFieldToPostBody : function(field) {
		var result = "num=" + field.files.length;
		for ( var fi = 0; fi < field.files.length; fi++) {
			var prefix = "file[" + fi + "].";
			var file = field.files[fi];
			result += "&" + prefix + "name" + "=" + wicketEncode(file.name)
					+ "&" + prefix + "size" + "=" + wicketEncode(file.size)
					+ "&" + prefix + "type" + "=" + wicketEncode(file.type);
			if (file.lastModifiedDate != null)
				result += "&" + prefix + "lastModifiedTime" + "="
						+ wicketEncode(file.lastModifiedDate.getTime());
		}
		return result;
	}
};

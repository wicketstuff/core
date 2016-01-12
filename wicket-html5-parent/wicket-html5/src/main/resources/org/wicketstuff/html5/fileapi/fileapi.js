if (typeof Wicketstuff === "undefined")
	Wicketstuff = {};

Wicketstuff.fileapi = {
	supports : function(field) {
		return field != null && typeof field.files === "object"
				&& typeof field.files.length === "number";
	},
	fileFieldToPostBody : function(field) {
		var encode = Wicket.Form.encode;
		var result = {};
		result.num = encode(field.files.length);

		for ( var fi = 0; fi < field.files.length; fi++) {
			var prefix = "file[" + fi + "].";
			var file = field.files[fi];
			result[prefix+'name'] = encode(file.name);
			result[prefix+'size'] = encode(file.size);
			result[prefix+'type'] = encode(file.type);

			if (file.lastModifiedDate != null) {
				result[prefix+'lastModifiedTime'] = encode(file.lastModifiedDate.getTime());
			}
		}
		return result;
	}
};

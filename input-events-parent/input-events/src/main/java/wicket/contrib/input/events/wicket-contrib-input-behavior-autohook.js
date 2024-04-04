<script type="text/javascript">
function init${wicketComponentId}() {
	shortcut.add("${keys}",function() {
				
				document.getElementById('${wicketComponentId}').${event}();
				
	},{
	'disable_in_input':${disable_in_input},
	'type':'${type}',
	'propagate':${propagate},
	'target':${target}
	
	});
}
init${wicketComponentId}();
</script>

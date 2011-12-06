<script type="text/javascript">
function init${wicketComponentId}() {
	shortcut.add("${keys}",function() {
				
				window.location=document.getElementById('${wicketComponentId}').href;
				
	},{
	'disable_in_input':${disable_in_input},
	'type':'${type}',
	'propagate':${propagate},
	'target':${target}
	
	});
}
init${wicketComponentId}();
</script>

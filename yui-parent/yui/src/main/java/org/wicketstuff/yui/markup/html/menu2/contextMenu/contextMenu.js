var targetId = null;
var contextMenuId = null;

function GetTargetIdFromEventTarget(p_oNode) {

	var id = null;
    if (p_oNode.getAttribute( 'targetId' ) ) {     
        id = p_oNode.getAttribute( 'targetId' );
    }
    else {
        do {
            if (p_oNode.getAttribute( 'targetId' )) {
                id = p_oNode.getAttribute( 'targetId' );
                break;
            }
            if ( p_oNode.parentNode == document ) {
            	break;
            }
        }
        while ((p_oNode = p_oNode.parentNode));
    } 
     
    return id;
}

function GetMenuIdFromEventTarget(p_oNode) {

	var id = null;
    if (p_oNode.getAttribute( 'contextMenuId' ) ) {     
        id = p_oNode.getAttribute( 'contextMenuId' );
    }
    else {
        do {
            if (p_oNode.getAttribute( 'contextMenuId' )) {
                id = p_oNode.getAttribute( 'contextMenuId' );
                break;
            }
            if ( p_oNode.parentNode == document ) {
            	break;
            }
        }
        while ((p_oNode = p_oNode.parentNode));
    } 
     
    return id;
}

function GetMenuFromEventTarget(p_oNode, menus) {

	var id = null;
    if (p_oNode.getAttribute( 'contextMenuId' ) ) {     
        id = p_oNode.getAttribute( 'contextMenuId' );
    }
    else {
        do {

            if (p_oNode.getAttribute( 'contextMenuId' )) {
                id = p_oNode.getAttribute( 'contextMenuId' );
                break;
            }

            
            if ( p_oNode.parentNode == document ) {
            	break;
            }
        }
        while ((p_oNode = p_oNode.parentNode));

    } 
    
   
    return id == null ? null : menus[id];

}

function onContextMenuBeforeShow(p_sType, p_aArgs) {

    targetId = GetTargetIdFromEventTarget(this.contextEventTarget);
    contextMenuId = GetMenuIdFromEventTarget(this.contextEventTarget);

    var aMenuItems = GetMenuFromEventTarget( this.contextEventTarget, this.menus );

    this.clearContent();
    
    if ( aMenuItems != null ) {
   		this.addItems(aMenuItems);
   		this.render();
   	}

}

/**
 * @author David Bernard
 */
var DnDSortableBehavior = {
  itemId : '',
  srcContainerId : '',
  srcPosition : -1,
  destContainerId : '',
  destPosition : -1,
  findId : function(el) {
    return el.id;
  },
  start : function(item, containerSelector, childrenSelector) {
    var container = $(item).parent(containerSelector);
    var list = container.children(childrenSelector);
    var idx = -1;
    for (idx = list.length-1; (idx > -1) && (list[idx].id != item.id); idx--);
    this.itemId = this.findId(item);
    this.destContainerId = '';
    this.destPosition = -1;
    this.srcContainerId = this.findId(container[0]);
    this.srcPosition = idx;
  },
  stop : function(item, containerSelector, childrenSelector) {
    var container = $(item).parent(containerSelector);
    var list = container.children(childrenSelector);
    var idx = -1;
    for (idx = list.length-1; (idx > -1) && (list[idx].id != item.id); idx--);
    this.itemId = this.findId(item);
    this.destContainerId = this.findId(container[0]);
    this.destPosition = idx;
  },
  isMoved : function() {
    return (this.destContainerId != this.srcContainerId) || (this.destPosition != this.srcPosition);
  },
  asQueryString : function() {
    return 'itemId=' + this.itemId + '&srcContainerId=' + this.srcContainerId + '&srcPosition=' + this.srcPosition + '&destContainerId='+ this.destContainerId + '&destPosition=' + this.destPosition;
  }
};
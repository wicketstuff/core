
YAHOO.Tools=function(){keyStr="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";regExs={quotes:/\x22/g,startspace:/^\s+/g,endspace:/\s+$/g,striptags:/<\/?[^>]+>/gi,hasbr:/<br/i,hasp:/<p>/i,rbr:/<br>/gi,rbr2:/<br\/>/gi,rendp:/<\/p>/gi,rp:/<p>/gi,base64:/[^A-Za-z0-9\+\/\=]/g,syntaxCheck:/^("(\\.|[^"\\\n\r])*?"|[,:{}\[\]0-9.\-+Eaeflnr-u \n\r\t])+?$/}
jsonCodes={'\b':'\\b','\t':'\\t','\n':'\\n','\f':'\\f','\r':'\\r','"':'\\"','\\':'\\\\'}
return{version:'1.0'}}();YAHOO.Tools.getHeight=function(elm){var elm=$(elm);var h=$D.getStyle(elm,'height');if(h=='auto'){elm.style.zoom=1;h=elm.clientHeight+'px';}
return h;}
YAHOO.Tools.getCenter=function(elm){var elm=$(elm);var cX=Math.round(($D.getViewportWidth()-parseInt($D.getStyle(elm,'width')))/2);var cY=Math.round(($D.getViewportHeight()-parseInt(this.getHeight(elm)))/2);return[cX,cY];}
YAHOO.Tools.makeTextObject=function(txt){return document.createTextNode(txt);}
YAHOO.Tools.makeChildren=function(arr,elm){var elm=$(elm);for(var i in arr){_val=arr[i];if(typeof _val=='string'){_val=this.makeTxtObject(_val);}
elm.appendChild(_val);}}
YAHOO.Tools.styleToCamel=function(str){var _tmp=str.split('-');var _new_style=_tmp[0];for(var i=1;i<_tmp.length;i++){_new_style+=_tmp[i].substring(0,1).toUpperCase()+_tmp[i].substring(1,_tmp[i].length);}
return _new_style;}
YAHOO.Tools.removeQuotes=function(str){var checkText=new String(str);return String(checkText.replace(regExs.quotes,''));}
YAHOO.Tools.trim=function(str){return str.replace(regExs.startspace,'').replace(regExs.endspace,'');}
YAHOO.Tools.stripTags=function(str){return str.replace(regExs.striptags,'');}
YAHOO.Tools.hasBRs=function(str){return str.match(regExs.hasbr)||str.match(regExs.hasp);}
YAHOO.Tools.convertBRs2NLs=function(str){return str.replace(regExs.rbr,"\n").replace(regExs.rbr2,"\n").replace(regExs.rendp,"\n").replace(regExs.rp,"");}
YAHOO.Tools.stringRepeat=function(str,repeat){return new Array(repeat+1).join(str);}
YAHOO.Tools.stringReverse=function(str){var new_str='';for(i=0;i<str.length;i++){new_str=new_str+str.charAt((str.length-1)-i);}
return new_str;}
YAHOO.Tools.printf=function(){var num=arguments.length;var oStr=arguments[0];for(var i=1;i<num;i++){var pattern="\\{"+(i-1)+"\\}";var re=new RegExp(pattern,"g");oStr=oStr.replace(re,arguments[i]);}
return oStr;}
YAHOO.Tools.setStyleString=function(el,str){var _tmp=str.split(';');for(x in _tmp){if(x){__tmp=YAHOO.Tools.trim(_tmp[x]);__tmp=_tmp[x].split(':');if(__tmp[0]&&__tmp[1]){var _attr=YAHOO.Tools.trim(__tmp[0]);var _val=YAHOO.Tools.trim(__tmp[1]);if(_attr&&_val){if(_attr.indexOf('-')!=-1){_attr=YAHOO.Tools.styleToCamel(_attr);}
$D.setStyle(el,_attr,_val);}}}}}
YAHOO.Tools.getSelection=function(_document,_window){if(!_document){_document=document;}
if(!_window){_window=window;}
if(_document.selection){return _document.selection;}
return _window.getSelection();}
YAHOO.Tools.removeElement=function(el){if(!(el instanceof Array)){el=new Array($(el));}
for(var i=0;i<el.length;i++){if(el[i].parentNode){el[i].parentNode.removeChild(el);}}}
YAHOO.Tools.setCookie=function(name,value,expires,path,domain,secure){var argv=arguments;var argc=arguments.length;var expires=(argc>2)?argv[2]:null;var path=(argc>3)?argv[3]:'/';var domain=(argc>4)?argv[4]:null;var secure=(argc>5)?argv[5]:false;document.cookie=name+"="+escape(value)+
((expires==null)?"":("; expires="+expires.toGMTString()))+
((path==null)?"":("; path="+path))+
((domain==null)?"":("; domain="+domain))+
((secure==true)?"; secure":"");}
YAHOO.Tools.getCookie=function(name){var dc=document.cookie;var prefix=name+'=';var begin=dc.indexOf('; '+prefix);if(begin==-1){begin=dc.indexOf(prefix);if(begin!=0)return null;}else{begin+=2;}
var end=document.cookie.indexOf(';',begin);if(end==-1){end=dc.length;}
return unescape(dc.substring(begin+prefix.length,end));}
YAHOO.Tools.deleteCookie=function(name,path,domain){if(getCookie(name)){document.cookie=name+'='+((path)?'; path='+path:'')+((domain)?'; domain='+domain:'')+'; expires=Thu, 01-Jan-70 00:00:01 GMT';}}
YAHOO.Tools.getBrowserEngine=function(){var opera=((window.opera&&window.opera.version)?true:false);var safari=((navigator.vendor&&navigator.vendor.indexOf('Apple')!=-1)?true:false);var gecko=((document.getElementById&&!document.all&&!opera&&!safari)?true:false);var msie=((window.ActiveXObject)?true:false);var version=false;if(msie){if(typeof document.body.style.maxHeight!="undefined"){version='7';}else{version='6';}}
if(opera){var tmp_version=window.opera.version().split('.');version=tmp_version[0]+'.'+tmp_version[1];}
if(gecko){if(navigator.registerContentHandler){version='2';}else{version='1.5';}
if((navigator.vendorSub)&&!version){version=navigator.vendorSub;}}
if(safari){try{if(console){if((window.onmousewheel!=='undefined')&&(window.onmousewheel===null)){version='2';}else{version='1.3';}}}catch(e){version='1.2';}}
var browsers={ua:navigator.userAgent,opera:opera,safari:safari,gecko:gecko,msie:msie,version:version}
return browsers;}
YAHOO.Tools.getBrowserAgent=function(){var ua=navigator.userAgent.toLowerCase();var opera=((ua.indexOf('opera')!=-1)?true:false);var safari=((ua.indexOf('safari')!=-1)?true:false);var firefox=((ua.indexOf('firefox')!=-1)?true:false);var msie=((ua.indexOf('msie')!=-1)?true:false);var mac=((ua.indexOf('mac')!=-1)?true:false);var unix=((ua.indexOf('x11')!=-1)?true:false);var win=((mac||unix)?false:true);var version=false;var mozilla=false;if(!firefox&&!safari&&(ua.indexOf('gecko')!=-1)){mozilla=true;var _tmp=ua.split('/');version=_tmp[_tmp.length-1].split(' ')[0];}
if(firefox){var _tmp=ua.split('/');version=_tmp[_tmp.length-1].split(' ')[0];}
if(msie){version=ua.substring((ua.indexOf('msie ')+5)).split(';')[0];}
if(safari){version=this.getBrowserEngine().version;}
if(opera){version=ua.substring((ua.indexOf('opera/')+6)).split(' ')[0];}
var browsers={ua:navigator.userAgent,opera:opera,safari:safari,firefox:firefox,mozilla:mozilla,msie:msie,mac:mac,win:win,unix:unix,version:version}
return browsers;}
YAHOO.Tools.checkFlash=function(){var br=this.getBrowserEngine();if(br.msie){try{var axo=new ActiveXObject("ShockwaveFlash.ShockwaveFlash.7");var versionStr=axo.GetVariable("$version");var tempArray=versionStr.split(" ");var tempString=tempArray[1];var versionArray=tempString.split(",");var flash=versionArray[0];}catch(e){}}else{var flashObj=null;var tokens,len,curr_tok;if(navigator.mimeTypes&&navigator.mimeTypes['application/x-shockwave-flash']){flashObj=navigator.mimeTypes['application/x-shockwave-flash'].enabledPlugin;}
if(flashObj==null){flash=false;}else{tokens=navigator.plugins['Shockwave Flash'].description.split(' ');len=tokens.length;while(len--){curr_tok=tokens[len];if(!isNaN(parseInt(curr_tok))){hasVersion=curr_tok;flash=hasVersion;break;}}}}
return flash;}
YAHOO.Tools.setAttr=function(attrsObj,elm){if(typeof elm=='string'){elm=$(elm);}
for(var i in attrsObj){switch(i.toLowerCase()){case'listener':if(attrsObj[i]instanceof Array){var ev=attrsObj[i][0];var func=attrsObj[i][1];var base=attrsObj[i][2];var scope=attrsObj[i][3];$E.addListener(elm,ev,func,base,scope);}
break;case'classname':case'class':elm.className=attrsObj[i];break;case'style':YAHOO.Tools.setStyleString(elm,attrsObj[i]);break;default:elm.setAttribute(i,attrsObj[i]);break;}}}
YAHOO.Tools.create=function(tagName){tagName=tagName.toLowerCase();elm=document.createElement(tagName);var txt=false;var attrsObj=false;if(!elm){return false;}
for(var i=1;i<arguments.length;i++){txt=arguments[i];if(typeof txt=='string'){_txt=YAHOO.Tools.makeTextObject(txt);elm.appendChild(_txt);}else if(txt instanceof Array){YAHOO.Tools.makeChildren(txt,elm);}else if(typeof txt=='object'){YAHOO.Tools.setAttr(txt,elm);}}
return elm;}
YAHOO.Tools.insertAfter=function(elm,curNode){if(curNode.nextSibling){curNode.parentNode.insertBefore(elm,curNode.nextSibling);}else{curNode.parentNode.appendChild(elm);}}
YAHOO.Tools.inArray=function(arr,val){if(arr instanceof Array){for(var i=(arr.length-1);i>=0;i--){if(arr[i]===val){return true;}}}
return false;}
YAHOO.Tools.checkBoolean=function(str){return((typeof str=='boolean')?true:false);}
YAHOO.Tools.checkNumber=function(str){return((isNaN(str))?false:true);}
YAHOO.Tools.PixelToEm=function(size){var data={};var sSize=(size/13);data.other=(Math.round(sSize*100)/100);data.msie=(Math.round((sSize*0.9759)*100)/100);return data;}
YAHOO.Tools.PixelToEmStyle=function(size,prop){var data='';var prop=((prop)?prop.toLowerCase():'width');var sSize=(size/13);data+=prop+':'+(Math.round(sSize*100)/100)+'em;';data+='*'+prop+':'+(Math.round((sSize*0.9759)*100)/100)+'em;';if((prop=='width')||(prop=='height')){data+='min-'+prop+':'+size+'px;';}
return data;}
YAHOO.Tools.base64Encode=function(str){var data="";var chr1,chr2,chr3,enc1,enc2,enc3,enc4;var i=0;do{chr1=str.charCodeAt(i++);chr2=str.charCodeAt(i++);chr3=str.charCodeAt(i++);enc1=chr1>>2;enc2=((chr1&3)<<4)|(chr2>>4);enc3=((chr2&15)<<2)|(chr3>>6);enc4=chr3&63;if(isNaN(chr2)){enc3=enc4=64;}else if(isNaN(chr3)){enc4=64;}
data=data+keyStr.charAt(enc1)+keyStr.charAt(enc2)+keyStr.charAt(enc3)+keyStr.charAt(enc4);}while(i<str.length);return data;}
YAHOO.Tools.base64Decode=function(str){var data="";var chr1,chr2,chr3,enc1,enc2,enc3,enc4;var i=0;str=str.replace(regExs.base64,"");do{enc1=keyStr.indexOf(str.charAt(i++));enc2=keyStr.indexOf(str.charAt(i++));enc3=keyStr.indexOf(str.charAt(i++));enc4=keyStr.indexOf(str.charAt(i++));chr1=(enc1<<2)|(enc2>>4);chr2=((enc2&15)<<4)|(enc3>>2);chr3=((enc3&3)<<6)|enc4;data=data+String.fromCharCode(chr1);if(enc3!=64){data=data+String.fromCharCode(chr2);}
if(enc4!=64){data=data+String.fromCharCode(chr3);}}while(i<str.length);return data;}
YAHOO.Tools.getQueryString=function(str){var qstr={};if(!str){var str=location.href.split('?');if(str.length!=2){str=['',location.href];}}else{var str=['',str];}
if(str[1].match('#')){var _tmp=str[1].split('#');qstr.hash=_tmp[1];str[1]=_tmp[0];}
if(str[1]){str=str[1].split('&');if(str.length){for(var i=0;i<str.length;i++){var part=str[i].split('=');if(part[0].indexOf('[')!=-1){if(part[0].indexOf('[]')!=-1){var arr=part[0].substring(0,part[0].length-2);if(!qstr[arr]){qstr[arr]=[];}
qstr[arr][qstr[arr].length]=part[1];}else{var arr=part[0].substring(0,part[0].indexOf('['));var data=part[0].substring((part[0].indexOf('[')+1),part[0].indexOf(']'));if(!qstr[arr]){qstr[arr]={};}
qstr[arr][data]=part[1];}}else{qstr[part[0]]=part[1];}}}}
return qstr;}
YAHOO.Tools.getQueryStringVar=function(str){var qs=this.getQueryString();if(qs[str]){return qs[str];}else{return false;}}
YAHOO.Tools.padDate=function(n){return n<10?'0'+n:n;}
YAHOO.Tools.encodeStr=function(str){if(/["\\\x00-\x1f]/.test(str)){return'"'+str.replace(/([\x00-\x1f\\"])/g,function(a,b){var c=jsonCodes[b];if(c){return c;}
c=b.charCodeAt();return'\\u00'+
Math.floor(c/16).toString(16)+
(c%16).toString(16);})+'"';}
return'"'+str+'"';}
YAHOO.Tools.encodeArr=function(arr){var a=['['],b,i,l=arr.length,v;for(i=0;i<l;i+=1){v=arr[i];switch(typeof v){case'undefined':case'function':case'unknown':break;default:if(b){a.push(',');}
a.push(v===null?"null":YAHOO.Tools.JSONEncode(v));b=true;}}
a.push(']');return a.join('');}
YAHOO.Tools.encodeDate=function(d){return'"'+d.getFullYear()+'-'+YAHOO.Tools.padDate(d.getMonth()+1)+'-'+YAHOO.Tools.padDate(d.getDate())+'T'+YAHOO.Tools.padDate(d.getHours())+':'+YAHOO.Tools.padDate(d.getMinutes())+':'+YAHOO.Tools.padDate(d.getSeconds())+'"';}
YAHOO.Tools.fixJSONDate=function(dateStr){var tmp=dateStr.split('T');var fixedDate=dateStr;if(tmp.length==2){var tmpDate=tmp[0].split('-');if(tmpDate.length==3){fixedDate=new Date(tmpDate[0],(tmpDate[1]-1),tmpDate[2]);var tmpTime=tmp[1].split(':');if(tmpTime.length==3){fixedDate.setHours(tmpTime[0],tmpTime[1],tmpTime[2]);}}}
return fixedDate;}
YAHOO.Tools.JSONEncode=function(o){if((typeof o=='undefined')||(o===null)){return'null';}else if(o instanceof Array){return YAHOO.Tools.encodeArr(o);}else if(o instanceof Date){return YAHOO.Tools.encodeDate(o);}else if(typeof o=='string'){return YAHOO.Tools.encodeStr(o);}else if(typeof o=='number'){return isFinite(o)?String(o):"null";}else if(typeof o=='boolean'){return String(o);}else{var a=['{'],b,i,v;for(var i in o){v=o[i];switch(typeof v){case'undefined':case'function':case'unknown':break;default:if(b){a.push(',');}
a.push(YAHOO.Tools.JSONEncode(i),':',((v===null)?"null":YAHOO.Tools.JSONEncode(v)));b=true;}}
a.push('}');return a.join('');}}
YAHOO.Tools.JSONParse=function(json,autoDate){var autoDate=((autoDate)?true:false);try{if(regExs.syntaxCheck.test(json)){var j=eval('('+json+')');if(autoDate){function walk(k,v){if(v&&typeof v==='object'){for(var i in v){if(v.hasOwnProperty(i)){v[i]=walk(i,v[i]);}}}
if(k.toLowerCase().indexOf('date')>=0){return YAHOO.Tools.fixJSONDate(v);}else{return v;}}
return walk('',j);}else{return j;}}}catch(e){console.log(e);}
throw new SyntaxError("parseJSON");}
YAHOO.tools=YAHOO.Tools;YAHOO.TOOLS=YAHOO.Tools;YAHOO.util.Dom.create=YAHOO.Tools.create;$A=YAHOO.util.Anim;$E=YAHOO.util.Event;$D=YAHOO.util.Dom;$T=YAHOO.Tools;$=YAHOO.util.Dom.get;$$=YAHOO.util.Dom.getElementsByClassName;
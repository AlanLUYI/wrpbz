// All material copyright ESRI, All Rights Reserved, unless otherwise specified.
// See http://js.arcgis.com/3.13/esri/copyright.txt for details.
//>>built
require({cache:{"dojo/data/util/filter":function(){define(["../../_base/lang"],function(l){var b={};l.setObject("dojo.data.util.filter",b);b.patternToRegExp=function(b,l){for(var g="^",d=null,a=0;a<b.length;a++)switch(d=b.charAt(a),d){case "\\":g+=d;a++;g+=b.charAt(a);break;case "*":g+=".*";break;case "?":g+=".";break;case "$":case "^":case "/":case "+":case ".":case "|":case "(":case ")":case "{":case "}":case "[":case "]":g+="\\";default:g+=d}g+="$";return l?RegExp(g,"mi"):RegExp(g,"m")};return b})},
"dojo/data/util/simpleFetch":function(){define(["../../_base/lang","../../_base/kernel","./sorter"],function(l,b,n){var m={};l.setObject("dojo.data.util.simpleFetch",m);m.errorHandler=function(g,d){d.onError&&d.onError.call(d.scope||b.global,g,d)};m.fetchHandler=function(g,d){var a=d.abort||null,q=!1,e=d.start?d.start:0,c=d.count&&Infinity!==d.count?e+d.count:g.length;d.abort=function(){q=!0;a&&a.call(d)};var f=d.scope||b.global;d.store||(d.store=this);d.onBegin&&d.onBegin.call(f,g.length,d);d.sort&&
g.sort(n.createSortFunction(d.sort,this));if(d.onItem)for(var k=e;k<g.length&&k<c;++k){var h=g[k];q||d.onItem.call(f,h,d)}d.onComplete&&!q&&(k=null,d.onItem||(k=g.slice(e,c)),d.onComplete.call(f,k,d))};m.fetch=function(b){b=b||{};b.store||(b.store=this);this._fetchItems(b,l.hitch(this,"fetchHandler"),l.hitch(this,"errorHandler"));return b};return m})},"dojo/data/util/sorter":function(){define(["../../_base/lang"],function(l){var b={};l.setObject("dojo.data.util.sorter",b);b.basicComparator=function(b,
l){var g=-1;null===b&&(b=void 0);null===l&&(l=void 0);if(b==l)g=0;else if(b>l||null==b)g=1;return g};b.createSortFunction=function(l,m){function g(a,c,b,f){return function(q,e){var k=f.getValue(q,a),d=f.getValue(e,a);return c*b(k,d)}}for(var d=[],a,q=m.comparatorMap,e=b.basicComparator,c=0;c<l.length;c++){a=l[c];var f=a.attribute;if(f){a=a.descending?-1:1;var k=e;q&&("string"!==typeof f&&"toString"in f&&(f=f.toString()),k=q[f]||e);d.push(g(f,a,k,m))}}return function(a,c){for(var b=0;b<d.length;){var f=
d[b++](a,c);if(0!==f)return f}return 0}};return b})},"dojox/data/CsvStore":function(){define("dojo/_base/lang dojo/_base/declare dojo/_base/xhr dojo/_base/kernel dojo/data/util/filter dojo/data/util/simpleFetch".split(" "),function(l,b,n,m,g,d){b=b("dojox.data.CsvStore",null,{constructor:function(a){this._attributes=[];this._attributeIndexes={};this._dataArray=[];this._arrayOfAllItems=[];this._loadFinished=!1;a.url&&(this.url=a.url);this._csvData=a.data;a.label?this.label=a.label:""===this.label&&
(this.label=void 0);this._storeProp="_csvStore";this._idProp="_csvId";this._features={"dojo.data.api.Read":!0,"dojo.data.api.Identity":!0};this._loadInProgress=!1;this._queuedFetches=[];this.identifier=a.identifier;""===this.identifier?delete this.identifier:this._idMap={};"separator"in a&&(this.separator=a.separator);"urlPreventCache"in a&&(this.urlPreventCache=a.urlPreventCache?!0:!1)},url:"",label:"",identifier:"",separator:",",urlPreventCache:!1,_assertIsItem:function(a){if(!this.isItem(a))throw Error(this.declaredClass+
": a function was passed an item argument that was not an item");},_getIndex:function(a){a=this.getIdentity(a);this.identifier&&(a=this._idMap[a]);return a},getValue:function(a,b,e){this._assertIsItem(a);var c=e;if("string"===typeof b)b=this._attributeIndexes[b],null!=b&&(c=this._dataArray[this._getIndex(a)][b]||e);else throw Error(this.declaredClass+": a function was passed an attribute argument that was not a string");return c},getValues:function(a,b){var e=this.getValue(a,b);return e?[e]:[]},getAttributes:function(a){this._assertIsItem(a);
var b=[];a=this._dataArray[this._getIndex(a)];for(var e=0;e<a.length;e++)""!==a[e]&&b.push(this._attributes[e]);return b},hasAttribute:function(a,b){this._assertIsItem(a);if("string"===typeof b){var e=this._attributeIndexes[b],c=this._dataArray[this._getIndex(a)];return"undefined"!==typeof e&&e<c.length&&""!==c[e]}throw Error(this.declaredClass+": a function was passed an attribute argument that was not a string");},containsValue:function(a,b,e){var c=void 0;"string"===typeof e&&(c=g.patternToRegExp(e,
!1));return this._containsValue(a,b,e,c)},_containsValue:function(a,b,e,c){a=this.getValues(a,b);for(b=0;b<a.length;++b){var f=a[b];if("string"===typeof f&&c)return null!==f.match(c);if(e===f)return!0}return!1},isItem:function(a){if(a&&a[this._storeProp]===this)if(a=a[this._idProp],this.identifier){if(this._dataArray[this._idMap[a]])return!0}else if(0<=a&&a<this._dataArray.length)return!0;return!1},isItemLoaded:function(a){return this.isItem(a)},loadItem:function(a){},getFeatures:function(){return this._features},
getLabel:function(a){if(this.label&&this.isItem(a))return this.getValue(a,this.label)},getLabelAttributes:function(a){return this.label?[this.label]:null},_fetchItems:function(a,b,e){var c=this,f=function(a,f){var e=null;if(a.query){var d,k,e=[],h=a.queryOptions?a.queryOptions.ignoreCase:!1,u={};for(d in a.query)k=a.query[d],"string"===typeof k&&(u[d]=g.patternToRegExp(k,h));for(h=0;h<f.length;++h){var G=!0,H=f[h];for(d in a.query)k=a.query[d],c._containsValue(H,d,k,u[d])||(G=!1);G&&e.push(H)}}else e=
f.slice(0,f.length);b(e,a)};if(this._loadFinished)f(a,this._arrayOfAllItems);else if(""!==this.url)if(this._loadInProgress)this._queuedFetches.push({args:a,filter:f});else{this._loadInProgress=!0;var k=n.get({url:c.url,handleAs:"text",preventCache:c.urlPreventCache});k.addCallback(function(b){try{c._processData(b),f(a,c._arrayOfAllItems),c._handleQueuedFetches()}catch(d){e(d,a)}});k.addErrback(function(b){c._loadInProgress=!1;if(e)e(b,a);else throw b;});var d=null;a.abort&&(d=a.abort);a.abort=function(){k&&
-1===k.fired&&k.cancel();d&&d.call(a)}}else if(this._csvData)try{this._processData(this._csvData),this._csvData=null,f(a,this._arrayOfAllItems)}catch(l){e(l,a)}else{var m=Error(this.declaredClass+": No CSV source data was provided as either URL or String data input.");if(e)e(m,a);else throw m;}},close:function(a){},_getArrayOfArraysFromCsvFileContents:function(a){if(l.isString(a)){var b=RegExp("^\\s+","g"),e=RegExp("\\s+$","g"),c=RegExp('""',"g"),f=[],d=this._splitLines(a);for(a=0;a<d.length;++a){var h=
d[a];if(0<h.length){for(var h=h.split(this.separator),g=0;g<h.length;){var m=h[g].replace(b,""),p=m.replace(e,""),n=p.charAt(0),t=p.charAt(p.length-1),z=p.charAt(p.length-2),A=p.charAt(p.length-3);if(2===p.length&&'""'==p)h[g]="";else if('"'==n&&('"'!=t||'"'==t&&'"'==z&&'"'!=A)){if(g+1===h.length)return;h[g]=m+this.separator+h[g+1];h.splice(g+1,1)}else'"'==n&&'"'==t&&(p=p.slice(1,p.length-1),p=p.replace(c,'"')),h[g]=p,g+=1}f.push(h)}}this._attributes=f.shift();for(a=0;a<this._attributes.length;a++)this._attributeIndexes[this._attributes[a]]=
a;this._dataArray=f}},_splitLines:function(a){var b=[],e,c="",f=!1;for(e=0;e<a.length;e++){var d=a.charAt(e);switch(d){case '"':f=!f;c+=d;break;case "\r":f?c+=d:(b.push(c),c="",e<a.length-1&&"\n"==a.charAt(e+1)&&e++);break;case "\n":f?c+=d:(b.push(c),c="");break;default:c+=d}}""!==c&&b.push(c);return b},_processData:function(a){this._getArrayOfArraysFromCsvFileContents(a);this._arrayOfAllItems=[];if(this.identifier&&void 0===this._attributeIndexes[this.identifier])throw Error(this.declaredClass+": Identity specified is not a column header in the data set.");
for(a=0;a<this._dataArray.length;a++){var b=a;this.identifier&&(b=this._dataArray[a][this._attributeIndexes[this.identifier]],this._idMap[b]=a);this._arrayOfAllItems.push(this._createItemFromIdentity(b))}this._loadFinished=!0;this._loadInProgress=!1},_createItemFromIdentity:function(a){var b={};b[this._storeProp]=this;b[this._idProp]=a;return b},getIdentity:function(a){return this.isItem(a)?a[this._idProp]:null},fetchItemByIdentity:function(a){var b,d=a.scope?a.scope:m.global;if(this._loadFinished)b=
this._createItemFromIdentity(a.identity),this.isItem(b)||(b=null),a.onItem&&a.onItem.call(d,b);else{var c=this;if(""!==this.url)this._loadInProgress?this._queuedFetches.push({args:a}):(this._loadInProgress=!0,b=n.get({url:c.url,handleAs:"text"}),b.addCallback(function(b){try{c._processData(b);var f=c._createItemFromIdentity(a.identity);c.isItem(f)||(f=null);a.onItem&&a.onItem.call(d,f);c._handleQueuedFetches()}catch(g){a.onError&&a.onError.call(d,g)}}),b.addErrback(function(b){this._loadInProgress=
!1;a.onError&&a.onError.call(d,b)}));else if(this._csvData)try{c._processData(c._csvData),c._csvData=null,b=c._createItemFromIdentity(a.identity),c.isItem(b)||(b=null),a.onItem&&a.onItem.call(d,b)}catch(f){a.onError&&a.onError.call(d,f)}}},getIdentityAttributes:function(a){return this.identifier?[this.identifier]:null},_handleQueuedFetches:function(){if(0<this._queuedFetches.length){for(var a=0;a<this._queuedFetches.length;a++){var b=this._queuedFetches[a],d=b.filter,c=b.args;d?d(c,this._arrayOfAllItems):
this.fetchItemByIdentity(b.args)}this._queuedFetches=[]}}});l.extend(b,d);return b})},"esri/arcgis/csv":function(){define("dojo/_base/lang dojo/_base/array dojo/_base/Deferred dojo/sniff dojo/number dojox/data/CsvStore ../kernel ../config ../request ../SpatialReference ../geometry/jsonUtils ../geometry/webMercatorUtils".split(" "),function(l,b,n,m,g,d,a,q,e,c,f,k){function h(a){var c=0,f="";b.forEach([","," ",";","|","\t"],function(b){var d=a.split(b).length;d>c&&(c=d,f=b)});return f}function C(a,
b){if(!a||"[object Date]"!==Object.prototype.toString.call(a)||isNaN(a.getTime()))return!1;var c=!0;if(m("chrome")&&/\d+\W*$/.test(b)){var f=b.match(/[a-zA-Z]{2,}/);if(f){for(var c=!1,d=0,e=f.length,g=/^((jan(uary)?)|(feb(ruary)?)|(mar(ch)?)|(apr(il)?)|(may)|(jun(e)?)|(jul(y)?)|(aug(ust)?)|(sep(tember)?)|(oct(ober)?)|(nov(ember)?)|(dec(ember)?)|(am)|(pm)|(gmt)|(utc))$/i;!c&&d<=e&&!(c=!g.test(f[d]));)d++;c=!c}}return c}function D(a,c,f){var e=a.indexOf("\n"),e=l.trim(a.substr(0,e)),k=c.columnDelimiter;
k||(k=h(e));var w=new d({data:a,separator:k});w.fetch({onComplete:function(a,d){var e=0,u={layerDefinition:c.layerDefinition,featureSet:{features:[],geometryType:"esriGeometryPoint"}},k=u.layerDefinition.objectIdField,h=u.layerDefinition.fields;!k&&!b.some(h,function(a){return"esriFieldTypeOID"===a.type?(k=a.name,!0):!1})&&(h.push({name:"__OBJECTID",alias:"__OBJECTID",type:"esriFieldTypeOID",editable:!1}),k="__OBJECTID");var r,m,v=w._attributes,p=[],n=[];b.forEach(h,function(a){"esriFieldTypeDate"===
a.type?p.push(a.name):("esriFieldTypeDouble"===a.type||"esriFieldTypeInteger"===a.type)&&n.push(a.name)});c.locationInfo&&"coordinates"===c.locationInfo.locationType?(r=c.locationInfo.latitudeFieldName,m=c.locationInfo.longitudeFieldName):b.forEach(v,function(a){var c;c=b.indexOf(z,a.toLowerCase());-1!==c&&(r=a);c=b.indexOf(A,a.toLowerCase());-1!==c&&(m=a)});if(!r||!m)setTimeout(function(){console.error("File does not seem to contain fields with point coordinates.")},1),f&&f(null,Error("File does not seem to contain fields with point coordinates."));
else{-1===b.indexOf(n,r)&&n.push(r);-1===b.indexOf(n,m)&&n.push(m);var q;l.isArray(c.outFields)&&-1===b.indexOf(c.outFields,"*")&&(q=c.outFields);b.forEach(v,function(a){b.some(h,function(b){return a===b.name})||h.push({name:a,alias:a,type:a===r||a===m?"esriFieldTypeDouble":"esriFieldTypeString"})});var v=0,t=a.length;for(v;v<t;v++){var x=a[v],y=w.getAttributes(x),s={};b.forEach(y,function(a){if(a&&(a===r||a===m||!q||-1<b.indexOf(q,a))){var c=a;0===a.length&&b.forEach(h,function(b,c){b.name==="attribute_"+
(c-1)&&(a="attribute_"+(c-1))});if(-1<b.indexOf(p,a)){var c=w.getValue(x,c),f=new Date(c);s[a]=C(f,c)?f.getTime():null}else if(-1<b.indexOf(n,a)){f=g.parse(w.getValue(x,c));if((a===r||a===m)&&(isNaN(f)||181<Math.abs(f)))f=parseFloat(w.getValue(x,c));isNaN(f)?s[a]=null:s[a]=f}else s[a]=w.getValue(x,c)}});s[k]=e;e++;var y=s[r],B=s[m];null==B||(null==y||isNaN(y)||isNaN(B))||(q&&-1===b.indexOf(q,r)&&delete s[r],q&&-1===b.indexOf(q,m)&&delete s[m],u.featureSet.features.push({geometry:{x:B,y:y,spatialReference:{wkid:4326}},
attributes:s}))}u.layerDefinition.name="csv";f&&f(u)}},onError:function(a){console.error("Error fetching items from CSV store: ",a);f&&f(null,a)}});return!0}function p(a,c,d,e,g,h){0===a.length&&g(null);var m=f.getGeometryType(c),n=[];b.forEach(a,function(a){a=new m(a);a.spatialReference=d;n.push(a)},this);c=[102113,102100,3857];d.wkid&&4326===d.wkid&&e.wkid&&-1<b.indexOf(c,e.wkid)?(b.forEach(n,function(a){a.xmin?(a.xmin=Math.max(a.xmin,-180),a.xmax=Math.min(a.xmax,180),a.ymin=Math.max(a.ymin,-89.99),
a.ymax=Math.min(a.ymax,89.99)):a.rings?b.forEach(a.rings,function(a){b.forEach(a,function(a){a[0]=Math.min(Math.max(a[0],-180),180);a[1]=Math.min(Math.max(a[1],-89.99),89.99)},this)},this):a.paths?b.forEach(a.paths,function(a){b.forEach(a,function(a){a[0]=Math.min(Math.max(a[0],-180),180);a[1]=Math.min(Math.max(a[1],-89.99),89.99)},this)},this):a.x&&(a.x=Math.min(Math.max(a.x,-180),180),a.y=Math.min(Math.max(a.y,-89.99),89.99))},this),a=[],b.forEach(n,function(b){b=k.geographicToWebMercator(b);102100!==
e.wkid&&(b.spatialReference=e);a.push(b.toJson())},this),g(a)):null!==d.wkid&&-1<b.indexOf(c,d.wkid)&&null!==e.wkid&&4326===e.wkid?(a=[],b.forEach(n,function(b){a.push(k.webMercatorToGeographic(b).toJson())},this),g(a)):(c=function(c,f){c&&c.length===a.length?(a=[],b.forEach(c,function(b){b&&(b.rings&&0<b.rings.length&&0<b.rings[0].length&&0<b.rings[0][0].length&&!isNaN(b.rings[0][0][0])&&!isNaN(b.rings[0][0][1])||b.paths&&0<b.paths.length&&0<b.paths[0].length&&0<b.paths[0][0].length&&!isNaN(b.paths[0][0][0])&&
!isNaN(b.paths[0][0][1])||b.xmin&&!isNaN(b.xmin)&&b.ymin&&!isNaN(b.ymin)||b.x&&!isNaN(b.x)&&b.y&&!isNaN(b.y))?a.push(b.toJson()):a.push(null)},this),g(a)):h(c,f)},q.defaults.geometryService?q.defaults.geometryService.project(n,e,l.hitch(this,c),h):g(null))}function E(a,c){var f=[102113,102100,3857];return a&&c&&a.wkid===c.wkid&&a.wkt===c.wkt||a&&c&&a.wkid&&c.wkid&&-1<b.indexOf(f,a.wkid)&&-1<b.indexOf(f,c.wkid)?!0:!1}function t(a,d,e,g){if(a.featureSet&&0!==a.featureSet.features.length)if(E(e,d))g(a);
else{var h,k=function(c){var f=[];b.forEach(a.featureSet.features,function(a,b){c[b]&&(a.geometry=c[b],f.push(a))},this);g(a)},m=function(b,c){console.error("error projecting featureSet ("+a.layerDefinition.name+"). Final try.");g(a)},n=function(b,c){console.error("error projecting featureSet ("+a.layerDefinition.name+"). Try one more time.");p(h,a.featureSet.geometryType,d,e,l.hitch(this,k),l.hitch(this,m))};a.featureSet.features&&0<a.featureSet.features.length?(h=[],b.forEach(a.featureSet.features,
function(b){if(b.geometry.toJson)h.push(b.geometry);else{var c=f.getGeometryType(a.featureSet.geometryType);h.push(new c(b.geometry))}}),d.toJson||(d=new c(d)),e.toJson||(e=new c(e)),p(h,a.featureSet.geometryType,d,e,l.hitch(this,k),l.hitch(this,n))):g(a)}}var z="lat latitude y ycenter latitude83 latdecdeg POINT-Y".split(" "),A="lon lng long longitude x xcenter longitude83 longdecdeg POINT-X".split(" "),F={latFieldStrings:z,longFieldStrings:A,buildCSVFeatureCollection:function(a){var b=new n,c=function(a,
c){c?b.errback(c):b.callback(a)},f={url:a.url,handleAs:"text",load:function(b){D(b,a,l.hitch(this,c))},error:function(a){b.errback(a);console.error("error: "+a)}};-1<a.url.indexOf("arcgis.com")&&(-1<a.url.indexOf("/content/items")&&-1<a.url.indexOf("/data"))&&(f.headers={"Content-Type":""});e(f,{usePost:!1});return b},projectFeatureCollection:function(a,b,f){var d=new n;f||(f=new c({wkid:4326}));t(a,f,b,l.hitch(this,function(a){d.callback(a)}));return d},generateDefaultPopupInfo:function(a){var c=
{esriFieldTypeDouble:1,esriFieldTypeSingle:1},f={esriFieldTypeInteger:1,esriFieldTypeSmallInteger:1},d={esriFieldTypeDate:1},e=null;a=b.map(a.layerDefinition.fields,l.hitch(this,function(a){"NAME"===a.name.toUpperCase()&&(e=a.name);var b="esriFieldTypeOID"!==a.type&&"esriFieldTypeGlobalID"!==a.type&&"esriFieldTypeGeometry"!==a.type,g=null;if(b){var h=a.name.toLowerCase();if(-1<",stretched value,fnode_,tnode_,lpoly_,rpoly_,poly_,subclass,subclass_,rings_ok,rings_nok,".indexOf(","+h+",")||-1<h.indexOf("area")||
-1<h.indexOf("length")||-1<h.indexOf("shape")||-1<h.indexOf("perimeter")||-1<h.indexOf("objectid")||h.indexOf("_")===h.length-1||h.indexOf("_i")===h.length-2&&1<h.length)b=!1;a.type in f?g={places:0,digitSeparator:!0}:a.type in c?g={places:2,digitSeparator:!0}:a.type in d&&(g={dateFormat:"shortDateShortTime"})}return l.mixin({},{fieldName:a.name,label:a.alias,isEditable:!0,tooltip:"",visible:b,format:g,stringFieldOption:"textbox"})}));return{title:e?"{"+e+"}":"",fieldInfos:a,description:null,showAttachments:!1,
mediaInfos:[]}},_getSeparator:h,_isValidDate:C,_processCsvData:D,_projectGeometries:p,_sameSpatialReference:E,_projectFeatureSet:t};m("extend-esri")&&l.setObject("arcgis.csv",F,a);return F})},"*noref":1}});
define("esri/layers/CSVLayer","dojo/_base/array dojo/_base/declare dojo/_base/lang dojo/has ../kernel ../arcgis/csv ./FeatureLayer ../geometry/Extent ../tasks/FeatureSet".split(" "),function(l,b,n,m,g,d,a,q,e){b=b(a,{declaredClass:"esri.layers.CSVLayer",_preventInit:!0,_fieldTypeMap:{Date:"esriFieldTypeDate",Number:"esriFieldTypeDouble",String:"esriFieldTypeString"},constructor:function(a,b){this.url=a;b=n.mixin({},b);this.columnDelimiter=b.columnDelimiter;this.latitudeFieldName=b.latitudeFieldName;
this.longitudeFieldName=b.longitudeFieldName;var d=b.layerDefinition;d||(d={fields:b.fields||[],geometryType:"esriGeometryPoint",copyrightText:b.copyright},b.fields&&l.forEach(b.fields,function(a){a.type=this._fieldTypeMap[a.type||"String"];a.alias||(a.alias=a.name)},this));this._buildCsvFcParam={url:this.url,columnDelimiter:this.columnDelimiter,layerDefinition:d,outFields:b.outFields};this.latitudeFieldName&&this.longitudeFieldName&&(this._buildCsvFcParam.locationInfo={locationType:"coordinates",
latitudeFieldName:this.latitudeFieldName,longitudeFieldName:this.longitudeFieldName});this._projectFeatures=n.hitch(this,this._projectFeatures);this._addFeatures=n.hitch(this,this._addFeatures);this._initCSVLayer(b)},refresh:function(){this._fireUpdateStart();this.applyEdits(null,null,this.graphics);this._loadFeatures()},_setMap:function(a){var b=this.inherited(arguments);this._fireUpdateStart();this._projectFeatures(this._csvFC).then(this._addFeatures).otherwise(this._errorHandler);this._csvFC=null;
return b},_initCSVLayer:function(a){var b=this;d.buildCSVFeatureCollection(this._buildCsvFcParam).then(function(d){b._csvFC=d;var e=d.layerDefinition;e.extent=b._getFCExtent(d);a.outFields||(a.outFields=["*"]);b._initFeatureLayer({layerDefinition:e},a)}).otherwise(this._errorHandler)},_loadFeatures:function(){d.buildCSVFeatureCollection(this._buildCsvFcParam).then(this._projectFeatures).then(this._addFeatures).otherwise(this._errorHandler)},_projectFeatures:function(a){return d.projectFeatureCollection(a,
this._map.spatialReference)},_addFeatures:function(a){a=new e(a.featureSet);this.applyEdits(a.features,null,null);this._fireUpdateEnd()},_getFCExtent:function(a){var b;if(a&&a.featureSet&&a.featureSet.features){a=a.featureSet.features;var d=a.length;if(1<d){var e=a[0].geometry;b=new q(e.x,e.y,e.x,e.y);for(d-=1;0<d;d--)e=a[d].geometry,b.xmin=Math.min(b.xmin,e.x),b.ymin=Math.min(b.ymin,e.y),b.xmax=Math.max(b.xmax,e.x),b.ymax=Math.max(b.ymax,e.y);0>=b.getWidth()&&0>=b.getHeight()&&(b=null)}}return b}});
m("extend-esri")&&n.setObject("layers.CSVLayer",b,g);return b});
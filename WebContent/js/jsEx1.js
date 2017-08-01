/**
 * author:zzj
 * date:2013.12.1
 * info:js帮助类
 */
//除法函数，用来得到精确的除法结果
//说明：javascript的除法结果会有误差，在两个浮点数相除的时候会比较明显。这个函数返回较为精确的除法结果。
//调用：accDiv(arg1,arg2)
//返回值：arg1除以arg2的精确结果
jQuery.bindModel = function(dom, model) {
	if (model) {
		var html = dom.innerHTML;
		for ( var p in model) {
			if (!(typeof (model[p]) == "function")) {
				eval("var re=/{" + p + "}/g;");
				html = html.replace(re, model[p]);
			}
		}
		dom.innerHTML = html.replace(new RegExp(/(null)/g),"");
//		$.each($("input[type=radio]"), function(a, b) {
		$.each($(dom).find("input[type=radio]"), function(a, b) {
			b.checked = b.value == b.title;
		});
//		$.each($("input[type=checkbox]"), function(a, b) {
		$.each($(dom).find("input[type=checkbox]"), function(a, b) {
			b.checked = b.title.indexOf(b.value) > -1;
		});
//		$.each($("select"), function(a, b) {			
		$.each($(dom).find("select"), function(a, b) {
			for (var i=0;i<b.options.length;i++)
			{
				if(b.options.item(i).value.indexOf(b.title) > -1 && b.options.item(i).value.length == b.title.length)
				{
					b.selectedIndex = i;break;
				}
			}
		});
		$.each($("WDate"), function(a, b) {			
			
		});
	} else {
		//$.each($("input"), function(a, b) { //mcg 2013-03-09,引起整个网页的input都变空，修改后可以只针对dom里面的input元素
		$.each($(dom, "input"), function(a, b) {
			if (b.type == "text")
				b.value = "";
			else
				b.checked = false;
		});
	}
	$.clearnull(dom);
}; 
jQuery.clearnull = function(dom) {
  	var html = dom.innerHTML;
//    $.each($("input"), function (a, b) {
  	$.each($(dom).find("input"), function(a, b) {
        if ((b.type == "text" || b.type == "hidden") && b.value == "null")
            b.value = "";

        if ((b.type == "text" || b.type == "hidden") && b.value.substr(b.value.length - 1, 1) == "}" && b.value.substr(0, 1) == "{")
            b.value = "";
        
        if ((b.type == "text" || b.type == "hidden") && b.value.substr(0,10) == "1900-01-01" )
            b.value = "";
    });
  	//修改页面值属性
    $(dom).find("input[value]").each(function(){
    	$(this).attr('value',$(this).val());
    });
//	$.each($(dom,"textarea"), function(a, b) {
    $.each($(dom).find("textarea"), function(a, b) {
        if (b.innerHTML.substr(b.value.length - 1, 1) == "}" && b.innerHTML.substr(0, 1) == "{")
            b.innerHTML = "";
        if (b.innerHTML=="null")
            b.innerHTML = "";
	});
};

function cacl(arr, callback) {
  var ret;
  for (var i=0; i<arr.length;i++) {
    ret = callback(arr[i], ret);
  }
  return ret;
}
Array.prototype.max = function () {
  return cacl(this, function (item, max) {
    if (!(max > item)) {
      return item;
    }
    else {
      return max;
    }
  });
};
Array.prototype.min = function () {
  return cacl(this, function (item, min) {
    if (!(min < item)) {
      return item;
    }
    else {
      return min;
    }
  });
};
Array.prototype.sum = function () {
  return cacl(this, function (item, sum) {
    if (typeof (sum) == 'undefined') {
      return item;
    }
    else {
      return sum += item;
    }
  });
};
Array.prototype.avg = function () {
  if (this.length == 0) {
    return 0;
  }
  return this.sum(this) / this.length;
};
function accDiv(arg1,arg2){
    var t1=0,t2=0,r1,r2;
    try{t1=arg1.toString().split(".")[1].length}catch(e){}
    try{t2=arg2.toString().split(".")[1].length}catch(e){}
    with(Math){
        r1=Number(arg1.toString().replace(".",""));
        r2=Number(arg2.toString().replace(".",""));
        return (r1/r2)*pow(10,t2-t1);
    }
}
//给Number类型增加一个div方法，调用起来更加方便。
Number.prototype.div = function (arg){
    return accDiv(this, arg);
};
//乘法函数，用来得到精确的乘法结果
//说明：javascript的乘法结果会有误差，在两个浮点数相乘的时候会比较明显。这个函数返回较为精确的乘法结果。
//调用：accMul(arg1,arg2)
//返回值：arg1乘以arg2的精确结果
function accMul(arg1,arg2)
{
    var m=0,s1=arg1.toString(),s2=arg2.toString();
    try{m+=s1.split(".")[1].length}catch(e){}
    try{m+=s2.split(".")[1].length}catch(e){}
    return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m);
}
//给Number类型增加一个mul方法，调用起来更加方便。
//Number.prototype.mul = function (arg){
//    return this=accMul(arg, this);
//};
//加法函数，用来得到精确的加法结果
//说明：javascript的加法结果会有误差，在两个浮点数相加的时候会比较明显。这个函数返回较为精确的加法结果。
//调用：accAdd(arg1,arg2)
//返回值：arg1加上arg2的精确结果
function accAdd(arg1,arg2){
    var r1,r2,m;
    try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}
    try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}
    m=Math.pow(10,Math.max(r1,r2));
    return (arg1*m+arg2*m)/m;
}
//给Number类型增加一个add方法，调用起来更加方便。
Number.prototype.add = function (arg){
    return accAdd(arg,this);
}
//减法函数
function accSub(arg1,arg2){
     var r1,r2,m,n;
     try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}
     try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}
     m=Math.pow(10,Math.max(r1,r2));
     //last modify by deeka
     //动态控制精度长度
     n=(r1>=r2)?r1:r2;
     return ((arg2*m-arg1*m)/m).toFixed(n);
}
///给number类增加一个sub方法，调用起来更加方便
//Number.prototype.sub = function (arg){
//    return this=accSub(arg,this);
//};

Array.prototype.mysearch=function(value,key){if(key){return $.grep(this,function(obj,i){return obj[key]==value;})[0];}else{return $.grep(this,function(obj,i){return obj==value;})[0];}};
//Array.prototype.remove = function (v) { this.splice(this.indexOf(v) == -1 ? this.length : this.indexOf(v), 1); }
//Array.prototype.max=function(p){
//	if(p) return this.getarr(p).max();
//	else return Math.max.apply({},this);
//};
//Array.prototype.min=function(p){
//	if(p) return this.getarr(p).min();
//	else return Math.min.apply({},this);
//};
//Array.prototype.getarr=function(p){
//	var arr=[];
//	for(var i=0;i<this.length;i++){arr.push(this[i][p]);}
//	return arr;
//}
//Array.prototype.sum=function(p){
//	var arr=this,sum=0;
//	if(p) arr=this.getarr(p);
//	for(var i=0;i<this.length;i++){sum=sum.add(arr[i]);}
//	return sum;
//}

//String.format = function() {
//  var s = arguments[0];
//  for (var i = 0; i < arguments.length - 1; i++) {       
//    var reg = new RegExp("\\{" + i + "\\}", "gm");             
//    s = s.replace(reg, arguments[i + 1]);
//  }
//  return s;
//}

String.prototype.format = function(args) {
	if (arguments.length > 0) {
		var result = this;
		if (arguments.length == 1 && typeof (args) == "object") {
			for ( var key in args) {
				var reg = new RegExp("({" + key + "})", "g");
				result = result.replace(reg, args[key]);
			}
		} else {
			for ( var i = 0; i < arguments.length; i++) {
				if (arguments[i] == undefined)
					arguments[i]=" ";
				if (arguments[i] == undefined) {
					return "";
				} else {
					var reg = new RegExp("({[" + i + "]})", "g");
					result = result.replace(reg, arguments[i]);
				}
			}
		}
		return result;
	} else {
		return this;
	}
}
// 对Date的扩展，将 Date 转化为指定格式的String
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符， 
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
// 例子： 
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 
// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18 
Date.prototype.format = function (fmt) { //author: meizz 
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};
Date.prototype.addDay=function(days){
	this.setDate(this.getDate() + days);
	return this;
};
Date.prototype.addMonth=function(month){
	this.setMonth(this.getMonth() + month);
	return this;
};
Date.prototype.addHour=function(hours){
	this.setHours(this.getHours() + hours);
	return this;
};
jQuery.getParams=jQuery.getUrlVars = function (name) {
    //return decodeURI(
        //(RegExp(name + '=' + '(.+?)(&|$)').exec(location.search) || [, null])[1]
    //);
	return (RegExp(name + '=' + '(.+?)(&|$)').exec(location.search) || [, null])[1];
};


/**
 * Copyright (c) 2005 - 2010, James Auldridge
 * All rights reserved.
 *
 * Licensed under the BSD, MIT, and GPL (your choice!) Licenses:
 *  http://code.google.com/p/cookies/wiki/License
 *
 */
var jaaulde=window.jaaulde||{};jaaulde.utils=jaaulde.utils||{};jaaulde.utils.cookies=(function(){var resolveOptions,assembleOptionsString,parseCookies,constructor,defaultOptions={expiresAt:null,path:'/',domain:null,secure:false};resolveOptions=function(options){var returnValue,expireDate;if(typeof options!=='object'||options===null){returnValue=defaultOptions;}else
{returnValue={expiresAt:defaultOptions.expiresAt,path:defaultOptions.path,domain:defaultOptions.domain,secure:defaultOptions.secure};if(typeof options.expiresAt==='object'&&options.expiresAt instanceof Date){returnValue.expiresAt=options.expiresAt;}else if(typeof options.hoursToLive==='number'&&options.hoursToLive!==0){expireDate=new Date();expireDate.setTime(expireDate.getTime()+(options.hoursToLive*60*60*1000));returnValue.expiresAt=expireDate;}if(typeof options.path==='string'&&options.path!==''){returnValue.path=options.path;}if(typeof options.domain==='string'&&options.domain!==''){returnValue.domain=options.domain;}if(options.secure===true){returnValue.secure=options.secure;}}return returnValue;};assembleOptionsString=function(options){options=resolveOptions(options);return((typeof options.expiresAt==='object'&&options.expiresAt instanceof Date?'; expires='+options.expiresAt.toGMTString():'')+'; path='+options.path+(typeof options.domain==='string'?'; domain='+options.domain:'')+(options.secure===true?'; secure':''));};parseCookies=function(){var cookies={},i,pair,name,value,separated=document.cookie.split(';'),unparsedValue;for(i=0;i<separated.length;i=i+1){pair=separated[i].split('=');name=pair[0].replace(/^\s*/,'').replace(/\s*$/,'');try
{value=decodeURIComponent(pair[1]);}catch(e1){value=pair[1];}if(typeof JSON==='object'&&JSON!==null&&typeof JSON.parse==='function'){try
{unparsedValue=value;value=JSON.parse(value);}catch(e2){value=unparsedValue;}}cookies[name]=value;}return cookies;};constructor=function(){};constructor.prototype.get=function(cookieName){var returnValue,item,cookies=parseCookies();if(typeof cookieName==='string'){returnValue=(typeof cookies[cookieName]!=='undefined')?cookies[cookieName]:null;}else if(typeof cookieName==='object'&&cookieName!==null){returnValue={};for(item in cookieName){if(typeof cookies[cookieName[item]]!=='undefined'){returnValue[cookieName[item]]=cookies[cookieName[item]];}else
{returnValue[cookieName[item]]=null;}}}else
{returnValue=cookies;}return returnValue;};constructor.prototype.filter=function(cookieNameRegExp){var cookieName,returnValue={},cookies=parseCookies();if(typeof cookieNameRegExp==='string'){cookieNameRegExp=new RegExp(cookieNameRegExp);}for(cookieName in cookies){if(cookieName.match(cookieNameRegExp)){returnValue[cookieName]=cookies[cookieName];}}return returnValue;};constructor.prototype.set=function(cookieName,value,options){if(typeof options!=='object'||options===null){options={};}if(typeof value==='undefined'||value===null){value='';options.hoursToLive=-8760;}else if(typeof value!=='string'){if(typeof JSON==='object'&&JSON!==null&&typeof JSON.stringify==='function'){value=JSON.stringify(value);}else
{throw new Error('cookies.set() received non-string value and could not serialize.');}}var optionsString=assembleOptionsString(options);document.cookie=cookieName+'='+encodeURIComponent(value)+optionsString;};constructor.prototype.del=function(cookieName,options){var allCookies={},name;if(typeof options!=='object'||options===null){options={};}if(typeof cookieName==='boolean'&&cookieName===true){allCookies=this.get();}else if(typeof cookieName==='string'){allCookies[cookieName]=true;}for(name in allCookies){if(typeof name==='string'&&name!==''){this.set(name,null,options);}}};constructor.prototype.test=function(){var returnValue=false,testName='cT',testValue='data';this.set(testName,testValue);if(this.get(testName)===testValue){this.del(testName);returnValue=true;}return returnValue;};constructor.prototype.setOptions=function(options){if(typeof options!=='object'){options=null;}defaultOptions=resolveOptions(options);};return new constructor();})();(function(){if(window.jQuery){(function($){$.cookies=jaaulde.utils.cookies;var extensions={cookify:function(options){return this.each(function(){var i,nameAttrs=['name','id'],name,$this=$(this),value;for(i in nameAttrs){if(!isNaN(i)){name=$this.attr(nameAttrs[i]);if(typeof name==='string'&&name!==''){if($this.is(':checkbox, :radio')){if($this.attr('checked')){value=$this.val();}}else if($this.is(':input')){value=$this.val();}else
{value=$this.html();}if(typeof value!=='string'||value===''){value=null;}$.cookies.set(name,value,options);break;}}}});},cookieFill:function(){return this.each(function(){var n,getN,nameAttrs=['name','id'],name,$this=$(this),value;getN=function(){n=nameAttrs.pop();return!!n;};while(getN()){name=$this.attr(n);if(typeof name==='string'&&name!==''){value=$.cookies.get(name);if(value!==null){if($this.is(':checkbox, :radio')){if($this.val()===value){$this.attr('checked','checked');}else
{$this.removeAttr('checked');}}else if($this.is(':input')){$this.val(value);}else
{$this.html(value);}}break;}}});},cookieBind:function(options){return this.each(function(){var $this=$(this);$this.cookieFill().change(function(){$this.cookify(options);});});}};$.each(extensions,function(i){$.fn[i]=this;});})(window.jQuery);}})();


/**
 * 扩展jqgrid使其支持setData方法
 * jQuery("#tableLeft").jqGrid('setData', data);
 * 主意设置datatype: "local"
 
$.extend($.fn.jqGrid, { setData: function(data) {
    this[0].p.data = data;
    this.trigger("reloadGrid");
    return this;
} } );*/
/**
 * 扩展jqGrid使其随容器大小相应改变，主意height要减去标题栏和表头的高度
 * parent：table的父容器
 * w：要从parent里减去的宽度，一般为0
 * h：要从parent里减去的高度，一般为标题栏和表头的高度和
 */
$.extend($.fn.jqGrid, { autoSize: function(parent,w,h) {
	var me=this;
	$('#'+parent).resize(function(){
		me.setGridWidth($('#'+parent).width()-w);
		me.setGridHeight($('#'+parent).height()-w);
	});
    return this;
} } );

$.extend($.fn.jqGrid,{
	autoSize:function(parent,w,h){
		var me=this;
		$('#'+parent).resize(function(){
			me.setGridWidth($('#'+parent).width()-w);
			me.setGridHeight($('#'+parent).height()-h);
		});
	    return this;
	},
	autoWidth:function(parent,w){
		var me=this;
		$('#'+parent).resize(function(){
			me.setGridWidth($('#'+parent).width()-w);
		});
	    return this;
	},
	autoHeight:function(parent,h){
		var me=this;
		$('#'+parent).resize(function(){
			me.setGridHeight($('#'+parent).height()-h);
		});
	    return this;
	},
	setData:function(data){
		this[0].p.data = data;
	    this.trigger("reloadGrid");
	    return this;
	},
	setHeight:function($parent,h){
		var me=this;
		setTimeout(function(){
			me.setGridHeight($parent.height()+h);
		},50);
		return this;
	}
});

/**
 * TextAreaExpander plugin for jQuery
 * v1.0
 * Expands or contracts a textarea height depending on the
 * quatity of content entered by the user in the box.
 *
 * By Craig Buckler, Optimalworks.net
 *
 * As featured on SitePoint.com:
 * http://www.sitepoint.com/blogs/2009/07/29/build-auto-expanding-textarea-1/
 *
 * Please use as you wish at your own risk.
 */

/**
 * Usage:
 *
 * From JavaScript, use:
 *     $(<node>).TextAreaExpander(<minHeight>, <maxHeight>);
 *     where:
 *       <node> is the DOM node selector, e.g. "textarea"
 *       <minHeight> is the minimum textarea height in pixels (optional)
 *       <maxHeight> is the maximum textarea height in pixels (optional)
 *
 * Alternatively, in you HTML:
 *     Assign a class of "expand" to any <textarea> tag.
 *     e.g. <textarea name="textarea1" rows="3" cols="40" class="expand"></textarea>
 *
 *     Or assign a class of "expandMIN-MAX" to set the <textarea> minimum and maximum height.
 *     e.g. <textarea name="textarea1" rows="3" cols="40" class="expand50-200"></textarea>
 *     The textarea will use an appropriate height between 50 and 200 pixels.
 */

(function($) {
	$.fn.TextAreaExpander = function(minHeight, maxHeight) {

		var hCheck = !($.browser.msie || $.browser.opera);

		// resize a textarea
		function ResizeTextarea(e) {

			// event or initialize element?
			e = e.target || e;

			// find content length and box width
			var vlen = e.value.length, ewidth = e.offsetWidth;
			if (vlen != e.valLength || ewidth != e.boxWidth) {

				if (hCheck && (vlen < e.valLength || ewidth != e.boxWidth)) e.style.height = "0px";
				var h = Math.max(e.expandMin, Math.min(e.scrollHeight, e.expandMax));

				e.style.overflow = (e.scrollHeight > h ? "auto" : "hidden");
				e.style.height = h + "px";

				e.valLength = vlen;
				e.boxWidth = ewidth;
			}

			return true;
		};

		// initialize
		this.each(function() {

			// is a textarea?
			if (this.nodeName.toLowerCase() != "textarea") return;

			// set height restrictions
			var p = this.className.match(/expand(\d+)\-*(\d+)*/i);
			this.expandMin = minHeight || (p ? parseInt('0'+p[1], 10) : 0);
			this.expandMax = maxHeight || (p ? parseInt('0'+p[2], 10) : 99999);

			// initial resize
			ResizeTextarea(this);

			// zero vertical padding and add events
			if (!this.Initialized) {
				this.Initialized = true;
				$(this).css("padding-top", 0).css("padding-bottom", 0);
				$(this).bind("keyup", ResizeTextarea).bind("focus", ResizeTextarea);
			}
		});

		return this;
	};

})(jQuery);


/**
 * jQuery Cookie Plugin v1.1
 * https://github.com/carhartl/jquery-cookie
 * $.cookie('the_cookie'); //读取Cookie值
 * $.cookie('the_cookie', 'the_value'); //设置cookie的值
 * $.cookie('the_cookie', 'the_value', {expires: 7, path: '/', domain: 'jquery.com', secure: true, raw: true});
 * expires：有效期，以天数为单位
 * path：默认情况，只有设置cookie的网页才能读取该cookie
 * domain：创建cookie的网页所拥有的域名
 * secure：如果为true，cookie的传输需要使用安全协议（HTTPS）
 * raw：读取和写入的时候自动进行编码，要关闭这功能设置为true即可
 */
(function($,document){var pluses=/\+/g;function raw(s){return s;}function decoded(s){return decodeURIComponent(s.replace(pluses," "));}$.cookie=function(key,value,options){if(arguments.length>1&&(!/Object/.test(Object.prototype.toString.call(value))||value==null)){options=$.extend({},$.cookie.defaults,options);if(value==null){options.expires=-1;}if(typeof options.expires==="number"){var days=options.expires,t=options.expires=new Date();t.setDate(t.getDate()+days);}value=String(value);return(document.cookie=[encodeURIComponent(key),"=",options.raw?value:encodeURIComponent(value),options.expires?"; expires="+options.expires.toUTCString():"",options.path?"; path="+options.path:"",options.domain?"; domain="+options.domain:"",options.secure?"; secure":""].join(""));}options=value||$.cookie.defaults||{};var decode=options.raw?raw:decoded;var cookies=document.cookie.split("; ");for(var i=0,parts;(parts=cookies[i]&&cookies[i].split("="));i++){if(decode(parts.shift())===key){return decode(parts.join("="));}}return null;};$.cookie.defaults={};})(jQuery,document);

/**
 * 清除html 标记
 */
jQuery.delHtmlTag=function(str){
	return str.replace(/<[^>]*>/g, "");
};

//等待插件：请稍等
$.fn.waiting=function(kaiguan){
	if(kaiguan) $(this).append('<div id="div-jquery-waiting" style="background:rgba(0,0,0,0.2);z-index:9999;position:absolute;top:0px;left:0px;width:100%;height:100%;display:table;"><div style="display:table-cell;vertical-align:middle;"><div style="width:200px;height:80px;background:#aaa;border-radius:8px;margin:0 auto;font-size:20px;line-height:80px;text-align:center;font-family:simhei;letter-spacing:2px;">请稍等...</div></div></div>');
	else $('#div-jquery-waiting').remove();
};


/*
 * cond - v0.1 - 6/10/2009
 * http://benalman.com/projects/jquery-cond-plugin/
 * 
 * Copyright (c) 2009 "Cowboy" Ben Alman
 * Licensed under the MIT license
 * http://benalman.com/about/license/
 * 
 * Based on suggestions and sample code by Stephen Band and DBJDBJ in the
 * jquery-dev Google group: http://bit.ly/jqba1
 */
(function($){$.fn.cond=function(){var e,a=arguments,b=0,f,d,c;while(!f&&b<a.length){f=a[b++];d=a[b++];f=$.isFunction(f)?f.call(this):f;c=!d?f:f?d.call(this,f):e}return c!==e?c:this}})(jQuery);

/**
 * jquery plugin next ever
 */
$.fn.nextever=function(){return this.next().length?this.next():this.siblings(':first');};

/**
 * 2014.2.13 zzj
 * 时钟插件，为元素的text添加时钟显示
 */
$.fn.timer=function(){
	if($(this).data('timer')) return;
	var $me=$(this).text(new Date().format('hh:mm:ss'));
	var timer=setInterval(function(){
		if($me)
		$me.text(new Date().format('hh:mm:ss'));
		else {$me=null;clearInterval(timer);}
	},1000);
	$me.data('timer',timer);
};

/*
TopZIndex 1.2 (October 21, 2010) plugin for jQuery
http://topzindex.googlecode.com/
Copyright (c) 2009-2011 Todd Northrop
http://www.speednet.biz/
Licensed under GPL 3, see  <http://www.gnu.org/licenses/>
*/
(function(a){a.topZIndex=function(b){return Math.max(0,Math.max.apply(null,a.map((b||"*")==="*"?a.makeArray(document.getElementsByTagName("*")):a(b),function(b){return parseFloat(a(b).css("z-index"))||null})))};a.fn.topZIndex=function(b){if(this.length===0)return this;b=a.extend({increment:1},b);var c=a.topZIndex(b.selector),d=b.increment;return this.each(function(){this.style.zIndex=c+=d})}})(jQuery);


/**
 * The Class class
 * @module Class
 * @author Micah Snyder <micah@digg.com>
 * @description Class creation and management for use with jQuery
 * @link https://code.google.com/p/digg/wiki/Class
 */
(function($){Class={create:function(){var s=(arguments.length>0&&arguments[arguments.length-1].constructor==Boolean)?arguments[arguments.length-1]:false;var c=s?{}:function(){this.init.apply(this,arguments);}
var methods={ns:[],supers:{},init:function(){},namespace:function(ns){if(!ns)return null;var _this=this;if(ns.constructor==Array){$.each(ns,function(){_this.namespace.apply(_this,[this]);});return;}else if(ns.constructor==Object){for(var key in ns){if([Object,Function].indexOf(ns[key].constructor)>-1){if(!this.ns)this.ns=[];this.ns[key]=ns[key];this.namespace.apply(this,[key]);}}
return;}
var levels=ns.split(".");var nsobj=this.prototype?this.prototype:this;$.each(levels,function(){nsobj[this]=_this.ns[this]||nsobj[this]||window[this]||Class.create(true);delete _this.ns[this];nsobj=nsobj[this];});return nsobj;},create:function(){var args=Array.prototype.slice.call(arguments);var name=args.shift();var temp=Class.create.apply(Class,args);var ns={};ns[name]=temp;this.namespace(ns);},sup:function(){try{var caller=this.sup.caller.name;this.supers[caller].apply(this,arguments);}catch(noSuper){return false;}}}
s?delete methods.init:null;$.extend(c,methods);if(!s)$.extend(c.prototype,methods);var extendee=s?c:c.prototype;$.each(arguments,function(){if(this.constructor==Object||typeof this.init!=undefined){for(i in this){if(extendee[i]&&extendee[i].constructor==Function&&['namespace','create','sup'].indexOf(i)==-1){this[i].name=extendee[i].name=i;extendee.supers[i]=extendee[i];}
extendee[i]=this[i];}}});return c;}};})(jQuery);

/**
 * 屏蔽在IE中没有console的错误
 */
/*if(!jQuery.browser.webkit){
	console={};
	console.log=function(message){
		
	};
}*/

//function深度克隆 zzj 2014.5.28
Function.prototype.clone = function() {
    var that = this;
    var temp = function temporary() { return that.apply(this, arguments); };
    for( key in this ) {
        temp[key] = this[key];
    }
    return temp;
};

function showInput(title, html, width, height, onShown) {
	var dialog = new BootstrapDialog({
		title : title,
		draggable: true,
		message : function(dialog){
			var $message = $(html);
			return $message;
		},
		closable : true,
		onshown:onShown
	}).open();
	dialog.getModalContent().width(width).height(height);
	var h  = ($('body').height() - dialog.getModalContent().height())/2;
	var w  = ($('body').width() - dialog.getModalContent().width())/2;	
	dialog.getModalContent().parent().css("margin-top",h+"px").css("margin-left",w+"px");	
	return dialog;
}


function showError(msg) {
	return new BootstrapDialog({
		title : '错误信息',
		message : msg,
		type : BootstrapDialog.TYPE_DANGER,
		size : BootstrapDialog.SIZE_SMALL,
		closable : true,
		buttons : [ {
			label : '确定',
			action : function(dialog) {
				dialog.close();
			}
		} ]
	}).open();
}

function showInfo(msg, callback) {
	return new BootstrapDialog({
		title : '提示信息',
		size : BootstrapDialog.SIZE_SMALL,
		message : msg,
		data : {
			'callback' : callback
		},
		closable : false,
		buttons : [ {
			label : '确定',
			action : function(dialog) {
				typeof dialog.getData('callback') === 'function'
						&& dialog.getData('callback')(true);
				dialog.close();
			}
		} ]
	}).open();
}
function showConfirm(msg, callback) {
	new BootstrapDialog({
		title : '确认信息',
		size : BootstrapDialog.SIZE_SMALL,
		cssClass:'confirmdialog',
		message : msg,
		closable : false,
		data : {
			'callback' : callback
		},
		buttons : [
				{
					label : '确定',
					cssClass : 'btn-primary',
					action : function(dialog) {
						typeof dialog.getData('callback') === 'function'
								&& dialog.getData('callback')(true);
						dialog.close();
					}
				},
				{
					label : '取消',
					action : function(dialog) {
						typeof dialog.getData('callback') === 'function'
								&& dialog.getData('callback')(false);
						dialog.close();
					}
				} ]
	}).open();

}
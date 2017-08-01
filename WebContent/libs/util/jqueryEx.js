String.prototype.format = function(args) {
    if (arguments.length > 0) {
        var result = this;
        if (arguments.length == 1 && typeof(args) == "object") {
            for (var key in args) {
                var reg = new RegExp("({" + key + "})", "g");
                result = result.replace(reg, args[key]);
            }
        } else {
            for (var i = 0; i < arguments.length; i++) {
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

/// <reference path="jquery1.7.2.js" />
jQuery.bindModel = function(dom, model) {
	if (model) {
		var html = dom.innerHTML;
		for ( var p in model) {
			if (!(typeof (model[p]) == "function")) {
				eval("var re=/{" + p + "}/g;");
				html = html.replace(re, model[p]);
			}
		}
		dom.innerHTML = html;
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
}


jQuery.json2str = jQuery.json2str || function(obj) {
	var t = typeof (obj);
	if (t != "object" || obj === null) {
		// simple data type
		if (t == "string")
			obj = '"' + obj + '"';
		return String(obj);
	} else {
		// recurse array or object
		var n, v, json = [], arr = (obj && obj.constructor == Array);
		for (n in obj) {
			v = obj[n];
			t = typeof (v);
			if (t == "string")
				v = '"' + v + '"';
			else if (t == "object" && v !== null)
				v = JSON.stringify(v);
			json.push((arr ? "" : '"' + n + '":') + String(v));
		}
		return (arr ? "[" : "{") + String(json) + (arr ? "]" : "}");
	}
};

jQuery.getUrlVars = function(name) {
	return decodeURI((RegExp(name + '=' + '(.+?)(&|$)').exec(location.search) || [
			, null ])[1]);
}
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
}


/**
 * artTemplate - Template Engine
 * https://github.com/aui/artTemplate
 */
var template = function(c,g){return template["object"===typeof g?"render":"compile"].apply(template,arguments)};(function(c,g){c.version="1.0";c.openTag="<%";c.closeTag="%>";c.parser=null;c.render=function(a,b){var d;d=l[a];void 0===d&&!q&&((d=document.getElementById(a))&&c.compile(a,d.value||d.innerHTML),d=l[a]);return void 0===d?i({id:a,name:"Render Error",message:"Not Cache"}):d(b)};c.compile=function(a,b,d){function f(d){try{return g.call(n,d)}catch(e){if(!r)return c.compile(a,b,!0)(d);e.id=a||b;e.name="Render Error";e.source=b;return i(e)}}var r=d;"string"!==typeof b&&(r=b,b=a,a=null);try{var g=v(b,r)}catch(j){return j.id=a,j.name="Syntax Error",i(j)}f.toString=function(){return g.toString()};a&&(l[a]=f);return f};c.helper=function(a,b){if(void 0===b)return n[a];n[a]=b};var l={},n={},o="".trim,q=o&&!g.document,v=function(a,b){function d(a){m+=a.split(/\n/).length-1;a=a.replace(/('|"|\\)/g,"\\$1").replace(/\r/g,"\\r").replace(/\n/g,"\\n");a=k[1]+"'"+a+"'"+k[2];return a+"\n"}function f(a){var d=m;j?a=j(a):b&&(a=a.replace(/\n/g,function(){m++;return"$line="+m+";"}));0===a.indexOf("=")&&(a=k[1]+(o?"$getValue(":"")+a.substring(1).replace(/[\s;]*$/,"")+(o?")":"")+k[2]);b&&(a="$line="+d+";"+a);g(a);return a+"\n"}function g(a){a=a.replace(/\/\*.*?\*\/|'[^']*'|"[^"]*"|\.[\$\w]+/g,"");p.call(a.split(/[^\$\w\d]+/),function(a){if(/^(this|\$helpers)$/.test(a))throw{message:'Prohibit the use of the "'+a+'"'};a&&!t[a]&&!/^\d/.test(a)&&!i[a]&&(s+=a+"="+("include"===a?q:n[a]?"$helpers."+a:"$data."+a)+",",i[a]=!0)})}var l=c.closeTag,j=c.parser,h,e="",m=1,i={$out:!0,$line:!0},s="var $helpers=this,"+(b?"$line=0,":""),k=o?["$out='';","$out+=",";","$out"]:["$out=[];","$out.push(",");","$out.join('')"],q="function(id,data){if(data===undefined){data=$data}return $helpers.$render(id,data)}";p.call(a.split(c.openTag),function(a){var a=a.split(l),b=a[0],c=a[1];1===a.length?e+=d(b):(e+=f(b),c&&(e+=d(c)))});h=e;b&&(h="try{"+h+"}catch(e){e.line=$line;throw e}");h=s+k[0]+h+"return "+k[3];try{return new Function("$data",h)}catch(u){throw u.temp="function anonymous($data) {"+h+"}",u;}},i=function(a){var b="[template]:\n"+a.id+"\n\n[name]:\n"+a.name;a.message&&(b+="\n\n[message]:\n"+a.message);a.line&&(b+="\n\n[line]:\n"+a.line,b+="\n\n[source]:\n"+a.source.split(/\n/)[a.line-1].replace(/^[\s\t]+/,""));a.temp&&(b+="\n\n[temp]:\n"+a.temp);g.console&&console.error(b);return"{Template Error}"},p=Array.prototype.forEach||function(a,b){for(var c=this.length>>>0,f=0;f<c;f++)f in this&&a.call(b,this[f],f,this)},t={};p.call("break,case,catch,continue,debugger,default,delete,do,else,false,finally,for,function,if,in,instanceof,new,null,return,switch,this,throw,true,try,typeof,var,void,while,with,abstract,boolean,byte,char,class,const,double,enum,export,extends,final,float,goto,implements,import,int,interface,long,native,package,private,protected,public,short,static,super,synchronized,throws,transient,volatile,arguments,let,yield".split(","),function(a){t[a]=!0});c.helper("$forEach",p);c.helper("$render",c.render);c.helper("$getValue",function(a){return void 0===a?"":a})})(template,this);if("undefined"!==typeof module&&module.exports)module.exports=template;

/**
 * 获取日期字符串yyyy-MM-dd
 * 
 * @param {Object}
 *            date
 * @return {TypeName}
 */
jQuery.getDatestr=function (date) {
	var thisYear = date.getFullYear();
	var thisMonth = date.getMonth() + 1;
	// 如果月份长度是一位则前面补0
	if (thisMonth < 10)
		thisMonth = "0" + thisMonth;
	var thisDay = date.getDate();
	// 如果天的长度是一位则前面补0
	if (thisDay < 10)
		thisDay = "0" + thisDay;
	return thisYear + "-" + thisMonth + "-" + thisDay;
};
/**
 * 获取时间字符串HH:mm:ss
 * 
 * @param {Object}
 *            date
 * @return {TypeName}
 */
jQuery.getTimestr=function (date) {
	var thisHour = date.getHours();
	var thisMinute = date.getMinutes();
	var thisSecond = date.getSeconds();
	// var thisSecond = date.getSeconds();
	/*
	 * var thisYear = date.getFullYear(); var thisMonth = date.getMonth() + 1;
	 * //如果月份长度是一位则前面补0 if(thisMonth<10) thisMonth = "0" + thisMonth; var
	 * thisDay = date.getDate(); //如果天的长度是一位则前面补0 if(thisDay<10) thisDay = "0" +
	 * thisDay;
	 */
	if(thisHour<10) thisHour = "0" + thisHour;
	if(thisMinute<10) thisMinute = "0" + thisMinute;
	if(thisSecond<10) thisSecond = "0" + thisSecond;
	return thisHour + ":" + thisMinute + ":" + thisSecond;
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
		if(data!=null){
			this[0].p.data = data;
		}else{
			this[0].p.data = [];
		}
		this.trigger("reloadGrid");
	    return this;
	}
});
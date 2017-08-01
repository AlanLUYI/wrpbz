/**
 * 读取问好后面参数
 * @param {Object} name
 * @return {TypeName}
 */
function getQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
}

/**
 * 根据时间截取时
 */
function formatTime(time) {
	if (time.indexOf(' ') > -1)
		return time.split(" ")[1].split(":")[0] + "时";
	else
		return time;
}

/**
 * 根据时间截取时
 */
function formatTime2Hour(time) {
	var date, hour;
	if (time.indexOf(' ') > -1)
		hour = time.split(" ")[1].split(":")[0] + "时";
	if (time.indexOf('-') > -1)
		date = time.split("-")[1] + "月" + time.split("-")[2].split(" ")[0]
				+ "日";
	return date + hour;
}

/**
 * 八点时间
 * @param {Object} date
 * @return {TypeName} 
 */
function formateightDate(date) {
	var m, d, h;
	if (parseInt(date.getMonth()) >= 9) {
		m = date.getMonth()+1;
	} else {
		m = '0'+(date.getMonth()+1);
	} 
	if (parseInt(date.getHours()) > 7) {
		 //==当天
		 if (parseInt(date.getDate()) > 9) {
		  d = date.getDate();
	     } else {
		 d = '0'+(date.getDate());
	}
	} else {
		 //==前一天
		if (parseInt(date.getDate()-1) > 9) {
		d = date.getDate()-1;
	    } else {
		d = '0'+(date.getDate()-1);
	    }
	}
	return date.getFullYear()+"-"+m+"-"+d+" 08:00";
}


/**
 * 八点时间只带日期
 * @param {Object} date
 * @return {TypeName} 
 */
function formateightDatewithoutHour(date) {
	var m, d, h;
	if (parseInt(date.getMonth()) >= 9) {
		m = date.getMonth()+1;
	} else {
		m = '0'+(date.getMonth()+1);
	} 
	if (parseInt(date.getHours()) > 7) {
		 //==当天
		 if (parseInt(date.getDate()) > 9) {
		  d = date.getDate();
	     } else {
		 d = '0'+(date.getDate());
	}
	} else {
		 //==前一天
		if (parseInt(date.getDate()-1) > 9) {
		d = date.getDate()-1;
	    } else {
		d = '0'+(date.getDate()-1);
	    }
	}
	return date.getFullYear()+"-"+m+"-"+d;
}

/**
 * 根据时间截取
 */
function formatTime2Water(time) {
	return (time.split("-")[1] + "月" + time.split("-")[2]).replace(" ", "日");
}

//去掉所有的html标记
function delHtmlTag(str) {
	return str.replace(/<[^>]*>/g, "");
}

//去掉所有的html标记
function getbracketValue(str) {
	return str.match(/([^\(]+?)(?=\))/gi);
}

function getPageHeight() {
	return document.body.clientHeight;
}

function getPageSize() {
	var xScroll, yScroll;
	if (window.innerHeight && window.scrollMaxY) {
		xScroll = window.innerWidth + window.scrollMaxX;
		yScroll = window.innerHeight + window.scrollMaxY;
	} else if (document.body.scrollHeight > document.body.offsetHeight) {// all but Explorer Mac
		xScroll = document.body.scrollWidth;
		yScroll = document.body.scrollHeight;
	} else {// Explorer Mac...would also work in Explorer 6 Strict, Mozilla and Safari
		xScroll = document.body.offsetWidth;
		yScroll = document.body.offsetHeight;
	}
	var windowWidth, windowHeight;
	if (self.innerHeight) {// all except Explorer
		if (document.documentElement.clientWidth) {
			windowWidth = document.documentElement.clientWidth;
		} else {
			windowWidth = self.innerWidth;
		}
		windowHeight = self.innerHeight;
	} else if (document.documentElement
			&& document.documentElement.clientHeight) {// Explorer 6 Strict Mode
		windowWidth = document.documentElement.clientWidth;
		windowHeight = document.documentElement.clientHeight;
	} else if (document.body) {// other Explorers
		windowWidth = document.body.clientWidth;
		windowHeight = document.body.clientHeight;
	}
	// for small pages with total height less then height of the viewport
	if (yScroll < windowHeight) {
		pageHeight = windowHeight;
	} else {
		pageHeight = yScroll;
	}
	// for small pages with total width less then width of the viewport
	if (xScroll < windowWidth) {
		pageWidth = xScroll;
	} else {
		pageWidth = windowWidth;
	}
	arrayPageSize = new Object();
	arrayPageSize.pageWidth = pageWidth;
	arrayPageSize.pageHeight = pageHeight;
	arrayPageSize.windowWidth = windowWidth;
	arrayPageSize.windowHeight = windowHeight;
	return arrayPageSize;
}

/**
 * 日期格式化
 */
function getToday() {
	var date = new Date();
	var m, d;
	if (parseInt(date.getMonth()) > 9) {
		m = date.getMonth() + 1;
	} else {
		m = '0' + (date.getMonth() + 1);
	}
	var dd = parseInt(date.getDate());
	if (dd > 10) {
		d = dd;
	} else {
		d = '0' + (dd);
	}
	return date.getFullYear() + "-" + m + "-" + d;
}

function getNow() {
	var date = new Date();
	var m, d, h, Minute, s;
	if (parseInt(date.getMonth()) > 9) {
		m = date.getMonth() + 1;
	} else {
		m = '0' + (date.getMonth() + 1);
	}
	
	var dd = parseInt(date.getDate());
	if (dd > 9) {
		d = dd;
	} else {
		d = '0' + (dd);
	}
	
	var hh = parseInt(date.getHours());
	if (hh > 9) {
		h = hh;
	} else {
		h = '0' + (hh);
	}
	
	var MinuteM = parseInt(date.getMinutes());
	if (MinuteM > 9) {
		Minute = MinuteM;
	} else {
		Minute = '0' + (MinuteM);
	}

	var ss = parseInt(date.getSeconds());
	if (ss > 9) {
		s = ss;
	} else {
		s = '0' + (ss);
	}
	
	return date.getFullYear() + "-" + m + "-" + d+ " " + h + ":" + Minute + ":" + s;
//	var d = new Date();
//	return d.getFullYear() + "-" +(d.getMonth()+1) + "-" + d.getDate() + " " + d.getHours() + ":" + d.getMinutes() + ":" + d.getSeconds();
}

function getDay(offset) {
	var date = new Date();
	date.setDate(date.getDate()+offset); 
	return formatDate(date);
}

function getDayStr(offset) {
	var date = new Date();
	date.setDate(date.getDate()+offset); 
	return date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate();
}

function formatFloat(src, pos)
{
    return Math.round(src*Math.pow(10, pos))/Math.pow(10, pos);
}

/**
 * 日期格式化
 */
function formatDate(date) {
	var m, d;
	if (parseInt(date.getMonth()) > 8) {
		m = date.getMonth() + 1;
	} else {
		m = '0' + (date.getMonth() + 1);
	}
	if (parseInt(date.getDate()) > 9) {
		d = date.getDate();
	} else {
		d = '0' + (date.getDate());
	}
	return date.getFullYear() + "-" + m + "-" + d;
}


function formatDate_yyyyMM(date) {
	var m;
	if (parseInt(date.getMonth()) > 9) {
		m = date.getMonth() + 1;
	} else {
		m = '0' + (date.getMonth() + 1);
	} 
	return date.getFullYear() + "-" + m;
}
/**
 * 适应IE时间处理
 */
function formatNewDate(str) {
	str = str.split('-');
	var date = new Date();
	date.setUTCFullYear(str[0], str[1] - 1, str[2]);
	date.setUTCHours(0, 0, 0, 0);
	return date;
}

/*
 * 转化成utf-8
 * @param {Object} str
 * @return {TypeName} 
 */
function ConvUtf(str)
{
	return str.replace(/[^\u0000-\u00FF]/g, function ($0) { return escape($0).replace(/(%u)(\w{4})/gi, "&#x$2;") })
}

/*
 * 转化中文
 * @param {Object} str
 */
function ResChinese(str)
{
	unescape(str.replace(/&#x/g, '%u').replace(/;/g, ''))
}

/**
 * 判断是否为移动端
 * @returns {Boolean}
 */
function IsPAD()  
{  
           var userAgentInfo = navigator.userAgent;  
           var Agents = new Array("Android", "iPhone", "SymbianOS", "Windows Phone", "iPad", "iPod");  
           var flag = true;  
           for (var v = 0; v < Agents.length; v++) {  
               if (userAgentInfo.indexOf(Agents[v]) > 0) { flag = false; break; }  
           }  
           return !flag;  
}

/**
 * 判断是否为PC端
 * @returns {Boolean}
 */
function IsPC()  
{  
           var userAgentInfo = navigator.userAgent;  
           var Agents = new Array("Android", "iPhone", "SymbianOS", "Windows Phone", "iPad", "iPod");  
           var flag = true;  
           for (var v = 0; v < Agents.length; v++) {  
               if (userAgentInfo.indexOf(Agents[v]) > 0) { flag = false; break; }  
           }  
           return flag;  
} 
var browser = {
		// 检测是否是IE浏览器
		isIE: function() {
			var _uaMatch = $.uaMatch(navigator.userAgent);
			var _browser = _uaMatch.browser;
			if (_browser == 'msie') {
				return true;
			} else {
				return false;
			}
		},
		// 检测是否是chrome浏览器
		isChrome: function() {
			var _uaMatch = $.uaMatch(navigator.userAgent);
			var _browser = _uaMatch.browser;
			if (_browser == 'chrome') {
				return true;
			} else {
				return false;
			}
		},
		// 检测是否是Firefox浏览器
		isMozila: function() {
			var _uaMatch = $.uaMatch(navigator.userAgent);
			var _browser = _uaMatch.browser;
			if (_browser == 'mozilla') {
				return true;
			} else {
				return false;
			}
		},
		// 检测是否是Firefox浏览器
		isOpera: function() {
			var _uaMatch = $.uaMatch(navigator.userAgent);
			var _browser = _uaMatch.browser;
			if (_browser == 'webkit') {
				return true;
			} else {
				return false;
			}
		}
	};


var now = new Date();                    //当前日期
var nowDayOfWeek = now.getDay();         //今天本周的第几天
var nowDay = now.getDate();              //当前日
var nowMonth = now.getMonth();           //当前月
var nowYear = now.getYear();             //当前年
var nowFormatYear = nowYear + 1900;

function getWeekStartDate() { //获取本周第一天 中国
	return new Date(nowFormatYear, nowMonth, nowDay - nowDayOfWeek+1);
}

function getWeekStartDateUsa() { //获取本周第一天 美国
	return new Date(nowFormatYear, nowMonth, nowDay - nowDayOfWeek);
}

function getDayBefore(n) { 
	return new Date(nowFormatYear, nowMonth, nowDay - n);
}

function getDay15Before() { //15天前日期
	return new Date(nowFormatYear, nowMonth, nowDay - 15);
}

function getMonthStartDate() { //获取本月第一天
	return new Date(nowFormatYear, nowMonth, 1);
}

function getLastMonthStartDate() { //获取上月第一天
	return new Date(nowFormatYear, nowMonth-1, 1);
}

//对Date的扩展，将 Date 转化为指定格式的String
//月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符， 
//年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
//例子： 
//(new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 
//(new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18 
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
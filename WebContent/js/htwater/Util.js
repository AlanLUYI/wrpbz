+function (global, factory) {
    if (typeof define === "function" && define.amd) {
        define([], factory);
    } else if (typeof exports === "object" && typeof module !== "undefined") {
        module.exports = factory();
    } else {
        global.Util = factory();
    }
} (this, function () {
    var Util = {};
    
    /**
     * 判断attributes与options是否匹配，eg.{a: "foo", b: "boo"}与{a: "foo"}、{a: "f"}、{a: ["f"]}均匹配
     * @param attributes Object
     * @param options Object
     */
    Util.isMatch = function (attributes, options) {
        var value, flag;
        for (var prop in options) {
            if (options.hasOwnProperty(prop)) {
                if (attributes.hasOwnProperty(prop)) {
                    value = options[prop];
                    if (Object.prototype.toString.call(value) === "[object Array]") {
                        if (value.length === 0) return false;
                        for (var i = 0, l = value.length; i < l; i++) {
                            flag = ("" + attributes[prop]).indexOf(value[i]) >= 0 ? true : false;
                            if (flag) {
                                break;
                            }
                        }
                    } else {
                        flag = ("" + attributes[prop]).indexOf(value) >= 0 ? true : false;
                    }
                    if (!flag) {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        }
        return true;
    };

    /**
     * 格式化模板字符串
     * @param: template with {0}...{n}, "str1", ..., "strn"
     */
    Util.format = function () {
        if (arguments.length == 0){
             return null;
        }
        var theString = arguments[0];
    
        for (var i = 1; i < arguments.length; i++) {
            var regEx = new RegExp("\\{" + (i - 1) + "\\}", "gm");
            theString = theString.replace(regEx, arguments[i]);
        }

        return theString;
    };

    return Util;
});
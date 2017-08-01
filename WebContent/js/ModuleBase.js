/**
 * @author chenchu
 * @date 2014 10 11
 * @info 模块基类
 */

define([
    "dojo/_base/declare",
    "./MapLeaf",
    "./BootMenuType",
    "./FullScreen",
    "./Counties",
    "underscore",
    "jquery"
], function (declare, htMap, BootMenuType, FullScreen, counties, _, $) {
        "use strict";
        return declare(null, {
            declaredClass: "js.ModuleBase",
            name: "ModuleBase",
            width: 400,
            container: null,
            htMap: htMap,
            promises: [],

            constructor: function () {
                console.log("module base init");
            },
            destructor: function () {
                if(Object.prototype.toString.apply(this.data) === "[object Array]"){
                    this.data.length = 0;
                }
                $(this.container).off().empty();
                var promise;
                for (var i = 0; i < this.promises.length; i++) {
                    promise = this.promises[i];
                    if (typeof promise.state === "function") { //jQuery ajax方法返回的promise对象
                        if (promise.state() === "pending") {
                            promise.abort && promise.abort();
                            console.log(this.name + " destructor aborts xhr");
                        }
                    } else if (!(promise.isResolved() || promise.isRejected())) { //dojo的promise对象
                        promise.otherwise(function (reason) {
                            console.log(reason);
                        });
                        promise.cancel(this.name + " destructor cancels promise");
                    }
                }
                this.promises.length = 0;
                (this.type == BootMenuType.TYPE_INDIVIDUAL) && this.htMap.clearGraphics();
                console.log(this.name + "module destroy");
            },
            reflow: function () {
                this.grid && this.grid.bootstrapTable && this.grid.bootstrapTable("resetView");
                this.chart && this.chart.reflow && this.chart.reflow();
                console.log(this.name + " module reflow");
            },
            toggleFullScreen: function (el) {
                if (FullScreen.supportsFullScreen && el.nodeType) {
                    if (!FullScreen.isFullScreen()) {
                        FullScreen.requestFullScreen(el);
                    } else {
                        FullScreen.cancelFullScreen(el);
                    }
                }
            },
            limitCounty: function(selectNode){
               var regionName = this.userInfo.regionName;
               var countiesName = _.pluck(counties, "name");
               countiesName.shift();
               var index = countiesName.indexOf(regionName);
               if(index > -1){
                   var $selectedOption = $(selectNode).children("option[value='"+ regionName +"']");
                   if($selectedOption.length){
                       $selectedOption.prop("selected", true).siblings().remove().end().parent().prop("disabled", true);
                   }else{
                       if(index < 3){
                           $selectedOption = $(selectNode).children("option[value='市区']");
                           if($selectedOption.length){
                               $selectedOption.prop("selected", true).siblings().remove().end().parent().prop("disabled", true);
                           }
                       }
                   } 
               }
            },
            getLegendTitle: function(){
                return "图例";
            },
            getLegendContent: function () {
                return null;
            }
        });
    });
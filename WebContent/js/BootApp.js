/**
 * @author chenchu
 * @Date 2014 10 11
 * @info single page app
 */
define([
    "require",
    "dojo/_base/declare",
    "./UserInfo",
    "jquery"
], function (require, declare, UserInfo, $) {
    "use strict";
    return declare(null, {
        declaredClass: "js.BootApp",

        constructor: function (options) {
            declare.safeMixin(this, options || {});

            var self = this,
                userPromise = UserInfo.checkUser(),
                initDeferred = $.Deferred();
            this.initPromise = initDeferred.promise();
            userPromise.then(function (user) {
                self.userInfo = user;
                $("#current-user").text(user.realname);
                require(["./BootMenu", "./BootModuleManager", "./MapLeaf", "./ContextMenu", "htwater/Print", "htwater/PrintConfig", "htwater/Search"], function (BootMenu, BootModuleManager, htMap, ContextMenu, Print, PrintConfig, Search) {
                    if ($(self.sidePanelContainer).length && $(self.frameContainer).length) {
                        self.bootModuleManager = new BootModuleManager({
                            sidePanelContainer: self.sidePanelContainer,
                            legendContainer: self.legendContainer,
                            frameContainer: self.frameContainer,
                            //profile: self.profile,
                            userInfo: user
                        });
                    }
                    if ($(self.menuContainer).length) {
                        self.bootMenu = new BootMenu({
                            menuContainer: self.menuContainer
                        });
                    }
                    self.htMap = htMap;
                    var map = htMap.map;
                    self.print = new Print({
                        map: map,
                        printTaskURL: PrintConfig.printTaskURL,
                        defaultTitle: PrintConfig.defaultTitle,
                        defaultLayout: PrintConfig.defaultLayout,
                        defaultFormat: PrintConfig.defaultFormat,
                        authorText: PrintConfig.authorText
                    }, PrintConfig.domNode);
                    var search = self.search = new Search({
                        map: map
                    });
                    self.contextMenu = new ContextMenu({
                        map: map,
                        search: search
                    });
                    map.infoWindow.on("hide", function (event) {
                        var timepickers = $(event.target.domNode).find("input");
                        $.each(timepickers, function(index, timepicker){
                           $(timepicker).datetimepicker && $(timepicker).datetimepicker("remove");
                        });
                    });
                    if (map.loaded) {
                        initDeferred.resolve();
                    } else {
                        map.on("load", function () {
                            initDeferred.resolve();
                        });
                    }
                });
            }, function (error) {
                initDeferred.reject(error);
            });
        },
        destructor: function () {
            this.bootModuleManager && this.bootModuleManager.destructor();
            this.bootMenu && this.bootMenu.destructor();
            this.htMap.destroy();
            return UserInfo.logOut();
        }

    });
});
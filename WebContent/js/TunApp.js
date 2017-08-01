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
        declaredClass: "js.TunApp",

        constructor: function (options) {
            declare.safeMixin(this, options || {});

            var self = this,
                userPromise = UserInfo.checkUser(),
                initDeferred = $.Deferred();
            this.initPromise = initDeferred.promise();
            userPromise.then(function (user) {
                self.userInfo = user;
                require(["./TunMenu", "./BootModuleManager", "./MapLeaf", "./ContextMenu", "htwater/Print", "htwater/PrintConfig", "htwater/Search"], function (TunMenu, BootModuleManager, htMap, ContextMenu, Print, PrintConfig, Search) {
                    if ($(self.sidePanelContainer).length && $(self.frameContainer).length) {
                        self.bootModuleManager = new BootModuleManager({
                            sidePanelContainer: self.sidePanelContainer,
                            legendContainer: self.legendContainer,
                            frameContainer: self.frameContainer,
                            userInfo: user
                        });
                    }
                    if ($(self.menuContainer).length) {
                        self.tunMenu = new TunMenu({
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
                        if (timepickers.datetimepicker) {
                            timepickers.datetimepicker("remove");
                        }
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
            this.tunMenu && this.tunMenu.destructor();
            this.htMap.destroy();
            return UserInfo.logOut();
        }

    });
});
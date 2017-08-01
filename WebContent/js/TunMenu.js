/**
 * @author chenchu
 * @date 2014 10 14
 * @info 菜单组件
 */
define([
    "dojo/_base/declare",
    "dojo/_base/lang",
    "dojo/topic",
    "./MapLeaf",
    "./Events",
    "./BootMenuType",
    "./BootMenuUtil",
    "text!./templates/tunMenu.html",
    "jquery",
    "underscore"
], function(declare, lang, topic, htMap, Events, BootMenuType, BootMenuUtil, menuTemp, $, _) {
    "use strict";
    return declare(null, {
        delaredClass: "js.TunMenu",
        menusData: null,

        constructor: function(options) {
            declare.safeMixin(this, options || {});

            if ($(this.menuContainer).length) {
                this.getMenusData();
            }
        },
        destructor: function() {
            $(this.menuContainer).length && $(this.menuContainer).off();
        },
        getMenusData: function() {
            var self = this;
            $.getJSON("getMenuTree!user", {}).then(function(data) {
                data = data.sort(function(a, b) {
                    return a.ord > b.ord ? 1 : -1;
                });
                self.menusData = self._toHierarchy(data);
                self.createMenus();
            }, function(jqXHR, textStatus, error) {
                console.error(error);
            });
        },
        createMenus: function() {
            var self = this;
            var menuTemplate = _.template(menuTemp);
            $(this.menuContainer).html(menuTemplate({
                menus: this.menusData
            }));

            $(this.menuContainer).on("click", ".submenu-col a", function(event) {
                var menu = BootMenuUtil.getMenuById($(this).data("id").toString(), self.menusData),
                    topMenu;
                if (menu) {
                    menu.type == BootMenuType.TYPE_WINDOW || event.preventDefault();
                    switch (menu.type) {
                        case BootMenuType.TYPE_INDIVIDUAL:
                        case BootMenuType.TYPE_FRAME:
                            topic.publish(Events.MENUCLICK, menu);
                            break;
                        case BootMenuType.TYPE_COMPOSITE:
                            topMenu = BootMenuUtil.getTopMenu(menu.id, self.menusData);
                            topic.publish(Events.MENUCLICK, menu, topMenu);
                            break;
                        default:
                            break;
                    }
                    
                    //记录访问
                    $.get("moduleLog!user",{module:menu.action});
                }
                $(self.menuContainer).find(".submenu-panel").hide();
            });

            var timer;
            $(this.menuContainer).on("mouseenter", ".menu-list li", function(event) {
                clearTimeout(timer);
                timer = setTimeout(function() {
                    $(event.target).closest("li").addClass("menu-hover").siblings().removeClass("menu-hover");

                    var index = $(self.menuContainer).find(".menu-list li").index($(event.target).closest("li"));
                    $(self.menuContainer).children(".submenu-panel").show().find("li.submenu-col").eq(index).addClass("submenu-hover")
                        .siblings("li.submenu-col").removeClass("submenu-hover");
                }, 20);
            });

            $(this.menuContainer).on("mouseleave", ".menu-list li", function(event) {
                clearTimeout(timer);
                timer = setTimeout(function() {
                    $(event.target).closest("li").removeClass("menu-hover").siblings().removeClass("menu-hover");

                    $(self.menuContainer).children(".submenu-panel").hide().find("li.submenu-col").removeClass("submenu-hover");
                }, 100);
            });

            $(this.menuContainer).on("mouseenter", ".submenu-list li.submenu-col", function(event) {
                clearTimeout(timer);
                timer = setTimeout(function() {
                    $(event.target).closest(".submenu-col").addClass("submenu-hover").siblings().removeClass("submenu-hover")
                        .closest(".submenu-panel").prev(".menu-bar").find("li").removeClass("menu-hover");
                }, 20);
            });

            $(this.menuContainer).on("mouseleave", ".submenu-list li.submenu-col", function(event) {
                clearTimeout(timer);
                timer = setTimeout(function() {
                    $(event.target).closest(".submenu-col").removeClass("submenu-hover").siblings().removeClass("submenu-hover")
                        .closest(".submenu-panel").hide().prev(".menu-bar").find("li").removeClass("menu-hover");
                }, 100);
            });
        },
        _toHierarchy: function(array) {
            var nodeObjects = this._createStructure(array);
            for (var i = nodeObjects.length - 1; i >= 0; i--) {
                var currentNode = nodeObjects[i];
                if (currentNode.pid === "") {
                    continue;
                }
                var parent = this._getParent(currentNode, nodeObjects);
                if (parent === null) {
                    continue;
                }
                parent.children.unshift(currentNode);
                nodeObjects.splice(i, 1);
            }
            return nodeObjects;
        },
        _createStructure: function(nodes) {
            for (var i = 0; i < nodes.length; i++) {
                nodes[i].children = [];
            }
            return nodes;
        },
        _getParent: function(child, nodes) {
            var parent = null;
            for (var i = 0; i < nodes.length; i++) {
                if (nodes[i].id == child.pid) {
                    return nodes[i];
                }
            }
            return parent;
        }
    });
});
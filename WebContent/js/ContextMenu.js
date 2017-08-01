/**
 * @author chenchu
 * @date 2015 4 2
 * @info 地图右键菜单
 */
define([
    "dojo/_base/declare",
    "esri/geometry/ScreenPoint",
    "jquery",
    "text!./templates/contextMenu.html"
], function(declare, ScreenPoint, $, contextMenuTpl) {
	"use strict";
    return declare(null, {
        constructor: function(options) {
            declare.safeMixin(this, options || {});
            this.startup();
        },

        startup: function() {
            var self = this,
                $contextMenu = this.$contextMenu = $(contextMenuTpl);
            $contextMenu.on("click", ".context-menu li", function(event) {
                if ($(this).hasClass("zoom-in")) {
                    self.zoomIn();
                } else if ($(this).hasClass("zoom-out")) {
                    self.zoomOut();
                } else if ($(this).hasClass("zoom-center")) {
                    self.zoomCenter();
                } else if ($(this).hasClass("where-it-is")) {
                    self.whereIt();
                }
                self.hide();
            });

            $(this.map.root).parent().append(this.$contextMenu);

            $(this.map.root).children(".container").on("contextmenu", function(event) {
                event.stopPropagation();
                self._updateMenuStatus();
                var offset = $(this).offset(),
                    left = self.screenX = event.clientX - offset.left,
                    top = self.screenY = event.clientY - offset.top;
                if (left + $contextMenu.width() + 5 > $(this).width()) {
                    left = left - $contextMenu.width();
                }
                if (top + $contextMenu.height() + 5 > $(this).height()) {
                    top = top - $contextMenu.height();
                }
                $contextMenu.css({
                    left: left,
                    top: top
                }).show();
                return false;
            });


            $(this.map.root).on("click", function() {
                self.hide();
            });
        },

        show: function() {
            this.$contextMenu.show();
        },

        hide: function() {
            this.$contextMenu.hide();
        },

        zoomIn: function() {
            var zoom = this.map.getZoom() + 1;
            zoom = zoom > this.map.getMaxZoom() ? this.map.getMaxZoom() : zoom;
            this.map.setZoom(zoom);
        },

        zoomOut: function() {
            var zoom = this.map.getZoom() - 1;
            zoom = zoom < this.map.getMinZoom() ? this.map.getMinZoom() : zoom;
            this.map.setZoom(zoom);
        },

        zoomCenter: function() {
            var mapPoint = this.map.toMap(new ScreenPoint(this.screenX, this.screenY));
            this.map.centerAt(mapPoint);
        },

        whereIt: function() {
            var mapPoint = this.map.toMap(new ScreenPoint(this.screenX, this.screenY));
            this.search.reverseGeocode(mapPoint, 500);
        },

        _updateMenuStatus: function() {
            var level = this.map.getZoom();
            if (level == this.map.getMinZoom()) {
                this.$contextMenu.find(".zoom-out").addClass("disabled").prev(".zoom-in").removeClass("disabled");
            } else if (level < this.map.getMaxZoom()) {
                this.$contextMenu.find(".zoom-out").removeClass("disabled").prev(".zoom-in").removeClass("disabled");
            } else {
                this.$contextMenu.find(".zoom-out").removeClass("disabled").prev(".zoom-in").addClass("disabled");
            }
        }
    });
});
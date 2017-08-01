/**
 * @author chenchu
 * @date 2015 3 6
 * @description custom overlay class
 */
define([
    "dojo/_base/declare",
    "dojo/dom-construct",
    "dojo/dom-style",
    "dojo/string",
    "esri/graphic"
], function(declare, domConstruct, domStyle, string, Graphic) {
    "use strict";
    return declare(null, {
        declaredClass: "htwater.Overlay",

        constructor: function(graphic, htmlTpl) {
            this.graphic = graphic;
            this.htmlTpl = htmlTpl;
            this.offsetx = 0;
            this.offsety = 0;
            this.isLoaded = false;
            this.isDirty = false;
            this.visible = true;
        },

        _getMap: function() {
            var ol = this._overlaysLayer;
            return ol._map;
        },

        show: function() {
            if (!this.container) {
                return;
            }
            domStyle.set(this.container, {
                visibility: "visible"
            });
            this.visible = true;
        },

        hide: function() {
            if (!this.container) {
                return;
            }
            domStyle.set(this.container, {
                visibility: "hidden"
            });
            this.visible = false;
        },

        _draw: function(container) {
            if (!(this.htmlTpl && this.graphic)) {
                return;
            }
            this.container = container;
            var map = this._getMap(),
                location = this.getCenter(),
                scrPt = map.toScreen(location),
                innerHTML;
            if (!this.isLoaded || this.isDirty) {
                if (typeof this.htmlTpl == "string") {
                    innerHTML = string.substitute(this.htmlTpl, this.graphic.attributes);
                    domConstruct.place(innerHTML, container, "only");
                } else if(this.htmlTpl.nodeType){
                    domConstruct.place(this.htmlTpl, container, "only");
                }
                this.isDirty = false;
            }
            var width = domStyle.get(container, "width"),
                height = domStyle.get(container, "height");
            domStyle.set(container, {
                left: (scrPt.x - width / 2 + this.offsetx) + "px",
                top: (scrPt.y - height / 2 + this.offsety) + "px"
            });
            this.isLoaded = true;
        },

        getCenter: function() {
            if (!this.graphic) {
                return;
            }
            var graphic = this.graphic,
                location;
            switch (graphic.geometry.type) {
                case "point":
                    location = graphic.geometry;
                    break;
                case "polyline":
                case "extent":
                    location = graphic.geometry.getExtent().getCenter();
                    break;
                case "polygon":
                    location = graphic.geometry.getCentroid();
                    break;
                default:
                    location = graphic.geometry.getExtent().getCenter();
            }
            return location;
        },

        setGraphic: function(graphic) {
            this.graphic = graphic instanceof Graphic ? graphic : null;
            return this;
        },

        /**
         * htmlTpl: string|Dom
         */
        setHTMLTpl: function(htmlTpl) {
            this.htmlTpl = htmlTpl;
            this.isDirty = true;
            return this;
        },

        setOffset: function(offsetx, offsety) {
            this.offsetx = typeof offsetx == "number" ? offsetx : 0;
            this.offsety = typeof offsety == "number" ? offsety : 0;
            return this;
        }
    });
});
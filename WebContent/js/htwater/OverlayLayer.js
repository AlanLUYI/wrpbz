/**
 * @author chenchu
 * @date 2015 3 6
 * @description custom overlay layer inherited from GraphicsLayer
 */

define([
    "dojo/_base/declare",
    "dojo/_base/lang",
    "dojo/on",
    "dojo/mouse",
    "dojo/dom-construct",
    "dojo/dom-style",
    "dojo/dom-attr",
    "dojo/query",
    "dojo/NodeList-traverse",
    "dojo/has",
    "dojo/sniff",
    "esri/layers/GraphicsLayer",
    "esri/graphicsUtils",
    "./Overlay",
    "./Util",
    "esri/SpatialReference"
], function (declare, lang, on, mouse, domConstruct, domStyle, domAttr, query, nodeTraverse, 
		has, sniff, GraphicsLayer, GraphicsUtils, Overlay, Util,SpatialReference) {
    return declare([GraphicsLayer], {
        declaredClass: "htwater.OverlayLayer",

        constructor: function (options) {
            declare.safeMixin(this, options || {});
            this.overlays = [];
            this.overlayId = 0;
            this.visible = true;
            this._isLegacyBrowser = (has("ie") && has("ie") <= 9);
        },

        _setMap: function (map, surface) {
            this.root = domConstruct.create("div", {
                id: "overlaylayer_" + new Date().getTime()
            }, surface._parent);

            this.clickHandle = on(this.root, ".overlay-item:click", lang.hitch(this, function (event) {
                var uid = domAttr.get(query.NodeList([event.target]).closest(".overlay-item")[0], "data-uid"),
                    overlay = this._getOverlayByUID(uid);
                if (!(overlay && overlay.graphic)) {
                    return;
                }
                var graphic = overlay.graphic;
                if (graphic.infoTemplate) {
                    map.infoWindow.setTitle(graphic.getTitle());
                    map.infoWindow.setContent(graphic.getContent());
                    map.infoWindow.show(overlay.getCenter());
                }
                event.overlay = overlay;
                on.emit(this, "click-overlay", event);
            }));
            this.mouseEnterHandle = on(this.root, on.selector(".overlay-item", mouse.enter), lang.hitch(this, function (event) {
                var uid = domAttr.get(query.NodeList([event.target]).closest(".overlay-item")[0], "data-uid"),
                    overlay = this._getOverlayByUID(uid);
                if (!overlay) {
                    return;
                }
                domStyle.set(overlay.container, {
                    "z-index": 1
                });
                event.overlay = overlay;
                on.emit(this, "mouseenter-overlay", event);
            }));
            this.mouseLeaveHandle = on(this.root, on.selector(".overlay-item", mouse.leave), lang.hitch(this, function (event) {
                var uid = domAttr.get(query.NodeList([event.target]).closest(".overlay-item")[0], "data-uid"),
                    overlay = this._getOverlayByUID(uid);
                if (!overlay) {
                    return;
                }
                domStyle.set(overlay.container, {
                    "z-index": 0
                });
                event.overlay = overlay;
                on.emit(this, "mouseleave-overlay", event);
            }));

            this.panHanle = map.on("pan", lang.hitch(this, "_onPanHandler"));
            this.panEndHandle = map.on("pan-end", lang.hitch(this, "_onPanEndHandler"));
            this.zoomStartHandle = map.on("zoom-start", lang.hitch(this, "_onZoomStartHandler"));
            this.zoomEndHandle = map.on("zoom-end", lang.hitch(this, "_onZoomEndHandler"));
            return this.inherited(arguments);
        },

        _unsetMap: function () {
            this.clickHandle.remove();
            this.mouseEnterHandle.remove();
            this.mouseLeaveHandle.remove();
            this.panHanle.remove();
            this.panEndHandle.remove();
            this.zoomStartHandle.remove();
            this.zoomEndHandle.remove();
            this.overlays.length = 0;
            domConstruct.destroy(this.root);
            this.inherited(arguments);
        },

        _onPanHandler: function (event) {
            if (!event.delta) {
                return;
            }
            var offsetx = event.delta.x,
                offsety = event.delta.y;
            if (this._isLegacyBrowser) {
                domStyle.set(this.root, {
                    "position": "relative",
                    "left": offsetx + "px",
                    "top": offsety + "px"
                });
            } else {
                domStyle.set(this.root, {
                    "-webkit-transform": "-webkit-translate(" + offsetx + "px," + offsety + "px)",
                    "-ms-transform": "-ms-translate(" + offsetx + "px," + offsety + "px)",
                    "-moz-transform": "-moz-translate(" + offsetx + "px," + offsety + "px)",
                    "transform": "translate(" + offsetx + "px," + offsety + "px)"
                });
            }
        },

        _onPanEndHandler: function (event) {
            if (this._isLegacyBrowser) {
                domStyle.set(this.root, {
                    "position": "relative",
                    "left": 0,
                    "top": 0
                });
            } else {
                domStyle.set(this.root, {
                    "-webkit-transform": "-webkit-translate(0,0)",
                    "-ms-transform": "-ms-translate(0,0)",
                    "-moz-transform": "-moz-translate(0,0)",
                    "transform": "translate(0,0)"
                });
            }
        },

        _onZoomStartHandler: function () {
            domStyle.set(this.root, {
                visibility: "hidden",
                display: "none"
            });
        },

        _onZoomEndHandler: function (event) {
            var i = 0,
                l = this.overlays.length,
                overlay;
            if (typeof this.zoomEnd == "function") {
                for (; i < l; i++) {
                    overlay = this.overlays[i];
                    this.zoomEnd.call(null, overlay, event);
                }
            }
            this._refresh();
            this.visible && this.show();
        },

        add: function (overlay) {
        	var g = overlay.graphic.geometry;
        	if(g.type == "point") g.spatialReference = new SpatialReference({wkid:4326});
            this.overlays.push(overlay);
            overlay._overlaysLayer = this;
            overlay._uid = this.overlayId++;
            this._draw(overlay);
            this.inherited(arguments);
        },

        hide: function () {
            domStyle.set(this.root, {
                visibility: "hidden",
                display: "none"
            });
            this.visible = false;
        },

        show: function () {
            domStyle.set(this.root, {
                visibility: "visible",
                display: "block"
            });
            this._refresh();
            this.visible = true;
        },

        _draw: function (overlay) {
            if (!this._map) {
                return;
            }
            if (overlay instanceof Overlay) {
                this._drawOverlay(overlay);
            }
        },

        _drawOverlay: function (overlay) {
            var graphic = overlay.graphic;
            if (!graphic) {
                return;
            }
            if (graphic.visible) {
                var container;
                if (!overlay.isLoaded) {
                    container = domConstruct.toDom("<div class='overlay-item' style='position:absolute;' data-uid='" + overlay._uid + "'></div>");
                    domConstruct.place(container, this.root);
                } else {
                    container = overlay.container;
                }
                overlay._draw(container);
            }
        },

        _refresh: function () {
            var i = 0,
                l = this.overlays.length,
                overlay;
            for (; i < l; i++) {
                overlay = this.overlays[i];
                this._draw(overlay);
            }
        },

        removeOverlay: function (overlay) {
            var i = 0,
                l = this.overlays.length,
                item;
            for (; i < l; i++) {
                item = this.overlays[i];
                if (item._uid == overlay._uid) {
                    domConstruct.destroy(overlay.container);
                    this.overlays.splice(i, 1);
                    break;
                }
            }
        },

        clear: function () {
            domConstruct.empty(this.root);
            this.overlays.length = 0;
        },

        _getOverlayByUID: function (uid) {
            var i = 0,
                len = this.overlays.length,
                item;
            for (; i < len; i++) {
                item = this.overlays[i];
                if (item._uid == uid) {
                    return item;
                }
            }
        },

        getOverlaysBy: function (options) {
            var overlay, i = 0,
                len = this.overlays.length,
                results = [];
            for (; i < len; i++) {
                overlay = this.overlays[i];
                if (overlay.graphic && overlay.graphic.attributes) {
                    if (Util.isMatch(overlay.graphic.attributes, options)) {
                        results.push(overlay);
                    }
                }
            }
            return results;
        },

        toggleOverlays: function (options, show) {
            show = typeof show == "undefined" ? true : !!show;
            var overlay, len = this.overlays.length,
                i = len - 1;
            for (; i >= 0; i--) {
                overlay = this.overlays[i];
                if (overlay.graphic && overlay.graphic.attributes) {
                    if (Util.isMatch(overlay.graphic.attributes, options)) {
                        show ? overlay.show() : overlay.hide();
                    } else {
                        show ? overlay.hide() : overlay.show();
                    }
                }
            }
        },

        getExtent: function () {
            if (!this.overlays.length) {
                return null;
            }
            var graphics = [];
            for (var i = 0, len = this.overlays.length; i < len; i++) {
                graphics.push(this.overlays[i].graphic);
            }
            return GraphicsUtils.graphicsExtent(graphics);
        }
    });
});
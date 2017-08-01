define([
	"dojo/_base/declare",
	"dojo/_base/lang",
	"esri/geometry/Point",
	"esri/geometry/Circle",
	"esri/symbols/SimpleFillSymbol",
	"esri/renderers/SimpleRenderer",
	"esri/graphic",
	"esri/layers/GraphicsLayer",
	"esri/toolbars/draw",
	"esri/graphicsUtils",
	"./Overlay",
	"./OverlayLayer",
	"jquery",
	"jrange",
	"css!./css/SpatialFilter.css",
	"css!libs/jRange/jquery.range.css"
], function (declare, lang, Point, Circle, SimpleFillSymbol, SimpleRenderer, Graphic, GraphicsLayer, Draw, GraphicsUtils, Overlay, OverlayLayer, $) {
	return declare(null, {
		constructor: function (options) {
			declare.safeMixin(this, options || {});

			this.startup();
		},

		destroy: function () {
			if (this.deferred && this.deferred.state() !== "resovled") {
				this.deferred.reject();
				this.deferred = null;
			}
			this.map.removeLayer(this.graphicsLayer);
			this.map.removeLayer(this.overlayLayer);
			this.drawToolbar.deactivate();
		},

		startup: function () {
			esri.bundle.toolbars.draw.addPoint = "单击以添加圆形";

			var fillSymbol = new SimpleFillSymbol({
				"type": "esriSFS",
				"style": "esriSFSSolid",
				"color": [70, 115, 204, 50],
				"outline": {
					"type": "esriSLS",
					"style": "esriSLSSolid",
					"color": [163, 177, 204, 230],
					"width": 1 / 1.33333333333
				}
			});

			var drawToolbar = this.drawToolbar = new Draw(this.map, {
				showTooltips: true
			});
			drawToolbar.setFillSymbol(fillSymbol);

			drawToolbar.respectDrawingVertexOrder = true;
			drawToolbar.on("draw-complete", lang.hitch(this, function (event) {
				if (this.isCircle) {
					this.deferred.resolve(new Circle(event.geometry, {
						radius: 2000
					}));
				} else {
					this.deferred.resolve(event.geometry);
				}
				this.deferred = null;
				this.drawToolbar.deactivate();
				this.map.enableMapNavigation();
			}));

			var render = new SimpleRenderer(fillSymbol);
			var graphicsLayer = this.graphicsLayer = new GraphicsLayer();
			graphicsLayer.setRenderer(render);
			this.map.addLayer(graphicsLayer, 1);

			this.map.addLayer((this.overlayLayer = new OverlayLayer()));
		},

		drawCircle: function () {
			this.clear();
			this.isCircle = true;
			this.map.disableMapNavigation();
			this.drawToolbar.activate(Draw.POINT);
			return (this.deferred = $.Deferred()).promise();
		},

		drawPolygon: function () {
			this.clear();
			this.isCircle = false;
			this.map.disableMapNavigation();
			this.drawToolbar.activate(Draw.POLYGON);
			return (this.deferred = $.Deferred()).promise();
		},

		setFilteredLayer: function (layer) {
			this.filteredLayer = layer;
			return this;
		},

		filterByCircle: function (circle) {
			return this.filterByPolygon(circle);
		},

		filterByPolygon: function (polygon) {
			this.graphicsLayer.add(new Graphic(polygon));
			this.map.setExtent(GraphicsUtils.graphicsExtent(this.graphicsLayer.graphics), true);
			return this._filter(polygon);
		},

		_filter: function (geometry) {
			if (!(geometry && this.filteredLayer)) {
				return;
			}
			var results = [];
			if (this.filteredLayer.declaredClass === "htwater.OverlayLayer") {
				$.each(this.filteredLayer.overlays, function (index, overlay) {
					if (geometry.contains(overlay.graphic.geometry)) {
						overlay.show();
						results.push(overlay);
					} else {
						overlay.hide();
					}
				});
			} else if (this.filteredLayer.declaredClass === "esri.layers.GraphicsLayer") {
				$.each(this.filteredLayer.graphics, function (index, graphic) {
					if (geometry.contains(graphic.geometry)) {
						graphic.show();
						results.push(graphic);
					} else {
						graphic.hide();
					}
				});
			}

			return results;
		},

		createSlider: function (location, value, callback) {
			var self = this;
			var $sliderTpl = $("<div id='radius-range'>" +
									"<div class='radius'>" +
										"<span>" + value + "m</span>" +
										"<i class='caret'></i>" +
									"</div>" +
									"<div class='slider-wrapper'>" +
										"<input type='hidden' id='slider' value='" + value + "'>" +
									"</div>" +
							"</div>");

			$sliderTpl.children(".radius").on("click", function (event) {
				event.stopPropagation();
				$(this).next(".slider-wrapper").toggleClass("open");
			});

			$sliderTpl.find("#slider").jRange({
				from: 1000,
				to: 5000,
				step: 100,
				scale: [1000, 2000, 3000, 4000, 5000],
				width: 150,
				format: "",
				showLabels: true,
				theme: "theme-blue",
				onstatechange: function (value) {
					$(this.inputNode).closest("#radius-range").children(".radius").children("span").text(value + "m");
					var newCircle = self._updateRadius(value);
					var result = self._filter(newCircle);
					typeof callback === "function" && callback(newCircle, result);
				}
			});
			var overlay = new Overlay(new Graphic(location), $sliderTpl[0]).setOffset(60, 20);
			this.overlayLayer.add(overlay);
		},

		_updateRadius: function (radius) {
			var oldCircleGraphic = this.graphicsLayer.graphics[0],
				center = oldCircleGraphic.geometry.center,
				newCircleGraphic = new Graphic(new Circle(center, {
					radius: radius
				}));
			this.graphicsLayer.remove(oldCircleGraphic);
			this.graphicsLayer.add(newCircleGraphic);
			return newCircleGraphic.geometry;
		},

		clear: function () {
			if (this.deferred && this.deferred.state() !== "resolved") {
				this.deferred.reject();
				this.deferred = null;
			}
			if(this.filteredLayer.declaredClass === "htwater.OverlayLayer"){
				$.each(this.filteredLayer.overlays, function (index, overlay) {
					overlay.show();
				});
			}else if(this.filteredLayer.declaredClass === "esri.layers.GraphicsLayer"){
				$.each(this.filteredLayer.graphics, function (index, graphic) {
					graphic.show();
				});
			}
			this.drawToolbar.deactivate();
			this.map.enableMapNavigation();
			this.graphicsLayer.clear();
			this.overlayLayer.clear();
		}
	});
});
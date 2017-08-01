/**
 * @author : chenchu
 * @info 重新封装esri/map
 * @date 2015 11 27
 */

define([
    "dojo/_base/declare",
    "dojo/_base/lang",
    "dojo/_base/array",
    "dojo/Deferred",
    "dojo/promise/all",
    "dojo/dom",
    "dojo/query",
    "dojo/dom-construct",
    "dojo/dom-style",
    "dojo/dom-attr",
    "dojo/string",
    "esri/request",
    "esri/map",
    "esri/SpatialReference",
    "esri/geometry/Extent",
    "esri/geometry/Point",
    "esri/geometry/Polygon",
    "esri/graphic",
    "esri/graphicsUtils",
    "esri/InfoTemplate",
    "esri/dijit/InfoWindow",
    "esri/layers/ArcGISTiledMapServiceLayer",
    "esri/layers/ArcGISDynamicMapServiceLayer",
    "esri/layers/GraphicsLayer",
    "esri/tasks/query",
    "esri/tasks/QueryTask",
    "esri/geometry/webMercatorUtils",
    "esri/geometry/geodesicUtils",
    "esri/units",
    "esri/Color",
    "esri/symbols/Font",
    "esri/symbols/TextSymbol",
    "esri/symbols/SimpleMarkerSymbol",
    "esri/symbols/PictureMarkerSymbol",
    "esri/symbols/SimpleLineSymbol",
    "esri/symbols/SimpleFillSymbol",
    "esri/dijit/Scalebar",
    "esri/toolbars/draw",
    "./TDTMapLayer",
    "./GoogleMapServiceLayer",
    "./kineticPanning",
    "text!./templates/maptoggle.html"
], function (declare, lang, arrayUtil, Deferred, all, dom, domQuery, domConstruct, domStyle, domAttr, string, esriRequest, Map, SpatialReference,Extent, Point, Polygon,Graphic, GraphicsUtils, InfoTemplate, InfoWindow,
    ArcGISTiledMapServiceLayer,ArcGISDynamicMapServiceLayer, GraphicsLayer, Query, QueryTask, webMercatorUtils, geodesicUtils, Units, Color, Font, TextSymbol, SimpleMarkerSymbol, 
    PictureMarkerSymbol, SimpleLineSymbol, SimpleFillSymbol, Scalebar, Draw, TiandituMapServiceLayer, GoogleMapServiceLayer, KineticPanning, toggleTpl) {
        "use strict";
        return declare(null, {
            declaredClass: "htwater.HTMap",
            extent: null,
            baseLayers: [],
            slider: false,
            logo: false,
            drawToolbar: null,
            measureCursor: "url(" + require.toUrl("htwater/images/measure.cur") + "),auto",
            markerSymbol: new SimpleMarkerSymbol(SimpleMarkerSymbol.STYLE_CIRCLE, 10, new SimpleLineSymbol(SimpleLineSymbol.STYLE_SOLID, new Color([255, 0, 0, .5]), 2), new Color([255, 255, 255])),
            lineSymbol: new SimpleLineSymbol(SimpleLineSymbol.STYLE_SOLID, new Color([255, 0, 0, .5]), 2),
            fillSymbol: new SimpleFillSymbol(SimpleFillSymbol.STYLE_SOLID, new SimpleLineSymbol(SimpleLineSymbol.STYLE_SOLID, new Color([255, 0, 0, .5]), 2), new Color([255, 0, 0, 0])),

            constructor: function (divId, options) {
                declare.safeMixin(this, options || {});

                this.isMeasureLine = false;
                this.isMeasureArea = false;
                this.visibleBaseLayer = null;
               //初始化map对象,并定义显示范围
                if (divId && typeof divId === 'string') {                    
                    var map = this.map = new Map(divId, options);
                    
                    var infoWindow = new InfoWindow({}, domConstruct.create("div", null, map.root));
                    infoWindow.startup();
                    map.setInfoWindow(infoWindow);
                    
                    var scalebar = new Scalebar({
                        map: map
                    });
                    var panning = new KineticPanning(map);
                    panning.enableMouse();
                    panning.enableTouch();

                    map.on("load", lang.hitch(this, function () {
                        this.measureLayer = new GraphicsLayer({
                            id: "measureGraphicsLayer"
                        });
                        map.addLayers([this.measureLayer]);

                        this.measureLayer.on("click", lang.hitch(this, function (evt) {
                            var graphic = evt.graphic;
                            if (graphic.attributes && graphic.attributes.hasOwnProperty("relatedGraphics")) {
                                arrayUtil.forEach(graphic.attributes.relatedGraphics, function(relatedGraphic){
                                    this.measureLayer.remove(relatedGraphic);
                                }, this);
                                this.measureLayer.remove(graphic);
                            }
                            this.map.setMapCursor("default");
                        }));
                        this.measureLayer.on("mouse-out", lang.hitch(this, function (evt) {
                            if (!(this.isMeasureLine || this.isMeasureArea)) {
                                this.map.setMapCursor("default");
                            }
                        }));
                        this.measureLayer.on("mouse-over", lang.hitch(this, function (evt) {
                            var graphic = evt.graphic;
                            if (graphic.attributes && graphic.attributes.hasOwnProperty("relatedGraphics")) {
                                this.map.setMapCursor("pointer");
                            }
                        }));

                        var drawToolbar = this.drawToolbar = new Draw(map, {
                            showTooltips: true
                        });
                        drawToolbar.setMarkerSymbol(this.markerSymbol);
                        drawToolbar.setLineSymbol(this.lineSymbol);
                        drawToolbar.setFillSymbol(this.fillSymbol);
                        drawToolbar.respectDrawingVertexOrder = true;

                        drawToolbar.on("draw-end", lang.hitch(this, function (evt) {
                            this.map.setMapCursor("default");
                            this._measureWith(evt.geometry);
                            this.drawToolbar.deactivate();
                        }));
                    }));

                    this._initBase();
                    this._createMapToggle();
                }
            },
           //初始化图层，并把他们加入到map对象，这里图层是谷歌、天地图等底图
            _initBase: function () {
                arrayUtil.forEach(this.baseLayers, function (item, index) {
                    var visible = !!this.baseLayers[index].options.visible;
                    var resource = this.baseLayers[index].resource.toUpperCase();
                    var tiledLayer;
                    if (resource == "TIANDITU") {
                    	
                       tiledLayer = new TiandituMapServiceLayer(this.baseLayers[index].options);
                    } else if (resource == "GOOGLE") {
                        tiledLayer = new GoogleMapServiceLayer(this.baseLayers[index].options);
                    } else if (resource == "ARCGIS") {
                        tiledLayer = new ArcGISTiledMapServiceLayer(this.baseLayers[index].url, this.baseLayers[index].options);
                    }
                    if (!tiledLayer) {
                        return;
                    }
                    if (visible) {
                        this.visibleBaseLayer = tiledLayer;
                    }
                    if (tiledLayer.loaded) {
                        this.map.addLayer(tiledLayer);
                    } else {
                        tiledLayer.on("load", lang.hitch(this, function () {
                            this.map.addLayer(tiledLayer);
                        }));
                    }
                }, this);
                //如果有county.json，则加载地区边界到地图上
                if (this.countyUrl) {
                    this._countiesDeferred = this._getCounties().then(lang.hitch(this, function (featureSets) {
                        this._addFeatureSets(featureSets);
                    }), function (error) {
                        console.log(error);
                    });
                }
                
//                if(this.HTLineUrl){
//        			this.map.setExtent(this.extent);
//                	var tiledLayer = new ArcGISTiledMapServiceLayer(this.HTLineUrl);
//        			this.map.addLayer(tiledLayer);
//                }
            },
          //根据行政编码获得边界区域
            initBoundaries:function(regioncode){
            	var self=this;
            	//var tiledLayer = new ArcGISDynamicMapServiceLayer(regioncode, {"id":"1"});
    			//self.map.addLayer(tiledLayer);
            	arrayUtil.forEach(self.regionLayers, function (item, index) {
            		 if (item.id==regioncode) {
            			 //设置本地地图初始化范围
            			var extent = new Extent(item.extent);
            			self.extent=extent;
            			self.map.setExtent(extent);
            			var tiledLayer = new ArcGISTiledMapServiceLayer(item.url, {"id":item.id});
            			self.map.addLayer(tiledLayer);
					}
            	 });
            	
            },
           
            //创建底图切换事件
            _createMapToggle: function () {
                var toggleWidget = this.mapToggle = domConstruct.toDom(toggleTpl);
                domQuery("span", toggleWidget).on("click", lang.hitch(this, function (event) {
                    domQuery.NodeList([event.target]).siblings().removeClass("selected");
                    domQuery.NodeList([event.target]).addClass("selected");
                    this.changeBase(domAttr.get(event.target, "for").split(","));
                }));
                domConstruct.place(toggleWidget, dom.byId(this.map.id), "after");
            },
            
            /**
             * @param layerIds  图层id名称或者[layerId1, layerId2,...]
             */
            //切换底图
            changeBase: function (layerIds) {
                if (!layerIds) {
                    return;
                }

                var isArray = (Object.prototype.toString.apply(layerIds) == "[object Array]");

                arrayUtil.forEach(this.baseLayers, function (item, index) {
                    var layer = this.map.getLayer(this.baseLayers[index].options.id);
                    if (!layer) {
                        return;
                    }
                    if (isArray) {
                        if (arrayUtil.indexOf(layerIds, layer.id) > -1) {
                            layer.setVisibility(true);
                            this.visibleBaseLayer = layer;
                        } else {
                            layer.setVisibility(false);
                        }
                    } else {
                        if (layer.id === layerIds) {
                            layer.setVisibility(true);
                            this.visibleBaseLayer = layer;
                        } else {
                            layer.setVisibility(false);
                        }
                    }
                }, this);
            },

            pan: function () {
                this.isMeasureLine = false;
                this.isMeasureArea = false;
                this.drawToolbar.deactivate();
            },

            zoomToMaxExtent: function () {
                this.isMeasureLine = false;
                this.isMeasureArea = false;
                this.map.setExtent(this.extent);
            },

            zoomIn: function () {
                this.isMeasureLine = false;
                this.isMeasureArea = false;
                var zoom = this.map.getZoom() + 1;
                zoom = zoom > this.map.getMaxZoom() ? this.map.getMaxZoom() : zoom;
                this.map.setZoom(zoom);
            },

            zoomOut: function () {
                var zoom = this.map.getZoom() - 1;
                zoom = zoom < this.map.getMinZoom() ? this.map.getMinZoom() : zoom;
                this.map.setZoom(zoom);
            },

            measureLine: function () {
                this.isMeasureLine = true;
                this.isMeasureArea = false;
                this.map.setMapCursor(this.measureCursor);
                this.drawToolbar.activate(Draw.POLYLINE);
            },

            measureArea: function () {
                this.isMeasureArea = true;
                this.isMeasureLine = false;
                this.map.setMapCursor(this.measureCursor);
                this.drawToolbar.activate(Draw.POLYGON);
            },

            reposition: function (immediately) {
                var self = this;
                var center = this.map.extent.getCenter();
                var resizeTimer;
                return (function () {
                    clearTimeout(resizeTimer);
                    resizeTimer = setTimeout(function () {
                        self.map.resize();
                        self.map.reposition();
                        setTimeout(function () {
                            self.map.centerAt(center);   //??? 导致地图晃动，在模块加载的时候不能getExtent
                        }, 300);
                    }, !!immediately ? 0 : 100);
                })();
            },

            _measureWith: function (geometry) {
                var measureGraphic, measureGeometry, result, path, ring, lastPoint;
                measureGeometry = geometry.spatialReference.isWebMercator() ? webMercatorUtils.webMercatorToGeographic(geometry) : geometry;
                if (this.isMeasureLine) {
                    measureGraphic = new Graphic(geometry, this.lineSymbol, {});
                    result = "长度：" + (geodesicUtils.geodesicLengths([measureGeometry], Units.KILOMETERS)[0]).toFixed(1) + "公里";

                    path = geometry.paths[0];
                    lastPoint = geometry.getPoint(0, path.length - 1);
                } else {
                    measureGraphic = new Graphic(geometry, this.fillSymbol);
                    result = "面积：" + (Math.abs(geodesicUtils.geodesicAreas([measureGeometry], Units.SQUARE_KILOMETERS)[0])).toFixed(1) + "平方公里";

                    ring = geometry.rings[0];
                    lastPoint = geometry.getPoint(0, ring.length - 2);
                }
                this.measureLayer.add(measureGraphic);

                //添加测量结果
                var textSymbol = new TextSymbol(result)
                    .setColor(new Color([255, 0, 0, .75]))
                    .setFont(new Font("12px", Font.STYLE_NORMAL,
                        Font.VARIANT_NORMAL, Font.WEIGHT_NORMAL, "arial, simsun"))
                    .setVerticalAlignment(TextSymbol.ALIGN_START)
                var textGraphic = new Graphic(lastPoint, textSymbol, {});
                this.measureLayer.add(textGraphic);
                var textWidth = textGraphic.getDojoShape().getTextWidth(); //获取测量结果文字宽度
                this.measureLayer.remove(textGraphic); //删除测量结果文字，添加背景之后重新添加，否则文字在背景下方
                //计算偏移量
                var angle, isRight, isUpper, textOffsetx, textOffsety, bgOffsetx, bgOffsety, closeOffsetx, closeOffsety;
                if (this.isMeasureLine) {
                    angle = this._getAngle(geometry.getPoint(0, path.length - 2), lastPoint);
                    if (angle >= 90 && angle < 270) {
                        textOffsetx = -(textWidth + 6) / 2;
                        textOffsety = 15;
                        bgOffsetx = -(textWidth + 8) / 2;
                        bgOffsety = 20;
                        closeOffsetx = -10;
                        closeOffsety = 0;
                    } else {
                        textOffsetx = (textWidth + 6) / 2;
                        textOffsety = 15;
                        bgOffsetx = (textWidth + 8) / 2;
                        bgOffsety = 20;
                        closeOffsetx = 10;
                        closeOffsety = 0;
                    }
                } else {
                    isRight = this._isSegDisjointRing([lastPoint.x, lastPoint.y], [10000, lastPoint.y], ring);
                    isUpper = this._isSegDisjointRing([lastPoint.x, lastPoint.y], [lastPoint.x, 10000], ring);
                    if (isRight) {
                        textOffsetx = (textWidth + 6) / 2;
                        textOffsety = isUpper ? 15 : -25;
                        bgOffsetx = (textWidth + 8) / 2;
                        bgOffsety = isUpper ? 20 : -20;
                        closeOffsetx = 10;
                        closeOffsety = 0;
                    } else {
                        textOffsetx = -(textWidth + 6) / 2;
                        textOffsety = isUpper ? 15 : -25;
                        bgOffsetx = -(textWidth + 8) / 2;
                        bgOffsety = isUpper ? 20 : -20;
                        closeOffsetx = -10;
                        closeOffsety = 0;
                    }
                }

                //添加测量结果背景框
                var bgSymbol = new SimpleMarkerSymbol()
                    .setPath(this.isMeasureLine ? "M84.566,68.52h-80v-16h80V68.52z" : "M104.359,53.869h-100v-16h100V53.869z")
                    .setColor(new Color([255, 255, 255, 1]))
                    .setOutline(new SimpleLineSymbol(SimpleLineSymbol.STYLE_SOLID, new Color([255, 0, 0, 1]), 1))
                    .setSize(textWidth + 8)
                    .setOffset(bgOffsetx, bgOffsety);
                var bgGraphic = new Graphic(lastPoint, bgSymbol, {});
                this.measureLayer.add(bgGraphic);

                //重新添加测量结果文字
                textSymbol = textSymbol.setOffset(textOffsetx, textOffsety);
                textGraphic = new Graphic(lastPoint, textSymbol, {});
                this.measureLayer.add(textGraphic);

                //添加清除按钮
                var closeSymbol = new SimpleMarkerSymbol()
                    .setPath("M0.982,31.786h29.586L15.775,16.995L0.982,31.786L0.982,31.786z M16.482,16.288l14.793,14.792V1.495L16.482,16.288L16.482,16.288z M0.275,1.495v29.584l14.793-14.792L0.275,1.495L0.275,1.495z M0.982,0.788l14.793,14.792L30.568,0.788H0.982L0.982,0.788z")
                    .setColor(new Color([255, 255, 255, 1]))
                    .setOutline(new SimpleLineSymbol(SimpleLineSymbol.STYLE_SOLID, new Color([255, 0, 0, 1]), 1.5))
                    .setSize("15")
                    .setOffset(closeOffsetx, closeOffsety);
                var closeGraphic = new Graphic(lastPoint, closeSymbol, {
                    relatedGraphics: [measureGraphic, textGraphic, bgGraphic]
                });
                this.measureLayer.add(closeGraphic);
                this.isMeasureLine = false;
                this.isMeasureArea = false;
            },

            /**
             * 判断线段是否与多边形相离
             */
            _isSegDisjointRing: function (start, end, ring) {
                /**
                 * 判断两条线段是否相交
                 */
                function isIntersect(point1, point2, point3, point4) {
                    //外接矩形初步判断
                    if (!((Math.min(point1[0], point2[0]) <= Math.max(point3[0], point4[0])) && (Math.min(point3[0], point4[0]) <= Math.max(point1[0], point2[0])) && (Math.min(point1[1], point2[1]) <= Math.max(point3[1], point4[1])) && (Math.min(point3[1], point4[1]) <= Math.max(point1[1], point2[1])))) {
                        return false;
                    }

                    var vector1 = [point2[0] - point1[0], point2[1] - point1[1]]; //线段1所在的向量
                    var vector2 = [point4[0] - point3[0], point4[1] - point3[1]]; //线段2所在的向量

                    var vector3 = [point3[0] - point1[0], point3[1] - point1[1]]; //点1到点3所在的向量
                    var vector4 = [point4[0] - point1[0], point4[1] - point1[1]]; //点1到点4所在向量
                    //通过向量叉积判断point3、point4是否在线段1的两侧
                    var denom1 = (vector1[0] * vector3[1] - vector3[0] * vector1[1]) * (vector1[0] * vector4[1] - vector4[0] * vector1[1]);

                    var vector5 = [point1[0] - point3[0], point1[1] - point3[1]]; //点3到点1的向量
                    var vector6 = [point2[0] - point3[0], point2[1] - point3[1]]; //点3到点2的向量
                    //通过向量叉积判断point1、point2是否在线段2的两侧
                    var denom2 = (vector2[0] * vector5[1] - vector5[0] * vector2[1]) * (vector2[0] * vector6[1] - vector6[0] * vector2[1]);

                    if (denom1 <= 0 && denom2 <= 0) {
                        return true;
                    }
                    return false;
                }
                var count = 0,
                    from, to;
                for (var i = 0, l = ring.length; i < l - 1; i++) {
                    from = ring[i];
                    to = ring[i + 1];
                    if (isIntersect(start, end, from, to)) {
                        count++; //不判断临界值和特殊情况了，只适用于有一个端点在多边形上的情况了
                    }
                }
                return count <= 2 ? true : false;
            },

            /**
             * 获得线段与x轴的夹角
             */
            _getAngle: function (from, to) {
                var angle = Math.atan2((to.y - from.y), (to.x - from.x)) * 180 / Math.PI;
                if (angle < 0) {
                    angle += 360;
                }
                return angle;
            },

            getLayerData: function (layerConfig) {
                var deferred = new Deferred();
                var query = new Query();
                var promises = [];
                var promise = null;
                query.returnGeometry = true;
                query.outFields = layerConfig.fields || [];
                query.where = layerConfig.filter || "1=1";
                var url = layerConfig.layerUrl ? layerConfig.layerUrl : this.globalQueryUrl + "/" + layerConfig.layerId || "";
                var queryTask = new QueryTask(url);
                queryTask.executeForIds(query, lang.hitch(this, function (results) {
                    var cnt = Math.ceil(results.length / 1000),
                        objIds, howMany = 0;
                    for (var i = 0; i < cnt; i++) {
                        howMany = results.length > 1000 ? 1000 : results.length;
                        objIds = results.splice(0, howMany);
                        query.objectIds = objIds;
                        promise = queryTask.execute(query);
                        promises.push(promise);
                    }
                    all(promises).then(lang.hitch(this, function (featureSets) {
                        deferred.resolve(featureSets);
                    }), function (error) {
                        console.log(error.message);
                        deferred.reject(error);
                    });
                }), function (error) {
                    console.log(error.message);
                    deferred.reject(error);
                });
                return deferred.promise;
            },
           //加载county.json数据，获取边届范围,创建graphic
            _getCounties: function () {
                return esriRequest({
                    url: this.countyUrl,
                    handleAs: "json"
                }).then(function(response){
                    var features = []
                    var sr = new SpatialReference(response.spatialReference)
                    arrayUtil.forEach(response.features, function(item){
                        var graphic = new Graphic(item)
                        //graphic.geometry.spatialReference = spatialReference
                        features.push(graphic)
                    })
                    return [{features: features}]
                })
                
                
                // return this.getLayerData({
                //     layerUrl: this.countyUrl,
                //     outFields: [this.countyField]
                // });
            },
            //重新为登录用户所在市画边界范围
            getBoundaries:function(region)
            {
            	var features=[],pointseries = [];
            	var points=region.split(",");
            	arrayUtil.forEach(points, function (item) {
            		var items=item.split(" ");
            		var point=[];
            		point.push(items[0]);point.push(items[1]);
            		pointseries.push(point);
            	});
            	var polygonJson= {"rings":[pointseries],"spatialReference":{"wkid":4326 }};
            	var polygon = new Polygon(polygonJson);
            	//var attr={"County":"杭州市"};
            	 var graphic = new Graphic(polygon);
            	 features.push(graphic);
            	 
            	 this._addFeatureSets([{features: features}]);
            },
            _addFeatureSets: function (featureSets) {
                if (!(featureSets || featureSets.length)) {
                    return;
                }
               /* arrayUtil.forEach(featureSets, function (featureSet) {
                    arrayUtil.forEach(featureSet.features, function (feature) {
                        feature.setSymbol(this.fillSymbol);
                        this.map.graphics.add(feature);
                        //feature.hide();
                    }, this);
                }, this);*/
            },

            showCounties: function (counties) {
                function showAndZoom(counties) {
                    var selectedGrapphics = [];
                    counties = counties || [];
                    arrayUtil.forEach(this.map.graphics.graphics, function (graphic) {
                        if (graphic.attributes && graphic.attributes[this.countyField]) {
                            if (arrayUtil.indexOf(counties, graphic.attributes[this.countyField]) > -1) {
                                graphic.show();
                                selectedGrapphics.push(graphic);
                            } else {
                                graphic.hide();
                            }
                        }
                    }, this);
                    selectedGrapphics.length && this.map.setExtent(GraphicsUtils.graphicsExtent(selectedGrapphics), true);
                }

                if (!this.countyUrl) {
                    console.log("please set county url");
                } else {
                    if (this._countiesDeferred.isResolved()) {
                        lang.hitch(this, showAndZoom, counties)();
                    } else if (!this._countiesDeferred.isRejected()) {
                        this._countiesDeferred.then(lang.hitch(this, showAndZoom, counties));
                    }
                }
            },
            //定位到城市中心点
            centerAt: function (point, level, animation) {
                var mapPoint = point;
                animation = typeof animation == "undefined" ? true : !!animation;
                domConstruct.destroy(dom.byId("flash-container"));
                if (point && Object.prototype.toString.call(point) === "[object Array]" && point.length === 2) {
                    mapPoint = new Point(point, new SpatialReference({ wkid:4326 }));//这里坐标参考系只能是4326，获取到的经纬度坐标是120,30样式
                }
                return (typeof level !== "number" ? this.map.centerAt(mapPoint) : this.map.centerAndZoom(mapPoint, level)).then(lang.hitch(this, function () {
                    animation && this._centerAnimation(mapPoint);
                }));
            },

            centerAtGraphicPos: function (graphic, level, animation) {
                if (!graphic) return;
                animation = typeof animation == "undefined" ? true : !!animation;
                var location;
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
                        location = this.extent.getCenter();
                }
                if (!(location.x || location.y)) {
                    throw new Error("the geometry of graphic is invalid");
                }
                if (this.map.infoWindow.isShowing) {
                    this.map.infoWindow.hide();
                }
                domConstruct.destroy(dom.byId("flash-container"));
                return (typeof level !== "number" ? this.map.centerAt(location) : this.map.centerAndZoom(location, level)).then(lang.hitch(this, function () {
                    animation && this._centerAnimation(location);
                    if (graphic.infoTemplate) {
                        this.map.infoWindow.setTitle(graphic.getTitle());
                        this.map.infoWindow.setContent(graphic.getContent());
                        this.map.infoWindow.show(location);
                    }
                }));
            },

            _centerAnimation: function (location) {
                var animationHtml = '<div id="flash-container" style="left:${left}px; top: ${top}px">' +
                    '<div class="el flash-tl"></div>' +
                    '<div class="el flash-bl"></div>' +
                    '<div class="el flash-tr"></div>' +
                    '<div class="el flash-br"></div>' +
                    '</div>';
                var visibleLayerNode = this.visibleBaseLayer.getNode();
                var screenPt = this.map.toScreen(location);
                var matrixArray = (domStyle.get(visibleLayerNode, "transform") || "").match(/(-?[0-9\.]+)/g);
                var offsetx = 0;
                var offsety = 0;
                if (matrixArray) {
                    if (matrixArray.length === 6) {    //ie 10 chrome firefox
                        offsetx = parseFloat(matrixArray[4]);
                        offsety = parseFloat(matrixArray[5]);
                    } else if (matrixArray.length === 17) {  //fuck ie11
                        offsetx = parseFloat(matrixArray[13]);
                        offsety = parseFloat(matrixArray[14]);
                    }
                } else {                             //fuck ie9 ie8
                    offsetx = domStyle.get(visibleLayerNode, "left");
                    offsety = domStyle.get(visibleLayerNode, "top");
                }
                var aDom = domConstruct.toDom(string.substitute(animationHtml, {
                    left: screenPt.x - offsetx,
                    top: screenPt.y - offsety
                }));
                domConstruct.place(aDom, visibleLayerNode, "last");
                setTimeout(function () {
                    domConstruct.destroy(aDom);
                }, 2000);
            },

            clearGraphics: function () {
                arrayUtil.forEach(this.map.graphics, function (graphic) {
                    graphic.hide();
                });
                this.measureLayer.clear();
                if (this.map.infoWindow.isShowing) {
                    this.map.infoWindow.hide();
                }
            },

            destroy: function () {
                this.map.removeAllLayers();
                this.map.destroy();
            },

            coordsTrans:function(x,y)
            {
            	var geometryService = new esri.tasks.GeometryService("http://tasks.arcgisonline.com/ArcGIS/rest/services/Geometry/GeometryServer");
                var incoord = 4326;  //当前坐标系
            	var outcoord = 102100;  //目标坐标系
            	var inSR = new esri.SpatialReference({wkid: incoord });
            	var outSR = new esri.SpatialReference({wkid: outcoord});
            	var inputpoint = new esri.geometry.Point(x, y, inSR);
            	var PrjParams = new esri.tasks.ProjectParameters();
            	PrjParams.geometries = [inputpoint];
            	PrjParams.outSR = outSR;
            	PrjParams.transformation = {wkid: parseInt(4326)}
            	geometryService.project(PrjParams, function (outputpoint) {
                
            	alert("经度："+outputpoint[0].x+",纬度："+ outputpoint[0].y);
            	     
            	});
            }
        });
    });
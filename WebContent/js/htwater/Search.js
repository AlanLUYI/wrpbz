/**
 * @author chenchu
 * @date 2015 3 30
 * @description search features by value and location
 */
define([
    "dojo/_base/declare",
    "dojo/_base/lang",
    "dojo/_base/array",
    "dojo/promise/all",
    "dojo/on",
    "dojo/string",
    "esri/dijit/Search",
    "esri/layers/FeatureLayer",
    "esri/layers/GraphicsLayer",
    "esri/graphic",
    "esri/InfoTemplate",
    "esri/symbols/PictureMarkerSymbol",
    "esri/symbols/SimpleFillSymbol",
    "esri/graphicsUtils",
    "esri/tasks/query",
    "esri/tasks/QueryTask",
    "esri/tasks/GeometryService",
    "esri/tasks/BufferParameters",
    "./SearchConfig"
], function(declare, lang, array, all, on, string, Search, FeatureLayer, GraphicsLayer, Graphic, InfoTemplate, PictureMarkerSymbol,
    SimpleFillSymbol, GraphicsUtils, Query, QueryTask, GeometryService, BufferParameters, SearchConfig) {
    return declare(null, {
        declaredClass: "htwater.Search",
        searchIcon: require.toUrl("htwater/images/search-pointer.png"),

        constructor: function(options) {
            declare.safeMixin(this, options || {});
            this._lastSearching = null;
            this.startup();
        },

        startup: function() {
            var graphicsLayer = this.graphicsLayer = new GraphicsLayer();
            if (this.map.loaded) {
                this.map.addLayer(graphicsLayer);
            } else {
                this.map.on("load", lang.hitch(this, function() {
                    this.map.addLayer(graphicsLayer);
                }));
            }
            this.picSymbol = new PictureMarkerSymbol(this.searchIcon, 36, 36).setOffset(9, 18);
            this.fillSymbol = new SimpleFillSymbol({
                type: "esriSFS",
                style: "esriSFSSolid",
                color: [255, 0, 0, 75],
                outline: {
                    type: "esriSLS",
                    style: "esriSLSSolid",
                    color: [255, 0, 0, 100],
                    width: 1
                }
            });
            this.geometryService = new GeometryService(SearchConfig.geometryServiceUrl);

            var searchDijit = this.searchDijit = new Search({
                autoNavigate: false,
                autoSelect: false,
                enableButtonMode: true,
                enableLabel: false,
                enableInfoWindow: true,
                showInfoWindowOnSelect: false,
                sources: [],
                map: this.map
            }, SearchConfig.domNode);

            var sources = searchDijit.get("sources");
            array.forEach(SearchConfig.sources, function(item, index) {
                sources.push({
                    featureLayer: new FeatureLayer(item.featureLayerUrl),
                    searchFields: item.searchFields || [],
                    displayField: item.displayField || "",
                    outFields: item.outFields || ["*"],
                    exactMatch: false,
                    name: item.name || ("layer_" + index),
                    placeholder: item.placeholder || "",
                    maxResults: 5,
                    maxSuggestions: 5,
                    enableSuggestions: true,
                    minCharacters: 0,
                    infoTemplate: this._createInfoTemplate(item.infoTemplate.title, item.infoTemplate.content)
                });
            }, this);
            searchDijit.set("sources", sources);
            on(searchDijit, "search-results", lang.hitch(this, "_onSearchResults"));
            searchDijit.startup();
        },

        _createInfoTemplate: function(title, content) {
            var infoTemplate = new InfoTemplate();
            infoTemplate.setTitle(title);
            infoTemplate.setContent(lang.hitch(this, function(graphic) {
                this.map.infoWindow.resize(350, 180);
                if (typeof content == "function") {
                    return content.call(null, graphic);
                } else if (typeof content == "string") {
                    return string.substitute(content, graphic.attributes);
                }
                return "";
            }));
            return infoTemplate;
        },

        _onSearchResults: function(event) {
            this.graphicsLayer.clear();
            if (event.error) {
                console.log(event.error);
                return;
            }
            var sources = this.searchDijit.get("sources"),
                activeSource = this.searchDijit.activeSource,
                results = event.results,
                result, graphic;
            for (var prop in results) {
                if (results.hasOwnProperty(prop)) {
                    result = results[prop];
                    for (var i = 0, l = result.length; i < l; i++) {
                        graphic = result[i].feature;
                        graphic.symbol = this.picSymbol;
                        graphic.infoTemplate = activeSource ? activeSource.infoTemplate : sources[prop].infoTemplate;
                        this.graphicsLayer.add(graphic);
                    }
                }
            }
            this.graphicsLayer.graphics.length && this.map.setExtent(GraphicsUtils.graphicsExtent(this.graphicsLayer.graphics), true);
        },

        /**
         *  @location : esri.geometry.Point
         *  @distance: integer
         *  @description 通过位置和缓冲范围来查找相应数据源中的要素
         */
        reverseGeocode: function(location, distance) {
            if (typeof distance !== "number" || distance < 0) {
                distance = 100;
            }
            var bufParams = new BufferParameters();
            bufParams.geometries = [location];
            bufParams.distances = [distance];
            bufParams.unit = GeometryService.UNIT_METER;
            bufParams.geodesic = true;
            bufParams.outSpatialReference = this.map.spatialReference;
            this.geometryService.buffer(bufParams, lang.hitch(this, function(geometries) {
                var bufGeometry = geometries[0],
                    activeSource = this.searchDijit.activeSource;
                if (!activeSource) {
                    this._queryByGeometry(this.searchDijit.get("sources"), bufGeometry);
                } else {
                    this._queryByGeometry([activeSource], bufGeometry);
                }
            }), function(error) {
                console.log(error);
            });
        },

        _queryByGeometry: function(sources, geometry) {
            var deferreds = [];
            array.forEach(sources, function(source) {
                var queryTask = new QueryTask(source.featureLayer.url),
                    query = new Query();
                query.returnGeometry = true;
                query.outFields = source.outFields;
                query.geometry = geometry;
                query.spatialRelationship = Query.SPATIAL_REL_CONTAINS;
                deferreds.push(queryTask.execute(query));
            }, this);
            if (this._lastSearching && !(this._lastSearching.isResolved() || this._lastSearching.isRejected())) {
                this._lastSearching.otherwise(function(reason) {
                    console.log(reason);
                });
                this._lastSearching.cancel("cancels the last unfulfilled search");
            }
            this._lastSearching = all(deferreds).then(lang.hitch(this, function(featureSets) {
                this.graphicsLayer.clear();
                this.graphicsLayer.add(new Graphic(geometry, this.fillSymbol));
                array.forEach(featureSets, function(featureSet, index) {
                    this._addFeatureSet(featureSet, sources[index]);
                }, this);
            }), function(error) {
                console.log(error);
            }).then(lang.hitch(this, function() {
                var extent = GraphicsUtils.graphicsExtent(this.graphicsLayer.graphics);
                this.map.setExtent(extent, true);
            }));
        },

        _addFeatureSet: function(featureSet, source) {
            if (!(featureSet && featureSet.features)) {
                return;
            }
            array.forEach(featureSet.features, function(feature) {
                feature.symbol = this.picSymbol;
                feature.infoTemplate = source.infoTemplate;
                this.graphicsLayer.add(feature);
            }, this);
        }
    });
});
define([
    "dojo/_base/declare",
    "dojo/_base/lang",
    "dojo/string",
    "esri/SpatialReference",
    "esri/geometry/Extent",
    "esri/layers/TileInfo",
    "esri/layers/TiledMapServiceLayer"
], function (declare, lang, string, SpatialReference, Extent, TileInfo, TiledMapServiceLayer) {
    "use strict";
    var GaodeMapServiceLayer = declare(TiledMapServiceLayer, {
        constructor: function (options) {
            options = options || {};
            
            this.mapType = options.mapType || GaodeMapServiceLayer.MAP_TYPE_ROAD;
            
            switch(this.mapType){
                case GaodeMapServiceLayer.MAP_TYPE_SATELLITE:
                    this.url= "http://webst0${num}.is.autonavi.com/appmaptile?style=6&x=${numX}&y=${numY}&z=${level}";
                    break;
                case GaodeMapServiceLayer.MAP_TYPE_ANNO:
                    this.url= "http://webst0${num}.is.autonavi.com/appmaptile?style=8&x=${numX}&y=${numY}&z=${level}";
                    break;
                case GaodeMapServiceLayer.MAP_TYPE_ROAD:
                default: 
                    this.url= "http://webrd0${num}.is.autonavi.com/appmaptile?lang=zh_cn&size=1&scale=1&style=8&x=${numX}&y=${numY}&z=${level}";
                    break;
            }
            
            this.spatialReference = new SpatialReference({
                wkid: 102100
            });

            this.initialExtent = options.initialExtent ||  new Extent(12971492.042598005, 3127303.907030518, 13754207.21223817, 3676427.5182311973, this.spatialReference); 
            
            this.fullExtent = options.fullExtent || new Extent(-20037508.3427892, -20037508.3427892, 20037508.3427892, 20037508.3427892, this.spatialReference);

            this.tileInfo = new TileInfo({
                "dpi": 96,
                "rows": 256,
                "cols": 256,
                "origin": {
                    "x": -20037508.3427892,
                    "y": 20037508.3427892
                },
                "spatialReference": this.spatialReference,
                "lods": [
                    //{ "level": 0, "resolution": 156543.033928, "scale": 591657527.591555 },
                    //{ "level": 1, "resolution": 78271.5169639999, "scale": 295828763.795777 },
                    { "level": 2, "resolution": 39135.7584820001, "scale": 147914381.897889 },
                    { "level": 3, "resolution": 19567.8792409999, "scale": 73957190.948944 },
                    { "level": 4, "resolution": 9783.93962049996, "scale": 36978595.474472 },
                    { "level": 5, "resolution": 4891.96981024998, "scale": 18489297.737236 },
                    { "level": 6, "resolution": 2445.98490512499, "scale": 9244648.868618 },
                    { "level": 7, "resolution": 1222.99245256249, "scale": 4622324.434309 },
                    { "level": 8, "resolution": 611.49622628138, "scale": 2311162.217155 },
                    { "level": 9, "resolution": 305.748113140558, "scale": 1155581.108577 },
                    { "level": 10, "resolution": 152.874056570411, "scale": 577790.554289 },
                    { "level": 11, "resolution": 76.4370282850732, "scale": 288895.277144 },
                    { "level": 12, "resolution": 38.2185141425366, "scale": 144447.638572 },
                    { "level": 13, "resolution": 19.1092570712683, "scale": 72223.819286 },
                    { "level": 14, "resolution": 9.55462853563415, "scale": 36111.9096437 },
                    { "level": 15, "resolution": 4.77731426794937, "scale": 18055.9548224 },
                    { "level": 16, "resolution": 2.38865713397468, "scale": 9027.977411 },
                    { "level": 17, "resolution": 1.19432856685505, "scale": 4513.988705 },
                    { "level": 18, "resolution": 0.597164283559817, "scale": 2256.994353 },
                    { "level": 19, "resolution": 0.298582141647617, "scale": 1128.497176 }
                ]
            });

            this.loaded = true;
            this.onLoad(this);
        },

        getTileUrl: function (level, row, col) {
            var num = (col + row) % 4 + 1;
            return string.substitute(this.url, {
                num: num,
                numX: col,
                numY: row,
                level: level
            });
        }

    });
    
    lang.mixin(GaodeMapServiceLayer, {
        MAP_TYPE_SATELLITE: "satellite",
        MAP_TYPE_ROAD: "road",
        MAP_TYPE_ANNO: "anno"
    });
    
    return GaodeMapServiceLayer;
});
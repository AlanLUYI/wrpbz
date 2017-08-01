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
    var TdtMapServiceLayer = declare(TiledMapServiceLayer, {
        constructor: function (options) {
            options = options || {};

            this.mapType = options.mapType || TdtMapServiceLayer.MAP_TYPE_ROAD;
            
            switch(this.mapType){
                case TdtMapServiceLayer.MAP_TYPE_ROAD_ANNO:
                    this.url = "http://t${num}.tianditu.com/cva_c/wmts?SERVICE=WMTS&VERSION=1.0.0&REQUEST=GetTile&LAYER=cva";
                    break;
                case TdtMapServiceLayer.MAP_TYPE_SATELLITE_ANNO:
                    this.url = "http://t${num}.tianditu.com/cia_c/wmts?SERVICE=WMTS&VERSION=1.0.0&REQUEST=GetTile&LAYER=cia";
                    break;
                case TdtMapServiceLayer.MAP_TYPE_TERRAIN_ANNO:
                    this.url = "http://t${num}.tianditu.com/cta_c/wmts?SERVICE=WMTS&VERSION=1.0.0&REQUEST=GetTile&LAYER=cta";
                    break;
                case TdtMapServiceLayer.MAP_TYPE_ROAD:
                    this.url = "http://t${num}.tianditu.com/vec_c/wmts?SERVICE=WMTS&VERSION=1.0.0&REQUEST=GetTile&LAYER=vec";
                    break;
                case TdtMapServiceLayer.MAP_TYPE_SATELLITE:
                    this.url = "http://t${num}.tianditu.com/img_c/wmts?SERVICE=WMTS&VERSION=1.0.0&REQUEST=GetTile&LAYER=img";
                    break;
                case TdtMapServiceLayer.MAP_TYPE_TERRAIN:
                default:
                   this.url = "http://t${num}.tianditu.com/ter_c/wmts?SERVICE=WMTS&VERSION=1.0.0&REQUEST=GetTile&LAYER=ter";
            }
            this.url += ("&STYLE=default&FORMAT=tiles&TILEMATRIXSET=c"
                          + "&TILEMATRIX=${level}"
                          + "&TILEROW=${row}"
                          + "&TILECOL=${col}");
            
            this.spatialReference = new SpatialReference({
                wkid: 4326
            });
            
            this.initialExtent = options.initialExtent || new Extent(-180.0, -90.0, 180.0, 90.0, this.spatialReference);
            
            this.fullExtent = options.fullExtent || new Extent(-180.0, -90.0, 180.0, 90.0, this.spatialReference);
            
            this.tileInfo = new TileInfo({
                "dpi": 96,
                "rows": 256,
                "cols": 256,
                "origin": {
                    "x": -180,
                    "y": 90
                },
                "spatialReference": this.spatialReference,
                "lods": [
                    { "level": 2, "resolution": 0.3515625, "scale": 147748796.52937502 },
                    { "level": 3, "resolution": 0.17578125, "scale": 73874398.264687508 },
                    { "level": 4, "resolution": 0.087890625, "scale": 36937199.132343754 },
                    { "level": 5, "resolution": 0.0439453125, "scale": 18468599.566171877 },
                    { "level": 6, "resolution": 0.02197265625, "scale": 9234299.7830859385 },
                    { "level": 7, "resolution": 0.010986328125, "scale": 4617149.8915429693 },
                    { "level": 8, "resolution": 0.0054931640625, "scale": 2308574.9457714846 },
                    { "level": 9, "resolution": 0.00274658203125, "scale": 1154287.4728857423 },
                    { "level": 10, "resolution": 0.001373291015625, "scale": 577143.73644287116 },
                    { "level": 11, "resolution": 0.0006866455078125, "scale": 288571.86822143558 },
                    { "level": 12, "resolution": 0.00034332275390625, "scale": 144285.93411071779 },
                    { "level": 13, "resolution": 0.000171661376953125, "scale": 72142.967055358895 },
                    { "level": 14, "resolution": 8.58306884765625e-005, "scale": 36071.483527679447 },
                    { "level": 15, "resolution": 4.291534423828125e-005, "scale": 18035.741763839724 },
                    { "level": 16, "resolution": 2.1457672119140625e-005, "scale": 9017.8708819198619 },
                    { "level": 17, "resolution": 1.0728836059570313e-005, "scale": 4508.9354409599309 },
                    { "level": 18, "resolution": 5.3644180297851563e-006, "scale": 2254.4677204799655 }
                ]
            });

            this.loaded = true;
            this.onLoad(this);
        },

        getTileUrl: function (level, row, col) {
            var num = (row + col) % 8;
            return string.substitute(this.url, {
                num: num,
                level: level,
                row: row,
                col: col
            });
        }

    });

    lang.mixin(TdtMapServiceLayer, {
        MAP_TYPE_ROAD: "road",
        MAP_TYPE_TERRAIN: "terrain",
        MAP_TYPE_SATELLITE: "satellite",
        MAP_TYPE_ROAD_ANNO: "road_anno",
        MAP_TYPE_TERRAIN_ANNO: "terrain_anno",
        MAP_TYPE_SATELLITE_ANNO: "satellite_anno"
    });

    return TdtMapServiceLayer;
});
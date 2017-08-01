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
	var TiandituMapServiceLayer = declare(TiledMapServiceLayer, {
		constructor: function (options) {
			options = options || {};
            this.mapType = options.mapType || TiandituMapServiceLayer.MAP_TYPE_TERRAIN;
			
            switch(this.mapType){
                case TiandituMapServiceLayer.MAP_TYPE_SATELLITE_ANNO:
                    this.url = "http://t${num}.tianditu.com/cia_w/wmts?SERVICE=WMTS&VERSION=1.0.0&REQUEST=GetTile&LAYER=cia";
                    break;
                case TiandituMapServiceLayer.MAP_TYPE_TERRAIN_ANNO:
                    this.url = "http://t${num}.tianditu.com/cta_w/wmts?SERVICE=WMTS&VERSION=1.0.0&REQUEST=GetTile&LAYER=cta";
                    break;
                case TiandituMapServiceLayer.MAP_TYPE_SATELLITE:
                    this.url = "http://t${num}.tianditu.com/img_w/wmts?SERVICE=WMTS&VERSION=1.0.0&REQUEST=GetTile&LAYER=img";
                    break;
                case TiandituMapServiceLayer.MAP_TYPE_TERRAIN:
                default:
                   this.url = "http://t${num}.tianditu.com/ter_w/wmts?SERVICE=WMTS&VERSION=1.0.0&REQUEST=GetTile&LAYER=ter";
            }
            this.url += ("&STYLE=default&FORMAT=tiles&TILEMATRIXSET=w"
                          + "&TILEMATRIX=${level}"
                          + "&TILEROW=${row}"
                          + "&TILECOL=${col}");
            
			this.spatialReference = new SpatialReference({
				wkid: 102100
			});

            this.initialExtent = options.initialExtent || new Extent(12971492.042598005, 3127303.907030518, 13754207.21223817, 3676427.5182311973, this.spatialReference);
            
			this.fullExtent = options.fullExtent || new Extent(-20037508.3427892 , -20037508.3427892, 20037508.3427892, 20037508.3427892, this.spatialReference);
            
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
					{ "level": 2, "resolution": 39135.75848201024, "scale": 147914677.7272828 },
					{ "level": 3, "resolution": 19567.87924100512, "scale": 73957338.8636414 },
					{ "level": 4, "resolution": 9783.93962050256, "scale": 36978669.4318207 },
					{ "level": 5, "resolution": 4891.96981025128, "scale": 18489334.71591035 },
					{ "level": 6, "resolution": 2445.98490512564, "scale": 9244667.357955175 },
					{ "level": 7, "resolution": 1222.99245256282, "scale": 4622333.678977588 },
					{ "level": 8, "resolution": 611.49622628141, "scale": 2311166.839488794 },
					{ "level": 9, "resolution": 305.748113140705, "scale": 1155583.419744397 },
					{ "level": 10, "resolution": 152.8740565703525, "scale": 577791.7098721985 },
					{ "level": 11, "resolution": 76.43702828517625, "scale": 288895.85493609926 },
					{ "level": 12, "resolution": 38.21851414258813, "scale": 144447.92746804963 },
					{ "level": 13, "resolution": 19.109257071294063, "scale": 72223.96373402482 },
					{ "level": 14, "resolution": 9.554628535647032, "scale": 36111.98186701241 },
					{ "level": 15, "resolution": 4.777314267823516, "scale": 18055.990933506204 },
					{ "level": 16, "resolution": 2.388657133911758, "scale": 9027.995466753102 },
					{ "level": 17, "resolution": 1.194328566955879, "scale": 4513.997733376551 },
					{ "level": 18, "resolution": 0.5971642834779395 , "scale": 2256.998866688275  }
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
    
    lang.mixin(TiandituMapServiceLayer, {
       MAP_TYPE_TERRAIN: "terrain",
       MAP_TYPE_SATELLITE: "satellite",
       MAP_TYPE_TERRAIN_ANNO: "terrain_anno",
       MAP_TYPE_SATELLITE_ANNO: "satellite_anno"
    });
    
    return TiandituMapServiceLayer;
});
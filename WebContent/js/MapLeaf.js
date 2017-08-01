/**
 * @author chenchu
 * @date 2014 10 13
 * @info 单例模式构造地图
 */
define([
    "esri/geometry/Extent",
    "htwater/HTMap",
    "htwater/MapConfig"
], function(Extent, HTMap, MapConfig) {
    "use strict";
    return new HTMap(MapConfig.mapDiv, {
        baseLayers: MapConfig.layers,
        extent: new Extent(MapConfig.extent),
        zoom: MapConfig.zoom,
        globalQueryUrl: MapConfig.globalQueryUrl,
        HTLineUrl:MapConfig.HTLineUrl,
        //countyUrl: MapConfig.countyUrl,
        slider: true,
        logo: false,
        minZoom:MapConfig.minZoom,
        wrapAround180: true, 
        //extent: esri.geometry.geographicToWebMercator(MapConfig.extent)
    });
});
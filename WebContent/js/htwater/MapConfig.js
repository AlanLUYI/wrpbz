define([], function () {
	return {
		"mapDiv": "map",
		"layers": [
					/*{
		                "resource": "GOOGLE",
						"options": {
		                    "id": "google_terrain",
							"mapType": "terrain",
							"visible": true
						}

					},*/
					{
						"resource": "TIANDITU",
						"options": {
		                    "id": "tianditu_vector",
							"mapType": "vector",
							"visible": true
						}
					},
					{
						"resource": "TIANDITU",
						"options": {
		                    "id": "tianditu_vector_anno",
							"mapType": "vector_anno",
							"visible": true
						}
					},
					{
		                "resource": "TIANDITU",
						"options": {
		                    "id": "tianditu_terrain",
							"mapType": "terrain",
							"visible": false
						}

					},
					{
		                "resource": "TIANDITU",
						"options": {
		                    "id": "tianditu_terrain_anno",
							"mapType": "terrain_anno",
							"visible": false
						}
					},
					{
						 "resource": "TIANDITU",
							"options": {
			                    "id": "tianditu_satellite",
								"mapType": "satellite",
								"visible": false
							}
					},
					{
						 "resource": "TIANDITU",
							"options": {
			                    "id": "tianditu_satellite_anno",
								"mapType": "satellite_anno",
								"visible": false
							}
					},
					/*{
						 "resource": "ARCGIS",
						 	"url": "http://61.153.21.220:6080/arcgis/rest/services/钱塘江海塘4326/MapServer",
							"options": {
			                    "id": "aaaa",
								"visible": true
							}
					}*/
		           
				],
		"globalQueryUrl": "http://qtjfc.qgj.cn:6080/arcgis/rest/services/QGJ2/MapServer",
		"countyUrl": "./data/county.json",
		"HTLineUrl": "http://61.153.21.220:6080/arcgis/rest/services/钱塘江海塘4326/MapServer",
		"countyField": "County",
		"extent":{
            "xmin": 120.339,
            "ymin": 27.582,
			"xmax": 120.907,
			"ymax": 28.187,
			"spatialReference": {
				"wkid": 4326
			}
		},
		"zoom": 9,
		"minZoom": 8
	};
});
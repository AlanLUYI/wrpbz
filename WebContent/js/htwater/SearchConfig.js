/**
 * @author chenchu
 * @date 2015 3 30
 * @description search config
 */
 define([], function() {
 	return {
 		geometryServiceUrl: "http://61.153.21.220:6080/arcgis/rest/services/Utilities/Geometry/GeometryServer",
 		sources: [
 			{
 				featureLayerUrl: "http://61.153.21.220:6080/arcgis/rest/services/LayerTree/FeatureServer/5",
 				searchFields: ["水库名称"],
 				displayField: "水库名称",
 				outFields: ["县","水库名称","水库编码","水库类型","总库容_万"],
 				name: "大中型水库",
 				placeholder: "请输入水库名称",
 				infoTemplate:{
 					title: "水库-${水库名称}",
 					content: "<table class='table table-bordered table-condensed'>"
				        		+	"<tr>"
					        	+		"<td width='40%'>所在区县</td>"
					        	+		"<td>${县}</td>"
					        	+	"</tr>"
					        	+	"<tr>"
					        	+		"<td>总库容</td>"
					        	+		"<td>${总库容_万}(万方)</td>"
					        	+	"</tr>"
					        	+	"<tr>"
					        	+		"<td>水库类型</td>"
					        	+		"<td>${水库类型}</td>"
					        	+	"</tr>"
					        	+	"<tr>"
					        	+		"<td>水库编码</td>"
					        	+		"<td>${水库编码}</td>"
					        	+	"</tr>"
					        	+ "</table>"
 				}
 			},{
 				featureLayerUrl: "http://61.153.21.220:6080/arcgis/rest/services/LayerTree/FeatureServer/10",
 				searchFields: ["水闸名称"],
 				displayField: "水闸名称",
 				outFields: ["县","水闸名称","水闸编码","水闸类型","闸孔数量_"],
 				name: "水闸",
 				placeholder: "请输入水闸名称",
 				infoTemplate:{
 					title: "水闸-${水闸名称}",
 					content: "<table class='table table-bordered table-condensed'>"
				        		+	"<tr>"
					        	+		"<td width='40%'>所在区县</td>"
					        	+		"<td>${县}</td>"
					        	+	"</tr>"
					        	+	"<tr>"
					        	+		"<td>水闸类型</td>"
					        	+		"<td>${水闸类型}</td>"
					        	+	"</tr>"
					        	+	"<tr>"
					        	+		"<td>水闸编码</td>"
					        	+		"<td>${水闸编码}</td>"
					        	+	"</tr>"
					        	+	"<tr>"
					        	+		"<td>闸孔数量</td>"
					        	+		"<td>${闸孔数量_}</td>"
					        	+	"</tr>"
					        	+ "</table>"
 				}
 			},{
 				featureLayerUrl: "http://61.153.21.220:6080/arcgis/rest/services/LayerTree/FeatureServer/4",
 				searchFields: ["测站名称"],
 				displayField: "测站名称",
 				outFields: ["测站名称","测站编码","测站类型","测站地点","河流名称","观测项目"],
 				name: "水文站",
 				placeholder: "请输入水文站名称",
 				infoTemplate:{
 					title: "水文站-${测站名称}",
 					content:  "<table class='table table-bordered table-condensed'>"
 					        	+	"<tr>"
 					        	+		"<td width='40%;'>测站地点</td>"
 					        	+		"<td>${测站地点}</td>"
 					        	+	"</tr>"
 					        	+	"<tr>"
 					        	+		"<td>测站类型</td>"
 					        	+		"<td>${测站类型}</td>"
 					        	+	"</tr>"
 					        	+	"<tr>"
					        	+		"<td>测站编码</td>"
					        	+		"<td>${测站编码}</td>"
					        	+	"</tr>"
					        	+	"<tr>"
					        	+		"<td>观测项目</td>"
					        	+		"<td>${观测项目}</td>"
					        	+	"</tr>"
 					        	+ "</table>"
 				}
 			}
 		],
 		domNode: "search"
 	};
 });
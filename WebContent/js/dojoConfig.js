/**
 * @date 2014 10 16
 * @info dojo config
 * chenchu modified
 */
var dojoConfig = {
		async: true,
		parseOnLoad: false,
		baseUrl: "./",
		map: {
			"*": {
				text: "dojo/text",
				css: "xstyle/css"
			}
		},
		packages: [
			{name: "js", location: "js"},
			{name: "css", location: "css"},
			{name: "libs", location: "libs"},
			{name: "modules", location: "modules"},
		    {name: "htwater", location: "js/htwater"},
			{name: "dojo", location: "libs/arcgis/dojo"},
			{name: "dijit", location: "libs/arcgis/dijit"},
			{name: "dojox", location: "libs/arcgis/dojox"},
			{name: "xstyle", location: "libs/arcgis/xstyle"},
			{name: "esri", location: "libs/arcgis/esri"},
			{name: "jquery", location: "libs/jquery", main: "jquery-1.11.1.min"},
			{name: "bootstrap", location: "libs/bootstrap/js", main: "bootstrap.min"},
			{name: "jqueryui", location: "libs/jquery-ui", main: "jquery-ui.min"},
			{name: "ztree", location: "libs/ztree", main: "jquery.ztree.all.min"},
			{name: "jqgrid", location: "libs/jqgrid", main: "jquery.jqGrid.min"},
			{name: "highcharts", location: "libs/highcharts", main : "highcharts"},
			{name: "highmaps", location: "libs/highcharts/modules", main : "map"},
			{name: "date", location: "libs/My97DatePicker", main : "WdatePicker"},
			{name: "underscore", location: "libs/underscore", main : "underscore-min"},
			{name: "datetimepicker", location: "libs/bootstrap-datetimepicker", main: "bootstrap-datetimepicker.min"},
			{name: "bootstrap-dialog", location: "libs/bootstrap-dialog", main: "bootstrap-dialog.min"},
			{name: "bootstrap-table", location: "libs/bootstrap-table", main: "bootstrap-table.min"},
			{name: "bootstrap-switch", location: "libs/bootstrap-switch", main: "bootstrap-switch.min"},
			{name: "bootstrap-multiselect", location: "libs/bootstrap-multiselect", main: "bootstrap-multiselect.min"},
			{name: "interact", location: "libs/interact", main: "interact-1.2.4.min"},
			{name: "jrange", location: "libs/jRange", main: "jquery.range"}
		]
	};
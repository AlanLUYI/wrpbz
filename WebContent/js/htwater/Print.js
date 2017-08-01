/**
 * @author chenchu
 * @date 2014 12 23
 * @decription Print Widget
 */

define([
    "dojo/_base/declare",
    "dijit/_WidgetBase",
    "dijit/_TemplatedMixin",
    "dijit/_WidgetsInTemplateMixin",
    "esri/request",
    "esri/tasks/PrintTask",
    "esri/tasks/PrintParameters",
    "esri/tasks/PrintTemplate",
    "dojo/_base/lang",
    "dojo/_base/array",
    "dojo/on",
    "dojo/query",
    "dojo/NodeList-traverse",
    "dojo/dom-attr",
    "dojo/dom-style",
    "dojo/dom-construct",
    "dojo/dom-class",
    "dojo/aspect",
    "text!./templates/Print.html",
    "text!./templates/PrintResult.html",
    "css!./css/Print.css"
], function(declare, _WidgetBase, _TemplatedMixin, _WidgetsInTemplateMixin, esriRequest, PrintTask, PrintParameters, PrintTemplate, lang, array, on, domQuery, NodeListTraverse, domAttr, domStyle, domConstruct, domClass, aspect, printTemplate, printResultTemplate) {

    var PrintDijit = declare([_WidgetBase, _TemplatedMixin, _WidgetsInTemplateMixin], {
        widgetsInTemplate: true,
        templateString: printTemplate,
        map: null,
        count: 1,
        results: [],
        authorText: null,
        copyrightText: null,
        defaultTitle: null,
        defaultFormat: null,
        defaultLayout: null,
        baseClass: "print-dijit",
        pdfIcon: require.toUrl("htwater/images/pdf.png"),
        imageIcon: require.toUrl("htwater/images/image.png"),
        printTaskURL: null,
        printTask: null,
        postCreate: function() {
            this.inherited(arguments);
            this.printTask = new PrintTask(this.printTaskURL);
            this.printParams = new PrintParameters();
            this.printParams.map = this.map;
            this.printParams.outSpatialReference = this.map.spatialReference;

            this.printResultsWrapper = domQuery("#print-results-wrapper")[0];

            this.titleInput = domQuery("#print-map-title", this.domNode)[0];
            this.layoutSelect = domQuery("#print-map-layout", this.domNode)[0];
            this.formatSelect = domQuery("#print-map-format", this.domNode)[0];
            this.scalebarSelect = domQuery("#print-scalebar-unit", this.domNode)[0];
            this.dpiInput = domQuery("#print-settings-dpi", this.domNode)[0];
            this.widthInput = domQuery("#print-settings-width", this.domNode)[0];
            this.heightInput = domQuery("#print-settings-height", this.domNode)[0];
            this.clearActionBar = domQuery("#clear-action-bar", this.domNode)[0];

            domQuery("#print-map-btn", this.domNode).on("click", lang.hitch(this, function(event) {
                event.preventDefault();
                this.print();
            }));

            domQuery("#clear-results-btn", this.domNode).on("click", lang.hitch(this, function(event) {
                event.stopPropagation();
                this.clearResults();
            }));

            domQuery(".dropdown-toggle", this.domNode).on("click", function(event) {
                event.stopPropagation();
                domClass.toggle(domQuery.NodeList([event.target]).closest(".btn-group.dropdown")[0], "open");
            });

            domQuery.NodeList([this.domNode]).closest(".dropdown-menu").on("click", lang.hitch(this, function(event) {
                var subDropdown = domQuery(".dropdown", this.domNode)[0];
                if (domClass.contains(subDropdown, "open")) {
                    domClass.remove(subDropdown, "open");
                }
                event.stopPropagation();
            }));

            domQuery(".dropdown-menu", this.domNode).on("click", function(event) {
                event.stopPropagation();
            });

            esriRequest({
                url: this.printTaskURL,
                content: {
                    f: "json"
                },
                handleAs: "json",
                callbackParamName: "callback",
                load: lang.hitch(this, "_handlePrintInfo"),
                error: lang.hitch(this, "_handleError")
            });
            aspect.after(this.printTask, "_createOperationalLayers", this.operationalLayersInspector, false);
        },
        operationalLayersInspector: function(opLayers) {
            array.forEach(opLayers, function(layer) {
                if (layer.id == "filterGraphicsLayer" || layer.id == "measureGraphicsLayer" || layer.id == "map_graphics") {
                    array.forEach(layer.featureCollection.layers, function(fcLayer) {
                        array.forEach(fcLayer.featureSet.features, function(feature) {
                            delete feature.attributes;
                            if (feature.symbol.font) {
                                feature.symbol.font.family = "宋体";
                                feature.symbol.font.variant = esri.symbol.Font.VARIANT_NORMAL;
                                feature.symbol.font.size = "12";
                            }
                        });
                    });
                }
            });
            return opLayers;
        },
        _handleError: function(err) {
            console.log(1, err);
        },
        _handlePrintInfo: function(data) {
            if (this.defaultTitle) {
                domAttr.set(this.titleInput, "value", this.defaultTitle);
            }

            var Layout_Template = array.filter(data.parameters, function(param, idx) {
                return param.name === "Layout_Template";
            });
            if (Layout_Template.length === 0) {
                console.log("print service parameters name for templates must be \"Layout_Template\"");
                return;
            }
            var layoutItems = array.map(Layout_Template[0].choiceList, function(item, i) {
                return {
                    name: item,
                    id: item
                };
            });
            layoutItems.sort(function(a, b) {
                return (a.name > b.name) ? 1 : ((b.name > a.name) ? -1 : 0);
            });
            var layoutOptions = array.map(layoutItems, function(item) {
                if (Layout_Template[0].defaultValue === item.name) {
                    return "<option selected='selected' value='" + item.name + "'>" + item.name + "</option>";
                } else {
                    return "<option value='" + item.name + "'>" + item.name + "</option>";
                }
            }, this);
            domConstruct.place(layoutOptions.join(""), this.layoutSelect, "only");
            if (this.defaultLayout) {
                domAttr.set(domQuery("option[value='" + this.defaultLayout + "']", this.layoutSelect)[0], "selected", "selected");
            }

            var Format = array.filter(data.parameters, function(param, idx) {
                return param.name === "Format";
            });
            if (Format.length === 0) {
                console.log("print service parameters name for format must be \"Format\"");
                return;
            }
            var formatItems = array.map(Format[0].choiceList, function(item, i) {
                return {
                    name: item,
                    id: item
                };
            });
            formatItems.sort(function(a, b) {
                return (a.name > b.name) ? 1 : ((b.name > a.name) ? -1 : 0);
            });
            var formatOptions = array.map(formatItems, function(item) {
                if (Format[0].defaultValue == item.name) {
                    return "<option selected='selected' value='" + item.name + "'>" + item.name + "</option>";
                } else {
                    return "<option value='" + item.name + "'>" + item.name + "</option>";
                }
            }, this);
            domConstruct.place(formatOptions.join(""), this.formatSelect, "only");
            if (this.defaultFormat) {
                domAttr.set(domQuery("option[value='" + this.defaultFormat + "']", this.formatSelect)[0], "selected", "selected");
            }
        },
        print: function() {
            var format = domAttr.get(this.formatSelect, "value");
            var layout = domAttr.get(this.layoutSelect, "value");
            var title = domAttr.get(this.titleInput, "value");
            if (title && format && layout) {
                var template = new PrintTemplate();
                template.format = format;
                template.layout = layout;
                template.label = title;
                template.preserveScale = eval(domAttr.get(domQuery("[name='preserveScale']:checked")[0], "value"));
                template.exportOptions = {
                    dpi: parseInt(domAttr.get(this.dpiInput, "value"), 10),
                    width: parseInt(domAttr.get(this.widthInput, "value"), 10),
                    height: parseInt(domAttr.get(this.heightInput, "value"), 10)
                };
                template.layoutOptions = {
                    authorText: this.authorText || "",
                    copyrightText: this.copyrightText || "",
                    legendLayers: [],
                    titleText: title,
                    scalebarUnit: domAttr.get(this.scalebarSelect, "value")
                };
                this.printParams.template = template;
                var fileHandle = this.printTask.execute(this.printParams);
                var printResult = new printResultDijit({
                    count: this.count.toString(),
                    icon: (format === "PDF") ? this.pdfIcon : this.imageIcon,
                    docName: title + "." + format.toLowerCase(),
                    title: title + "." + format.toLowerCase(),
                    fileHandle: fileHandle
                }).placeAt(this.printResultsWrapper, "last");
                printResult.startup();
                domStyle.set(this.printResultsWrapper, "display", "block");
                domStyle.set(this.clearActionBar, "display", "block");
                this.count++;
            }
        },
        clearResults: function() {
            domConstruct.empty(this.printResultsWrapper);
            domStyle.set(this.printResultsWrapper, "display", "none");
            domStyle.set(this.clearActionBar, "display", "none");
            this.count = 1;
        }
    });

    var printResultDijit = declare([_WidgetBase, _TemplatedMixin, _WidgetsInTemplateMixin], {
        widgetsInTemplate: true,
        templateString: printResultTemplate,
        baseClass: "print-result",
        url: null,
        postCreate: function() {
            this.inherited(arguments);

            on(this.domNode, "click", lang.hitch(this, "_openPrint"));
            this.nameNode = domQuery(".print-result-name", this.domNode)[0];

            this.fileHandle.then(lang.hitch(this, '_onPrintComplete'), lang.hitch(this, '_onPrintError'));

        },
        _onPrintComplete: function(data) {
            if (data.url) {
                this.url = data.url;
                this.nameNode.innerHTML = '<span class="bold">' + this.docName + '</span>';
                domClass.add(this.domNode, "print-result-hover");
            } else {
                this._onPrintError('导出失败, 请重试');
            }
        },
        _onPrintError: function(err) {
            console.log(err);
            this.nameNode.innerHTML = '<span class="bold">导出失败, 请重试</span>';
            domClass.add(this.domNode, "print-result-error");
        },
        _openPrint: function() {
            if (this.url !== null) {
                window.open(this.url);
            }
        }
    });
    return PrintDijit;
});
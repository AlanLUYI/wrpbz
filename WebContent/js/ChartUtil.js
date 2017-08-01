/**
 * @author chenchu
 * @date 2014 10 24
 * @info chart utils
 */
define(["dojo/_base/lang", "./NBMap", "highcharts", "highmaps"], function(lang, mapData) {
    Highcharts.setOptions({
        global: {
            useUTC: false  
        },
        chart: {
            type: 'line',
            plotShadow: false,
            borderWidth: 0,
            backgroundColor: '',
            plotBackgroundColor: null,
            plotBorderWidth: null
        },
        colors: ['#058DC7', '#50B432', '#ED561B', '#DDDF00', '#24CBE5', '#64E572', '#FF9655', '#FFF263', '#6AF9C4'],
        credits: {
            enabled: false
        }
    });

    return {
        /**
         * 初始化时序数据图表,参数与highcharts参数一致
         *
         */
        initTimeChart: function(options) {
            if (!options.chart.renderTo) return;

            var chartOptions = {
                chart: {
                    renderTo: null,
                    type: "column",
                    plotShadow: false,
                    borderWidth: 0,
                    backgroundColor: "",
                    plotBackgroundColor: null,
                    plotBorderWidth: null
                },
                legend: {
                    enabled: true
                },
                title: {
                    text: ""
                },
                tooltip: {
                    formatter: function() {
                        return Highcharts.dateFormat('%m-%d', this.x) + ":" + this.y;
                    }
                },
                xAxis: {
                    type: "datetime",
                    dateTimeLabelFormats: {
                        year: '%Y-%m-%d',
                        month: '%Y-%m-%d',
                        day: '%m-%d',
                        week: '%Y-%m-%d',
                        hour: '%H:%M',
                        minute: '%H:%M',
                        second: '%H:%M'
                    },
                    startOnTick: false,
                    endOnTick: false,
                    tickInterval: 24 * 3600 * 1000
                },
                yAxis: {
                    lineWidth: 1,
                    gridLineWidth: 1,
                    title: {
                        text: "",
                        style: {
                            fontWeight: "bold"
                        }
                    },
                    endOnTick: false
                },
                plotOptions: {
                    column: {
                        dataLabels: {
                            enabled: false,
                            crop: false,
                            overflow: "none",
                            formatter: function() {
                                return this.y.toFixed(1);
                            },
                            x: -1,
                            y: 2
                        },
                        pointRange: 24 * 3600 * 1000,
                        enableMouseTracking: true
                    }
                },
                series: [{}]
            };
            chartOptions = lang.mixin(chartOptions, options);
            return new Highcharts.Chart(chartOptions);
        },

        /**
         * 初始化普通数据图表，参数与highcharts参数一致
         *
         */
        initCategoryChart: function(options) {
            if (!options.chart.renderTo) return;

            var chartOptions = {
                chart: {
                    renderTo: null,
                    type: "column",
                    plotShadow: false,
                    borderWidth: 0,
                    backgroundColor: "",
                    plotBackgroundColor: null,
                    plotBorderWidth: null
                },
                legend: {
                    enabled: true
                },
                title: {
                    text: ""
                },
                tooltip: {
                    formatter: function() {
                        return this.x + ":" + this.y;
                    }
                },
                xAxis: {
                    labels: {
                        rotation: 0,
                    }
                },
                yAxis: {
                    lineWidth: 1,
                    gridLineWidth: 1,
                    title: {
                        text: ""
                    },
                    endOnTick: false
                },
                plotOptions: {
                    column: {
                        dataLabels: {
                            enabled: false,
                            crop: false,
                            overflow: "none",
                            formatter: function() {
                                return this.y.toFixed(1);
                            },
                            x: -1,
                            y: 2
                        },
                        enableMouseTracking: true
                    },
                    pie: {
                        allowPointSelect: true,
                        cursor: "pointer",
                        dataLabels: {
                            enabled: false
                        },
                        showInLegend: true
                    }
                },
                series: [{}]
            };
            chartOptions = lang.mixin(chartOptions, options);
            return new Highcharts.Chart(chartOptions);
        },


        /**
         *返回宁波Highmap图表
         *{
         *  chartContainer: dom
         *  tooltipSuffix:string
         *  legendTitle:string
         *   min: number
         *   max: number
         *   serieName: string
         *}
         *
         */
        initNBHighmap: function(options) {
            if (!(options || options.chartContainer)) return;
            options = lang.mixin({
                tooltipSuffix: "",
                legendTitle: "",
                serieName: "",
                min: 0,
                max: 100
            }, options);

            var highmap = new Highcharts.Map({
                chart: {
                    renderTo: options.chartContainer,
                    borderWidth: 0,
                    borderRadius: 5,
                    backgroundColor: {
                        linearGradient: {
                            x1: 0,
                            y1: 0,
                            x2: 0,
                            y2: 1
                        },
                        stops: [
                            [0, '#67a8ce'],
                            [1, '#3c8dbc']
                        ]
                    },
                    plotBorderWidth: 0,
                    plotShadow: false
                },
                title: {
                    text: ""
                },
                mapNavigation: {
                    enabled: false
                },
                colorAxis: {
                    min: options.min,
                    max: options.max,
                    type: "linear",
                    minColor: "#e6e696",
                    maxColor: "#003700"
                },
                series: [{
                    data: [],
                    mapData: mapData,
                    joinBy: ["name", "city"],
                    name: options.serieName,
                    states: {
                        hover: {
                            color: '#bada55',
                            borderColor: 'gray'
                        }
                    },
                    dataLabels: {
                        enabled: false,
                        format: "{point.name}"
                    },
                    tooltip: {
                        valueSuffix: options.tooltipSuffix
                    }
                }],
                legend: {
                    enabled: true,
                    title: {
                        text: options.legendTitle
                    },
                    layout: "vertical",
                    align: "right",
                    verticalAlign: "middle",
                    backgroundColor: "rgba(255,255,255,0.85)"
                },
                credits: {
                    enabled: false
                }
            });
            return highmap;
        }

    };
});
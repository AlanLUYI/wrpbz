/**
 * @author chenchu
 * @date 2015 1 15
 * @info 模块管理器
 */
define([
    "require",
    "dojo/_base/declare",
    "dojo/_base/lang",
    "dojo/topic",
    "./MapLeaf",
    "./BootMenuType",
    "./BootMenuUtil",
    "./Events",
    "./FullScreen",
    "text!./templates/sidePanel.html",
    "text!./templates/sidebar.html",
    "jquery",
    "underscore",
    "interact"
], function (require, declare, lang, topic, htMap, BootMenuType, BootMenuUtil, Events, FullScreen, sidePanelTemp, sidebarTemp, $, _, interact) {
        "use strict";
        return declare(null, {
            declaredClass: "js.BootModuleManager",
            frameContainer: null,
            sidePanelContainer: null,
            miniPanelWidth: 104,

            constructor: function (options) {
                declare.safeMixin(this, lang.mixin({
                    moduleContainer: null,
                    legendContainer: null,
                    compositeModuleContainer: null,
                    sidePanelToggle: null,
                    activeModule: null,
                    currentModules: [],
                    currentTopMenu: null,
                    handler: null,
                    isBuild: false,
                    isPanelShow: true,
                    isMiniPanel: true
                }, options || {}));
                this.handler = topic.subscribe(Events.MENUCLICK, lang.hitch(this, function (menu) { //绑定函数上下文为this，即创建的BootMenuManager   
                    if (menu.type !== BootMenuType.TYPE_FRAME && menu.enable) {
                        this.isBuild || this.render(); //如果this.isBuild为false，则执行render方法
                    }
                    this.loadModule(menu);
                }));

                this.isHistroySupport = !!(window.history && history.pushState);
                if (this.isHistroySupport) {
                    window.addEventListener("popstate", lang.hitch(this, function (event) {
                        if (event.state) {
                            var menu = event.state;
                            if (menu.type === BootMenuType.TYPE_INDIVIDUAL) {
                                this.isBuild || this.render();
                                this._loadIndividualModule(menu);
                            } else if (menu.type === BootMenuType.TYPE_FRAME) {
                                this._loadFrameModule(menu);
                            }else if(menu.type === BootMenuType.TYPE_COMPOSITE) {
                                this.isBuild || this.render();
                                this._loadCompositeModule(menu);
                            }
                        } else {
                            this._destroyCurrentModules();
                            if (this.isPanelShow) {
                                $(this.sidePanelToggle).click();
                            }
                        }
                    }));
                }
            },
            destructor: function () {
                this._destroyCurrentModules(true);
                this.handler && this.handler.remove();
                $(this.sidePanelContainer).length && $(this.sidePanelContainer).off(); //清除sidePanelContainer代理的事件
            },
            render: function () {
                var self = this;
                this.isPanelShow = true;
                this.isMiniPanel = true;
                $(this.sidePanelContainer).append(sidePanelTemp).addClass("show-mini-panel");
                this.moduleContainer = $(this.sidePanelContainer).children("#side-panel-wrapper").find(".side-panel"); //缓存moduleContainer，方便后续调用
                this.sidePanelToggle = $(this.sidePanelContainer).children("#side-panel-wrapper").find(".side-panel-toggle"); //缓存。。。。。

                var lastModuleWidth = 0;
                $(this.sidePanelContainer).on("click", "#side-panel-wrapper .side-panel-toggle", function (event) { //sidePanelContainer代理side-panel-toggle的点击事件
                    self.isPanelShow = !self.isPanelShow;
                    if (self.isPanelShow) {
                        if (self.isMiniPanel) {
                            $(self.sidePanelContainer).removeClass("show-panel").addClass("show-mini-panel"); //微型面板，只展示复选框列表
                        } else {
                            $(self.sidePanelContainer).removeClass("show-mini-panel").addClass("show-panel"); //展开左侧面板
                            self.setModuleContainerWidth(lastModuleWidth);
                        }
                        $(this).attr("title", "收起左栏").children(".fa").removeClass("fa-caret-right").addClass("fa-caret-left");
                    } else {
                        lastModuleWidth = $(self.moduleContainer).outerWidth();
                        $(self.sidePanelContainer).removeClass("show-panel show-mini-panel");
                        $(self.moduleContainer).hide();
                        self.resetModuleContainerWidth();
                        $(this).attr("title", "显示左栏").children(".fa").removeClass("fa-caret-left").addClass("fa-caret-right");
                    }
                    self.isPanelShow && $(self.moduleContainer).show(); 
                    self.activeModule && self.activeModule.reflow();
                    htMap.reposition();
                });

                $(this.legendContainer).on("click", ".legend-title", function (event) {
                    var isExpanded = $(this).parent().hasClass("open"),
                        contentHeight = $(this).next().outerHeight();
                    if (isExpanded) {
                        $(this).children(".fa").removeClass("fa-chevron-circle-down").addClass("fa-chevron-circle-up")
                            .end().parent().css({ "bottom": -contentHeight }).removeClass("open");
                    } else {
                        $(this).children(".fa").removeClass("fa-chevron-circle-up").addClass("fa-chevron-circle-down")
                            .end().parent().css({ "bottom": 5 }).addClass("open");
                    }
                });

                if (FullScreen.supportsFullScreen) {
                    document.addEventListener(FullScreen.fullScreenEventName, function () {
                        if (FullScreen.isFullScreen()) {
                            $(document.body).addClass("full-screen");
                            $(self.activeModule.container).find(".full-screen-btn").children("span").text("退出全屏")
                                .prev(".fa").removeClass("fa-arrows-alt").addClass("fa-reply");
                        } else {
                            self.setModuleContainerWidth(self.activeModule.width);
                            $(document.body).removeClass("full-screen");
                            $(self.activeModule.container).find(".full-screen-btn").children("span").text("全屏")
                                .prev(".fa").removeClass("fa-reply").addClass("fa-arrows-alt");
                        }
                        self.activeModule && self.activeModule.reflow();
                    }, false);
                }

                interact(this.moduleContainer[0]).resizable({
                    edges: {
                        left: false,
                        right: true,
                        bottom: false,
                        top: false
                    },
                    invert: "none"
                }).on("resizestart", function (event) {
                    $(event.target).find(".fixed-table-body").css("overflow-y", "hidden");
                }).on("resizemove", _.throttle(function (event) {
                    var width = event.rect.width;
                    var miniWidth;
                    if(self.isMiniPanel){
                        miniWidth = self.miniPanelWidth;
                    }else{
                        miniWidth = (self.activeModule ? 
                                        (self.activeModule.type == BootMenuType.TYPE_COMPOSITE ? 
                                            (self.activeModule.width + self.miniPanelWidth) : self.activeModule.width) : 0);
                    }
                    if(width < miniWidth){
                        return;
                    }
                    self.setModuleContainerWidth(width);
                    self.activeModule && self.activeModule.reflow();
                }, 200)).on("resizeend", function (event) {
                    $(event.target).find(".fixed-table-body").css("overflow-y", "");
                    self.activeModule && self.activeModule.reflow();
                });

                this.isBuild = true;
            },
            loadModule: function (menu) {
                if (!(menu && menu.enable)) {
                    return;
                }
                
                function getModuleHash(menu){
                    if(menu.type == BootMenuType.TYPE_INDIVIDUAL || menu.type == BootMenuType.TYPE_FRAME){
                        return ["action=" + menu.action, "name=" + menu.name, "type=" + menu.type].join("&");
                    }else if(menu.type == BootMenuType.TYPE_COMPOSITE){
                        var simplifiedModules = _.map(menu.children, function(item){
                            return {name: item.name, action: item.action, enable: item.enable ? 1 : 0, id: item.id} 
                        });
                        return ["action=" + _.pluck(simplifiedModules, "action").join(","),
                                "enable=" + _.pluck(simplifiedModules, "enable").join(","),
                                "id=" + _.pluck(simplifiedModules, "id").join(","),
                                "name=" + _.pluck(simplifiedModules, "name").join(","),
                                "comAction=" + menu.action,
                                "type=" + menu.type
                        ].join("&"); 
                    }
                }
                
                switch (menu.type) {
                    case BootMenuType.TYPE_INDIVIDUAL:
                        this._loadIndividualModule(menu);
                        this.isPanelShow || $(this.sidePanelToggle).click(); //如果isPanelShow为false，则显示左侧面板
                        this.isHistroySupport && history.pushState(menu, "", "#" + getModuleHash(menu));
                        break;
                    case BootMenuType.TYPE_COMPOSITE:
                        this._loadCompositeModule(menu);
                        this.isPanelShow || $(this.sidePanelToggle).click();
                        this.isHistroySupport && history.pushState(menu, "", "#" + getModuleHash(menu));
                        break;
                    case BootMenuType.TYPE_FRAME:
                        this._loadFrameModule(menu);
                        this.isHistroySupport && history.pushState(menu, "", "#" + getModuleHash(menu));
                        break;
                    default:
                        break;
                }
            },
            _loadIndividualModule: function (menu) {
                if (!menu) {
                    return;
                }
                var self = this;
                if (this.currentModules.length && this.currentModules[this.currentModules.length - 1].type == BootMenuType.TYPE_FRAME) { //如果上一个模块为FRAME类型，则隐藏该模块
                    $(this.frameContainer).hide();
                }
                for (var i = 0; i < this.currentModules.length; i++) {
                    if (menu.action && menu.action == this.currentModules[i].action) {
                        this.activeModule = this.currentModules[i];
                        return;
                    }
                }
                this._destroyCurrentModules(false); //销毁已经加载到地图上的模块，不包括iframe嵌入的网页
                this.currentTopMenu = null;
                if (menu.action && menu.enable) {
                    require(["modules/" + menu.action + "/" + menu.action], function (Module) {
                        if (!self.isPanelShow) {
                            $(self.sidePanelToggle).click();
                        } else {
                            if (self.isMiniPanel) {
                                $(self.sidePanelContainer).removeClass("show-mini-panel").addClass("show-panel");
                                self.isMiniPanel = false;
                                htMap.reposition();
                            }
                        }
                        var module = new Module({
                            container: self.moduleContainer.empty()[0],
                            id: menu.id,
                            name: menu.name,
                            action: menu.action,
                            type: BootMenuType.TYPE_INDIVIDUAL,
                            userInfo: self.userInfo,
                            promises: []
                        }); //创建模块，赋予参数
                        var legendContent = module.getLegendContent();
                        if (legendContent) {
                            $(self.legendContainer).show().children(".legend-content").html(legendContent)
                                .end().children(".legend-title").children("span").text(module.getLegendTitle());
                            if (!$(self.legendContainer).hasClass("open")) {
                                $(self.legendContainer).children(".legend-title").click();
                            }
                        }
                        self.setModuleContainerWidth(module.width);
                        self.currentModules.push(module); //加入到currentModules数组中
                        self.activeModule = module;
                    });
                }
            },
            _loadCompositeModule: function (menu) {
                if (!menu) {
                    return;
                }
                var self = this,
                    template;
                if (this.currentModules.length && this.currentModules[this.currentModules.length - 1].type == BootMenuType.TYPE_FRAME) { //如果上一个模块为FRAME类型，则隐藏该模块
                    $(this.frameContainer).hide();
                }
                if (!this.currentTopMenu || (menu.action != this.currentTopMenu.action)) { 
                    this._destroyCurrentModules(false);
                    this.currentTopMenu = menu;
                    template = _.template(sidebarTemp);
                    $(this.moduleContainer).html(template({
                        hierarchy: menu
                    }));
                    this.compositeModuleContainer = $(this.moduleContainer).children("#module-wrapper").children("#module-inner-wrapper"); //缓存变量。。。。。
                    $(this.sidePanelContainer).off("change", ".group-item input:checkbox").on("change", ".group-item input:checkbox", function (event) { //先清除代理的change事件，再重新绑定change事件
                        var checkboxMenu = BootMenuUtil.getMenuById($(this).data("id"), [menu]);
                        if (!checkboxMenu) {
                            return;
                        }
                        if (this.checked) { //如果勾选了复选框
                            if (checkboxMenu.action) {
                                require(["modules/" + checkboxMenu.action + "/" + checkboxMenu.action], function (Module) { //异步加载模块
                                    if (event.target.checked) { //因为是异步，所以要重新判断一下复选框是否已经勾选了
                                        if (!self.isPanelShow) {
                                            $(self.sidePanelToggle).click();
                                        } else {
                                            if (self.isMiniPanel) {
                                                $(self.sidePanelContainer).removeClass("show-mini-panel").addClass("show-panel");
                                                self.isMiniPanel = false;
                                                htMap.reposition();
                                            }
                                        }
                                        $(event.target).closest(".group-item").addClass("active").siblings(".group-item").removeClass("active");
                                        $(self.compositeModuleContainer).find(".module").removeClass("active");
                                        var container = $("<div class='module active module-" + checkboxMenu.action + "'></div>").appendTo(self.compositeModuleContainer);
                                        var module = new Module({
                                            container: container[0],
                                            id: checkboxMenu.id,
                                            name: checkboxMenu.name,
                                            type: BootMenuType.TYPE_COMPOSITE,
                                            action: checkboxMenu.action,
                                            userInfo: self.userInfo,
                                            promises: []
                                        });
                                        var legendContent = module.getLegendContent();
                                        if (legendContent) {
                                            $(legendContent).appendTo($(self.legendContainer).show().children(".legend-title").children("span").text("图例")
                                                .end().end().children(".legend-content")).wrap("<div class='legend-item legend-item-" + checkboxMenu.action + "'></div>")
                                                .parent().prepend("<strong>"+ module.getLegendTitle() +"</strong>");
                                            if (!$(self.legendContainer).hasClass("open")) {
                                                $(self.legendContainer).children(".legend-title").click();
                                            }
                                        }
                                        self.setModuleContainerWidth(module.width + self.miniPanelWidth);
                                        self.currentModules.push(module);
                                        self.activeModule = module;
                                    }
                                });
                            }
                        } else { //取消勾选
                            var currentModule = null;
                            for (var i = self.currentModules.length - 1; i >= 0; i--) { //判断该模块是否加载
                                currentModule = self.currentModules[i];
                                if (checkboxMenu.id == currentModule.id) {
                                    currentModule.destructor(); //卸载该模块
                                    self.currentModules.splice(i, 1); //从currentModules数组中删除该模块记录
                                    $(self.compositeModuleContainer).find(".module-" + checkboxMenu.action).remove(); //删除该模块面板容器
                                    $(self.legendContainer).children(".legend-content").children(".legend-item-" + currentModule.action).remove(); //删除该模块图例容器
                                    break;
                                }
                            }
                            var lastComModule, remainComModules = $.grep(self.currentModules, function (item) {
                                return item.type == BootMenuType.TYPE_COMPOSITE;
                            }); //判断是否还有剩余的COMPOSITE模块
                            if (remainComModules.length) {
                                lastComModule = remainComModules[remainComModules.length - 1];
                                self.activeModule = lastComModule;
                                self.setModuleContainerWidth(lastComModule.width + self.miniPanelWidth);
                                $(this).closest(".group-item").parent().children().removeClass("active").siblings(".group-item-" + lastComModule.action).addClass("active");
                                $(self.compositeModuleContainer).find(".module").removeClass("active").siblings(".module-" + lastComModule.action).addClass("active");
                            } else {
                                self.activeModule = null;
                                $(this).closest(".group-item").removeClass("active");
                                $(self.sidePanelContainer).removeClass("show-panel").addClass("show-mini-panel");
                                self.resetModuleContainerWidth();
                                self.isMiniPanel = true;
                                htMap.reposition();
                            }
                        }
                    });

                    $(this.sidePanelContainer).off("click", ".group-item span").on("click", ".group-item span", function (event) { //sidePanelContainer代理复选框标签文字的点击事件，用来切换上下级关系
                        var checkbox = $(this).prev("input:checkbox")[0],
                            checkboxMenu, module;
                        if (checkbox.checked) { //如果模块已经加载，则移动到最上方   
                            checkboxMenu = BootMenuUtil.getMenuById($(checkbox).data("id"), [menu]);
                            if (checkboxMenu.action && checkboxMenu.id !== self.activeModule.id) {
                                module = $.grep(self.currentModules, function (item) {
                                    return item.id == checkboxMenu.id;
                                })[0];
                                self.setModuleContainerWidth(module.width + self.miniPanelWidth);
                                module.reflow && module.reflow();
                                self.activeModule = module;
                                $(self.moduleContainer).find(".group-item-" + checkboxMenu.action).addClass("active").siblings(".group-item").removeClass("active");
                                $(self.moduleContainer).find(".module-" + checkboxMenu.action).addClass("active").siblings(".module").removeClass("active");
                            }
                        } else { //如果模块没有加载，则加载
                            $(checkbox).click();
                        }
                    });
                }

                function traverseMenu(hierarchyMenu, context, callback) { //深度递归遍历层级菜单
                    var subMenu;
                    if (hierarchyMenu.children && hierarchyMenu.children.length) {
                        for (var i = 0; i < hierarchyMenu.children.length; i++) {
                            subMenu = hierarchyMenu.children[i];
                            if (subMenu.enable && subMenu.action) {
                                callback.call(context, subMenu);
                            } else {
                                traverseMenu(subMenu, context, callback);
                            }
                        }
                    }
                }

                traverseMenu(menu, null, function (subMenu) {
                    if (subMenu && subMenu.action) {
                        $(self.sidePanelContainer).find(".group-item-" + subMenu.action).children("span").click(); //最下级菜单执行的回调函数
                    }
                });
            },
            _loadFrameModule: function (menu) {
                if (!menu) {
                    return;
                }
                var module, isExist = false;
                for (var i = this.currentModules.length - 1; i >= 0; i--) {
                    module = this.currentModules[i];
                    if (module.type == BootMenuType.TYPE_FRAME) {
                        this.currentModules.splice(i, 1);
                        if(menu.name=="首页") {
                        	isExist = false;
                        } else if (menu.action && menu.action == module.action) {
                            isExist = true;
                            break;
                        }
                    }
                }
                this._destroyCurrentModules(false);
                if (isExist) {
                    $(this.frameContainer).show();
                    //个别模块特别处理，其他系统不适用请删除
                    /*if(menu.id=="2.3"){
                        var iframe=$(this.frameContainer[0]).find("iframe")[0];
                        $(iframe).css('margin-top','-160px').css('height','119%');
                        $(iframe).css('margin-left','-208px').css('width','117%');
                        
                    }*/
                    this.currentModules.push(menu);
                } else {
                    if (menu.action && menu.enable) {
                    	//个别模块特别处理，其他系统不适用请删除
//                        if(menu.name=="事务处理"){
//                        	var account = this.profile.uid;
//                        	var url="http://portal.qgj.cn/bpm/async.aspx?account="+account+"&uri=http%3A%2F%2Fportal.qgj.cn%2Fbpm%2Fdefault.aspx%3Fstartapp%3Dhtxc";
//                            $(this.frameContainer).html("<iframe style='margin-top:-61px;height:107.4%;' src='" + url + "'></iframe>").show();
//                        }else{
                            $(this.frameContainer).html("<iframe src='" + menu.action + "'></iframe>").show();
//                        }
                        //---------------------
                        this.currentModules.push(menu);
                    }
                   
                }
            },
            _destroyCurrentModules: function (includeFrame) {
                var module;
                if (this.currentModules.length) {
                    for (var i = this.currentModules.length - 1; i >= 0; i--) {
                        module = this.currentModules[i];
                        if (module.type === BootMenuType.TYPE_FRAME) {
                        	if (!!includeFrame) { //是否要清除嵌入的FRAME,两个感叹号表示将其他类型转换为对应的bool类型
                                $(this.frameContainer).empty();
                                this.currentModules.splice(i, 1);
                            } else {
                                $(this.frameContainer).hide();
                            }
                        } else {
                        	module.destructor();
                            this.currentModules.splice(i, 1);
                        }
                    }
                    if(this.currentTopMenu){
                    	this.currentTopMenu = null;
                    }
                    $(this.legendContainer).hide().children(".legend-content").empty();
                }
            },
            setModuleContainerWidth: function (width) {
                if (typeof width != "number" || width < 0) {
                    return;
                }

                $(htMap.map.root).parent().parent().css({
                    "margin-left": width + "px"
                });
                $(this.sidePanelContainer).children("#side-panel-wrapper").css({
                    "width": width + "px"
                });
                htMap.reposition(true);
            },
            resetModuleContainerWidth: function () {
                $(htMap.map.root).parent().parent().css({
                    "margin-left": ""
                });
                $(this.sidePanelContainer).children("#side-panel-wrapper").css({
                    "width": ""
                });
                htMap.reposition(true);
            }
        });
    });
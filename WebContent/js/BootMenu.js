/**
 * @author chenchu
 * @date 2014 10 14
 * @info 菜单组件
 */
define([
    "dojo/_base/declare",
    "dojo/topic",
    "./MapLeaf",
    "./Events",
    "./BootMenuType",
    "./BootMenuUtil",
    "text!./templates/bootMenu.html",
    "jquery",
    "underscore"
], function(declare, topic, htMap, Events, BootMenuType, BootMenuUtil, menuTemp, $, _) {
    "use strict";
    return declare(null, {
        delaredClass: "js.BootMenu",
        menusData: null,
        
        constructor: function(options) {
            declare.safeMixin(this, options || {});

            if(screen.width <= 1024){
                alert("您的屏幕分辨率小于1024*768，系统将进入简洁模式！请提升分辨率后获得更好体验！");
                $(".pull-left").hide();
            }
            
            if ($(this.menuContainer).length) {
                this.getMenusData();
            }
        },
        destructor: function() {
            $(this.menuContainer).length && $(this.menuContainer).off();
        },
        getMenusData: function() {
            var self = this;
            $.getJSON("getMenuTree!user", {}).then(function(data) {
                data = data.sort(function(a, b) {
                    return a.ord > b.ord ? 1 : -1;
                });
                self.menusData = self._toHierarchy(data);
                self.createMenus();
            }, function(jqXHR, textStatus, error) {
                console.error(error);
            });
        },
        createMenus: function() {
            var self = this;
            var menuTemplate = _.template(menuTemp);
            $(this.menuContainer).html(menuTemplate({
                menus: this.menusData
            }));

            $(this.menuContainer).on("click", ".dropdown-menu a", function(event) {
                var menu = BootMenuUtil.getMenuById($(this).data("id"), self.menusData);
                if (menu) {
                    switch (menu.type) {
                        case BootMenuType.TYPE_INDIVIDUAL:
                        case BootMenuType.TYPE_FRAME:
                        case BootMenuType.TYPE_COMPOSITE:
                            topic.publish(Events.MENUCLICK, menu);
                            event.preventDefault();
                            break;
                        case BootMenuType.TYPE_WINDOW:
                        	window.open(menu.action);
                            break;
                        default:
                            break;
                    }
                    $.get("moduleLog!user",{module:menu.action});
                }
            });

	        $(this.menuContainer).on("click", "li.dropdown", function(event) {
	        	$(event.target).closest(".dropdown").addClass("click").siblings().removeClass("click");
	        	var id = $(this).find("a").data("id");
	        	$("#page-wrapper").addClass("show-sub"); //展开左侧面板
                self.setModuleContainerWidth(200);
                //$(self.moduleContainer).show(); 
                
                self.initSubMenu(id);
                //self.activeModule && self.activeModule.reflow();
                htMap.reposition();
	        	
	        });
            
          var timer;
          $(this.menuContainer).on("mouseenter", "li.dropdown", function(event) {
              clearTimeout(timer);
              timer = setTimeout(function() {
                  $(event.target).closest(".dropdown").addClass("open").siblings().removeClass("open");
              }, 100);
          });

          $(this.menuContainer).on("mouseleave", "li.dropdown", function(event) {
              clearTimeout(timer);
              timer = setTimeout(function() {
                  $(event.target).closest(".dropdown").removeClass("open").siblings().removeClass("open");
              }, 100);
          });     
        },
        setModuleContainerWidth: function (width) {
            if (typeof width != "number" || width < 0) {
                return;
            }

            $(htMap.map.root).parent().parent().css({
                "margin-left": width + "px"
            });
            $("#page-wrapper").children("#submenu-wrapper").css({
                "width": width + "px"
            });
            htMap.reposition(true);
        },
        initSubMenu:function(id){
        	var self = this;
        	var submenus = "";		
        	var menu = BootMenuUtil.getMenuById(id, self.menusData);
        	if(!menu.children){
        		alert("获取子菜单失败，请联系管理员！！");
        		return;
        	}
			for(var i =0;i<menu.children.length;i++){
				var submenu = menu.children[i];
				submenus += '<li data-id="'+submenu.id+'">';
				if(submenu.children&&submenu.children.length>0){					
					submenus +='<div class="link" ><i class="sub-icon icon-'+submenu.name+'"></i><span class="sub-span">'+submenu.name+'</span><i class="fa fa-chevron-right"></i></div><ul class="submenu">';
					$.each(submenu.children,function(j,v){
						submenus += '<li data-id="'+v.id+'"><a href="javascript: void(0);" >'+v.name+'</a></li>';
					});
					submenus += '</ul></li>';
				}else{				
					submenus +='<div class="link" ><i class=" sub-icon icon-'+submenu.name+'"></i><span class="sub-span">'+submenu.name+'</span></div></li>';					
				}					
			}		
			$('#submenu-wrapper ul').empty().append(submenus);	
			self.initAccordion();
			$('#submenu-wrapper li').click(function(){
				if($(this).find(".link i").hasClass("fa-chevron-right")){
					return;
				}
				var menu = BootMenuUtil.getMenuById($(this).data("id"), self.menusData);
                if (menu) {
                    switch (menu.type) {
                        case BootMenuType.TYPE_INDIVIDUAL:
                        case BootMenuType.TYPE_FRAME:
                        case BootMenuType.TYPE_COMPOSITE:
                            topic.publish(Events.MENUCLICK, menu);
                            event.preventDefault();
                            break;
                        case BootMenuType.TYPE_WINDOW:
                        	window.open(menu.action);
                            break;
                        default:
                            break;
                    }
                    $.get("moduleLog!user",{module:menu.action});
                }				
			});
//			
//			$('#submenus').mouseleave(function(){
//				hideSubMenus();
//			});
        },
        initAccordion:function(){
			var Accordion = function(el, multiple) {
				this.el = el || {};
				this.multiple = multiple || false;

				// Variables privadas
				var links = this.el.find('.link');
				// Evento
				links.on('click', {el: this.el, multiple: this.multiple}, this.dropdown)
			}

			Accordion.prototype.dropdown = function(e) {
				var $el = e.data.el,
					$this = $(this),
					$next = $this.next();

				$next.slideToggle();
				$this.parent().toggleClass('open');

				if (!e.data.multiple) {
					$el.find('.submenu').not($next).slideUp().parent().removeClass('open');
				};
			}	
			var accordion = new Accordion($('#accordion'), false);
		},
        _toHierarchy: function(array) {
            var nodeObjects = this._createStructure(array);
            for (var i = nodeObjects.length - 1; i >= 0; i--) {
                var currentNode = nodeObjects[i];
                if (currentNode.pid === "") {
                    continue;
                }
                var parent = this._getParent(currentNode, nodeObjects);
                if (parent === null) {
                    continue;
                }
                parent.children.unshift(currentNode);
                nodeObjects.splice(i, 1);
            }
            return nodeObjects;
        },
        _createStructure: function(nodes) {
            for (var i = 0; i < nodes.length; i++) {
                nodes[i].children = [];
            }
            return nodes;
        },
        _getParent: function(child, nodes) {
            var parent = null;
            for (var i = 0; i < nodes.length; i++) {
                if (nodes[i].id == child.pid) {
                    return nodes[i];
                }
            }
            return parent;
        }
    });
});
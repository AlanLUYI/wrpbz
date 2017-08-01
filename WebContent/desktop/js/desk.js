WEBOS.desk={
	isedit:false,
	init:function(){
		var $this=this;
		var $divdesks=$('#divDesks');//has muti silders
		this.desks=$divdesks.swiper({
			initialSlide:0,
			onSlideChangeStart:function(swiper,direction){
				var $desker=$("#deskPager .desk-pager").eq(swiper.activeIndex);
				$desker.addClass('active')
				.siblings().removeClass('active');
				if($this.isedit) $this.onEdit4Pager();
			}
		});
		//为桌面图标添加事件
		WEBOS.app.bindEvent($divdesks);
		WEBOS.folder.bindEvent($divdesks);
		$divdesks.on('mouseup','#mask',function(e){
			if($(e.target).data('show')) return;
			$(this).hide().empty();
		});
		//左右按钮
		$('#divLeft').click(function(e){
			e.preventDefault();
			$this.desks.swipePrev();
		});
		$('#divRight').click(function(e){
			e.preventDefault();
			$this.desks.swipeNext();
		});
		$('.slip').hover(function() {
			$(this).find('div').fadeIn();
		}, function() {
			$(this).find('div').fadeOut();
		});
		
		//自动设置导航栏宽度
		$(window).resize(function(){
			$this.renderNav();
		});
		$this.renderNav();
		//隐藏标题栏
		$('#divExpand').click(function(){
			LAYOUT.toggle('north');
		}).click();//默认收缩
		//返回桌面
		$('#divHomeBtn').click(function(){
			$this.home();
		});
		
		//桌面遮罩mask
		$('#mask').on('mousedown',function(){
			//
		});
		//右键菜单的隐藏
		$(document).click(function(){
			$('.popup-menu').hide();
		});
		window.onblur = function() {
			$('.popup-menu').hide();
		};
		//按钮:编辑桌面
		this._bindDeskEditButton();
		//按钮:添加桌面
		$('#deskPager .desk-add').on('click',function(){
			$this.add();
		});
	},
	_bindDeskEditButton:function(){
		var $this=this;
		$('#deskPager .desk-edit').on('click',function(){
			var $divapps=$('#divDesks .div-apps');
			if($(this).text()=="编辑"){
				$this.isedit=true;
				$(this).text('完成');
				$(this).prev().show();
				$('#divDesks .swiper-slide').addClass('editing');
				var edithtml="<div class='app-edit-handle'></div><a class='app-delete'>x</a>";
				$divapps.find('.deskico').append(edithtml);
				$divapps.on('mousedown',function(e){
					if($(e.target).hasClass('app-delete')){
						var $deskico=$(e.target).parent();
						var str='确定要删除“'+$deskico.find('div.name').text()+'”吗？';
						if(window.confirm(str)){
							if($deskico.attr('appid').indexOf('folder_')>-1){
								WEBOS.folder.del($deskico.attr('appid').split('_')[1]);
							}
							$deskico.remove();
							$this.updateItems($(this));
						}else{}
					}
					e.stopPropagation();
					return false;
				});
				$divapps.sortable({
					handle:'.app-edit-handle',
					update:function(e,ui){//拖动后位置变了
						$this.updateItems($(this));
					},
					start:function(e,ui){
						console.log('sort start');
						//如果拖动的是一个文件夹，则不针对文件夹本身做任何改动，只排序
						if(ui.helper.attr('id').indexOf('folder')>-1) return;
						
						var $droper=$('<div id="droper" style="position:absolute;width:200px;height:100px;background:red;"></div>');
						var $targets=ui.helper.siblings().filter('.deskico:not(.ui-sortable-placeholder)');
						var tml='<div class="droper" for="#{0}" style="left:{1}px;top:{2}px;"></div>';
						var html="";
						$targets.each(function(){
							html+=tml.format($(this).attr('id'),$(this).offset().left+65,$(this).offset().top+40);
						});
						var $html=$(html).droppable({
							tolerance:'pointer',
							over:function(e,ui){
								ui.helper.siblings().filter('.ui-sortable-placeholder').hide();
								$($(this).attr('for')).find('.bg').css('background','blue');
								console.log('drop over');
							},
							out:function(){
								ui.helper.siblings().filter('.ui-sortable-placeholder').show();
								$($(this).attr('for')).find('.bg').css('background','transparent');
								console.log('drop out');
							},
							drop:function(e,ui){
								//新建文件夹 或 更新文件夹
								var _for=$(this).attr('for');
								var $for=$(_for);
								var $divapps=$for.parent();
								if(_for.indexOf('folder')>-1){//移动到一个现有文件夹上面了
									var obj=$for.data('obj');
									var items=$.map(obj.items,function(o){return 'app_'+o.id}).join(',');
									//更新文件夹
									WEBOS.folder.updateFolder({
										folderid:$for.attr('id').split('_')[2],
										foldername:$for.attr('title'),
										folder_items:items+','+ui.helper.attr('appid')
									});
									ui.helper.remove();
									$this.updateItems($divapps);
								}else{//新建一个文件夹
									var items=_for.replace('#desk_','')
									+','+ui.helper.attr('appid');
									WEBOS.folder.createFolder(items,$for.parent());
									$for.remove();
									ui.helper.remove();
								}
								//更新桌面
								
								$for.find('.bg').css('background','transparent');
								
								console.log('drop done');
							}
						});
						$('body').append($html);
						//$('#divDesks .deskico .folder-drop').show();
						//ui.helper.find('.folder-drop').hide();
					},
					stop:function(e,ui){
						console.log('sort stop');
						$('.droper').remove();
						//$('#divDesks .deskico .folder-drop').hide();
					},
					change:function(e,ui){
						console.log('sort change');
					}
				});
				$this.onEdit4Pager();
				$this.onEdit4PagerOrd(true);
			}else{
				$this.isedit=false;
				$(this).text('编辑');
				$(this).prev().hide();
				$('#divDesks .swiper-slide').removeClass('editing');
				$divapps.filter('.ui-sortable').sortable('destroy');
				$divapps.off('mousedown');
				$divapps.find('.app-edit-handle,.app-delete').remove();
				$('#deskPager .desk-pager-edit').remove();
				
				$this.onEdit4PagerOrd(false);
			}
		});
	},
	home:function(){
		$('#divFrame').hide();
		$('#divNav .selected').removeClass('selected');
	},
	deskResize:function(){
		this.desks.reInit();//swiper自带方法
	},
	renderNav:function(){
		var _nav=$('#divNav');
		var _w=0;
		_nav.siblings(':visible').each(function(){
			_w=_w+$(this).width();
		});
		$('#divNav').width(_nav.parent().width()-_w);
	},
	toDesk:function(index){
		this.desks.swipeTo(index);
	},
	onEdit4Pager:function(){
		var $this=this;
		var $desker=$("#deskPager .desk-pager").eq(this.desks.activeIndex);
		$desker.siblings().find('.desk-pager-edit').remove();
		var $desk_edit=$("<div class='desk-pager-edit'><input><button class='del'>删除</button><button class='add'>添加</button><div>");
		$desk_edit.appendTo($desker);
		var left=(($desk_edit.width()/$desker.width())/2)*100-50;
		$desk_edit.css('left',(-left).toString()+'%');
			
		$desk_edit.find('input').val($desker.find('label').text())
		.on('change',function(){
			var data={deskid:$desker.attr('deskid'),desk_name:$(this).val()};
			$.getJSON('updateDeskName!webos',data,function(json){
				if(json.result){
					$desker.find('label').text(data.desk_name);
				}else alert(json.message);
			});
		});
		$desk_edit.find('button.del').on('click',function(){
			var str='确定要删除桌面“'+$(this).prev().val()+'”吗？';
			if(window.confirm(str)){
				var data={deskid:$desker.attr('deskid')};
				$.getJSON('delDesk!webos',data,function(json){
					if(json.result){
						$('#divDesks .swiper-slide[deskid='+data.deskid+']').remove();
						$desker.remove();
						$this.deskResize();
						$this.toDesk(0);
					}else alert(json.message);
				});
			}
		});
		$desk_edit.find('button.add').on('click',function(){
			WEBOS.market.show($('#divDesks .swiper-slide-active .div-apps'));
		});
	},
	onEdit4PagerOrd:function(issort){
		var $this=this;
		var oldindex=0;
		if(issort){
			var $wrapper=$('#deskPager .pagers');
			$wrapper.sortable({
				axis:'x',
				start:function(e,ui){
					$this.toDesk(oldindex=ui.helper.index());
				},
				update:function(e,ui){
					var index=ui.item.index();
					if(index==0){
						$('#divDesks .swiper-slide[deskid="'+ui.item.attr('deskid')+'"]').prependTo('#divDesks .swiper-wrapper');
					}else{
						var preid=ui.item.prev().attr('deskid');
						$('#divDesks .swiper-slide:eq('+oldindex+')').insertAfter('#divDesks .swiper-slide[deskid='+preid+']');
					}
					$this.deskResize();
					$this.toDesk(index);
					
					var $prev=ui.item.prev(),
					$next=ui.item.next(),
					ord_prev=$prev.length ? +$prev.attr('ord') : 0,
					ord_next=$next.length ? +$next.attr('ord') : +$prev.attr('ord')+2;
					var neword = (ord_prev + ord_next)/2;
					$.getJSON('updateDeskOrd!webos',{deskid:ui.item.attr('deskid'),ord:neword},function(json){
						if(json.result){
							ui.item.attr('ord',neword);
						}else{
							alert(json.message);
							window.location.reload();
						}
					});
				}
			});
		}else{
			$('#deskPager .pagers').sortable('destroy');
		}
	},
	updateItems:function($divapps){
		var items=$divapps.find('.deskico').map(function(){return $(this).attr('appid')}).toArray().join(',');
		$.getJSON('updateDeskItems!webos',{deskid:$divapps.attr('deskid'),items:items},function(json){
			if(json.result){
				
			}else{
				alert(json.message);
			}
		});
	},
	add:function(){
		var $this=this;
		$.getJSON('addDesk!webos',function(json){
			var $desk_wrapper=$('#divDesks .swiper-wrapper');
			var $desk=$('<div deskid="{id}" class="swiper-slide editing"><div class="div-apps" deskid="{id}"></div></div>'.format(json))
			.appendTo($desk_wrapper).find('div');
			
			var $pager=$('<span deskid="{id}" ord="{ordby}" class="desk-pager"><label>{name}</label></span>'.format(json))
			.appendTo('#divDesks .pagination .pagers')
			.on('touchstart mousedown',function(){
				$this.toDesk($(this).index());
			});
			
			$this.deskResize();
			$this.toDesk($pager.index());
			$('#deskPager .desk-edit').trigger('click').trigger('click');
		});
	}
};


WEBOS.app={
	selector:'#divApps .app',
	width:160,
	height:130,
	create:function(data){//单处理
		var $this=this;
		var _app='<div id="desk_{type}_{id}" appid="{type}_{id}" class="deskico app" title="{name}"><div class="bg"><img src="{icon}" class="icon"/><div class="name">{name}</div></div></div>';
		_app=$(_app.format(data)).attr({
			url:data.url
		});
//		_app.mousedown(function(e){
//			$(this).data('startY',e.pageY);
//		}).mouseup(function(e){
//			if($(this).data('startY')==e.pageY){
//				$this.open($(this));
//			}
//		});
		return _app;
	},
	bindEvent:function($parent){
		var $this=this;
		$parent.on('mousedown','.app',function(e){
			$(this).data('startY',e.pageY);
		}).on('mouseup','.app',function(e){
			if($(this).data('startY')==e.pageY){
				if(!($(e.target).hasClass('app-edit-handle') || $(e.target).hasClass('app-delete'))) 
					$this.open($(this));
			}
		});
	},
	contextmenu:function($el){
		if(!this.cmenu){
			this.cmenu = $('<div class="popup-menu app-menu" style="z-index:9990;display:none"><ul><li><a menu="close" href="javascript:;">关闭</a></li><li><a menu="refresh" href="javascript:;">刷新</a></li><li style="display:none;"><a menu="lock" href="javascript:;">锁定</a></li><li style="border-bottom:1px solid #F0F0F0"><a menu="closeother" href="javascript:;">关闭其它标签页</a></li><li><a menu="home" href="javascript:;">返回桌面</a></li></ul></div>');
			$('body').append(this.cmenu);
			$('.app-menu').on('contextmenu', function(){
				return false;
			}).on('focusout',function(){
				//alert();
			});
		}
		if($el.has('.ti_closebtn img:hidden').length>0)//关闭
			$('.app-menu a[menu="close"]').addClass('disabled').off('click');
		else
			$('.app-menu a[menu="close"]').off('click').on('click', function(){
				$el.find('.close').click();
			});
		
		$('.app-menu a[menu="home"]').off('click').on('click', function(){//返回桌面
			WEBOS.desk.home();
			$('.popup-menu').hide();
		});
		
		$('.app-menu a[menu="lock"]').off('click').on('click', function(){//锁定
			if($(this).text()=="锁定"){
				$(this).text('解除锁定');
				$el.find('.ti_closebtn img').hide().addClass('locked');
			}else{
				$(this).text('锁定');
				$el.find('.ti_closebtn img').show().removeClass('locked');
				$('.app-menu a[menu="close"]').removeClass('disabled');
			}
		});
		
		$('.app-menu a[menu="closeother"]').off('click').on('click', function(){//关闭其它
			$el.siblings().each(function(){
				$(this).find('.close').click();
			});
			$('.popup-menu').hide();
		});
		
		$('.app-menu a[menu="refresh"]').off('click').on('click', function(){//刷新
			var _iframe=$('#'+$el.attr('for'));
			_iframe[0].contentWindow.location.reload(true);
			$('.popup-menu').hide();
		});
		return this.cmenu;
	},
	open:function($app){
//		if($app.attr('id').indexOf('_res_')>-1 || $app.attr('id').indexOf('_link_')>-1){
//			$('<a>').attr('href',$app.attr('url')).attr('target','_blank')[0].click();
//			return;
//		}
		if($app.attr('id').indexOf('_res_')>-1){
			$('<a>').attr('href',$app.attr('url')).attr('target','_blank')[0].click();
			return;
		}
		var $this=this;
		var _has=$('#divFrame iframe[appid="{0}"]'.format($app.attr('appid')));
		if(_has.length){
			//$('#divFrame iframe').css('z-index',1);
			//_has.css('z-index',2).parent().show();
			$('#tabitem_'+$app.attr('appid')).click();
		}else{
			var _app={
				appid:$app.attr('appid'),
				url:$app.attr('url'),
				name:$app.attr('title'),
				ico:$app.find('.icon').attr('src')
			};
			var $tabitem='<div><img class="ico" src="{ico}" /><div class="txt">{name}</div><div class="close" title="关闭"></div></div>';
			$tabitem=$($tabitem.format(_app)).attr({
				title:_app.name,
				id:'tabitem_'+_app.appid,
				appid:_app.appid,
				'class':'tabitem selected',
				'for':'iframe_'+_app.appid
			}).click(function(){
				$('#'+$(this).attr('for')).css('display','block').siblings().css('display','none');
				$('#divFrame').show();
				if($(this).hasClass('selected')) return;
				$(this).siblings('.selected').removeClass('selected');
				$(this).addClass('selected');
			}).on('contextmenu',function(e){
				$('.popup-menu').hide();
				var _cm=$this.contextmenu($(this));
				var l = ($(document).width() - e.clientX) < _cm.width() ? (e.clientX - _cm.width()): e.clientX;
				var t = ($(document).height() - e.clientY) < _cm.height() ? (e.clientY - _cm.height()): e.clientY;
				_cm.css( {left : l,top : t}).show();
				return false;
			}).css('z-index',2);
			$tabitem.find('.close').click(function(e){
				e.stopPropagation();
				$this.close($(this).parent());
			});
			$('#divNav').append($tabitem).sortable({axis: "x"});
			$tabitem.siblings().css('z-index',1);
			
			var $iframe='<iframe id="iframe_{appid}" appid="{appid}" src="{url}"></iframe>';
			$iframe=$($iframe.format(_app));
			$('#divFrame iframe').css('z-index',1);
			$('#divFrame').append($iframe.css('z-index',2));
		}
		$('#divFrame').show();
	},
	close:function($tabitem){
		$('#'+$tabitem.attr('for')).remove();
		$tabitem.remove();
		if($('#divNav .selected').length==0) WEBOS.desk.home();
	}
};
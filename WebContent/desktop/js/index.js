var WEBOS={};
$(function(){
	//初始化layout
	initLayout();
	appstart();
	
	//4秒钟之后加载一个图标新消息提醒的测试数据
//	setTimeout(function(){
//		$('#desk_folder_1').append('<a>1</a>');
//		$('#desk_folder_9').append('<a>1</a>');
//	},4000);
//	setTimeout(function(){
//		$('#desk_folder_8').append('<a>!</a>');
//		$('#desk_folder_16').append('<a>!</a>');
//	},3500);
});
function initLayout(){
	var reposition=function(){
		$('#tbLayout td').each(function(){
			$($(this).attr('for')).css({
				width:$(this).width(),
				height:$(this).height(),
				top:$(this).position().top,
				left:$(this).position().left
			});
		});
	};
	LAYOUT = $('body').layout({
		name : 'outerLayout',
		resizable:false,
		animatePaneSizing:false,
		fxSettings: { duration: 0},
		slidable:true,
		closable:true,
		contentSelector:'.content',
		north__size:70,
		spacing_open : 0,
		spacing_closed : 0,
		north__onopen:function(){
			$('#divTitle').hide(true,function(){
				WEBOS.desk.renderNav();
			});
		},
		north__onclose:function(){
			$('#divTitle').show(true,function(){
				WEBOS.desk.renderNav();
			});
		},
		center__onresize:function(){
			WEBOS.desk.deskResize();
			reposition();
		}
	});
	reposition();
}

function appstart(){
	WEBOS.user.getdesks(function(json){
		if(json.length){
			var $desk_wrapper=$('#divDesks .swiper-wrapper');
			var $desk_pager=$('#divDesks .pagination .pagers');
			//创建desk DOM
			$.each(json,function(i,v){
				var $desk=$('<div deskid="{id}" class="swiper-slide"><div class="div-apps" deskid="{id}"></div></div>'.format(v))
				.appendTo($desk_wrapper).find('div');
				if(v.items){
					$.each(v.items,function(ii,vv){
						if(vv.type!="folder"){
							$desk.append(WEBOS.app.create(vv));
						}else{
							$desk.append(WEBOS.folder.create(vv));
						}
					});
				}
				var $pager=$('<span deskid="{id}" ord="{ordby}" class="desk-pager"><label>{name}</label></span>'.format(v))
				.appendTo($desk_pager)
				.on('touchstart mousedown',function(){
				    WEBOS.desk.toDesk($(this).index());
				});
			});
		}else{
			var cfm=window.confirm("您还没有创建桌面，是否采用默认配置，点击是按默认配置自动创建桌面，点击否可手动创建桌面，桌面创建之后您依然可以自定义自己的桌面！");
			if(cfm){
				$.getJSON('createDeskByDefault!webos',function(json){
					if(json.result) window.location.reload();
					else{
						alert(json.message);
					}
				});
			}else{
				alert('您现在可以手动创建自己的桌面');
			}
		}
		WEBOS.desk.init();
		$('.desk-pager:first').addClass('active');
	});
	
	//切换到菜单模式
	$('#toggle-view').on('click',function(){
		window.location.href="../index.html";
	});
}

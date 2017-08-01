resos.boss.tab={
	navSelector:'#divNav',
	ifSelector:'#divFrame',
	open:function(id,name,url,ico){
		var _has=$(this.navSelector).find('#tabitem_'+id);
		if(_has.length){
			if(!_has.hasClass('selected')) _has.addClass('selected').siblings().removeClass('selected');
			$(_has.attr('for')).vfront().parent().show();//.css('z-index',2).siblings().css('z-index',1).parent().show();
		}else{
			var data={
				ico:ico,
				name:name,
				id:id,
				url:url
			};
			var $tabitem='<div><img class="ico" src="{ico}" /><div class="txt">{name}</div><div class="close" title="关闭"></div></div>';
			$tabitem=$($tabitem.format(data)).attr({
				title:name,
				id:'tabitem_'+id,
				'class':'tabitem selected',
				'for':'#iframe_'+id
			}).click(function(){
				if(!$(this).hasClass('selected')) $(this).addClass('selected').siblings().removeClass('selected');
				$($(this).attr('for')).vfront().parent().show();
			}).find('.close').click(function(e){
				e.stopPropagation();
				resos.boss.tab.close($(this).parent());
			}).parent();
			$(this.navSelector).append($tabitem).sortable({axis: "x"});
			
			var $iframe='<iframe id="iframe_{id}" src="{url}"></iframe>';
			$iframe=$($iframe.format(data));
			$(this.ifSelector).append($iframe);
			$iframe.vfront().parent().show();
		}
	},
	close:function($tabitem){
		$($tabitem.attr('for')).remove();
		$tabitem.remove();
		if($(this.navSelector).find('.selected').length==0) RB.desk.home();
	}
};
$.fn.zfront=function(){
	$(this).css('z-index',999).siblings('z-index',1);
	return $(this);
}
$.fn.vfront=function(){
	$(this).show().siblings().hide();
	return $(this);
}
WEBOS.market={
	$divapps:null,
	data_class:null,
	data_app:null,
	$market:null,
	curentapps:'',
	init:function(){
		var $this=this;
		var _initEl=function(){
			var html="<div id='divMarket'><div class='wrapper'><div class='title'><span>添加应用到当前桌面</span><div class='close' title='关闭'>X</div></div><div class='content'><div class='left cls'></div><div class='left list'></div></div></div><div>";
			$this.$market=$(html);
			$this.$market.find('.wrapper').draggable({
				handle:'.title'
			});
			$this.$market.find('.close').on('click',function(){
				$this.$market.detach();
			});
			
			var $list=$this.$market.find('.list').on('click','.list-item',function(){
				$this.$divapps.append(WEBOS.app.create($(this).data('obj')));
				$(this).remove();
				WEBOS.desk.updateItems($this.$divapps);
			});
			
			var html_cls="";
			$.each($this.data_class,function(i,v){
				html_cls+="<div class='cls-item' clsid={id}>{classname}</div>".format(v);
			});
			var $cls=$this.$market.find('.cls').append(html_cls)
			.on('click','.cls-item',function(){
				var clsid=$(this).attr('clsid');
				var _data=$this.data_app.filter(function(o){return o.classid==clsid;});
				var html_list="";
				$list.empty();
				$.each(_data,function(i,v){
					v.type='app';
					if($this.curentapps.indexOf('app_'+v.id+',')<0){
						var $item=$("<div class='list-item' appid='{id}'>{name}</div>".format(v))
						.data('obj',v);
						$list.append($item);
					}
				});
			});
		};
		$.getJSON('getAppClass!webos',function(json){
			$this.data_class=json;
			$.getJSON('getAllApp!webos',function(json){
				$this.data_app=json;
				_initEl();
				$this.show();
			});
		});
	},
	show:function($divapps){
		if($divapps) this.$divapps=$divapps;
		
		var arr=[];
		$('#divDesks .deskico').each(function(){
			if($(this).hasClass('app'))
				arr.push($(this).attr('appid'));
			else if($(this).hasClass('folder')) {
				var dd=$.map($(this).data('obj').items,function(o){return 'app_'+o.id;});
				arr=arr.concat(dd);
			}
		});
		this.curentapps=arr.join(',')+','
		
		if(!this.$market){
			this.init();
			return;
		}
		var $body=$('body');
		this.$market.appendTo($body)
		.find('.wrapper').css('left',$body.width()/2-250);
	}
}
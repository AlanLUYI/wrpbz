WEBOS.folder={
	selector:'#divApps .app .folder',
	width:160,
	height:130,
	create:function(data){//单处理
		var $this=this;
		var _app='<div id="desk_folder_{id}" appid="folder_{id}" class="deskico folder" title="{name}"><div class="bg"><img src="img/icon/folder.png" class="icon"/><div class="name">{name}</div></div></div>';
		_app=$(_app.format(data));
		_app.data('obj',data);
		var c=_app.find('.bg');
		$.each(data.items,function(i,v){
			c.append('<img src="{icon}" class="innericon"/>'.format(v));
			if(i>=3) return false;
		});
		return _app;
	},
	bindEvent:function($parent){
		var $this=this;
		$parent.on('mousedown','.folder',function(e){
			$(this).data('startY',e.pageY);
		}).on('mouseup','.folder',function(e){
			if($(this).hasClass('isclone')){
				return;
			}
			if($(e.target).hasClass('app-edit-handle')){//编辑状态中打开文件夹
				if($(this).hasClass('ui-sortable-helper')){//虽然是在编辑状态，但用户在拖动改变位置
					return;
				}
				$this.openEdit($(this));
			}
			if($(this).data('startY')==e.pageY){
				$this.open($(this));
			}
		});
	},
	open:function($app){
		var $this=this;
		$('#mask').empty().show();
		var f="<div id='divFolder' style='display:none;'><div><div class='fcontainer'></div></div></div>";
		$('#mask').html(f);
		$('#divFolder').css({top:$app.position().top+$app.height()+80});
		$app.clone().css({left:$app.offset().left,top:$app.position().top+80})
		.css('position','absolute')
		.appendTo('#mask')
		.addClass('isclone');
		$.each($app.data('obj').items,function(i,v){
			$('#divFolder').find('.fcontainer').append(WEBOS.app.create(v));
		});
		$('#divFolder').fadeIn('slow');
	},
	openEdit:function($app){
		var $this=this;
		var $mask=$('#mask').show();
		var $appclone=$app.clone();
		var $divFolder=$("<div id='divFolder' class='folder_editing' style='display:none;'><div><div class='fcontainer'></div></div></div>");
		$mask.append($divFolder);
		$divFolder.on('mousedown',function(e){
			$(e.target).data({show:true});
		});
		$divFolder.css({top:$app.position().top+$app.height()+80});
		$.each($app.data('obj').items,function(i,v){
			$divFolder.find('.fcontainer').append(WEBOS.app.create(v));
		});
		var edithtml="<div class='app-edit-handle'></div><a class='app-delete'>x</a>";
		$divFolder.find('.deskico').append(edithtml);
		$divFolder.fadeIn('slow');
		$divFolder.find('.fcontainer').sortable({
			handle:'.app-edit-handle',
			update:function(e,ui){//拖动后位置变了
				$this.update($app,$(this));
			},
			sort:function(e,ui){
				ui.helper.data('isout',!$this._isover($divFolder,ui.helper.find('.bg')));
			},
			beforeStop:function(e,ui){
				if(ui.item.data('isout')){
					ui.item.appendTo($app.parent());
					$this.update($app,$divFolder.find('.fcontainer'));
					WEBOS.desk.updateItems($app.parent());
				}
			}
		}).on('mousedown','.app-delete',function(e){
			var $deskico=$(e.target).parent();
			var str='确定要删除“'+$deskico.find('div.name').text()+'”吗？';
			if(window.confirm(str)){
				$deskico.remove();
				$this.update($app,$divFolder.find('.fcontainer'));
			}else{}
		});
		
		var folder_name=$appclone.find('.name').text();
		$appclone.css({left:$app.offset().left,top:$app.position().top+80})
		.css('position','absolute')
		.appendTo($mask)
		.addClass('isclone')
		.find('.app-edit-handle').append('<input>').find('input')
		.data('old',folder_name)
		.val(folder_name).on('mouseup',function(){
			return false;
		}).on('focusout',function(e){
			if($(this).val()!=$(this).data('old')){
				$app.find('.name').text($(this).val());
				$this.update($app,$divFolder.find('.fcontainer'));
			}
		});
	},
	_isover:function(objOne,objTwo){
		var offsetOne = objOne.offset(),
        offsetTwo = objTwo.offset(),
        topOne=offsetOne.top,
        topTwo=offsetTwo.top,
        leftOne=offsetOne.left,
        leftTwo=offsetTwo.left,
        widthOne = objOne.width(),
        widthTwo = objTwo.width(),
        heightOne = objOne.height(),
        heightTwo = objTwo.height();
		var leftTop = leftTwo > leftOne && leftTwo < leftOne+widthOne 
            && topTwo > topOne && topTwo < topOne+heightOne,
        rightTop = leftTwo+widthTwo > leftOne && leftTwo+widthTwo < leftOne+widthOne 
            && topTwo > topOne && topTwo < topOne+heightOne,
        leftBottom = leftTwo > leftOne && leftTwo < leftOne+widthOne 
            && topTwo+heightTwo > topOne && topTwo+heightTwo < topOne+heightOne,
        rightBottom = leftTwo+widthTwo > leftOne && leftTwo+widthTwo < leftOne+widthOne 
            && topTwo+heightTwo > topOne && topTwo+heightTwo < topOne+heightOne;
            return leftTop || rightTop || leftBottom || rightBottom;
	},
	close:function($tabitem){
		$('#'+$tabitem.attr('for')).remove();
		$tabitem.remove();
		if($('#divNav .selected').length==0) WEBOS.desk.home();
	},
	update:function($folder,$fcontainer){
		var $this=this;
		var data={
			folderid:$folder.attr('appid').split('_')[1],
			foldername:$folder.find('.name').text(),
			folder_items:$fcontainer.find('.deskico').map(function(){return $(this).attr('appid');}).toArray().join(',')
		};
		this.updateFolder(data);
	},
	updateFolder:function(data){//folderid,foldername,folder_items
		var $this=this;
		if(data){
			$.getJSON('updateFolder!webos',data,function(json){
				if(json.result){
					$this.reRender(data.folderid);
				}else{
					alert(json.message);
				}
			});
		}
	},
	del:function(folderid){
		$.getJSON('deleteFolder!webos',{folderid:folderid},function(json){
			if(json.result){}else{
				if(json.result){
					
				}else{
					alert(json.message);
				}
			}
		});
	},
	reRender:function(folderid){
		$.getJSON('getFolder!webos',{folderid:folderid},function(json){
			if(json){
				var $folder=$('#desk_folder_'+json.id);
				$folder.find('.name').text(json.name);
				$folder.data('obj',json);
				
				var $c=$folder.find('.bg');
				$c.find('img.innericon').remove();
				$.each(json.items,function(i,v){
					$c.append('<img src="{icon}" class="innericon"/>'.format(v));
					if(i>=3) return false;
				});
			}
		});
	},
	createFolder:function(items,$divapps){
		var $this=this;
		$.getJSON('createFolder!webos',{folder_items:items},function(json){
			if(json.result){
				//get json.data and create a new folder
				var $folder=$this.create(json.data);
				$divapps.append($folder);
				WEBOS.desk.updateItems($divapps);
			}else{
				alert(json.message);
			}
		});
	}
};

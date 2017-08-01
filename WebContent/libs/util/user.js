var treedivheight =250;
/**
 * 使用说明：
 * var config = {menuBtn:"",   请选择按钮
 * 				menuContentdiv:"",  tree的父级容器
 * 				treediv:"",    tree
 * 				displaydiv:"",  显示文字部分
 * 				hiddendiv:""       隐藏编码部分
 * };
 * var config = {menuBtn:"",menuContentdiv:"",treediv:"",displaydiv:"",hiddendiv:""};
 * 
 * 
 * 人员单选：UserTreeRadio，
 * 人员复选：UserTreeCheck，UserTreeCheck2（多角度）
 * 岗位单选：PositionTreeRadio，XZPositionTreeRadio，OtherPositionTreeRadio
 * 岗位复选：PositionTreeCheck，XZPositionTreeCheck，OtherPositionTreeCheck
 * 部门单选：DepartmentTreeRadio，XZDepartmentTreeRadio，OtherDepartmentTreeRadio
 * 部门复选：DepartmentTreeCheck，XZDepartmentTreeCheck，OtherDepartmentTreeCheck
 * 
 */

//行政岗位+其他岗位
PositionTreeCheck = (function(){
	var UserTreeClass = function(config){
		this.menuBtn = config.menuBtn;
		this.menuContentdiv = config.menuContentdiv;
		this.treediv = config.treediv;
		this.displaydiv = config.displaydiv;
		this.hiddendiv = config.hiddendiv;
		this.rescd = config.rescd;
		this.init();
	};
	$.extend(UserTreeClass.prototype,{
		init:function(){
			
		},	
		resetTree:function(){
			var me = this;
			var setting = {
				check: {
					enable: true,
				},
				view: {
					dblClickExpand: false
				},	
				data : {
					simpleData : {
						enable : true
					}
				},
				callback: {
					beforeClick: function(treeId, treeNode){
						me.beforeClick(treeId, treeNode, me);
					},
					onCheck: function(e,treeId, treeNode){
						me.onCheck(treeId, treeNode, me);
					},
				}
			};
			$("#"+me.displaydiv).bind("click", function(){
				me.showMenu(me);
			});
			$.ajax( {
				type : "post",
				data : {
					rescd:me.rescd
				},
				url : "getPostTree!userutil",
				success : function(data) {	
					var json = $.parseJSON(data);
					$.fn.zTree.init($("#" + me.treediv), setting, json);
					me.initsettree();
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					
				}
			});
		},
		beforeClick:function(treeId, treeNode, me) {
			var zTree = $.fn.zTree.getZTreeObj(me.treediv);
			zTree.checkNode(treeNode, !treeNode.checked, null, true);
			return false;
		},		
		onCheck:function(treeId, treeNode, me) {
			var zTree = $.fn.zTree.getZTreeObj(me.treediv),
			nodes = zTree.getCheckedNodes(true),
			v = "";
			e = "";
			for (var i=0, l=nodes.length; i<l; i++) {
				if(nodes[i].type==undefined){
					v += nodes[i].name + ",";
					e += "("+nodes[i].id+ ")";
				}
			}
			if (v.length > 0 ) v = v.substring(0, v.length-1);
			var displayObj = $("#"+me.displaydiv);
			displayObj.attr("value", v);
			displayObj.val(v);
			displayObj.html(v);
			var hiddenObj = $("#"+me.hiddendiv);
			hiddenObj.attr("value", e);	
			hiddenObj.val(e);
			hiddenObj.html(e);		
		},
		showMenu:function(me) {
			var displayObj = $("#"+me.displaydiv);
			$("#"+me.menuContentdiv).css("display","");
			$("body").bind("mousedown", function(){
				me.onBodyDown(me);
			});

			var _h = 0 ;
			$($("#"+me.treediv+" li")).each(function(){
				_h += $(this).height();
			});
			if(_h>=treedivheight) $("#"+me.treediv).css("height",treedivheight);
			else  $("#"+me.treediv).css("height",_h);
		},
		hideMenu:function(me) {
			$("#"+me.menuContentdiv).fadeOut("fast");
			$("body").unbind("mousedown", function(){
				me.onBodyDown();
			});
		},
		onBodyDown:function(me) {
			if (!((event.target.id == me.menuBtn && me.menuBtn != "")
					|| event.target.id == me.displaydiv || event.target.id == me.menuContentdiv || $(event.target).parents("#"+me.menuContentdiv).length>0)) {
				me.hideMenu(me);
			}
		},
		initsettree:function(){
			var idsString = $("#"+this.hiddendiv).val().toString();
			if(idsString.length>0){
				var me = this;
				var zTree = $.fn.zTree.getZTreeObj(me.treediv);
				var nodes = zTree.transformToArray(zTree.getNodes());
				for (var i=0, l=nodes.length; i<l; i++) {					
					if(nodes[i].type==undefined && idsString.indexOf("("+nodes[i].id+")")>=0 ){
						zTree.checkNode(nodes[i], true, true);				
					}
				}
			}
		}
	});
	return UserTreeClass;
})();

//其他岗位
OtherPositionTreeCheck = (function(){
	var UserTreeClass = function(config){
		this.menuBtn = config.menuBtn;
		this.menuContentdiv = config.menuContentdiv;
		this.treediv = config.treediv;
		this.displaydiv = config.displaydiv;
		this.hiddendiv = config.hiddendiv;
		this.regionid = config.regionid;
		this.init();
	};
	$.extend(UserTreeClass.prototype,{
		init:function(){
			
		},	
		resetTree:function(){
			var me = this;
			var setting = {
				check: {
					enable: true,
				},
				view: {
					dblClickExpand: false
				},	
				data : {
					simpleData : {
						enable : true
					}
				},
				callback: {
					beforeClick: function(treeId, treeNode){
						me.beforeClick(treeId, treeNode, me);
					},
					onCheck: function(e,treeId, treeNode){
						me.onCheck(treeId, treeNode, me);
					},
				}
			};
			$("#"+me.displaydiv).bind("click", function(){
				me.showMenu(me);
			});
			$.ajax( {
				type : "post",
				data : {
					regionid:me.regionid
				},
				url : "getOtherPostTree!userutil",
				success : function(data) {	
					var json = $.parseJSON(data);
					$.fn.zTree.init($("#" + me.treediv), setting, json);
					me.initsettree();
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					
				}
			});
		},
		beforeClick:function(treeId, treeNode, me) {
			var zTree = $.fn.zTree.getZTreeObj(me.treediv);
			zTree.checkNode(treeNode, !treeNode.checked, null, true);
			return false;
		},		
		onCheck:function(treeId, treeNode, me) {
			var zTree = $.fn.zTree.getZTreeObj(me.treediv),
			nodes = zTree.getCheckedNodes(true),
			v = "";
			e = "";
			for (var i=0, l=nodes.length; i<l; i++) {
				if(nodes[i].type==undefined){
					v += nodes[i].name + ",";
					e += "("+nodes[i].id+ ")";
				}
			}
			if (v.length > 0 ) v = v.substring(0, v.length-1);
			var displayObj = $("#"+me.displaydiv);
			displayObj.attr("value", v);
			displayObj.val(v);	
			displayObj.html(v);
			var hiddenObj = $("#"+me.hiddendiv);
			hiddenObj.attr("value", e);	
			hiddenObj.val(e);	
		},
		showMenu:function(me) {			
			var displayObj = $("#"+me.displaydiv);
			$("#"+me.menuContentdiv).css("display","");
			
			$("body").bind("mousedown", function(){
				me.onBodyDown(me);
			});

			var _h = 0 ;
			$($("#"+me.treediv+" li")).each(function(){
				_h += $(this).height();
			});
			if(_h>=treedivheight) $("#"+me.treediv).css("height",treedivheight);
			else  $("#"+me.treediv).css("height",_h);
		},
		hideMenu:function(me) {
			$("#"+me.menuContentdiv).fadeOut("fast");
			$("body").unbind("mousedown", function(){
				me.onBodyDown();
			});
		},
		onBodyDown:function(me) {
			if (!((event.target.id == me.menuBtn && me.menuBtn != "")
					|| event.target.id == me.displaydiv || event.target.id == me.menuContentdiv || $(event.target).parents("#"+me.menuContentdiv).length>0)) {
				me.hideMenu(me);
			}
		},
		initsettree:function(){
			var idsString = $("#"+this.hiddendiv).val().toString();
			if(idsString.length>0){
				var me = this;
				var zTree = $.fn.zTree.getZTreeObj(me.treediv);
				var nodes = zTree.transformToArray(zTree.getNodes());
				for (var i=0, l=nodes.length; i<l; i++) {					
					if(nodes[i].type==undefined && idsString.indexOf("("+nodes[i].id+")")>=0 ){
						zTree.checkNode(nodes[i], true, true);				
					}
				}
			}
		}
	});
	return UserTreeClass;
})();

//其他机构部门
OtherDepartmentTreeCheck = (function(){
	var DepartmentTreeClass = function(config){
		this.menuBtn = config.menuBtn;
		this.menuContentdiv = config.menuContentdiv;
		this.treediv = config.treediv;
		this.displaydiv = config.displaydiv;
		this.hiddendiv = config.hiddendiv;
		this.leader_knm = config.leader_knm;
		this.leader_k = config.leader_k;
		this.leader_fnm = config.leader_fnm;
		this.leader_f = config.leader_f;
		this.regionid = config.regionid;
		this.init();
	};
	$.extend(DepartmentTreeClass.prototype,{
		init:function(){
			
		},	
		resetTree:function(){		
			var me = this;
			var setting = {
				check: {
					enable: true
				},
				view: {
					dblClickExpand: false
				},	
				data : {
					simpleData : {
						enable : true
					}
				},
				callback: {
					beforeClick: function(treeId, treeNode){
						me.beforeClick(treeId, treeNode, me);
					},
					onCheck: function(e,treeId, treeNode){
						me.onCheck(treeId, treeNode, me);
					},
				}
			};
			$("#"+me.displaydiv).bind("click", function(){
				me.showMenu(me);
			});
			$.ajax( {
				type : "post",
				data : {
					regionid:me.regionid
				},
				url : "getOtherDepartment!userutil",
				success : function(data) {	
					var json = $.parseJSON(data);
					$.fn.zTree.init($("#" + me.treediv), setting, json);
					me.initsettree();
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					
				}
			});
		},
		beforeClick:function(treeId, treeNode, me) {
			var zTree = $.fn.zTree.getZTreeObj(me.treediv);
			zTree.checkNode(treeNode, !treeNode.checked, null, true);
			return false;
		},		
		onCheck:function(treeId, treeNode, me) {
			var zTree = $.fn.zTree.getZTreeObj(me.treediv),
			nodes = zTree.getCheckedNodes(true),
			v = "";
			e = "";
			for (var i=0, l=nodes.length; i<l; i++) {
				if(nodes[i].type==undefined){
					v += nodes[i].name + ",";
					e += "("+nodes[i].id+")";
				}
			}
			if (v.length > 0 ) v = v.substring(0, v.length-1);
			var displayObj = $("#"+me.displaydiv);
			displayObj.attr("value", v);
			displayObj.val(v);	
			displayObj.html(v);
			var hiddenObj = $("#"+me.hiddendiv);
			hiddenObj.attr("value", e);	
			hiddenObj.val(e);	
			
			if($("#"+me.leader_knm)[0]!=undefined){
				$("#"+me.leader_knm)[0].value = treeNode.leader_knm;
				$("#"+me.leader_k)[0].value = treeNode.leader_k;
			}
			if($("#"+me.leader_fnm)[0]!=undefined){
				$("#"+me.leader_fnm)[0].value = treeNode.leader_fnm;
				$("#"+me.leader_f)[0].value = treeNode.leaderid_f;	
			}
		},
		showMenu:function(me) {			
			var displayObj = $("#"+me.displaydiv);
			$("#"+me.menuContentdiv).css("display","");			
			$("body").bind("mousedown", function(){
				me.onBodyDown(me);
			});
			
			var _h = 0 ;
			$($("#"+me.treediv+" li")).each(function(){
				_h += $(this).height();
			});
			if(_h>=treedivheight) $("#"+me.treediv).css("height",treedivheight);
			else  $("#"+me.treediv).css("height",_h);
			
		},
		hideMenu:function(me) {
			$("#"+me.menuContentdiv).fadeOut("fast");
			$("body").unbind("mousedown", function(){
				me.onBodyDown();
			});
		},
		onBodyDown:function(me) {
			if (!((event.target.id == me.menuBtn && me.menuBtn != "")
					|| event.target.id == me.displaydiv || event.target.id == me.menuContentdiv || $(event.target).parents("#"+me.menuContentdiv).length>0)) {
				me.hideMenu(me);
			}
		},
		initsettree:function(){
			var idsString = $("#"+this.hiddendiv).val().toString();
			if(idsString.length>0){
				var me = this;
				var zTree = $.fn.zTree.getZTreeObj(me.treediv);
				var nodes = zTree.transformToArray(zTree.getNodes());
				for (var i=0, l=nodes.length; i<l; i++) {					
					if(nodes[i].type==undefined && idsString.indexOf("("+nodes[i].id+")")>=0 ){
						zTree.checkNode(nodes[i], true, true);				
					}
				}
			}
		}
	});
	return DepartmentTreeClass;
})();

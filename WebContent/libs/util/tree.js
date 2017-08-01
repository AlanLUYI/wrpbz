treedivheight=300;
RESTreeCheck = (function(){
	var UserTreeClass = function(config){
		this.menuBtn = config.menuBtn;
		this.menuContentdiv = config.menuContentdiv;
		this.treediv = config.treediv;
		this.displaydiv = config.displaydiv;
		this.hiddendiv = config.hiddendiv;
		this.rescd = config.rescd;
		this.init();
		this.completeTree=config.completeTree;
		this.completeCheck=config.completeCheck;
		this.selectedRES= [];
		this.allRES= [];
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
			$("#"+me.menuBtn).bind("click", function(){
				me.showMenu(me);
			});
			$.ajax( {
				type : "post",
				data : {
					rescd:me.rescd
				},
				url : "getResTreeList!util",
				success : function(data) {	
					var json = $.parseJSON(data);
					$.each(json,function(i,v){
						delete v.url;
					});
					$.fn.zTree.init($("#" + me.treediv), setting, json);
					me.initsettree();
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					
				}
			});
		},
		completeTree:function(tree){
			tree = zTree;
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
			var sortArray = new Array();
			var j = 0;
			for (var i=0, l=nodes.length; i<l; i++) {
				if(nodes[i].type!=undefined){
					if(nodes[i].type=="res"){
						v += nodes[i].name + ",";
						e += "("+nodes[i].id+ ")";
						sortArray[j] = new Array();
						sortArray[j][0] = nodes[i].ordby;
						sortArray[j][1] = nodes[i].name;
						sortArray[j][2] = nodes[i].id;
						j+=1;
					}
				}
			}
			sortArray.sort(function(a,b){return a[0]-b[0];});
			this.selectedRES = sortArray;
			if (v.length > 0 ) v = v.substring(0, v.length-1);
			var displayObj = $("#"+me.displaydiv);
			displayObj.attr("value", v);
			var hiddenObj = $("#"+me.hiddendiv);
			hiddenObj.attr("value", e);	
			if(me.completeCheck){
			    me.completeCheck.call();
			}
		},
		showMenu:function(me) {
			var displayObj = $("#"+me.displaydiv);
			if(me.menuBtn==""){
				$("#"+me.menuContentdiv).css("left",$("#"+me.displaydiv)[0].offsetLeft);
			}else{
				$("#"+me.menuContentdiv).css("left",$("#"+me.menuBtn)[0].offsetLeft);
			}
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
			var me =this;
			var zTree = $.fn.zTree.getZTreeObj(me.treediv);
			var idsString = $("#"+this.hiddendiv).val().toString();
			if(idsString.length>0){
				var nodes = zTree.transformToArray(zTree.getNodes());
				for (var i=0, l=nodes.length; i<l; i++) {					
					if((nodes[i].type==undefined||nodes[i].type=="res") 
							&& idsString.indexOf("("+nodes[i].id+")")>=0 ){
						zTree.checkNode(nodes[i], true, true);				
					}
				}
			}
			me.zTree = zTree;
			$.each(zTree.transformToArray(zTree.getNodes()),function(a,b){
				if(b.type=="res"){
					me.allRES.push(b);
				}
			});
			if(me.completeTree){
			    me.completeTree.call();
			}
		},
		selectResByType:function(restp){
			var me =this;
			var nodes = me.zTree.transformToArray(me.zTree.getNodes());
			for (var i=0, l=nodes.length; i<l; i++) {			
				me.zTree.checkNode(nodes[i], false, false);
				if(nodes[i].type=="res"  
						&& restp.indexOf("("+nodes[i].restp+")")>=0  ){
					me.zTree.checkNode(nodes[i], true, true);				
				}					
			}
			
			var zTree = $.fn.zTree.getZTreeObj(me.treediv),
			nodes = zTree.getCheckedNodes(true),
			v = "";
			e = "";
			var sortArray = new Array();
			var j = 0;
			for (var i=0, l=nodes.length; i<l; i++) {
				if(nodes[i].type!=undefined){
					if(nodes[i].type=="res"){
						v += nodes[i].name + ",";
						e += "("+nodes[i].id+ ")";
						sortArray[j] = new Array();
						sortArray[j][0] = nodes[i].ordby;
						sortArray[j][1] = nodes[i].name;
						sortArray[j][2] = nodes[i].id;
						j+=1;
					}
				}
			}
			sortArray.sort(function(a,b){return a[0]-b[0];});
			this.selectedRES = sortArray;
			if (v.length > 0 ) v = v.substring(0, v.length-1);
			var displayObj = $("#"+me.displaydiv);
			displayObj.attr("value", v);
			var hiddenObj = $("#"+me.hiddendiv);
			hiddenObj.attr("value", e);
		},
		selectRes:function(regionid,restp){
			var me =this;
			var nodes = me.zTree.transformToArray(me.zTree.getNodes());
			for (var i=0, l=nodes.length; i<l; i++) {			
				me.zTree.checkNode(nodes[i], false, false);
				if(nodes[i].type=="res"  
						&& regionid.indexOf("("+nodes[i].pId+")")>=0 
						&& restp.indexOf("("+nodes[i].restp+")")>=0  ){
					me.zTree.checkNode(nodes[i], true, true);				
				}					
			}
			
			var zTree = $.fn.zTree.getZTreeObj(me.treediv),
			nodes = zTree.getCheckedNodes(true),
			v = "";
			e = "";
			var sortArray = new Array();
			var j = 0;
			for (var i=0, l=nodes.length; i<l; i++) {
				if(nodes[i].type!=undefined){
					if(nodes[i].type=="res"){
						v += nodes[i].name + ",";
						e += "("+nodes[i].id+ ")";
						sortArray[j] = new Array();
						sortArray[j][0] = nodes[i].ordby;
						sortArray[j][1] = nodes[i].name;
						sortArray[j][2] = nodes[i].id;
						j+=1;
					}
				}
			}
			sortArray.sort(function(a,b){return a[0]-b[0];});
			this.selectedRES = sortArray;
			if (v.length > 0 ) v = v.substring(0, v.length-1);
			var displayObj = $("#"+me.displaydiv);
			displayObj.attr("value", v);
			var hiddenObj = $("#"+me.hiddendiv);
			hiddenObj.attr("value", e);
		}
	});
	return UserTreeClass;
})();

AllRESTreeCheck = (function(){
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
				url : "getDocRes!doc",
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
				if((nodes[i].type==undefined||nodes[i].type=="res") ){
					v += nodes[i].name + ",";
					e += "("+nodes[i].id+ ")";
				}
			}
			if (v.length > 0 ) v = v.substring(0, v.length-1);
			var displayObj = $("#"+me.displaydiv);
			displayObj.attr("value", v);
			var hiddenObj = $("#"+me.hiddendiv);
			hiddenObj.attr("value", e);	
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
					if((nodes[i].type==undefined||nodes[i].type=="res") 
							&& idsString.indexOf("("+nodes[i].id+")")>=0 ){
						console.log(nodes[i].id);
						zTree.checkNode(nodes[i], true, true);				
					}
				}
			}
		}
	});
	return UserTreeClass;
})();
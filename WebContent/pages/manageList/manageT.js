/**
 * 管理机构
 * @author 
 */
(function($,undefined){
	//var profile=new window.top.QGJPROFILE;
	
	var LogManager = function(options){
		$.extend(this,options||{});
		this.initPage();
		this.initEvent();
		this.isOpen=false;
	};
	
	LogManager.prototype = { 
			initPage: function(){
				var me=this;				
				this.initTable();
				this.initData();
			},
			
			initEvent:function(){
				var me=this;
				
			},
			initTable:function(){
				var height = $(parent).height()-100;
				console.log(height);
				this.table.bootstrapTable({
						
					height:height,
					columns: [
						{ title: '科室', field: 'keshi', width: 100, align: "center", },
						{ title: '人员', field: 'renyuan', width: 120, align: "center", },
						{ title: '岗位', field: 'gangwei', width: 100, align: "center"},
						{ title: '管理事项', field: 'shixian', width: 120, align: "center"},
						{ title: '管理内容', field: 'neirong', width: 200, align: "center"}
						
						],
				});
			},	
			initData:function(){
				var me = this;
				$.getJSON("queryManageList!office",function(json){
					me.data= json;
					me.filter();
				});
			},
			filter:function(){
				var me = this,result=[];
				var input = $("#search").val();
				$.each(this.data,function(i,v){
					if(v.shixian.indexOf(input)>-1){
						result.push(v);
					}
				});
				this.table.bootstrapTable('load',result);
			}
	
	};
	window.LogManager = LogManager;
	
})(jQuery);



function resizeall(){
	$("#appul").css("height",$("#tab5-grid-div").height());
	
	$("#dpu-gridTable").jqGrid('setGridHeight',$('#tab6-west').height()/2-65);			
	$("#dpu-gridTable").jqGrid('setGridWidth',$('#tab6-west').width());	
	
	$("#dpu2-gridTable").jqGrid('setGridHeight',$('#tab6-west').height()/2-65);			
	$("#dpu2-gridTable").jqGrid('setGridWidth',$('#tab6-west').width());	

	$("#onguard-gridTable").jqGrid('setGridHeight',$('#tab6-center').height()-100);			
	$("#onguard-gridTable").jqGrid('setGridWidth',$('#tab6-center').width());	

	$("#alluser-gridTable").jqGrid('setGridHeight',$('#tab6-east').height()-100);			
	$("#alluser-gridTable").jqGrid('setGridWidth',$('#tab6-east').width());

	$("#app-gridTable").jqGrid('setGridHeight',$('#tab7-center').height()-65);			
	$("#app-gridTable").jqGrid('setGridWidth',$('#tab7-center').width());
}


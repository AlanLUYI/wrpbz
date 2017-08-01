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
				
			},
			
			initEvent:function(){
				var me=this;
				$(".accordion li").click(function(){
					$(this).addClass('click');
					$(this).siblings().removeClass('click');
					var id = $(this).attr("id");
					$("#"+id+"-wrapper").show();
					$("#"+id+"-wrapper").siblings().hide();
				});
			},
						
	
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

function formatKV(v){
	if(v.toString().length==1){
		return "00"+v;
	}else if(v.toString().length==2){
		return "0"+v;
	}else{ 
		return v;
	}
}


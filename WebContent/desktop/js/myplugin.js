/*自定义的panel插件（必须牛X）
常规html:
<div class="mycontainer right-2">
	<div class='panel-header'>天气情况</div>
	<div class='panel-content'></div>
</div>

带有滚动条的panel的html部分
<div class="mycontainer left swiper">
<div class='panel-header'>应用模块</div>
<div class='panel-content swiper-container'>
	<div class="swiper-scrollbar"></div>
	<div class='swiper-wrapper'>
		<div class='swiper-slide' id='divApps'>
			
		</div>
	</div>
</div>
</div>

css部分：
.mycontainer{
	border:1px solid silver;
	background:rgba(255,255,255,0.3);
	overflow: hidden;
	 transition: All 0.4s ease-in-out;
	-webkit-transition: All 0.4s ease-in-out;
	-moz-transition: All 0.4s ease-in-out;
	-o-transition: All 0.4s ease-in-out; 
}
.mycontainer .panel-content{
	
}
.mycontainer .panel-header{
	height:30px;
	background:rgba(128,128,128,0.2);
	border-bottom:1px solid gray;
	line-height:30px;
	font-size:16px;
	font-family:simhei;
	padding-left:8px;
	letter-spacing: 2px;
}
.mycontainer .swiper-container,.swiper-wrapper{
	width:100%;
	height:100%;
}
.mycontainer .swiper-scrollbar {
	background: none;
	position: absolute;
	right: 3px;
	top: 0;
	height: 100%;
	width: 5px;
}

要求：带有滚动条时用到了swiper插件
功能：content部分随panel变化自动调整大小，充满除header以外的空间；可以添加类ios的滚动条；内容高度变化时swiper相应刷新
使用方法：$('your selector').panel();
*/
$.fn.panel=function(opt){
	var options={
		headerSelector:'.panel-header',
		contentSelector:'.panel-content'
	};
	$.extend(options,opt);
	
	$.each($(this),function(i,v){
		if($(this).hasClass('swiper')){
			var me=$(this);
			var swiper=$(this).find('.swiper-container').swiper({
				mode:'vertical',
				scrollContainer:true,
				mousewheelControl : true,
				scrollbar:{
					container:$(this).find('.swiper-scrollbar')[0]
				}
			});
			$(this).data('swiper',swiper);
			$(this).find('.swiper-slide').resize(function(){
				me.data('swiper').reInit();
			});
		}
		
		$(this).resize(function(){
			$(this).find(options.contentSelector).height($(this).height()-$(this).find(options.headerSelector).height());
			if($(this).hasClass('swiper')) $(this).data('swiper').reInit();
		}).find(options.contentSelector).height($(this).height()-$(this).find(options.headerSelector).height());
	});
	
	return $(this);
};
//交接班系统
$(function(){
	//加载时获取当前班次，如果是初次加载，则显示当天的值班人员
		$.getJSON("queryFORduty!PUBLIC",function(json){
			var obj = json[0];
			$("#next_duty").attr("now",obj.id);
			var isduty = obj.isduty;
			//值班领导
			var arry_name = obj.member_name.split(";");
			var arry_id = obj.member.split(";");
			var zgry = "在岗人员：";
			obj["member_1"] = arry_id[0],obj["member_2"] = arry_id[1],obj["member_1_name"] = arry_name[0],obj["member_2_name"] = arry_name[1];
			var json = [{"title":"leader","img":"img1","name":"leader_name"},
			            {"title":"master","img":"img2","name":"master_name"},
			            {"title":"member_1","img":"img3","name":"member_1_name"},
			            {"title":"member_2","img":"img4","name":"member_2_name"},
			            {"title":"dirver","img":"img5","name":"dirver_name"}];
			$.each(json,function(i,v){
				if(obj[v["title"]]){
					$(".h3_"+v["title"]).html( obj[v["name"]] );
					$("#"+v["img"]).attr("forid",obj[v["title"]]);
					$("#"+v["img"]).attr("name",obj[v["name"]]);
					if( isduty!=null && isduty.indexOf( ","+obj[v["title"]]+"," )>=0 ){					
						$("#"+v["img"]).attr("src","image/zaixian.png");
						$("#"+v["img"]).attr("isduty",'1');
						$(".h3_"+v["title"]).css("color","rgb(56,56,56)");				
						zgry += obj[v["name"]]+" ";	
						if( v["title"]=="member_1" || v["title"]=="member_2" ){
							$(".log_member").attr("isduty","1");
							$(".log_member").attr("forid",obj[v["title"]]+","+$(".log_member").attr("forid"));
							$(".log_member").attr("forname",obj[v["name"]]+","+$(".log_member").attr("forname"));
						}else{
							$(".log_"+v["title"]).attr("isduty","1");
							$(".log_"+v["title"]).attr("forid",obj[v["title"]]);
							$(".log_"+v["title"]).attr("forname",obj[v["name"]]);
						}
					}else{
						$("#"+v["img"]).attr("src","image/lixian.png");
						$("#"+v["img"]).attr("isduty",'0');
						$(".h3_"+v["title"]).css("color","gray");
					}
				}
			})
					
			$(".log").click(function(){
				var name = $(this).attr("forname");
				var id = $(this).attr("forid");
				if( id ){
					PromptDialog_log("日志登记",$("#dialog_log").html(),"",name,id);
				}
			})
			$("#zgry").html(zgry);
			$(".lixian").click(function(){
				//判断是登陆还是提出
				var isduty = $(this).attr("isduty");
				var id = $(this).attr("forid");
				var name = $(this).attr("name");
				if( isduty == "1"){
					//在前在岗，需要退岗，判断当前在岗人员的数目
					var count = 0;
					$.each($('.lixian'),function(i,v){
						if( $(v).attr("isduty")=="1" ){
							count ++;
						}
					})
					if( count == 1){
						alert("当前只有您在岗，不能退岗");
					}else{
						//退岗
						PromptDialog_out("退出",$("#dialog_out").html(),"",name,id);
					}
				}else{
					//上岗
					PromptDialog_in(name+" 你好",$("#dialog_in").html(),"",name,id);
				}
			})
			//加载值班事件
			$.getJSON("queryFORlog!PUBLIC",function(json){
				if( json.length > 0 ){
					$(".div_4_left").css("background","white");
					var html = "<table class='table table-bordered'>";
					html += "<tr><th>事件类型</th><th>事件内容</th><th>登记人</th><th>处理意见</th><th>时间</th><th>操作</th></tr>";
					$.each(json,function(i,v){
						  html += "<tr><td>"+v.type+"</td><td>"+v.shijian+"</td><td>"+v.radio_n+"</td><td>"+v.yijian+"</td><td>"+v.time+"</td><td><span style='cursor:pointer' forid='"+v.id+"' title='标记已处理' class='icon icon-check-square-o text-big margin-left icon_do'></span></td></tr>";
					})
					html+="</table>";
					$(".div_4_left").html(html);
				}
				$(".icon_do").click(function(){
					var forid = $(this).attr("forid");
					if( confirm("确定标记已处理？") ){
						$.getJSON("updateFORlogdo!PUBLIC",{id:forid},function(result){
							location.reload();
						})
					}
				})
			})
			//下一班次按钮
			$("#next_duty").click(function(){
				//是否已经是交接班时间
				var id = $(this).attr("now");
				$.getJSON("queryFORchange!PUBLIC",{id:id},function(result){
					if( result.ok ){
						//允许交接班,获取下一天的值班情况
						$.getJSON("queryFORdutybyid!PUBLIC",{id:id*1+1},function(json){
							var obj = json[0];
							var name = "";
							var id = "";
							name = obj.leader_name +";"+ obj.master_name +";"+ obj.member_name +";"+ obj.dirver_name;
							id = obj.leader + ";" + obj.master + ";" + obj.member + ";" + obj.dirver;
							PromptDialog_change("换班登入",$("#dialog_change").html(),"",name,id);
						})
					}else{
						alert(result.message)
					}
				})
			})
			//值班表查询
			$("#zbbcx").click(function(){
				PromptDialog_zbap("值班表查询",$("#dialog_zbap").html());			
			})
			$("#zbrzcx").click(function(){
				PromptDialog_zbrz("值班日志查询",$("#dialog_zbrzcx").html());		
			})
			$("#tongxunlu").click(function(){
				$.getJSON("queryFORtongxunlu!PUBLIC",function(src){	
					window.open(src.url);															
				})			
			})
		})

	querysyq();
	function querysyq(){
			//加载水雨情
		$.getJSON("getAlertInfo!PUBLIC",function(json){
				if( json.water == "0" ){
					$("#river").html("当前全市主要江河控制站水位均在警戒水位以下。");
				}else{
					$("#river_count").html(json.water);
				}
				if( json.rsvr == "0" ){
					$("#res").html("当前全市大中型水库水位均在警戒水位以下。");
				}else{
					$("#res_count").html(json.rsvr);
				}
				$("#rain_one").html(json.rain1);
				$("#rain_three").html(json.rain3);
				$("#rain_seven").html(json.rain7);
		})
	}
	//水雨情刷新
	show_syq();
	function show_syq(){
		setTimeout(function(){
			show_syq();
			querysyq();
		},5*60*1000);
	}
	//秒表刷新
	show_cur_times();
	showtime();
	function showtime(){
		setTimeout(function(){
			show_cur_times();
			showtime();
		},1000)
	}

	function PromptDialog_in(sTitle,PromptContent,iconName,man_name,man_id){
		$.dialog({
		    title: sTitle,
		    content:PromptContent,
		    icon:iconName,
		    zIndex:100,
		    lock:true,
		    ok: function(){
		    	var radio = $("input[name='leixing']");
		    	var type = "";
		    	$.each(radio,function(i,v){
		    		 if(  $(v)[0].checked ){
		    			 type = $(v).val();
		    		 }
		    	})
		    	if( type&&$("#password").val() ){
		    		var password = $.md5($("#password").val());
			    	var id = $("#next_duty").attr("now");
			    	//提交登陆
			    	$.ajax({
			    		dataType:"json",
			    		type: "get",
			            url:'updateFORdutyin!PUBLIC',
			            data:{
			            	id:id,
			            	man_name:man_name,
			            	type:type,
			            	password:password,
			            	man_id:man_id,
			            	stamp:new Date().getTime()
			            },
			            success:function(result){
			            	if( !result.ok ){
				    			alert(result.message);
				    			return false;
				    		}else{
				    			location.reload();
				    		}
			            }
			        })
			    	
			    	/*$.getJSON("updateFORdutyin!PUBLIC",{id:id,man_name:man_name,type:type,password:password,man_id:man_id},function(result){
			    		if( !result.ok ){
			    			alert(result.message);
			    			return false;
			    		}else{
			    			location.reload();
			    		}
			    	})*/
		    	}else{
		    		alert("请填写必要信息");
		    		return false;
		    	}		    	
		    },
		    cancel:function(){
				
		    }
		});
	};
	function PromptDialog_out(sTitle,PromptContent,iconName,man_name,man_id){
		$.dialog({
		    title: sTitle,
		    content:PromptContent,
		    icon:iconName,
		    zIndex:100,
		    lock:true,
		    ok: function(){
		    	var password = $.md5($("#password_out").val());
		    	var id = $("#next_duty").attr("now");
		    	$.getJSON("updateFORdutyout!PUBLIC",{id:id,man_name:man_name,password:password,man_id:man_id,stamp:new Date().getTime()},function(result){
		    		if( !result.ok ){
		    			alert(result.message);
		    			return false;
		    		}else{
		    			location.reload();
		    		}
		    	})
		    },
		    cancel:function(){
				
		    },
		    init:function(){
		    	$("#dialog_out_h3").html(man_name);
		    }
		});
	};
	function PromptDialog_zbap(sTitle,PromptContent){
		$.dialog({
		    title: sTitle,
		    content:PromptContent,
		    icon:"",
		    zIndex:100,
		    lock:true,
		    ok: function(){
		    	
		    },
		    init:function(){
		    	$.getJSON("queryFORzbbcx!PUBLIC",{stamp:new Date().getTime()},function(src){			
					 $("#iframe_zbap").attr("src",src.url);												
				})
		    }
		});
	};
	function PromptDialog_zbrz(sTitle,PromptContent){
		$.dialog({
		    title: sTitle,
		    content:PromptContent,
		    icon:"",
		    zIndex:100,
		    lock:true,
		    ok: function(){
		    	
		    },
		    init:function(){
		    	$("#Tm1").val( new Date().addDay(-7).format("yyyy-MM-dd hh:00") );
		    	$("#Tm2").val( new Date().addHour(1).format("yyyy-MM-dd hh:00") );
		    	queryforlogbytime();
		    	$("#btn_query_log").click(function(){
		    		queryforlogbytime();
		    	})
		    	function queryforlogbytime(){
		    		var time1 = $("#Tm1").val();
		    		var time2 = $("#Tm2").val();
		    		$.getJSON("queryforlogbytime!PUBLIC",{time1:time1,time2:time2},function(json){
		    			var html = "<table class='table table-bordered'>";
						html += "<tr><th>事件类型</th><th>事件内容</th><th>登记人</th><th>处理意见</th><th>时间</th></tr>";
						$.each(json,function(i,v){
							  html += "<tr><td>"+v.type+"</td><td>"+v.shijian+"</td><td>"+v.radio_n+"</td><td>"+v.yijian+"</td><td>"+v.time+"</td></tr>";
						})
						html+="</table>";
						$("#dialog_log_table").html(html);
		    		})
		    	}
		    }
		});
	};

	function PromptDialog_change(sTitle,PromptContent,iconName,name,id){
		$.dialog({
		    title: sTitle,
		    content:PromptContent,
		    icon:iconName,
		    zIndex:100,
		    lock:true,
		    ok: function(){
		    	//提交数据库
		    	var radio = $("input[name='next']");
		    	var id = ""
		    	var name = "";
		    	$.each(radio,function(i,v){
		    		 if(  $(v)[0].checked ){
		    			 id = $(v).val();
		    			 name =$(v).attr("forname");
		    		 }
		    	})
		    	var password = $.md5($("#jiaojieban").val());
		    	if( id&&password&&name ){
		    		var duty = $("#next_duty").attr("now")*1+1
		    		$.getJSON("updateFORdutychange!PUBLIC",{id:id,name:name,password:password,duty:duty,stamp:new Date().getTime()},function(result){
		    			if(result.ok){
		    				location.reload();
		    			}else{
		    				alert(result.message)
		    			}
		    		})
		    	}else{
		    		alert("请填写完整信息");
		    		return false;
		    	}
		    	
		    	
		    },
		    cancel:function(){
				
		    },
		    init:function(){
		    	var arr_name = name.split(";");
		    	var arr_id = id.split(";");
		    	var html = "";
		    	$.each(arr_name,function(i,v){
		    		if(v){
		    			html += "<input style='margin-right:12px;margin-top:12px' type='radio' name='next' forname='"+arr_name[i]+"' value='"+arr_id[i]+"'>" +v + "</br>";
		    		}
		    	})
		    	html += "</br><span style='margin-top:10px'>密码：</span><input  class='input' type='password' id='jiaojieban'> ";
		    	$("#next_day").html(html);
		    }
		});
	};
	function PromptDialog_log(sTitle,PromptContent,iconName,man_name,man_id){
		$.dialog({
		    title: sTitle,
		    content:PromptContent,
		    icon:iconName,
		    zIndex:100,
		    lock:true,
		    ok: function(){
		    	//提交日志，先判断是否填写完整
		    	var type = $("#sel_type").val();
		    	var shijian = $("#shijian").val();
		    	var radio_1 = $("input[name='do_not']");
		    	var radio_d = "";
		    	$.each(radio_1,function(i,v){
		    		 if(  $(v)[0].checked ){
		    			 radio_d = $(v).val();
		    		 }
		    	})
		    	var radio_2 = $("input[name='nn']");
		    	var radio_n = "";
		    	if( radio_2.length > 0){
		    		$.each(radio_2,function(i,v){
			    		 if(  $(v)[0].checked ){
			    			 radio_n = $(v).val();
			    		 }
			    	})
		    	}else{
		    		radio_n = $("#dengji").html();
		    	}
		    	var yijian = $("#yijian").val();
		    	var time = $("#log_time").html();	
		    	if( type&&shijian&&radio_d&&radio_n&&yijian&&time ){
		    		//提交数据库
		    		$.getJSON("updateFORlog!PUBLIC",{type:type,shijian:shijian,radio_d:radio_d,radio_n:radio_n,yijian:yijian,time:time},function(result){
		    			location.reload();
		    		})
		    	}else{
		    		alert("请填写完整消息");
		    		return false;
		    	}	    	
		    },
		    cancel:function(){
				
		    },
		    init:function(){
		    	//登记人，时间初始化
		    	if(man_name.indexOf(",")>=0){
		    		man_name=man_name.substring(0,man_name.length-1)
		    	}
		    	var arry_name = man_name.split(",");
		    	var html = "";
		    	if( arry_name.length > 1 ){
		    		//2人
		    		for(var i=0;i<=1;i++){
		    			html += "<input type='radio' name='nn' value='"+arry_name[i]+"' class='margin-left'>"+arry_name[i];
		    		}
		    	}else{
		    		html += "<span id='dengji' class='text-red'>"+man_name+"</span>";    		
		    	}
		    	$("#dengjiren").html(html);	
		    	$("#log_time").html(new Date().format("yyyy-MM-dd hh:mm"));
		    }
		});
	};
})

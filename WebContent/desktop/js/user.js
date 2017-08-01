WEBOS.user={
	apps:[],
	getapps:function(callback){
		$.getJSON('getApps!webos',function(json){
			//callback(json);
		});
		
	},
	getdesks:function(callback){
		$.getJSON('getDesks!webos',function(json){
			callback(json);
		});
	}
};
/**
 * @author chenchu
 * @date 2014 10 14
 * @info 用户登录信息
 */
define(["jquery"], function($) {
    return {
        checkUser: function() {
            var dtd = $.Deferred();
            $.getJSON("getUserInfo!user", {})
                .then(function(data) {
                    if (data && data.realname) {
                        data.isLogged = true;
                        dtd.resolve(data);
                    } else {
                    	dtd.resolve({
                    		isLogged: true,
                    		realname: ""
                    	})
//                        dtd.reject({
//                            isLogged: false,
//                            realname: ""
//                        });
                    }
                }, function(jqXHR, textStatus, error) {
                	dtd.resolve({
                		isLogged: true,
                		realname: ""
                	})
//                    dtd.reject({
//                        isLogged: false,
//                        realname: ""
//                    });
                });
            return dtd.promise();
        },
        logOut: function() {
            return $.getJSON("logout!login", {});
        }
    };
});
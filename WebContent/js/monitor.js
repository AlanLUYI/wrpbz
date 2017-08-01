/**
 * @author chenchu
 * @date 2015 4 28
 * @info 按照Bootstrap插件规范重构监控插件
 */

+(function(factory) {
	if (typeof define === "function" && define.amd) {
		define(["jquery"], factory);
	} else {
		factory(jQuery);
	}
})(function(jQuery) {
	"use strict";
	if (typeof jQuery === "undefined") {
		throw new Error("monitor requires jQuery");
	}

	+ (function($) {
		var Monitor = function(element, option) {
			this.$element = $(element);
			this.options = $.extend({}, Monitor.DEFAULTS, option);
			var isMonitoring = true;

			var callback = this.options.fun;
			if (typeof callback === "function") {
				this.timer = setInterval(function() {
					isMonitoring && callback();
				}, this.options.interval);
			}
			this.$element.addClass("monitor").attr("title", "已开启监控,点击关闭")
				.append("<div class='shineGreen'></div>")
				.on("click", function() {
					isMonitoring = !isMonitoring;
					$(this).toggleClass("off");
					$(this).attr("title", isMonitoring ? "已开启监控,点击关闭" : "已关闭监控,点击开启");
				});

		};

		Monitor.DEFAULTS = {
			fun: null,
			interval: 300 * 1000
		};

		Monitor.prototype.red = function() {
			this.$element.children("div").removeClass("shineGreen").addClass("shineRed");
		};

		Monitor.prototype.green = function() {
			this.$element.children("div").removeClass("shineRed").addClass("shineGreen");
		};

		Monitor.prototype.destroy = function() {
			clearInterval(this.timer);
			this.$element.empty();
		};

		function Plugin(option) {
			var allowedMethods = ["red", "green", "destroy"];

			return this.each(function() {
				var $this = $(this);
				var data = $this.data("cc.monitor");
				var options = typeof option === "object" && option;

				if (!data && option === "destroy") return;

				if (!data) {
					$this.data("cc.monitor", (data = new Monitor(this, options)));
				}
				if (typeof option === "string") {
					if ($.inArray(option, allowedMethods) < 0) {
						throw new Error("Unknown method: " + option);
					}
					data[option]();
				}

			});
		}

		var old = $.fn.monitor;

		$.fn.monitor = Plugin;
		$.fn.monitor.constructor = Monitor;

		$.fn.monitor.noConflict = function() {
			$.fn.monitor = old;
			return this;
		};

	})(jQuery);
});
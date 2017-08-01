/**
 * @author chenchu
 * @date 2015 4 30
 * @info 自定义Bootstrap Notify插件
 */
+(function (factory) {
	if (typeof define === "function" && define.amd) {
		define(["jquery"], factory);
	} else {
		factory(jQuery);
	}
})(function (jQuery) {
	"use strict";
	if (typeof jQuery === "undefined") {
		throw new Error("Notify requires jQuery");
	}
	(function ($) {
		var Notify = function (element, option) {
			this.$element = $(element);
			this.options = $.extend({}, Notify.DEFAULTS, option);
			this.$notification = $("<div class='alert'><i class='fa fa-check-circle' style='margin-right:5px;vertical-align: middle;font-size: 2em;'></i></div>");

			if (this.options.type) {
				this.$notification.addClass("alert-" + this.options.type);
			} else {
				this.$notification.addClass("alert-success");
			}

			if (this.$element.data("message")) {
				this.$notification.append(this.$element.data("message"));
			} else {
				this.$notification.append(this.options.message || "");
			}

			if (this.options.closeable) {
				var $close = $("<a class='close' href='#'>&times;</a>");
				$close.on("click", $.proxy(close, this));
				this.$notification.append($close);
			}
		}

		var close = function () {
			this.$notification.remove();
			this.options.onClosed();
			return false;
		}

		Notify.DEFAULTS = {
			type: "success",
			closeable: false,
			fadeOut: {
				enabled: true,
				delay: 3000
			},
			onClosed: function () {
				return false;
			},
			message: ""
		}

		Notify.prototype.show = function () {
			if (this.options.fadeOut.enabled) {
				this.$notification.delay(this.options.fadeOut.delay || 3000).fadeOut("slow", $.proxy(close, this));
			}
			this.$element.append(this.$notification);
		}

		Notify.prototype.hide = function () {
			if (this.options.fadeOut.enabled) {
				this.$notification.delay(this.options.fadeOut.delay || 3000).fadeOut("slow", $.proxy(close, this));
			} else {
				close.call(this);
			}
		}

		function Plugin(option) {
			var allowedMethods = ["show", "hide"];
			return this.each(function () {
				var $this = $(this);
				var data = $this.data("cc.notify");
				var options = typeof option === "object" && option;

				if (!data) {
					$this.data("cc.notify",(data = new Notify(this, options)));
				}
				if (typeof option === "string") {
					if ($.inArray(option, allowedMethods) < 0) {
						throw new Error("Unknown method: " + option);
					}
					data[option]();
				}
			});
		}

		var old = $.fn.notify;

		$.fn.notify = Plugin;
		$.fn.notify.constructor = Notify;

		$.fn.notify.noConflict = function () {
			$.fn.notify = old;
			return this;
		}

	})(jQuery);
});
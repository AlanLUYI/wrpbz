/**
 * @author chenchu
 * @date 2015 1 4
 * @description grunt init config
 */
module.exports = function(grunt){
	"use strict";

	grunt.initConfig({
		pkg: grunt.file.readJSON("package.json"),
		concat: {
			options: {
				separator: ";",
				stripBanners: true,
				banner: '/*! <%= pkg.name %> - v<%= pkg.version %> - ' +
        			'<%= grunt.template.today("yyyy-mm-dd") %> */\n'
			},
			dist: {
				src: [
						"WebContent/modules/sys-config/LogManager.js",
						"WebContent/modules/sys-config/RoleManager.js",
						"WebContent/modules/sys-config/UserManager.js"
					],
				dest: "WebContent/modules/sys-config/Manager.js"
			}
		},
		copy: {
			main: {
				files: [{
				expand: true,
				cwd: "WebContent/js/templates/",
				src: "*",
				dest: "WebContent/dist/js/templates/",
				filter: "isFile"
			},{
				expand: true,
				cwd: "WebContent/js/htwater/",
				src: ["css/*", "images/*", "templates/*"],
				dest: "WebContent/dist/js/htwater/"
			}]
			},
			module_target: {
				files: [{
					expand: true,
					cwd: "WebContent/modules",
					src: "**/*",
					dest: "WebContent/dist/modules",
					filter: "isFile"
				}]
			}
		},
		cssmin: {
			module_target: {
				files: [{
					expand: true,
					cwd: "WebContent/modules",
					src: "**/*.css",
					dest: "WebContent/dist/modules/",
					ext: ".css"
				}]
			}	
		},
		uglify: {
			options: {
				//banner: "/*! <%= pkg.name %> <%= grunt.template.today('yyyy-mm-dd') %> */\n",
				mangle: true,
				compress: {
					drop_console: true
				}
			},
			my_target: {
				files: [{
					expand: true,
					flatten: true,
					cwd: "WebContent/js",
					src: "*.js",
					dest: "WebContent/dist/js/",
					ext: ".js"
				},{
					expand: true,
					flatten: true,
					cwd: "WebContent/js/htwater",
					src: "*.js",
					dest: "WebContent/dist/js/htwater/",
					ext: ".js"
				}]
			},
			another_target: {
				files: [{
					expand: true,
					flatter: true,
					src: "<%= concat.dist.dest %>",
					dest: "./",
					ext: ".min.js"
				}]
			},
			module_target: {
				files: [{
					expand: true,
					cwd: "WebContent/modules",
					src: "**/*.js",
					dest: "WebContent/dist/modules",
					ext: ".js"
				}]
			}
		}
	});

	grunt.loadNpmTasks("grunt-contrib-concat");
	grunt.loadNpmTasks("grunt-contrib-uglify");
	grunt.loadNpmTasks("grunt-contrib-cssmin");
	grunt.loadNpmTasks("grunt-contrib-copy");

	grunt.registerTask("default",["uglify:my_target"]);
	grunt.registerTask("concat-uglify",["concat","uglify:another_target"]);
	grunt.registerTask("module-uglify",["copy:module_target", "uglify:module_target", "cssmin:module_target"]);
	grunt.registerTask("copy-files",["copy:main"]);
	
};
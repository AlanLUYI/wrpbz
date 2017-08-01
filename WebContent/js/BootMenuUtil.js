/**
 * @author chenchu
 * @date 2015 1 19
 * @description menu utility
 */
define([], function() {
    "use strict";
    return {
        getMenuById: function(id, hierarchyArray) {
            if (!(id && hierarchyArray && hierarchyArray.length)) {
                return null;
            }
            var item, temp;
            for (var i = 0; i < hierarchyArray.length; i++) {
                item = hierarchyArray[i];
                if (item.id == id) {
                    return item;
                } else {
                    if (item.children && item.children.length) {
                        temp = this.getMenuById(id, item.children);
                        if (temp != null) {
                            return temp;
                        }
                    }
                }
            }
            return null;
        },
        getTopMenu: function(id, hierarchyArray) {
            if (!(id && hierarchyArray && hierarchyArray.length)) {
                return null;
            }
            var node, temp = id;
            while (true) {
                node = this.getMenuById(id, hierarchyArray);
                if (node) {
                    temp = id;
                    id = node.pid;
                } else {
                    return this.getMenuById(temp, hierarchyArray);
                }
            }
        }
    };
});
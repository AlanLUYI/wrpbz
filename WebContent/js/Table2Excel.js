//zzj 2015.3.16 表格转excel文件
define([], function() {
    return {
        tableToExcel: function(table, sheetname, filename) {
            var uri = 'data:application/vnd.ms-excel;base64,',
                template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>',
                base64 = function(s) {
                    return window.btoa(unescape(encodeURIComponent(s)));
                },
                format = function(s, c) {
                    return s.replace(/{(\w+)}/g, function(m, p) {
                        return c[p];
                    });
                };

            if (!table.nodeType)
                table = document.getElementById(table);
            var ctx = {
                worksheet: sheetname || 'Worksheet',
                table: table.innerHTML
            };
            var el = document.getElementById("table2excel");
            if (!el) {
                el = document.createElement("a");
                el.id = "table2excel";
                el.style = "display:none;";
                document.body.appendChild(el);
            }
            el.href = uri + base64(format(template, ctx));
            el.download = filename;
            el.click();
        }
    }
});
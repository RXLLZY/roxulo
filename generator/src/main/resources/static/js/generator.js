$(function () {
    $("#jqGrid").jqGrid({
        url: 'sys/generator/list',
        datatype: "json",
        cellEdit: true,
        cellsubmit: "clientArray",
        colModel: [
            {label: '表名', name: 'tableName', width: 100, key: true},
            {label: 'Engine', name: 'engine', width: 70},
            {
                label: '表备注',
                name: 'tableComment',
                width: 100,
                editable: true,
                edittype: 'text',
                editrules: {required: true}
            },
            {
                label: '模块路径',
                name: 'moduleName',
                width: 100,
                editable: true,
                edittype: 'text',
                editrules: {required: true}
            },
            {label: '表前缀', name: 'tablePrefix', width: 100, editable: true, edittype: 'text'},
            {label: '创建时间', name: 'createTime', width: 100}
        ],
        viewrecords: true,
        height: 385,
        rowNum: 10,
        rowList: [10, 30, 50, 100, 200],
        rownumbers: true,
        rownumWidth: 25,
        autowidth: true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader: {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order"
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });
});

var vm = new Vue({
    el: '#rrapp',
    data: {
        q: {
            tableName: null
        }
    },
    methods: {
        query: function () {
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {'tableName': vm.q.tableName},
                page: 1
            }).trigger("reloadGrid");
        },
        generator: function () {
            var tables = getSelectedRows();
            if (tables == null) {
                return;
            }
            $.post("sys/generator/code", JSON.stringify(tables), function (data) {
                alert(data.message)
            })
        },
        zipExport: function () {
            var tables = getSelectedRows();
            if (tables == null) {
                return;
            }
            var xhr = new XMLHttpRequest();
            xhr.open("post", "sys/generator/export", true);
            xhr.setRequestHeader("Content-Type", "application/json");
            xhr.responseType = "blob";
            xhr.onload = function() {
                if (this.status == 200) {
                    var content = xhr.response;
                    var elink = document.createElement('a');
                    elink.download = "code.zip";
                    elink.style.display = 'none';
                    var blob = new Blob([content]);
                    elink.href = URL.createObjectURL(blob);
                    document.body.appendChild(elink);
                    elink.click();
                    document.body.removeChild(elink);
                }
            }
            xhr.send(JSON.stringify(tables))
        },
        deleteCode: function () {
            var tables = getSelectedTableRows();
            if (tables == null) {
                return;
            }
            $.post("sys/generator/deleteCode", JSON.stringify(tables), function (data) {
                alert(data.message)
            })
        }
    }
});


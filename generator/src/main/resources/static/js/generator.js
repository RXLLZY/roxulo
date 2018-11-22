$(function () {
    $("#jqGrid").jqGrid({
        url: 'sys/generator/list',
        datatype: "json",
        cellEdit:true,
        cellsubmit: "clientArray",
        colModel: [			
			{ label: '表名', name: 'tableName', width: 100, key:true},
			{ label: 'Engine', name: 'engine', width: 70},
			{ label: '表备注(可编辑)', name: 'tableComment', width: 100, editable: true, edittype:'text'},
			{ label: '创建时间', name: 'createTime', width: 100 }
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50,100,200],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		q:{
			tableName: null
		}
	},
	methods: {
		query: function () {
			$("#jqGrid").jqGrid('setGridParam',{ 
                postData:{'tableName': vm.q.tableName},
                page:1 
            }).trigger("reloadGrid");
		},
        generator: function() {
            var tableNames = getSelectedRows();
            if(tableNames == null){
                return ;
            }
            $.get("sys/generator/code?tables=" + tableNames.join(),function (data) {
                alert(data.message)
            })
        },
        zipExport: function() {
            var tableNames = getSelectedRows();
            if(tableNames == null){
                return ;
            }
            location.href = "sys/generator/export?tables=" + tableNames.join();
        },
        deleteCode: function() {
            var tableNames = getSelectedTableRows();
            if(tableNames == null){
                return ;
            }
            $.get("sys/generator/deleteCode?tables=" + tableNames.join(),function (data) {
                alert(data.message)
            })
        }
	}
});


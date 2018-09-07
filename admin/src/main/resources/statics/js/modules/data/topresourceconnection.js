$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'data/topresourceconnection',
        datatype: "json",
        colModel: [			
			{ label: 'connectionId', name: 'connectionId', index: 'connection_id', width: 50, key: true },
			{ label: '数据源名称', name: 'name', index: 'name', width: 80 }, 			
			{ label: '数据源类型(MYSQL、ORACLE等，建议大写)', name: 'type', index: 'type', width: 80 }, 			
			{ label: '数据库名称', name: 'database', index: 'database', width: 80 }, 			
			{ label: '主机ip', name: 'serverIp', index: 'server_ip', width: 80 }, 			
			{ label: '数据库端口号', name: 'port', index: 'port', width: 80 }, 			
			{ label: '用户名', name: 'username', index: 'username', width: 80 }, 			
			{ label: '密码', name: 'password', index: 'password', width: 80 }, 			
			{ label: '来源', name: 'source', index: 'source', width: 80 }			
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
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
		showList: true,
		title: null,
		topResourceConnection: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.topResourceConnection = {};
		},
		update: function (event) {
			var connectionId = getSelectedRow();
			if(connectionId == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(connectionId)
		},
		saveOrUpdate: function (event) {
			var type = vm.topResourceConnection.connectionId == null ? "POST" : "PUT";
			$.ajax({
				type: type,
			    url: baseURL + "data/topresourceconnection" ,
                contentType: "application/json",
			    data: JSON.stringify(vm.topResourceConnection),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(index){
							vm.reload();
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},
		del: function (event) {
			var connectionIds = getSelectedRows();
			if(connectionIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "DELETE",
				    url: baseURL + "data/topresourceconnection",
                    contentType: "application/json",
				    data: JSON.stringify(connectionIds),
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(index){
								$("#jqGrid").trigger("reloadGrid");
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		getInfo: function(connectionId){
			$.get(baseURL + "data/topresourceconnection/"+connectionId, function(r){
                vm.topResourceConnection = r.topResourceConnection;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		}
	}
});
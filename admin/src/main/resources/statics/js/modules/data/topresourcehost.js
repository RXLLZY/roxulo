$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'data/topresourcehost',
        datatype: "json",
        colModel: [			
			{ label: 'hostId', name: 'hostId', index: 'host_id', width: 50, key: true },
			{ label: '启用的主机IP地址', name: 'host', index: 'host', width: 80 }, 			
			{ label: '启用的端口号', name: 'port', index: 'port', width: 80 }, 			
			{ label: 'kide所在位置', name: 'kideHome', index: 'kide_home', width: 80 }, 			
			{ label: 'job脚本位置', name: 'kideJobPath', index: 'kide_job_path', width: 80 }, 			
			{ label: '增量更新、查询、备用', name: 'serverType', index: 'server_type', width: 80 }, 			
			{ label: 'IDLE=1,RUN=2,INVALID=3', name: 'status', index: 'status', width: 80 }			
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
		topResourceHost: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.topResourceHost = {};
		},
		update: function (event) {
			var hostId = getSelectedRow();
			if(hostId == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(hostId)
		},
		saveOrUpdate: function (event) {
			var type = vm.topResourceHost.hostId == null ? "POST" : "PUT";
			$.ajax({
				type: type,
			    url: baseURL + "data/topresourcehost" ,
                contentType: "application/json",
			    data: JSON.stringify(vm.topResourceHost),
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
			var hostIds = getSelectedRows();
			if(hostIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "DELETE",
				    url: baseURL + "data/topresourcehost",
                    contentType: "application/json",
				    data: JSON.stringify(hostIds),
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
		getInfo: function(hostId){
			$.get(baseURL + "data/topresourcehost/"+hostId, function(r){
                vm.topResourceHost = r.topResourceHost;
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
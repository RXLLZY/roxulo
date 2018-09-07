$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'data/topresourcefield/list',
        datatype: "json",
        colModel: [			
			{ label: 'resourceId', name: 'resourceId', index: 'resource_id', width: 50, key: true },
			{ label: '资源表名称', name: 'resourceName', index: 'resource_name', width: 80 }, 			
			{ label: '资源表英文名', name: 'fieldEn', index: 'field_en', width: 80 }, 			
			{ label: '资源表中文名', name: 'fieldCn', index: 'field_cn', width: 80 }, 			
			{ label: '资源表字段备注', name: 'fieldRemark', index: 'field_remark', width: 80 }, 			
			{ label: '是否展示该字段', name: 'fieldShow', index: 'field_show', width: 80 }, 			
			{ label: '字段类型（字符、日期等）', name: 'fieldType', index: 'field_type', width: 80 }, 			
			{ label: '主键、更新字段、普通字段标识', name: 'fieldClass', index: 'field_class', width: 80 }			
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
		topResourceField: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.topResourceField = {};
		},
		update: function (event) {
			var resourceId = getSelectedRow();
			if(resourceId == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(resourceId)
		},
		saveOrUpdate: function (event) {
			var type = vm.topResourceField.resourceId == null ? "POST" : "PUT";
			$.ajax({
				type: type,
			    url: baseURL + "data/topresourcefield" ,
                contentType: "application/json",
			    data: JSON.stringify(vm.topResourceField),
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
			var resourceIds = getSelectedRows();
			if(resourceIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "DELETE",
				    url: baseURL + "data/topresourcefield",
                    contentType: "application/json",
				    data: JSON.stringify(resourceIds),
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
		getInfo: function(resourceId){
			$.get(baseURL + "data/topresourcefield/info/"+resourceId, function(r){
                vm.topResourceField = r.topResourceField;
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
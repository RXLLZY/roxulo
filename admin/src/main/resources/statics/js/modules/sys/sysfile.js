$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/file',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '原始名称', name: 'originalName', index: 'original_name', width: 80 }, 			
			{ label: '文件路径', name: 'path', index: 'path', width: 80 }, 			
			{ label: '文件类型', name: 'contentType', index: 'content_type', width: 80 }, 			
			{ label: '文件大小', name: 'size', index: 'size', width: 80 }, 			
			{ label: '描述', name: 'description', index: 'description', width: 80 }, 			
			{ label: '创建者', name: 'crtUserId', index: 'crt_user_id', width: 80 }, 			
			{ label: '创建时间', name: 'crtTime', index: 'crt_time', width: 80 },
            { label: '预览', name: 'path', index: 'path', width: 80 ,classes:"avatar", formatter:function(cellvalue, options, rowObject){;
                    var contentType = rowObject.contentType;
                    var url = baseURL + cellvalue;
                    var html;
                    if(contentType.indexOf("image") > -1){
                        html = '<img src="' + url + '" style = "height: 20px;">';
                    }else if(contentType.indexOf("auto") > -1){
                        html = '<audio src="' + url + '" controls>您的浏览器不支持 audio 元素。</audio>';
                    }else if(contentType.indexOf("video") > -1){
                        html = '<video src="' + url + '" controls="controls" width="60" height="40">\n' +
                            'rowObject.originalName\n' +
                            '</video>';
                    }else if(contentType.indexOf("application") > -1){
                        html = '<a href="' + url + '" class="tit" target="_blank"> rowObject.originalName</a>';
                    }else{
                        html = '<a href="' + url + '" class="tit" target="_blank"> rowObject.originalName</a>';
                    }
                    return html;
                }}
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
            root: "result.list",
            page: "result.currPage",
            total: "result.totalPage",
            records: "result.totalCount"
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

    new AjaxUpload('#upload', {
        action: baseURL + "sys/file/upload",
        name: 'file',
        autoSubmit:true,
        responseType:"json",
        onSubmit:function(file, extension){
            // if (!(extension && /^(jpg|jpeg|png|gif)$/.test(extension.toLowerCase()))){
            //     alert('只支持jpg、png、gif格式的图片！');
            //     return false;
            // }
        },
        onComplete : function(file, r){
            if(r.status == 200){
                alert(r.result.originalName + "上传成功");
                vm.sysFile.originalName = r.result.originalName;
                vm.sysFile.contentType = r.result.contentType;
                vm.sysFile.path = r.result.path;
                vm.sysFile.size = r.result.size;
            }else{
                alert(r.message);
            }
        }
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
        q:{
            originalName: null
        },
		showList: true,
		title: null,
		sysFile: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.sysFile = {};
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(id)
		},
		saveOrUpdate: function (event) {
			if(vm.sysFile.id == null) {
                $.ajax({
                    type: "POST",
                    url: baseURL + "sys/file",
                    contentType: "application/json",
                    data: JSON.stringify(vm.sysFile),
                    success: function (r) {
                            alert('操作成功', function (index) {
                                vm.reload();
                            });
                    },
                    error:function(r){
                        alert(r.responseJSON.message);
                    }
                });
            }else{
                $.ajax({
                    type: "PUT",
                    url: baseURL + "sys/file/" + vm.sysFile.id,
                    contentType: "application/json",
                    data: JSON.stringify(vm.sysFile),
                    success: function (r) {
                        alert('操作成功', function (index) {
                            vm.reload();
                        });
                    },
                    error:function(r){
                        alert(r.responseJSON.message);
                    }
                });
			}
		},
		del: function (event) {
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "DELETE",
				    url: baseURL + "sys/file",
                    contentType: "application/json",
				    data: JSON.stringify(ids),
				    success: function(r){
                        alert('操作成功', function(index){
                            $("#jqGrid").trigger("reloadGrid");
                        });
					},
                    error:function(r){
                        alert(r.responseJSON.message);
                    }
				});
			});
		},
		getInfo: function(id){
			$.get(baseURL + "sys/file/"+id, function(r){
                vm.sysFile = r.result;
                console.log(r.sysFile)
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{
                postData:{'originalName': vm.q.originalName},
                page:page
            }).trigger("reloadGrid");
		}
	}
});
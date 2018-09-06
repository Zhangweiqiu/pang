$(function () {
    //1.初始化Table
    var oTable = new TableInit();
    oTable.Init();

});

var TableInit = function () {
    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $('#taskTable').bootstrapTable({
            url: '/admin?method=seeAdmin',
            method: 'get',                      //请求方式（*）
            toolbar: '#toolbar',                //工具按钮用哪个容器
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   //是否显示分页（*）
            sortable: false,                     //是否启用排序
            sortOrder: "asc",                   //排序方式
            queryParamsType:"limit",
            queryParams: oTableInit.queryParams,//传递参数（*）
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            pageNumber:1,                       //初始化加载第一页，默认第一页
            pageSize: 10,                     //每页的记录行数（*）
            pageList: [10,20,50,100],        //可供选择的每页的行数（*）
            search: false,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
            strictSearch: false,
            showColumns: true,                  //是否显示所有的列
            showRefresh: true,                  //是否显示刷新按钮
            minimumCountColumns: 2,             //最少允许的列数
            clickToSelect: true,                //是否启用点击选中行
            height: 500,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId: "uid",                     //每一行的唯一标识，一般为主键列
            showToggle:true,                    //是否显示详细视图和列表视图的切换按钮
            cardView: false,                    //是否显示详细视图
            detailView: false,                   //是否显示父子表onEditableSave
            // showExport: true,                     //是否显示导出
            // exportDataType: "basic",              //basic', 'all', 'selected'.
            columns: [{
                checkbox: true
            }, {
                field: 'kid',
                align: 'center',
                title: '编号',

            }, {
                field: 'kname',
                align: 'center',
                title: '姓名'

            },{
                field: 'operate',
                title: '操作',
                align: 'center',
                formatter: operateFormatter
            }
            ],
            onDblClickRow:function (row) {
                alert(row.uid);
            }
        });
    };
    //得到查询的参数
    oTableInit.queryParams = function (params) {
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            limit: params.limit,   //页面大小
            offset: (params.offset / params.limit) ,
            role:'管理员',
        };
        return temp;
    };
    return oTableInit;
}

function operateFormatter(value, row, index) {
    // return [
    //     '<a class="like" href="javascript:void(0)" title="Like">',
    //     '<i class="fa fa-pencil-square-o"></i>',
    //     '</a>  ',
    //     '<a class="remove" href="javascript:void(0)" title="Remove">',
    //     '<i class="glyphicon glyphicon-remove"></i>',
    //     '</a>'].join('');
    var id = row.ucount;
    var result = "";
//    result += "<a href='javascript:;'  onclick=\"EditAdminById('" + id + "')\" title='编辑'><span class='glyphicon glyphicon-pencil'></span></a> &nbsp;| &nbsp;";
    result += "<a href='javascript:;'  onclick=\"DeleteAdminByIds('" + id + "')\" title='删除'><span class='glyphicon glyphicon-remove'></span></a>";

    return result;
}


function EditAdminById(id) {
    $.ajax({
        url:'/admin?method=myinfo',
        type:'get',
        dataType:'json',
        data:{mycount:id},
        success:function (data) {
            $("#tl").val(data.uid);
            $("#tn").val(data.ucount);
            $("#ts").val(data.uname);
            $("#tp").val(data.role);
        }
    });
    $('#myModal').modal('show');
}

function  DeleteAdminByIds(value) {
    swal({
            title: "确定删除吗？",
            text: "你将无法恢复该该条数据！",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "确定删除！",
            cancelButtonText: "取消删除！",
            closeOnConfirm: false,
            closeOnCancel: false
        },
        function(isConfirm){
            if (isConfirm) {
                $.ajax({
                    url: '/admin?method=deleteAdmin',
                    type: 'get',
                    dataType: 'json',
                    data: {mycount: value},
                    success: function (data) {
                        if (data) {
                            swal("删除！", "你的这条数据已被删除已经被删除。", "success");
                            $("#taskTable").bootstrapTable('refresh');
                        }

                    }
                });
            } else {
                swal("取消！", "你的这条数据是安全的:)",
                    "error");
            }
        });
}

$('#changTask').click(function () {
    var mycount = $("#tn").val();
    var role = $("#tp").val();
    $.ajax({
        url:'/admin?method=changeInfo',
        type:'get',
        dataType:'json',
        data:{mycount:mycount,role:role},
        success:function (data) {
            if (data) {
                $('#myModal').modal('hide');
                $("#taskTable").bootstrapTable('refresh');
            }
        }
    });
});
var option = "";
var nu = "==暂无可选客服==";
$(function () {
    $.ajax({
        url:"/getKefus",
        type:"get",
        data:{},
        dataType:'Json',
        success:function (data) {
            option ="<option value = '" + "0"+"'>" + "====请选择客服===="+"</option>";
            if (data.leng > 0) {
                for (var i = 0 ; i < data.leng; i++){
                    option += "<option value = '" + data.list[i].kid+"'>" + data.list[i].kname +"</option>";
                }
            }else {
                option ="<option value = '" + "0"+"'>" + nu+"</option>";
            }
            $("#msgType").append(option);
        }
    });


    var t = $("#msgType").val();
    var t = $("#msgType").val();
    if (t == null || t == "")
        t = 0;
    $.ajax({
        url:"/showMoneny",
        type:"get",
        data:{kefu:t,days:$("#msgTypes").val()},
        dataType:"json",
        success:function (data) {
            $("#totl").text(data);
        }
    })

    //1.初始化Table
    var oTable = new TableInit();
    oTable.Init();

});

var TableInit = function () {
    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $('#taskTable').bootstrapTable({
            url: '/showMyPayList',
            contentType: "application/x-www-form-urlencoded",
            method: 'post',                      //请求方式（*）
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
            uniqueId: "tid",                     //每一行的唯一标识，一般为主键列
            showToggle:true,                    //是否显示详细视图和列表视图的切换按钮
            cardView: false,                    //是否显示详细视图
            detailView: false,                   //是否显示父子表onEditableSave
            // showExport: true,                     //是否显示导出
            // exportDataType: "basic",              //basic', 'all', 'selected'.
            columns: [{
                checkbox: true
            }, {
                field: 'pid',
                align: 'center',
                title: '编号',
                visible: false

            }, {
                field: 'name',
                align: 'center',
                title: '名字'

            }, {
                field: 'accessPayNo',
                title: '接入商订单号',
                align: 'center'
            }, {
                field: 'payNo',
                title: '平台订单号',
                align: 'center'
            }, {
                field: 'tradeAmt',
                align: 'center',
                title: '订单金额'

            }, {
                field: 'actualAmt',
                align: 'center',
                title: '实际金额'

            }, {
                field: 'payTime',
                title: '支付时间',
                align: 'center',
                formatter: function (value, row, index) {
                    return changeDateFormat(value)
                }
            }, {
                field: 'goodsName',
                title: '商品名称',
                align: 'center'
            }, {
                field: 'kefu',
                title: '客服',
                align: 'center'
            }
            ],
            onDblClickRow:function (row) {
                alert(row.tid);
            }
        });
    };


    //得到查询的参数
    oTableInit.queryParams = function (params) {
        var t = $("#msgType").val();
        if (t == null || t == "")
            t = 0;
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            limit: params.limit,   //页面大小
            offset: (params.offset / params.limit) ,
            kefu:t,
            days:$("#msgTypes").val(),
        };
        return temp;
    };
    return oTableInit;
}



function about() {
    var t = $("#msgType").val();
    if (t == null || t == "")
        t = 0;
    $.ajax({
        url:"/showMoneny",
        type:"get",
        data:{kefu:t,days:$("#msgTypes").val()},
        dataType:"json",
        success:function (data) {
            $("#totl").text(data);
        }
    })
    $("#taskTable").bootstrapTable('refresh');
}



//转换日期格式(时间戳转换为datetime格式)
function changeDateFormat(cellval) {
    var dateVal = cellval + "";
    if (cellval != null) {
        var date = new Date(parseInt(dateVal.replace("/Date(", "").replace(")/", ""), 10));
        var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
        var currentDate = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();

        var hours = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
        var minutes = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
        var seconds = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();

        return date.getFullYear() + "-" + month + "-" + currentDate + " " + hours + ":" + minutes + ":" + seconds;
    }
}


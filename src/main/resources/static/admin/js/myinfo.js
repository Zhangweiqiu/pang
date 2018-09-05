$(function () {
    var mycount = sessionStorage.getItem("mycount");
    $.ajax({
        url:"/admin?method=myinfo",
        type:'get',
        data:{mycount:mycount},
        dataType:'Json',
        success:function (result) {
            $("#mycount").val(result.ucount);
            $("#mynames").val(result.uname);
        }
    })
})


function changeInfo() {
    var mc = $("#mycount").val();
    $.ajax({
        url:'/modifyName',
        type:'post',
        data:{count:mc},
        dataType:'Json',
        success:function (result) {
            if (result) {
                alert('修改成功');
            }
            else
                alert('修改失败！！！');

        }
    })
}
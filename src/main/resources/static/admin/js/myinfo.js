

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
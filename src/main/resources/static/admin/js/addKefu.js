
function changeInfo() {
    var mynames = $("#mynames").val();
    $.ajax({
        url:'/addkefu',
        type:'post',
        data:{name:mynames},
        dataType:'Json',
        success:function (result) {
            if (result) {
                alert('添加成功');
            }
            else
                alert('添加失败！！！');

        }
    })
}

function changeInfo() {
    var mc = $("#mycount").val();
    var mynames = $("#mynames").val();
    $.ajax({
        url:'/addAdmin',
        type:'post',
        data:{name:mynames,aid:mc},
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
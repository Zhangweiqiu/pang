
function changeInfo() {
    var mc = $("#mycount").val();
    var mynames = $("#mynames").val();


    $.ajax({
        url: '/ifexsit',
        type: 'post',
        data: {ucount:mc},
        dataType: 'Json',
        success:function (data) {
            if (data){
                alert("该账户已存在");
                return false;
            }
        }
    });


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
$(function () {

    var apert = sessionStorage.getItem('apart');
    var myname = sessionStorage.getItem('myname');
    var mycount = sessionStorage.getItem('mycount');
    if (myname ==null&& mycount == null) {
        window.location.href = "../../index.html";
    }
    document.getElementById("apart").innerHTML = '上一次登录时间: '+apert +' hours ago';
    document.getElementById("myname").innerHTML = 'Hello ' + myname;
    $("#containers").load('./Mychart.html');
});
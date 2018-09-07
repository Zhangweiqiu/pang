$(function () {

    var role = sessionStorage.getItem('role');
    if (role == null || role == "") {
        window.location.href = "../../admin.html";
    }else if (role == "super") {
            $("#admin").show();
            $("#tti").show();
    }
    document.getElementById("myname").innerHTML = 'Hello ' + role;
});
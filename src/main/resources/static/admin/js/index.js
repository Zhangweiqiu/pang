$(function () {

    var role = sessionStorage.getItem('role');
    if (role ==null&& role == "") {
        window.location.href = "../../index.html";
    }else if (role == "super") {
            $("#admin").show();
    }
    document.getElementById("myname").innerHTML = 'Hello ' + role;
});
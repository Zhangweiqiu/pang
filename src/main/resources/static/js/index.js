//设置一个全局的变量，便于保存验证码
var code;
function createCode(){
    //首先默认code为空字符串
    code = '';
    //设置长度，这里看需求，我这里设置了4
    var codeLength = 4;
    var codeV = document.getElementById('code');
    //设置随机字符
    var random = new Array(0,1,2,3,4,5,6,7,8,9,'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R', 'S','T','U','V','W','X','Y','Z');
    //循环codeLength 我设置的4就是循环4次
    for(var i = 0; i < codeLength; i++){
        //设置随机数范围,这设置为0 ~ 36
        var index = Math.floor(Math.random()*36);
        //字符串拼接 将每次随机的字符 进行拼接
        code += random[index];
    }
    //将拼接好的字符串赋值给展示的Value
    codeV.value = code;
}

//下面就是判断是否== 的代码，无需解释
function validate(){

}

//设置此处的原因是每次进入界面展示一个随机的验证码，不设置则为空
window.onload = function (){

    createCode();
}


function checkUser() {

    var aid = document.getElementById("acount").value;
    var password = document.getElementById("apassword").value;
    if(aid == ""){
        swal("我想想", "哦，天哪！你账号忘记填了","error");
        return false;
    }
    if(password=="" ){
        swal({
            title: "忘记了",
            text: "输入密码",
            timer: 2000,
            showConfirmButton: false
        });
        return false;
    }
    var oValue = document.getElementById('ayzm').value;
    if(oValue ==0){
        swal("验证码", "这东西不能空呀","error");
        $("ayzm").val("");
        return false;
    }else if(oValue != code.toLowerCase()){
        swal("验证码", "这东西都可以错吗","error");
        $("ayzm").val("");
        oValue = ' ';
        createCode();
        return false;
    }
    $.ajax({
        url:'/login',
        type:'post',
        data: {aid:aid, password:password},
        dataType:'json',
        success:function (data) {
            if(data.states){
                sessionStorage.setItem("role",data.role);
                    window.location.href = "/admin/index.html";
            }else{
                swal({
                    title:'错误信息',
                    text:data.msg,
                    type:'error',
                    timer:2000,
                    showConfirmButton: false
                });
            }
        }
    });
}




$(function(){

    $('#switch_qlogin').click(function(){
        $('#switch_login').removeClass("switch_btn_focus").addClass('switch_btn');
        $('#switch_qlogin').removeClass("switch_btn").addClass('switch_btn_focus');
        $('#switch_bottom').animate({left:'0px',width:'70px'});
        $('#qlogin').css('display','none');
        $('#web_qr_login').css('display','block');

    });
    $('#switch_login').click(function(){

        $('#switch_login').removeClass("switch_btn").addClass('switch_btn_focus');
        $('#switch_qlogin').removeClass("switch_btn_focus").addClass('switch_btn');
        $('#switch_bottom').animate({left:'154px',width:'70px'});

        $('#qlogin').css('display','block');
        $('#web_qr_login').css('display','none');
    });
    if(getParam("a")=='0')
    {
        $('#switch_login').trigger('click');
    }

});

function logintab(){
    scrollTo(0);
    $('#switch_qlogin').removeClass("switch_btn_focus").addClass('switch_btn');
    $('#switch_login').removeClass("switch_btn").addClass('switch_btn_focus');
    $('#switch_bottom').animate({left:'154px',width:'96px'});
    $('#qlogin').css('display','none');
    $('#web_qr_login').css('display','block');

}


//根据参数名获得该参数 pname等于想要的参数名
function getParam(pname) {
    var params = location.search.substr(1); // 获取参数 平且去掉？
    var ArrParam = params.split('&');
    if (ArrParam.length == 1) {
        //只有一个参数的情况
        return params.split('=')[1];
    }
    else {
        //多个参数参数的情况
        for (var i = 0; i < ArrParam.length; i++) {
            if (ArrParam[i].split('=')[0] == pname) {
                return ArrParam[i].split('=')[1];
            }
        }
    }
}


    pwdmin = 6;

$(document).ready(function() {


    $('#reg').click(function() {

        if ($('#user').val() == "") {
            $('#user').focus().css({
                border: "1px solid red",
                boxShadow: "0 0 2px red"
            });
            $('#userCue').html("<font color='red'><b>×用户名不能为空</b></font>");
            return false;
        }



        if ($('#user').val().length < 3 || $('#user').val().length > 16) {

            $('#user').focus().css({
                border: "1px solid red",
                boxShadow: "0 0 2px red"
            });
            $('#userCue').html("<font color='red'><b>×用户名位3-16字符</b></font>");
            return false;

        }



        if ($('#passwd').val().length < pwdmin) {
            $('#passwd').focus();
            $('#userCue').html("<font color='red'><b>×密码不能小于" + pwdmin + "位</b></font>");
            return false;
        }
        if ($('#passwd2').val() != $('#passwd').val()) {
            $('#passwd2').focus();
            $('#userCue').html("<font color='red'><b>×两次密码不一致！</b></font>");
            return false;
        }

        var sqq = /^[1-9]{1}[0-9]{4,9}$/;
        if (!sqq.test($('#qq').val()) || $('#qq').val().length < 5 || $('#qq').val().length > 12) {
            $('#qq').focus().css({
                border: "1px solid red",
                boxShadow: "0 0 2px red"
            });
            $('#userCue').html("<font color='red'><b>×QQ号码格式不正确</b></font>");return false;
        } else {
            $('#qq').css({
                border: "1px solid #D7D7D7",
                boxShadow: "none"
            });

        }


        $.ajax({

            url: "/admin?method=register",
            type: 'post',
            data: {account:$('#qq').val(),password:$('#passwd').val(),name:$('#user').val()},
            dataType: 'Json',
            success: function(result) {

                if (result.state) {
                    swal("注册成功", "快去登录吧", "success");
                    $('#switch_login').removeClass("switch_btn_focus").addClass('switch_btn');
                    $('#switch_qlogin').removeClass("switch_btn").addClass('switch_btn_focus');
                    $('#switch_bottom').animate({left:'0px',width:'70px'});
                    $('#qlogin').css('display','none');
                    $('#web_qr_login').css('display','block');
                    $('#passwd').val("");
                    $('#qq').val("");
                    $('#user').val("");
                    $('#passwd2').val("");
                } else {
                    $('#userCue').html("<font color='red'><b>×QQ号码已被注册，请重新输入！！！</b></font>");return false;
                }

            }
        });


    });


});

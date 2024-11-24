$(document).ready(function() {
    localStorage.clear();
});

$("#login").click(function(){
    var user = {
        nickname : $("#userId").val(),
        password : $("#password").val()
    }

    $.ajax({
        type : "POST",
        url : "/auth/adminLogin",
        contentType: "application/json",
        datatype : "json",
        data : JSON.stringify(user),
        success : function(res) {
            var value = res['accessToken'];
            successLogin(value);
        },
        error : function(err){
            alert("로그인 실패! 아이디나 비밀번호를 확인하세요");
        }
    })
});

function successLogin(value) {
    localStorage.clear();
    localStorage.setItem('token', value);
    window.location.href = "/admin";
}
$(document).ready(function() {
    // 입력 필드에서 Enter 키를 눌렀을 때 로그인 버튼 클릭
    $("#userId, #password").keydown(function(event) {
        if (event.key === "Enter") { // Enter 키가 눌렸을 때
            $("#login").click(); // 로그인 버튼 클릭
        }
    });

    // 로그인 버튼 클릭 시 동작
    $("#login").click(function(){
        var user = {
            nickname : $("#userId").val(),
            password : $("#password").val(),
            employed : false
        };

        $.ajax({
            type : "POST",
            url : "/members/login",
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
        });
    });
});

function successLogin(value) {
    localStorage.clear();
    localStorage.setItem('token', value);
    window.location.href = "/admin";
}
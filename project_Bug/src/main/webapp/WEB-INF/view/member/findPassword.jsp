<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../inc/header.jsp" %>

<main class="container mt-5 flex-grow-1">
    <h3> 비밀번호 변경 </h3>
    <form id="findPasswordForm" method="post">
        <div class="mb-3 mt-3">
            <label for="email" class="form-label">이메일:</label>
            <input type="email" class="form-control" id="email" name="email" required>
        </div>
        <div class="mb-3">
            <label for="mobile" class="form-label">휴대폰 번호:</label>
            <input type="text" class="form-control" id="mobile" name="mobile" required>
        </div>
        <div class="mb-3">
            <label for="newPassword" class="form-label">새 비밀번호:</label>
            <input type="password" class="form-control" id="newPassword" name="newPassword" required>
        </div>
        <button type="submit" class="btn btn-success">비밀번호 변경</button>
    </form>
</main>

<script>
$(function(){
    $("#findPasswordForm").on("submit", function(e){
        e.preventDefault(); // 기본 submit 막기

        $.ajax({
            url: "${pageContext.request.contextPath}/security/findPassword",
            type: "POST",
            data: { 
                email: $("#email").val(),
                mobile: $("#mobile").val(),
                newPassword: $("#newPassword").val()
            },
            beforeSend: function(xhr) {
                xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
            },
            success: function(data){
                if(data.resetResult === "success"){
                    alert("비밀번호가 성공적으로 변경되었습니다.");
                    // 로그인 페이지로 이동
                    window.location.href = "${pageContext.request.contextPath}/security/login";
                } else {
                    alert("입력한 정보가 올바르지 않습니다.");
                }
            },
            error: function(xhr){
                alert("서버 오류 발생: " + xhr.status);
            }
        });
    });
});
</script>

<%@ include file="../inc/footer.jsp" %>

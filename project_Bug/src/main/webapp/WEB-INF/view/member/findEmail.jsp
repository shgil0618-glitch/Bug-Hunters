<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../inc/header.jsp" %>

<main class="container mt-5 flex-grow-1" >
    <h3> 이메일 찾기 </h3>
   <form id="findEmailForm" method="post">
        <div class="mb-3 mt-3">
            <label for="mobile" class="form-label">휴대폰 번호:</label>
            <input type="text" class="form-control" id="mobile" name="mobile" required>
        </div>
        <button type="submit" class="btn btn-primary">이메일 찾기</button>
   </form>
</main>

<script>
$(function(){
    $("#findEmailForm").on("submit", function(e){
        e.preventDefault(); // 기본 submit 막기

        $.ajax({
            url: "${pageContext.request.contextPath}/security/findEmail",
            type: "POST",
            data: { mobile: $("#mobile").val() },
            beforeSend: function(xhr) {
                xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
            },
            success: function(data){
                if(data.emailResult && data.emailResult.length > 0){
                    alert("당신의 이메일은 : " + data.emailResult);
                    // 로그인 페이지로 이동
                    window.location.href = "${pageContext.request.contextPath}/security/login";
                } else {
                    alert("등록된 이메일이 없습니다.");
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

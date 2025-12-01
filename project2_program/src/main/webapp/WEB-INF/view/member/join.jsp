<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../inc/header.jsp" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!-- CSRF 토큰 AJAX 사용을 위해 meta 추가 -->
<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_header" content="${_csrf.headerName}" />

<script>
    // 모든 AJAX 요청에 자동으로 CSRF 토큰 추가
    $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(
            $("meta[name='_csrf_header']").attr("content"),
            $("meta[name='_csrf']").attr("content")
        );
    });
</script>

<div class="container mt-5">
    <h3>WELCOME! 회원가입</h3>

    <form action="${pageContext.request.contextPath}/security/join"
          method="post" enctype="multipart/form-data">

        <!-- Spring Security CSRF Token -->
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

        <!-- 이메일 입력 -->
        <div class="mb-3 mt-3">
            <label for="email" class="form-label">Email:</label>
            <input type="email" class="form-control" id="email"
                   placeholder="이메일을 입력해주세요" required name="email">
            <div class="iddouble_result"></div>
        </div>

        <!-- 닉네임 입력 -->
        <div class="mb-3 mt-3">
            <label for="nickname" class="form-label">Nickname:</label>
            <input type="text" class="form-control" id="nickname"
                   placeholder="닉네임을 입력해주세요" required name="nickname">
        </div>

        <!-- 연락처 입력 -->
        <div class="mb-3 mt-3">
            <label for="mobile" class="form-label">Mobile:</label>
            <input type="text" class="form-control" id="mobile"
                   placeholder="연락처를 입력해주세요" required name="mobile">
        </div>

        <!-- 이메일 중복 체크 스크립트 -->
        <script>
            $(function() {
                $("#email").on("keyup", function() {
                    let keyword = $(this).val().trim();

                    if (keyword === "") {
                        $(".iddouble_result").html("<span class='text-danger'>이메일을 입력해주세요</span>");
                        return;
                    }

                    $.ajax({
                        url : "${pageContext.request.contextPath}/security/iddouble",
                        type : "POST",
                        data : { email : keyword },
                        success : function(res) {
                            if (res.cnt == 1) {
                                $(".iddouble_result").html("<span class='text-danger'>이미 사용중인 이메일입니다.</span>");
                            } else {
                                $(".iddouble_result").html("<span class='text-primary'>사용 가능한 이메일입니다.</span>");
                            }
                        },
                        error : function() {
                            $(".iddouble_result").html("<span class='text-danger'>서버 요청 중 오류 발생</span>");
                        }
                    });

                });
            });
        </script>

        <!-- 비밀번호 입력 -->
        <div class="mb-3">
            <label for="password" class="form-label">Password:</label>
            <input type="password" class="form-control" id="password"
                   placeholder="비밀번호를 입력해주세요" name="password" required>
        </div>

        <!-- 프로필 이미지 업로드 -->
        <div class="mb-3">
            <label for="file" class="form-label">Profile Image:</label>
            <input type="file" class="form-control" id="file" name="file">
        </div>

        <!-- 회원가입 버튼 -->
        <button type="submit" class="btn btn-primary">회원가입</button>

    </form>
</div>

<%@ include file="../inc/footer.jsp" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../inc/header.jsp" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>

<script>
  // 모든 AJAX 요청에 자동으로 CSRF 토큰 추가
  $(document).ajaxSend(function(e, xhr, options) {
      xhr.setRequestHeader(
          $("meta[name='_csrf_header']").attr("content"),
          $("meta[name='_csrf']").attr("content")
      );
  });
</script>

<main class="container mt-5">
  <h3>WELCOME! 회원가입</h3>

  <form id="joinForm" enctype="multipart/form-data">
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
      <div class="nickdouble_result"></div>
    </div>

    <!-- 연락처 입력 -->
    <div class="mb-3 mt-3">
      <label for="mobile" class="form-label">Mobile:</label>
      <input type="text" class="form-control" id="mobile"
             placeholder="연락처를 입력해주세요" required name="mobile">
      <div class="mobiledouble_result"></div>
    </div>

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
</main>

<script>
$(function() {
  // 이메일 중복 체크
  $("#email").on("keyup", function() {
      let keyword = $(this).val().trim();
      let token = $("meta[name='_csrf']").attr("content");
      let header = $("meta[name='_csrf_header']").attr("content");

      if (keyword === "") {
          $(".iddouble_result").html("<span class='text-danger'>이메일을 입력해주세요</span>");
          return;
      }

      $.ajax({
          url : "${pageContext.request.contextPath}/security/iddouble",
          type : "POST",
          data : { email : keyword },
          beforeSend : function(xhr){
              xhr.setRequestHeader(header, token);
          },
          success : function(res) {
              if(res.cnt != null){
                  $(".iddouble_result").attr("data-role", "no");  
                  $(".iddouble_result").html("<span class='text-danger p-3'>이미 사용중인 이메일 입니다.</span>");
              }else{ 
                  $(".iddouble_result").attr("data-role", "ok");  
                  $(".iddouble_result").html("<span class='text-success p-3'>사용가능한 이메일 입니다.</span>");
              }
          },
          error : function() {
              $(".iddouble_result").html("<span class='text-danger'>서버 요청 중 오류 발생</span>");
          }
      });
  });

  // 닉네임 중복체크
  $("#nickname").on("keyup", function() {
      let keyword = $(this).val().trim();
      let token = $("meta[name='_csrf']").attr("content");
      let header = $("meta[name='_csrf_header']").attr("content");

      if (keyword === "") {
          $(".nickdouble_result").html("<span class='text-danger'>닉네임을 입력해주세요</span>");
          return;
      }

      $.ajax({
          url : "${pageContext.request.contextPath}/security/nickdouble",
          type : "POST",
          data : { nickname : keyword },
          dataType: "json",
          beforeSend : function(xhr){
              xhr.setRequestHeader(header, token);
          },
          success : function(res) {
              if(res.cnt > 0){
                  $(".nickdouble_result").attr("data-role", "no");  
                  $(".nickdouble_result").html("<span class='text-danger p-3'>이미 사용중인 닉네임 입니다.</span>");
              }else{ 
                  $(".nickdouble_result").attr("data-role", "ok");  
                  $(".nickdouble_result").html("<span class='text-success p-3'>사용가능한 닉네임 입니다.</span>");
              }
          },
          error : function() {
              $(".nickdouble_result").html("<span class='text-danger'>서버 요청 중 오류 발생</span>");
          }
      });
  });
  
  // 휴대폰번호 중복체크
  $("#mobile").on("keyup", function() {
      let keyword = $(this).val().trim();
      let token = $("meta[name='_csrf']").attr("content");
      let header = $("meta[name='_csrf_header']").attr("content");

      if (keyword === "") {
          $(".mobiledouble_result").html("<span class='text-danger'>휴대폰 번호를 입력해주세요</span>");
          return;
      }

      $.ajax({
          url : "${pageContext.request.contextPath}/security/mobiledouble",
          type : "POST",
          data : { mobile : keyword },
          dataType: "json",
          beforeSend : function(xhr){
              xhr.setRequestHeader(header, token);
          },
          success : function(res) {
              if(res.cnt > 0){
                  $(".mobiledouble_result").attr("data-role", "no");  
                  $(".mobiledouble_result").html("<span class='text-danger p-3'>이미 사용중인 휴대폰 번호 입니다.</span>");
              }else{ 
                  $(".mobiledouble_result").attr("data-role", "ok");  
                  $(".mobiledouble_result").html("<span class='text-success p-3'>사용가능한 휴대폰 번호 입니다.</span>");
              }
          },
          error : function() {
              $(".mobiledouble_result").html("<span class='text-danger'>서버 요청 중 오류 발생</span>");
          }
      });
  });

  // 회원가입 제출
  $("#joinForm").on("submit", function(e) {
      e.preventDefault();

      // 중복체크 상태 검증 (선택)
      const idOk = $(".iddouble_result").attr("data-role") === "ok";
      const nickOk = $(".nickdouble_result").attr("data-role") === "ok";
      const mobileOk = $(".mobiledouble_result").attr("data-role") === "ok";
      if(idOk === false || nickOk === false || mobileOk === false){
          alert("중복체크를 확인해주세요.");
          return;
      }

      let formData = new FormData(this);
      let token = $("meta[name='_csrf']").attr("content");
      let header = $("meta[name='_csrf_header']").attr("content");

      $.ajax({
          url: "${pageContext.request.contextPath}/security/join",
          type: "POST",
          data: formData,
          processData: false,
          contentType: false,
          beforeSend: function(xhr) {
              xhr.setRequestHeader(header, token);
          },
          success: function(res) {
              if(res.cnt == 1 ){
                  alert("회원가입 성공!");
                  window.location.href = "${pageContext.request.contextPath}/security/login";
              } else {
                  alert("중복체크 확인 해주세요");
              }
          },
          error: function() {
              alert("서버 요청 중 오류 발생");
          }
      });
  });
});
</script>

<%@ include file="../inc/footer.jsp" %>

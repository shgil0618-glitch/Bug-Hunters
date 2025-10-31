<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@include file="../inc/header.jsp" %>

   <div class="container card  my-5 p-4 ">
      <h3 class="card-header"> MBTI 글삭제</h3>
	  <form action="<%=request.getContextPath()%>/delete.do?id=<%=request.getParameter("id") %>"  method="post"> 
	      <input type="hidden"   name="app_user_id"  value=""> 
		  <div class="my-3">
		    <label for="pass" class="form-label">PASS:</label>
		    <input type="password" class="form-control" 
		    	id="pass"  placeholder="비밀번호를 입력해주세요" name="pass">
		  </div> 
		  <div class="my-3  text-end">
		  	<button type="submit" class="btn btn-primary">글삭제</button>
		  	<a href="javascript:history.go(-1)"  class="btn btn-danger">BACK</a>
		  </div>
	 </form>
   </div>
   
<%@include file="../inc/footer.jsp" %>



<!-- [ mbtiBoard - list.jsp ]  -->

<%-- <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../inc/header.jsp" %>

   <div class="container card  my-5">
      <h3  class="card-header"> MBTI 글삭제 </h3>
        <form action="${pageContext.request.contextPath}/delete.do?id=${param.id}" method="post">
            <div class="my-3">
                <label for="pass" class="form-label">PASS:</label> 
                <input type="password" class="form-control" id="pass"
                    placeholder="비밀번호를 입력해주세요" name="pass">
            </div>
            <div class="my-3 text-end">
                <button type="submit" class="btn btn-primary">글삭제</button>
                <a href="javascript:history.go(-1)" class="btn btn-danger">BACK</a>
            </div>
        </form>
    </div>
    
<%@ include file="../inc/footer.jsp" %> --%>
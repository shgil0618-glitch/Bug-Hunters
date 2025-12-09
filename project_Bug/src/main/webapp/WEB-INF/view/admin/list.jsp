<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ include file="../inc/header.jsp" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>

<main class="container my-5 flex-grow-1">
<sec:authorize access="hasRole('ROLE_ADMIN')">

   <div class="card p-4 userTable">
      <h3 class="card-header">관리자 USER BOARD</h3>  
      <table class="table table-striped table-bordered table-hover">    
        <thead>
            <tr>
                <th scope="col">NO</th>
                <th scope="col">PROFILE</th> 
                <th scope="col">USERNO</th>
                <th scope="col">EMAIL</th>
                <th scope="col">MOBILE</th>
                <th scope="col">NICKNAME</th>
                <th scope="col">DATE</th>  
                <th scope="col">삭제</th> 
            </tr>   
        </thead>
        <tbody>   
        </tbody>
      </table>        
   </div> 

</sec:authorize>
</main>

<script>
$(function(){        
    userList();   // 전체리스트
    userSelect(); // 1명분의정보
    userDelete(); // 유저정보삭제
    inlineNicknameEdit(); // 닉네임 인라인 수정
});

// 전체 유저 목록 조회
function userList(){
    $.ajax({
        url:"${pageContext.request.contextPath}/security/admin/selectAll", 
        type:"GET", 
        success: userListResult , 
        error:function(xhr, status, msg){  alert(status + "/" + msg); }
    });
}

function userListResult(json){   
    $(".userTable tbody").empty();  
    let total = json.length;  
    
    $.each(json , function(idx, user){   
        $("<tr>")   
            .append($("<td>").html(total-idx))    
            .append($("<td>").html('<img src="${pageContext.request.contextPath}/upload/'+user.bfile+'" style="width:80px" />')) 
            .append($("<td>").html(user.appUserId)) 
            .append($("<td>").html(user.email)) 
            .append($("<td>").html(user.mobile)) 
            .append($("<td>").html("<span class='editable-nickname'>" + user.nickname + "</span>")) 
            .append($("<td>").html(user.joindate)) 
            .append($("<td>").html("<input type='button' class='btn btn-primary deleteUser' value='삭제' />")) 
            .append($("<input type='hidden' class='hidden_id'/>").val(user.appUserId))  
            .append($("<input type='hidden' class='hidden_email'/>").val(user.email))  
            .append($("<input type='hidden' class='hidden_nickname'/>").val(user.nickname))  
            .appendTo(".userTable tbody"); 
    });
}

// 특정 유저 조회
function userSelect(){
    $("body").on("click" , ".selectUser" , function(){
        let appUserId = $(this).closest("tr").find(".hidden_id").val();
        $.ajax({
            url:"${pageContext.request.contextPath}/security/admin/select",
            type:"GET",
            data:{appUserId:appUserId},
            success:function(json){
                $("#img").attr("src", "upload/" + json.result.bfile);  
                $("#email").val(json.result.email);  
                $(".userhidden_no")
                    .html($('<input type="hidden" class="hidden_id" />').val(json.result.appUserId));  
            },
            error:function(){ alert("error");}
        });
    });
}

// 유저 삭제
function userDelete(){
    $("body").on("click" , ".deleteUser" , function(){  
        let appUserId = $(this).closest("tr").find(".hidden_id").val();
        let email     = $(this).closest("tr").find(".hidden_email").val();
        let token = $("meta[name='_csrf']").attr("content");  
        let header = $("meta[name='_csrf_header']").attr("content"); 
        
        if(confirm(email + " 유저를 삭제하시겠습니까?")){  
            $.ajax({
                url:"${pageContext.request.contextPath}/security/admin/deleteAdmin",
                type:"POST",
                data:{ appUserId:appUserId, email:email },
                beforeSend : function(xhr){ 
                    xhr.setRequestHeader(header, token);
                },
                success:function(json){ 
                    if(json.result == 1){ 
                        userList(); 
                        alert("유저가 성공적으로 삭제되었습니다!"); 
                    } 
                    else{ alert("삭제 실패"); }
                },
                error:function(){ alert("error"); }
            });
        }
    }); 
}

// 닉네임 인라인 수정
function inlineNicknameEdit(){
    $("body").on("click", ".editable-nickname", function(){
        let $span = $(this);
        let oldNickname = $span.text();
        let appUserId = $span.closest("tr").find(".hidden_id").val();

        let $input = $("<input type='text' class='form-control nickname-input' />")
                        .val(oldNickname);
        $span.replaceWith($input);
        $input.focus();

        $input.on("blur keypress", function(e){
            if(e.type === "blur" || (e.type === "keypress" && e.which === 13)){
                let newNickname = $input.val();
                if(newNickname !== oldNickname){
                    let token = $("meta[name='_csrf']").attr("content");
                    let header = $("meta[name='_csrf_header']").attr("content");

                    $.ajax({
                        url:"${pageContext.request.contextPath}/security/admin/updateNickname",
                        type:"POST",
                        data:{ appUserId:appUserId, nickname:newNickname },
                        beforeSend : function(xhr){
                            xhr.setRequestHeader(header, token);
                        },
                        success:function(json){
                            if(json.result == 1){
                                $input.replaceWith("<span class='editable-nickname'>" + newNickname + "</span>");
                                alert("닉네임이 성공적으로 수정되었습니다!"); 
                            } else {
                                alert("수정 실패");
                                $input.replaceWith("<span class='editable-nickname'>" + oldNickname + "</span>");
                            }
                        },
                        error:function(){
                            alert("error");
                            $input.replaceWith("<span class='editable-nickname'>" + oldNickname + "</span>");
                        }
                    });
                } else {
                    $input.replaceWith("<span class='editable-nickname'>" + oldNickname + "</span>");
                }
            }
        });
    });
}
</script>

<%@ include file="../inc/footer.jsp" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../inc/header.jsp"%>
<div class="show-result container card my-5 p-4">
<form>
	<div class="mb-3">
		<img src="" alt="" style="width:120px;border-radius:10px" id="img"/>
	</div>
	<div class="mb-3 mt-3">
		<label for="title" class="from-label">재료명 : </label>
		<input type="text" class="from-control" id="title" required name="title">
	</div>
	<div class="mb-3">
		<label class="from-label" for="season" >재철 : </label>
		<input type="text" class="from-control" id="season" name="season">
	</div>
	<div class="mb-3">
		<label class="from-label" for="calories100g" >열량(100g) : </label>
		<input type="number" class="from-control" id="calories100g" name="calories100g">
	</div>
	<div class="mb-3">
		<label class="from-label" for="imageurl" >이미지 URL : </label>
		<input type="text" class="from-control" id="imageurl" name="imageurl">
	</div>
	<div class="userhidden_no"></div>
	<input type="button" value="재료 정보 수정"
		class="btn btn-primary" id="updateMaterial"/>
	</form>
</div>
<script>
	//전체 리스트보기
	$(function() {
		materialList(); // 목록 출력
		materialSelect(); // 단건 가져오기
		materialUpdate(); // 수정 이벤트 등록
		materialDelete(); // 삭제 이벤트 등록
	});

	function materialList() {
		$.ajax({
			url : "selectAll.material",
			type : "GET",
			success : materialListResult,
			error : function(xhr, status, msg) {
				alert("ERROR : " + status + "/" + msg);
			}
		});
	}
	function materialListResult(json){
	    console.log(json);
	    $(".userTable tbody").empty();

	    let total = json.length;
	    $.each(json, function(idx, material){
	        $("<tr>")
	            .append( $("<td>").html(total - idx) )
	            .append( $("<td>").html('<img src="'+material.imageurl+'" style="width:80px;border-radius:10px" />') )
	            .append( $("<td>").html(material.title) )
	            .append( $("<td>").html(material.season) )
	            .append( $("<td>").html(material.calories100g + " kcal") )
	            .append( $("<td>").html("<input type='button' class='btn btn-primary selectMaterial' value='수정'/>") )
	            .append( $("<td>").html("<input type='button' class='btn btn-danger deleteMaterial' value='삭제'/>") )
	            .append( $("<input type='hidden' class='hidden_id'/>").val(material.materialid) )
	            .appendTo(".userTable tbody");
	    });
	}
	//수정버튼 클릭시 -> 상세보기
	function materialSelect(){
	    $("body").on("click", ".selectMaterial", function(){
	        let materialid = $(this).closest("tr").find(".hidden_id").val();

	        $.ajax({
	            url:"select.material",
	            type:"GET",
	            data:{ materialid:materialid },
	            success:function(json){
	                console.log(json);

	                $("#img").attr("src", json.result.imageurl);
	                $("#title").val(json.result.title);
	                $("#season").val(json.result.season);
	                $("#calories100g").val(json.result.calories100g);
	                $("#imageurl").val(json.result.imageurl);

	                $(".userhidden_no").html(
	                    $('<input type="hidden" class="hidden_id">').val(json.result.materialid)
	                );
	            },
	            error:function(){
	                alert("정보 불러오기 실패");
	            }
	        });
	    });
	}
	//업데이트
	function materialUpdate(){
	    $("#updateMaterial").on("click", function(){

	        let materialid   = $(".userhidden_no .hidden_id").val();
	        let title        = $("#title").val();
	        let season       = $("#season").val();
	        let calories100g = $("#calories100g").val();
	        let imageurl     = $("#imageurl").val();

	        $.ajax({
	            url:"update.material",
	            type:"POST",
	            data:{
	                materialid:materialid,
	                title:title,
	                season:season,
	                calories100g:calories100g,
	                imageurl:imageurl
	            },
	            success:function(json){
	                alert("수정 완료!");
	                materialList();
	            },
	            error:function(){
	                alert("수정 오류");
	            }
	        });
	    });
	}
	//삭제
	function materialDelete(){
	    $("body").on("click", ".deleteMaterial", function(){
	        let materialid = $(this).closest("tr").find(".hidden_id").val();

	        if(confirm("해당 재료를 삭제하시겠습니까?")){
	            $.ajax({
	                url:"delete.material",
	                type:"POST",
	                data:{ materialid:materialid },
	                success:function(json){
	                    alert("삭제 완료");
	                    materialList();
	                },
	                error:function(){
	                    alert("삭제 실패");
	                }
	            });
	        }
	    });
	}

</script>
<%@include file="../inc/footer.jsp" %>
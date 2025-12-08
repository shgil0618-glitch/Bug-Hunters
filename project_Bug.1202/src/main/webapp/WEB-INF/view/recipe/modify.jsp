<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../inc/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 

<div class="container mt-5">
    <h3>레시피 수정</h3>
    
    <form id="modifyForm" action="${pageContext.request.contextPath}/recipe/modify"  
          method="post" enctype="multipart/form-data"> 
        <sec:csrfInput/>
        <input type="hidden" name="recipeId" value="${recipe.recipeId}">

        <div class="mb-3 mt-3">
            <label for="title" class="form-label">레시피 제목 *</label> 
            <input type="text" class="form-control" id="title"
                   name="title" value="${recipe.title}" required>
        </div>
        
        <div class="row">
            <div class="col-md-6 mb-3">
                <label for="category" class="form-label">카테고리 *</label>
                <select id="category" name="category" class="form-select" required>
                    <option value="">-- 선택 --</option>
                    <option value="1" ${recipe.category==1?'selected':''}>전체</option>
                    <option value="2" ${recipe.category==2?'selected':''}>한식</option>
                    <option value="3" ${recipe.category==3?'selected':''}>양식</option>
                    <option value="4" ${recipe.category==4?'selected':''}>중식</option>
                    <option value="5" ${recipe.category==5?'selected':''}>일식</option>
                    <option value="6" ${recipe.category==6?'selected':''}>디저트</option>
                    <option value="7" ${recipe.category==7?'selected':''}>건강식</option>
                    <option value="8" ${recipe.category==8?'selected':''}>기타</option>
                </select>
            </div>
            <div class="col-md-6 mb-3">
                <label for="difficulty" class="form-label">난이도 *</label>
                <select id="difficulty" name="difficulty" class="form-select" required>
                    <option value="">-- 선택 --</option>
                    <option value="쉬움" ${recipe.difficulty=='쉬움'?'selected':''}>쉬움</option>
                    <option value="보통" ${recipe.difficulty=='보통'?'selected':''}>보통</option>
                    <option value="어려움" ${recipe.difficulty=='어려움'?'selected':''}>어려움</option>
                </select>
            </div>
        </div>
        
        <div class="row">
            <div class="col-md-6 mb-3">
                <label for="cookTime" class="form-label">조리 시간 (분) *</label>
                <input type="number" class="form-control" id="cookTime" name="cookTime" 
                       value="${recipe.cookTime}" required>
            </div>
            <div class="col-md-6 mb-3">
                <label for="servings" class="form-label">인분 *</label>
                <input type="number" class="form-control" id="servings" name="servings" 
                       value="${recipe.servings}" required>
            </div>
        </div>
        
        <div class="mb-3">
            <label for="image" class="form-label">대표 이미지 URL (선택사항)</label>
            <input type="text" class="form-control" id="image" name="image" 
                   value="${recipe.image}" placeholder="URL을 입력하거나 아래 파일 업로드를 이용하세요">
        </div>

        <div class="mb-3">
            <label for="description" class="form-label">레시피 설명 *</label>
            <textarea class="form-control" id="description" name="description" rows="3" required>${recipe.description}</textarea>
        </div>

        <div class="mb-3">
            <div class="d-flex justify-content-between align-items-center">
                <label class="form-label">재료 *</label>
                <button type="button" class="btn btn-sm btn-outline-primary" id="addIngredientBtn">+ 재료 추가</button>
            </div>
            <div id="ingredientsContainer" class="mt-2">
                <c:forEach var="ingre" items="${recipe.ingredients}" varStatus="status">
                    <div class="row g-2 ingredient-row mb-2">
                        <div class="col-5">
                            <input type="text" class="form-control" 
                                   name="ingredients[${status.index}].ingreName" 
                                   value="${ingre.ingreName}" required placeholder="재료 이름">
                        </div>
                        <div class="col-5">
                            <input type="text" class="form-control" 
                                   name="ingredients[${status.index}].ingreNum" 
                                   value="${ingre.ingreNum}" required placeholder="양">
                        </div>
                        <div class="col-2 d-grid">
                            <button type="button" class="btn btn-danger btn-remove-ingre">삭제</button>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
        
        <div class="mb-3">
            <div class="d-flex justify-content-between align-items-center">
                <label class="form-label">조리 방법 *</label>
                <button type="button" class="btn btn-sm btn-outline-primary" id="addStepBtn">+ 단계 추가</button>
            </div>
            <div id="instructionsContainer" class="mt-2">
                <%-- 조리 단계 텍스트와 이미지를 함께 처리하는 반복문 --%>
                <%-- instructionSteps는 Controller에서 분리한 텍스트 리스트 --%>
                <c:forEach var="line" items="${instructionSteps}" varStatus="status">
                    <div class="card p-3 mb-2 instruction-group">
                        <label><span class="step-number">${status.index+1}</span>. 조리 단계 설명</label>
                        
                        <textarea class="form-control instruction-text" name="instructionTexts[${status.index}]" rows="2" required>${line}</textarea>
                        
                        <div class="mt-2">
                            <label class="form-label">단계별 이미지 (선택)</label>
                            
                            <c:set var="currentImage" value="${recipe.images[status.index]}" />
                            <c:if test="${not empty currentImage}">
                                <div class="existing-image-container mb-2">
                                    
                                    <%-- 💡 [RURL 호환성] DB에 /upload/가 붙어있는 경우 대비 --%>
                                    <c:set var="imagePath" value="${currentImage.rurl}" />
                                    <c:if test="${!fn:startsWith(currentImage.rurl, '/upload/') and !fn:startsWith(currentImage.rurl, 'http') }">
                                        <c:set var="imagePath" value="/upload/${currentImage.rurl}" />
                                    </c:if>
                                    
                                    <img src="${pageContext.request.contextPath}${imagePath}" 
                                         alt="기존 이미지" style="max-width: 150px; height: auto;">
                                    <span class="text-muted ms-2">(기존 이미지. 새 파일 선택 시 대체됨)</span>
                                    
                                    <input type="hidden" name="existingFileNames" value="${currentImage.rurl}">
                                </div>
                            </c:if>
                            
                            <input type="file" name="recipeFiles" class="form-control">
                        </div>
                        
                        <button type="button" class="btn btn-sm btn-danger mt-2 btn-remove-step" 
                                style="width: 80px;">단계 삭제</button>
                    </div>
                </c:forEach>

                <c:if test="${empty instructionSteps}">
                    <div class="card p-3 mb-2 instruction-group">
                        <label><span class="step-number">1</span>. 조리 단계 설명</label>
                        <textarea class="form-control instruction-text" name="instructionTexts[0]" rows="2" placeholder="조리 단계 1 설명" required></textarea>
                        <div class="mt-2">
                            <label class="form-label">단계별 이미지 (선택)</label>
                            <input type="file" name="recipeFiles" class="form-control">
                        </div>
                        <button type="button" class="btn btn-sm btn-danger mt-2 btn-remove-step" style="width: 80px;">단계 삭제</button>
                    </div>
                </c:if>
                
            </div>
        </div>

        <input type="hidden" id="instructions" name="instructions">

        <div class="d-flex justify-content-end mt-4">
            <button type="button" class="btn btn-secondary me-2" onclick="history.back()">취소</button>
            <button type="submit" class="btn btn-warning">수정 완료</button>
        </div>
    </form>
</div>

<script>
document.addEventListener('DOMContentLoaded', function() {
    const ingredientsContainer = document.getElementById('ingredientsContainer');
    const addIngredientBtn = document.getElementById('addIngredientBtn');
    const instructionsContainer = document.getElementById('instructionsContainer');
    const addStepBtn = document.getElementById('addStepBtn');
    const modifyForm = document.getElementById("modifyForm");

    // 재료 인덱스 재설정 (중요: 삭제 후 순서 꼬임 방지)
    function reindexIngredients() {
        const rows = ingredientsContainer.querySelectorAll('.ingredient-row');
        rows.forEach((row, idx) => {
            row.querySelector("input[name*='ingreName']").name = `ingredients[${idx}].ingreName`;
            row.querySelector("input[name*='ingreNum']").name = `ingredients[${idx}].ingreNum`;
        });
    }

    // 단계 번호 및 name 재설정
    function renumberSteps() {
        const steps = instructionsContainer.querySelectorAll('.instruction-group');
        steps.forEach((step, idx) => {
            step.querySelector('.step-number').textContent = idx + 1;
            step.querySelector('.instruction-text').name = `instructionTexts[${idx}]`;
        });
    }

    // 재료 추가
    addIngredientBtn.addEventListener('click', function() {
        const idx = ingredientsContainer.querySelectorAll('.ingredient-row').length;
        const newRow = document.createElement('div');
        newRow.classList.add('row', 'g-2', 'ingredient-row', 'mb-2');
        
        newRow.innerHTML = `
            <div class="col-5">
                <input type="text" class="form-control" name="ingredients[${idx}].ingreName" placeholder="재료 ${idx + 1} 이름" required>
            </div>
            <div class="col-5">
                <input type="text" class="form-control" name="ingredients[${idx}].ingreNum" placeholder="재료 ${idx + 1} 양" required>
            </div>
            <div class="col-2 d-grid">
                <button type="button" class="btn btn-danger btn-remove-ingre">삭제</button>
            </div>
        `;
        ingredientsContainer.appendChild(newRow);
    });

    // 재료 삭제 이벤트 위임
    ingredientsContainer.addEventListener('click', (e) => {
        if(e.target.classList.contains('btn-remove-ingre')){
            e.target.closest('.ingredient-row').remove();
            reindexIngredients(); 
        }
    });

    // 단계 추가
    addStepBtn.addEventListener('click', function() {
        const idx = instructionsContainer.querySelectorAll('.instruction-group').length;
        const newStep = document.createElement('div');
        newStep.classList.add('card', 'p-3', 'mb-2', 'instruction-group');
        newStep.innerHTML = `
            <label><span class="step-number">${idx+1}</span>. 조리 단계 설명</label>
            <textarea class="form-control instruction-text" name="instructionTexts[${idx}]" rows="2" placeholder="조리 단계 ${idx + 1} 설명" required></textarea>
            <div class="mt-2">
                <label class="form-label">단계별 이미지 (선택)</label>
                <input type="file" name="recipeFiles" class="form-control">
            </div>
            <button type="button" class="btn btn-sm btn-danger mt-2 btn-remove-step" style="width: 80px;">단계 삭제</button>
        `;
        instructionsContainer.appendChild(newStep);
        renumberSteps();
    });

    // 단계 삭제 이벤트 위임
    instructionsContainer.addEventListener('click', (e) => {
        if(e.target.classList.contains('btn-remove-step')){
            e.target.closest('.instruction-group').remove();
            renumberSteps();
        }
    });

    // 제출 시 처리: instructions 텍스트를 하나의 문자열로 합쳐서 히든 필드에 저장
    modifyForm.addEventListener("submit", function() {
        // 1. instructions 텍스트 합치기
        const steps = document.querySelectorAll(".instruction-text");
        let combined = "";
        steps.forEach((step, idx) => {
            if (step.value.trim() !== "") {
                combined += `${idx+1}. ${step.value.trim()}\n`; 
            }
        });
        document.getElementById("instructions").value = combined.trim();

        // 2. 인덱스 최종 정리 (안전장치)
        reindexIngredients();
        renumberSteps();
    });
});
</script>

<%@ include file="../inc/footer.jsp"%>
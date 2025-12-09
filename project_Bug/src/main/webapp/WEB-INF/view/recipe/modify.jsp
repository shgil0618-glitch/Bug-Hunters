<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../inc/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 

<div class="container mt-5 flex-grow-1">
    <h3>레시피 수정 ✏️</h3>
    
    <form id="modifyForm" action="${pageContext.request.contextPath}/recipe/modify" 
          method="post" enctype="multipart/form-data"> 
        <sec:csrfInput/>
        <input type="hidden" name="recipeId" value="${recipe.recipeId}">

        <!-- 기본 정보 영역 -->
        <div class="mb-3 mt-3">
            <label class="form-label">레시피 제목 *</label> 
            <input type="text" class="form-control" name="title" value="${recipe.title}" required>
        </div>

        <div class="row">
            <div class="col-md-6 mb-3">
                <label class="form-label">카테고리 *</label>
                <select name="category" class="form-select" required>
                    <option value="">-- 선택 --</option>
                    <%-- <option value="1" ${recipe.category==1?'selected':''}>전체</option> --%>
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
                <label class="form-label">난이도 *</label>
                <select name="difficulty" class="form-select" required>
                    <option value="">-- 선택 --</option>
                    <option value="쉬움" ${recipe.difficulty eq '쉬움' ? 'selected':''}>쉬움</option>
                    <option value="보통" ${recipe.difficulty eq '보통' ? 'selected':''}>보통</option>
                    <option value="어려움" ${recipe.difficulty eq '어려움' ? 'selected':''}>어려움</option>
                </select>
            </div>
        </div>

        <div class="row">
            <div class="col-md-6 mb-3">
                <label class="form-label">조리 시간 *</label>
                <input type="number" class="form-control" name="cookTime" value="${recipe.cookTime}" required>
            </div>
            <div class="col-md-6 mb-3">
                <label class="form-label">인분 *</label>
                <input type="number" class="form-control" name="servings" value="${recipe.servings}" required>
            </div>
        </div>

        <%-- <div class="mb-3">
            <label class="form-label">대표 이미지 URL</label>
            <input type="text" class="form-control" name="image" value="${recipe.image}">
            <input type="file" class="form-control" id="mainImage" name="recipeFiles" placeholder="대표 이미지 사진을 설정해 주세요.">
        </div> --%>
        
        <div class="mb-3">
    <label class="form-label">대표 이미지</label>

    <!-- 기존 대표 이미지 미리보기 -->
    <c:if test="${not empty recipe.image}">
        <div class="mb-2">
            <img src="${pageContext.request.contextPath}/upload/${recipe.image}"
                 style="max-width:150px;">
            <input type="hidden" name="existingMainImage" value="${recipe.image}" >
        </div>
    </c:if>

    <!-- 대표 이미지 파일 업로드 (register.jsp와 동일한 구조) -->
    <input type="file" class="form-control" name="recipeFiles" required>
</div>
        

        <div class="mb-3">
            <label class="form-label">설명 *</label>
            <textarea class="form-control" name="description" rows="3" required>${recipe.description}</textarea>
        </div>

        <!-- ---------------------------------- -->
        <!--  🔥 재료 영역 (regist.jsp 구조 동일) -->
        <!-- ---------------------------------- -->
        <div class="mb-3">
            <div class="d-flex justify-content-between align-items-center">
                <label class="form-label">재료 *</label>
                <button type="button" class="btn btn-sm btn-outline-primary" id="addIngredientBtn">+ 재료 추가</button>
            </div>

            <div id="ingredientsContainer" class="mt-2">

                <c:forEach var="ingre" items="${recipe.ingredients}" varStatus="status">
                    <div class="row g-2 ingredient-row mb-2">

                        <!-- 기존 재료의 PK 저장 (ingreMapId) -->
                        <input type="hidden" 
                               name="ingredients[${status.index}].ingreMapId" 
                               value="${ingre.ingreMapId}">

                        <div class="col-5">
                            <input type="text" class="form-control" 
                                   name="ingredients[${status.index}].ingreName"
                                   value="${ingre.ingreName}" required>
                        </div>

                        <div class="col-5">
                            <input type="text" class="form-control" 
                                   name="ingredients[${status.index}].ingreNum"
                                   value="${ingre.ingreNum}" required>
                        </div>

                        <div class="col-2 d-grid">
                            <button type="button" class="btn btn-danger btn-remove-ingre">삭제</button>
                        </div>
                    </div>
                </c:forEach>

            </div>
        </div>

        <!-- ---------------------------------- -->
        <!-- 🔥 조리 단계 영역 (regist.jsp 구조 기반 + 기존 이미지 포함) -->
        <!-- ---------------------------------- -->
        <div class="mb-3">
            <div class="d-flex justify-content-between align-items-center">
                <label class="form-label">조리 방법 *</label>
                <button type="button" class="btn btn-sm btn-outline-primary" id="addStepBtn">+ 단계 추가</button>
            </div>

            <div id="instructionsContainer" class="mt-2">

                <c:forEach var="line" items="${instructionSteps}" varStatus="status">
                    <div class="card p-3 mb-2 instruction-group">

                        <label><span class="step-number">${status.index + 1}</span>. 조리 단계 설명</label>
                        <textarea class="form-control instruction-text"
                                  name="instructionTexts[${status.index}]"
                                  required>${line}</textarea>

                        <!-- 기존 이미지 표시 -->
                        <c:if test="${not empty recipe.images[status.index]}">
                            <div class="mt-2">
                                <img src="${pageContext.request.contextPath}/upload/${recipe.images[status.index].rurl}" 
                                     style="max-width:150px;">
                                <input type="hidden" name="existingFileNames" 
                                       value="${recipe.images[status.index].rurl}">
                            </div>
                        </c:if>

                        <div class="mt-2">
                            <label class="form-label">단계별 이미지 (선택)</label>
                            <input type="file" name="recipeFiles" class="form-control">
                        </div>

                        <button type="button" class="btn btn-sm btn-danger mt-2 btn-remove-step"
                                style="width:80px;">삭제</button>

                    </div>
                </c:forEach>

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
document.addEventListener("DOMContentLoaded", () => {

    const ingredientsContainer = document.getElementById("ingredientsContainer");
    const instructionsContainer = document.getElementById("instructionsContainer");
    const addIngredientBtn = document.getElementById("addIngredientBtn");
    const addStepBtn = document.getElementById("addStepBtn");
    const modifyForm = document.getElementById("modifyForm");

    /* ---------------------------
       🔥 regist.jsp 동일 구조 유지
    --------------------------- */

    // 재료 인덱싱
    function reindexIngredients() {
        const rows = ingredientsContainer.querySelectorAll(".ingredient-row");
        rows.forEach((row, idx) => {

            // 기존 PK(hidden)
            const pk = row.querySelector("input[name*='ingreMapId']");
            if (pk) pk.name = `ingredients[\${idx}].ingreMapId`;

            row.querySelector("input[name*='ingreName']").name = `ingredients[\${idx}].ingreName`;
            row.querySelector("input[name*='ingreNum']").name = `ingredients[\${idx}].ingreNum`;
        });
    }

    // 단계 인덱싱
    function renumberSteps() {
        const steps = instructionsContainer.querySelectorAll(".instruction-group");
        steps.forEach((step, idx) => {
            step.querySelector(".step-number").textContent = idx + 1;
            step.querySelector(".instruction-text").name = `instructionTexts[\${idx}]`;
        });
    }

    // 재료 추가
    addIngredientBtn.addEventListener("click", () => {
        const idx = ingredientsContainer.querySelectorAll(".ingredient-row").length;

        const row = document.createElement("div");
        row.classList.add("row", "g-2", "ingredient-row", "mb-2");

        row.innerHTML = `
            <div class="col-5">
                <input type="text" class="form-control"
                       name="ingredients[\${idx}].ingreName"
                       placeholder="재료 \${idx+1} 이름" required>
            </div>
            <div class="col-5">
                <input type="text" class="form-control"
                       name="ingredients[\${idx}].ingreNum"
                       placeholder="재료 \${idx+1} 양" required>
            </div>
            <div class="col-2 d-grid">
                <button type="button" 
                        class="btn btn-danger btn-remove-ingre">삭제</button>
            </div>
        `;

        ingredientsContainer.appendChild(row);
    });

    // 재료 삭제
    ingredientsContainer.addEventListener("click", (e) => {
        if (e.target.classList.contains("btn-remove-ingre")) {
            e.target.closest(".ingredient-row").remove();
            reindexIngredients();
        }
    });

    // 단계 추가
    addStepBtn.addEventListener("click", () => {
        const idx = instructionsContainer.querySelectorAll(".instruction-group").length;

        const card = document.createElement("div");
        card.classList.add("card", "p-3", "mb-2", "instruction-group");

        card.innerHTML = `
            <label><span class="step-number">\${idx+1}</span>. 조리 단계 설명</label>
            <textarea class="form-control instruction-text"
                      name="instructionTexts[\${idx}]"
                      rows="2" placeholder="조리 단계 \${idx+1} 설명" required></textarea>

            <div class="mt-2">
                <label class="form-label">단계별 이미지 (선택)</label>
                <input type="file" name="recipeFiles" class="form-control">
            </div>

            <button type="button" 
                    class="btn btn-sm btn-danger mt-2 btn-remove-step"
                    style="width:80px;">삭제</button>
        `;

        instructionsContainer.appendChild(card);
        renumberSteps();
    });

    // 단계 삭제
    instructionsContainer.addEventListener("click", (e) => {
        if (e.target.classList.contains("btn-remove-step")) {
            e.target.closest(".instruction-group").remove();
            renumberSteps();
        }
    });

    // 제출 시 instructions 문자열 합침
    modifyForm.addEventListener("submit", () => {
        reindexIngredients();
        renumberSteps();

        const list = [...document.querySelectorAll(".instruction-text")]
            .map((el, idx) => `\${idx+1}. \${el.value.trim()}`);

        document.getElementById("instructions").value = list.join("\n");
    });
});
</script>
<br>
<%@ include file="../inc/footer.jsp"%>

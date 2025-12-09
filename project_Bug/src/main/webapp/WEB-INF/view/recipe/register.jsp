<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../inc/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<div class="container mt-5">
    <h3>새 레시피 작성</h3>

    <form id="recipeForm" action="${pageContext.request.contextPath}/recipe/register" method="post" enctype="multipart/form-data">
        <sec:csrfInput/>

        <!-- 레시피 제목 -->
        <div class="mb-3 mt-3">
            <label for="title" class="form-label">레시피 제목 *</label>
            <input type="text" class="form-control" id="title" placeholder="예: 김치찌개" required name="title">
        </div>

        <!-- 카테고리, 난이도 -->
        <div class="row">
            <div class="col-md-6 mb-3">
                <label for="category" class="form-label">카테고리 *</label>
                <select id="category" name="category" class="form-select" required>
                    <option value="">-- 선택 --</option>
                    <!-- <option value="1">전체</option> -->
                    <option value="2">한식</option>
                    <option value="3">양식</option>
                    <option value="4">중식</option>
                    <option value="5">일식</option>
                    <option value="6">디저트</option>
                    <option value="7">건강식</option>
                    <option value="8">기타</option>
                </select>
            </div>
            <div class="col-md-6 mb-3">
                <label for="difficulty" class="form-label">난이도 *</label>
                <select id="difficulty" name="difficulty" class="form-select" required>
                    <option value="">-- 선택 --</option>
                    <option value="쉬움">쉬움</option>
                    <option value="보통" selected>보통</option>
                    <option value="어려움">어려움</option>
                </select>
            </div>
        </div>

        <!-- 조리 시간, 인분 -->
        <div class="row">
            <div class="col-md-6 mb-3">
                <label for="cookTime" class="form-label">조리 시간 (분) *</label>
                <input type="number" class="form-control" id="cookTime" name="cookTime" value="30" required>
            </div>
            <div class="col-md-6 mb-3">
                <label for="servings" class="form-label">인분 *</label>
                <input type="number" class="form-control" id="servings" name="servings" value="2" required>
            </div>
        </div>

        <!-- 이미지 URL -->
        <div class="mb-3">
            <label for="image" class="form-label">대표 이미지</label>
            <!-- <input type="text" class="form-control" id="image" name="image" placeholder="이미지 URL을 입력하지 않으면 기본 이미지가 사용됩니다."> -->
              <input type="file" class="form-control" id="mainImage" name="recipeFiles" placeholder="대표 이미지 사진을 설정해 주세요." required>
        </div>

        <!-- 레시피 설명 -->
        <div class="mb-3">
            <label for="description" class="form-label">레시피 설명 *</label>
            <textarea class="form-control" id="description" name="description" rows="3" placeholder="이 레시피에 대한 간단한 설명을 입력하세요" required></textarea>
        </div>

        <!-- 재료 추가 -->
        <div class="mb-3">
            <div class="d-flex justify-content-between align-items-center">
                <label class="form-label">재료 *</label>
                <button type="button" class="btn btn-sm btn-outline-primary" id="addIngredientBtn">+ 재료 추가</button>
            </div>
            <div id="ingredientsContainer" class="mt-2">
                <div class="row g-2 ingredient-row mb-2">
                    <div class="col-5">
                        <input type="text" class="form-control" name="ingredients[0].ingreName" placeholder="재료 1 이름" required>
                    </div>
                    <div class="col-5">
                        <input type="text" class="form-control" name="ingredients[0].ingreNum" placeholder="재료 1 양" required>
                    </div>
                    <div class="col-2 d-grid">
                        <button type="button" class="btn btn-danger btn-remove-ingre">삭제</button>
                    </div>
                </div>
            </div>
        </div>

        <!-- 조리 단계 추가 -->
        <div class="mb-3">
            <div class="d-flex justify-content-between align-items-center">
                <label class="form-label">조리 방법 *</label>
                <button type="button" class="btn btn-sm btn-outline-primary" id="addStepBtn">+ 단계 추가</button>
            </div>
            <div id="instructionsContainer" class="mt-2">
                <div class="card p-3 mb-2 instruction-group">
                    <label><span class="step-number">1</span>. 조리 단계 설명</label>
                    <textarea class="form-control instruction-text" name="instructionTexts[0]" rows="2" placeholder="조리 단계 1 설명" required></textarea>

                    <div class="mt-2">
                        <label class="form-label">단계별 이미지 (선택)</label>
                        <input type="file" name="recipeFiles" class="form-control">
                    </div>

                    <button type="button" class="btn btn-sm btn-danger mt-2 btn-remove-step" style="width: 80px;">단계 삭제</button>
                </div>
            </div>
        </div>

        <!-- 숨겨진 입력 필드 (합친 instructions) -->
        <input type="hidden" id="instructions" name="instructions">

        <!-- 제출 버튼 -->
        <div class="d-flex justify-content-end mt-4">
            <button type="button" class="btn btn-secondary me-2" onclick="history.back()">취소</button>
            <button type="submit" class="btn btn-warning">레시피 등록</button>
        </div>
    </form>
</div>
<script>
document.addEventListener('DOMContentLoaded', function() {
    const ingredientsContainer = document.getElementById('ingredientsContainer');
    const addIngredientBtn = document.getElementById('addIngredientBtn');
    const instructionsContainer = document.getElementById('instructionsContainer');
    const addStepBtn = document.getElementById('addStepBtn');
    const recipeForm = document.getElementById("recipeForm");

    // 재료 인덱스 재설정
    function reindexIngredients() {
        const rows = ingredientsContainer.querySelectorAll('.ingredient-row');
        rows.forEach((row, idx) => {
            // 수정됨: \${idx} 로 변경하여 JSP 파싱 방지
            row.querySelector("input[name*='ingreName']").name = `ingredients[\${idx}].ingreName`;
            row.querySelector("input[name*='ingreNum']").name = `ingredients[\${idx}].ingreNum`;
        });
    }

    // 단계 재정렬
    function renumberSteps() {
        const steps = instructionsContainer.querySelectorAll('.instruction-group');
        steps.forEach((step, idx) => {
            step.querySelector('.step-number').textContent = idx + 1;
            // 수정됨: \${idx} 로 변경
            step.querySelector('.instruction-text').name = `instructionTexts[\${idx}]`;
            // file input은 배열이 아닌 하나의 리스트로 받아도 되므로 그대로 둠 (혹은 필요시 인덱스 부여)
            step.querySelector("input[type='file']").name = "recipeFiles";
        });
    }

    // 재료 추가
    addIngredientBtn.addEventListener('click', () => {
        const idx = ingredientsContainer.querySelectorAll('.ingredient-row').length;
        const newRow = document.createElement('div');
        newRow.classList.add('row', 'g-2', 'ingredient-row', 'mb-2');
        // 수정됨: \${idx} 로 변경
        newRow.innerHTML = `
            <div class="col-5">
                <input type="text" class="form-control" name="ingredients[\${idx}].ingreName" placeholder="재료 \${idx + 1} 이름" required>
            </div>
            <div class="col-5">
                <input type="text" class="form-control" name="ingredients[\${idx}].ingreNum" placeholder="재료 \${idx + 1} 양" required>
            </div>
            <div class="col-2 d-grid">
                <button type="button" class="btn btn-danger btn-remove-ingre">삭제</button>
            </div>
        `;
        ingredientsContainer.appendChild(newRow);
    });

    // 재료 삭제
    ingredientsContainer.addEventListener('click', (e) => {
        if(e.target.classList.contains('btn-remove-ingre')){
            e.target.closest('.ingredient-row').remove();
            reindexIngredients();
        }
    });

    // 단계 추가
    addStepBtn.addEventListener('click', () => {
        const idx = instructionsContainer.querySelectorAll('.instruction-group').length;
        const newStep = document.createElement('div');
        newStep.classList.add('card', 'p-3', 'mb-2', 'instruction-group');
        // 수정됨: \${idx} 로 변경
        newStep.innerHTML = `
            <label><span class="step-number">\${idx + 1}</span>. 조리 단계 설명</label>
            <textarea class="form-control instruction-text" name="instructionTexts[\${idx}]" rows="2" placeholder="조리 단계 \${idx + 1} 설명" required></textarea>
            <div class="mt-2">
                <label class="form-label">단계별 이미지 (선택)</label>
                <input type="file" name="recipeFiles" class="form-control">
            </div>
            <button type="button" class="btn btn-sm btn-danger mt-2 btn-remove-step" style="width: 80px;">단계 삭제</button>
        `;
        instructionsContainer.appendChild(newStep);
        renumberSteps();
    });

    // 단계 삭제
    instructionsContainer.addEventListener('click', (e) => {
        if(e.target.classList.contains('btn-remove-step')){
            e.target.closest('.instruction-group').remove();
            renumberSteps();
        }
    });

    // submit 처리
    recipeForm.addEventListener('submit', () => {
        // instruction 합치기
        // 수정됨: \${idx} 로 변경
        const texts = Array.from(document.querySelectorAll(".instruction-text")).map((t, idx) => `\${idx+1}. \${t.value.trim()}`);
        document.getElementById("instructions").value = texts.join("\n");

        reindexIngredients();
        renumberSteps();
    });
});
</script>
<%@ include file="../inc/footer.jsp"%>

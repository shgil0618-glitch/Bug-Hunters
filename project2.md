> 주제 
- 오늘 뭐 먹지? (음식추천 및 레시피 공유 사이트)
■1. 주요기능 - crud
1-1. 멤버관리
             사용자측        관리자측
CREATE       회원가입       회원가입
READ         로그인/아이디찾기/비밀번호찾기/마이페이지 , 전체유저정보확인
UPDATE       정보수정       정보수정, 회원정보수정
DELETE       탈퇴                   회원탈퇴

1-2. 게시판
            사용자측                    관리자측
CREATE      게시판글쓰기                 게시판글쓰기
READ                    전체읽기, 상세읽기          
UPDATE       글수정 (본인 글 삭제 가능)   글수정 (모든 글 삭제 가능)
DELETE       글삭제 (본인 글 삭제 가능)   글삭제 (모든 글 삭제 가능)

1-3. 검색기능 (CREATE,UPDATE,DELETE는 하드코딩으로 넣을거기 때문에 READ만 신경쓰면됨.)
                 사용자측                    관리자측
CREATE              X                  검색로직 추가(타입,이름,제목,조회수,좋아요,즐겨찾기)
READ         전체읽기, 상세읽기(검색)     전체읽기, 상세읽기 (검색)
UPDATE              X                  검색로직 수정(타입,이름,제목,조회수,좋아요,즐겨찾기)
DELETE              X                  검색로직 삭제(타입,이름,제목,조회수,좋아요,즐겨찾기)

■2. 흐름도 

# 🍳 **▶ 사용자(User) 레시피 흐름도**

```
[사용자]
   │
   ├── 레시피 작성(Insert)
   │         │
   │         └──────────────▶ [DB 저장]
   │
   ├── 내 레시피 전체 읽기(SelectAll)
   │         │
   │         └──────────────▶ [DB 조회: userId = 본인]
   │
   ├── 레시피 상세 읽기(Select)
   │         │
   │         └──────────────▶ [DB 조회: recipeId]
   │
   ├── 레시피 수정(Update)
   │         │
   │         ├── [본인 레시피인지 확인]
   │         │
   │         └──────────────▶ [DB 업데이트]
   │
   └── 레시피 삭제(Delete)
             │
             ├── [본인 레시피인지 확인]
             │
             └──────────────▶ [DB 삭제]
```

---

# 🛠 **▶ 관리자(Admin) 레시피 흐름도**

```
[관리자]
   │
   ├── 레시피 생성(필요 시) ─────────▶ [DB 저장]
   │
   ├── 전체 레시피 읽기(SelectAll) ───▶ [DB 전체 조회]
   │
   ├── 레시피 상세 읽기(Select) ──────▶ [DB 조회: recipeId]
   │
   ├── 레시피 수정(Update: 전체 가능) ─▶ [DB 업데이트]
   │
   └── 레시피 삭제(Delete: 전체 가능) ─▶ [DB 삭제]
```

---

---

# 🌱 **① Spring 컨트롤러 기반 레시피 CRUD 흐름도**

## ▶ **사용자(User) 컨트롤러 흐름도**

```
[User Controller]
        │
        ├── POST /users/{id}/recipes
        │         │
        │         └────────▶ RecipeService.createRecipe()
        │                           │
        │                           └────▶ [DB INSERT]
        │
        ├── GET /users/{id}/recipes
        │         │
        │         └────────▶ RecipeService.getUserRecipes()
        │                           │
        │                           └────▶ [DB SELECT userId=본인]
        │
        ├── GET /recipes/{recipeId}
        │         │
        │         └────────▶ RecipeService.getRecipeById()
        │                           │
        │                           └────▶ [DB SELECT]
        │
        ├── PUT /users/{id}/recipes/{recipeId}
        │         │
        │         ├────────▶ [작성자 검증: userId == ownerId]
        │         │
        │         └────────▶ RecipeService.updateRecipe()
        │                           │
        │                           └────▶ [DB UPDATE]
        │
        └── DELETE /users/{id}/recipes/{recipeId}
                  │
                  ├────────▶ [작성자 검증: userId == ownerId]
                  │
                  └────────▶ RecipeService.deleteRecipe()
                                        │
                                        └────▶ [DB DELETE]
```

---

## ▶ **관리자(Admin) 컨트롤러 흐름도**

```
[Admin Controller]
        │
        ├── GET /admin/recipes
        │         │
        │         └────────▶ RecipeService.getAllRecipes()
        │                           │
        │                           └────▶ [DB SELECT ALL]
        │
        ├── GET /admin/recipes/{id}
        │         │
        │         └────────▶ RecipeService.getRecipeById()
        │                           │
        │                           └────▶ [DB SELECT]
        │
        ├── PUT /admin/recipes/{id}
        │         │
        │         └────────▶ RecipeService.updateRecipe()
        │                           │
        │                           └────▶ [DB UPDATE]
        │
        └── DELETE /admin/recipes/{id}
                  │
                  └────────▶ RecipeService.deleteRecipe()
                                        │
                                        └────▶ [DB DELETE]
```

---

# 🗂 **② DB 테이블 구조 기반 CRUD 흐름도**

📌 예시 테이블
**RECIPE(레시피 메인)**

```
RECIPE_ID (PK)
USER_ID (FK)
TITLE
CATEGORY
IMAGE
COOK_TIME
DIFFICULTY
SERVINGS
DESCRIPTION
CREATED_AT
UPDATED_AT
```

**RECIPE_INGREDIENTS(재료)**

```
ING_ID (PK)
RECIPE_ID (FK)
INGREDIENT
```

**RECIPE_INSTRUCTIONS(요리 순서)**

```
STEP_ID (PK)
RECIPE_ID (FK)
STEP_ORDER
STEP_CONTENT
```

---

## ▶ **Insert(등록) 흐름**

```
[Controller] 
   │
   └── createRecipe(dto)
           │
           └── [Service]
                    │
                    ├── INSERT INTO RECIPE ...
                    │
                    ├── INSERT INTO RECIPE_INGREDIENTS (N개)
                    │
                    └── INSERT INTO RECIPE_INSTRUCTIONS (N개)
```

---

## ▶ **Select(상세 조회)**

```
[Controller]
   │
   └── getRecipeById(recipeId)
           │
           └── [Service]
                    │
                    ├── SELECT * FROM RECIPE WHERE recipe_id=?
                    │
                    ├── SELECT * FROM RECIPE_INGREDIENTS WHERE recipe_id=?
                    │
                    └── SELECT * FROM RECIPE_INSTRUCTIONS WHERE recipe_id=?
```

---

## ▶ **SelectAll(전체 조회)**

```
[Controller]
   │
   └── getAllRecipes()
           │
           └── [Service]
                    │
                    └── SELECT * FROM RECIPE ORDER BY created_at DESC
```

---

## ▶ **Update(수정)**

```
[Controller]
   │
   └── updateRecipe(recipeId, dto)
           │
           └── [Service]
                    │
                    ├── [작성자 검증 or 전체 수정(admin)]
                    │
                    ├── UPDATE RECIPE SET ...
                    │
                    ├── DELETE FROM RECIPE_INGREDIENTS WHERE recipe_id=?
                    │
                    ├── INSERT 새로운 INGREDIENTS
                    │
                    ├── DELETE FROM RECIPE_INSTRUCTIONS WHERE recipe_id=?
                    │
                    └── INSERT 새로운 INSTRUCTIONS
```

---

## ▶ **Delete(삭제)**

```
[Controller]
   │
   └── deleteRecipe(recipeId)
           │
           └── [Service]
                    │
                    ├── [작성자 검증 or 관리자 삭제]
                    │
                    ├── DELETE FROM RECIPE_INGREDIENTS WHERE recipe_id=?
                    │
                    ├── DELETE FROM RECIPE_INSTRUCTIONS WHERE recipe_id=?
                    │
                    └── DELETE FROM RECIPE WHERE recipe_id=?
```

---


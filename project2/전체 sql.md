--- 유희재 고객파트

Table BUG이(가) 변경되었습니다.

이름        널?       유형            
--------- -------- ------------- 
APPUSERID NOT NULL NUMBER        
PASSWORD  NOT NULL VARCHAR2(100) 
NICKNAME           VARCHAR2(50)  
EMAIL              VARCHAR2(100) 
MOBILE             VARCHAR2(20)  
BFILE              VARCHAR2(225) 
JOINDATE           DATE          
BLIKE              VARCHAR2(100) 

INSERT INTO APPUSER (
    APPUSERID, PASSWORD, NICKNAME, EMAIL, MOBILE, BFILE, BLIKE
) VALUES (
    1, '1', '승현', '1@1', '010-1234-5678', '1.jpg', '1'
);

SELECT * FROM APPUSER;

SELECT APPUSERID, NICKNAME, EMAIL, MOBILE
FROM APPUSER
WHERE APPUSERID = 1;

UPDATE APPUSER
SET PASSWORD = '11',
    NICKNAME = '11',
    BLIKE    = '11'
WHERE APPUSERID = 1;

DELETE FROM APPUSER
WHERE APPUSERID = 1;


---

--- 길상현 요리파트
CREATE TABLE recipes (
    RECIPE_ID NUMBER PRIMARY KEY,
    USER_ID NUMBER,
    TITLE VARCHAR2(255) NOT NULL,
    CATEGORY VARCHAR2(100),
    IMAGE VARCHAR2(255) DEFAULT 'user.png',
    COOK_TIME NUMBER DEFAULT 0,
    DIFFICULTY VARCHAR2(50),
    SERVINGS NUMBER DEFAULT 1,
    DESCRIPTION VARCHAR2(4000),
    INGREDIENTS VARCHAR2(4000),
    INSTRUCTIONS VARCHAR2(4000),
    CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- 생성일
    UPDATED_AT TIMESTAMP,                           -- 수정일 (NULL이 기본)
    VIEWS NUMBER DEFAULT 0,
    FOREIGN KEY (USER_ID) REFERENCES BUG(USER_ID)
);


-- 1. 레시피 한 개 조회 (SELECT)
SELECT 
    RECIPE_ID, 
    USER_ID, 
    TITLE, 
    CATEGORY, 
    IMAGE, 
    COOK_TIME, 
    DIFFICULTY, 
    SERVINGS, 
    DESCRIPTION, 
    INGREDIENTS, 
    INSTRUCTIONS, 
    CREATED_AT, 
    UPDATED_AT, 
    VIEWS
FROM 
    recipes
WHERE 
    RECIPE_ID = :recipe_id;  -- :recipe_id는 조회하려는 레시피의 ID

-- 2. 모든 레시피 조회 (SELECTALL)
SELECT *
FROM recipes
ORDER BY GREATEST(CREATED_AT, NVL(UPDATED_AT, CREATED_AT)) DESC;


-- 3. 레시피 추가 (INSERT)
INSERT INTO recipes (
    RECIPE_ID, 
    USER_ID, 
    TITLE, 
    CATEGORY, 
    IMAGE, 
    COOK_TIME, 
    DIFFICULTY, 
    SERVINGS, 
    DESCRIPTION, 
    INGREDIENTS, 
    INSTRUCTIONS, 
    CREATED_AT, 
    UPDATED_AT, 
    VIEWS
) 
VALUES (
    recipes_seq.nextval,  -- 자동 증가된 RECIPE_ID를 위한 시퀀스 사용
    :user_id,             -- :user_id는 레시피를 추가하는 사용자의 ID
    :title, 
    :category, 
    NVL(:image, 'user.png'), -- 이미지가 없으면 기본값 'user.png'
    :cook_time, 
    :difficulty, 
    :servings, 
    :description, 
    :ingredients, 
    :instructions, 
    CURRENT_TIMESTAMP, 
    CURRENT_TIMESTAMP, 
    0 -- 기본 조회수 0
);

-- 4. 레시피 수정 (UPDATE)
UPDATE recipes
SET 
    TITLE = :title,
    CATEGORY = :category,
    IMAGE = NVL(:image, 'user.png'),
    COOK_TIME = :cook_time,
    DIFFICULTY = :difficulty,
    SERVINGS = :servings,
    DESCRIPTION = :description,
    INGREDIENTS = :ingredients,
    INSTRUCTIONS = :instructions,
    UPDATED_AT = CURRENT_TIMESTAMP
WHERE 
    RECIPE_ID = :recipe_id;


-- 5. 레시피 삭제 (DELETE)
DELETE FROM recipes
WHERE 
    RECIPE_ID = :recipe_id;  -- :recipe_id는 삭제하려는 레시피의 ID

-- 6. 조회수 증가 (VIEW + 1)
UPDATE recipes
SET 
    VIEWS = VIEWS + 1
WHERE 
    RECIPE_ID = :recipe_id;


---

--- 김정민 재료파트
create table material (
    materialid          NUMBER(6)        PRIMARY KEY,           -- 재료 고유번호 (PK)
    imageurl            VARCHAR2(300)   default  'defult.png',  -- 이미지 경로 또는 URL
    title                  VARCHAR2(200)    NOT NULL,           -- 재료명
    season               VARCHAR2(100),                         -- 제철 정보
    temperature      VARCHAR2(50),                              -- 보관 온도
    calories100g      NUMBER(6),                                -- 100g당 열량
    efficacy             VARCHAR2(1000),
    buyguide            VARCHAR2(1000),                         -- 구입요령
    trimguide           VARCHAR2(1000),                         -- 손질법
    storeguide          VARCHAR2(1000),                         -- 보관법
    recipelink          VARCHAR2(500)                          -- 레시피 검색 링크(URL)
);

insert into material(
    materialid, title, imageurl, season, temperature, calories100g,
    efficacy, buyguide, trimguide, storeguide, recipelink
) values (
       1,
    '메밀면',
    'https://대충주소.com/img.jpg',
    '연중제철',
    '1~5℃',
    142,
    '소화 촉진',
    '유통기한을 확인하고 구입한다.',
    '끓는 물에 삶아서 사용한다.',
    '직사광선을 피해 보관한다.',
    'https://대충주소.com/"검색파트 가져오기"?keyword=국산메밀면'
);

select * from material
where materialid = 1;

select * from material
order by materialid desc;

//기본 JOIN 방식 (FROM A, B WHERE A= B)
SELECT m.materialid,
       m.title,
       mc.category_name
FROM material m, materialcategory mc
WHERE m.materialid = mc.materialid
  AND m.materialid = 1;
//2. NATURAL JOIN 방식
  SELECT materialid, title, categoryname
FROM material 
NATURAL JOIN materialcategory
WHERE materialid = 1;
//3. LEFT JOIN (명시적 ON 사용)
SELECT m.materialid,
       m.title,
       mc.category_name
FROM material m
LEFT JOIN materialcategory mc
       ON m.materialid = mc.materialid
WHERE m.materialid = 1;
//4. LEFT JOIN USING (공통 컬럼 자동 인식)
SELECT materialid, title, categoryname
FROM material
LEFT JOIN materialcategory USING(materialid)
WHERE materialid = 1;


update material set 
title = '메밀면 (국산)',
calories100g = 150,
efficacy = '소화 촉진, 장 건강 도움',
recipelink = 'https://대충주소.com/"검색파트 가져오기"?keyword=국산메밀면',
storeguide = '서늘하고 통풍이 잘 되는 곳에 보관한다.'
where materialid =1;

delete from material
where materialid=1;


---


```
CREATE TABLE FOOD_TB (
    food_id      NUMBER(6)        PRIMARY KEY,       -- 음식 고유번호 (PK)
    name         VARCHAR2(100)    NOT NULL,          -- 음식명
    category     VARCHAR2(50),                       -- 음식 카테고리 (예: 한식, 양식, 중식 등)
    difficulty   VARCHAR2(20),                       -- 조리 난이도 (쉬움 / 보통 / 어려움)
    kcal         NUMBER(5),                          -- 칼로리
    protein      NUMBER(5,1),                        -- 단백질(g)
    carb         NUMBER(5,1),                        -- 탄수화물(g)
    fat          NUMBER(5,1),                        -- 지방(g)
    recipe       CLOB,                               -- 조리 방법 (긴 텍스트)
    image_url    VARCHAR2(200),                      -- 음식 이미지 경로
    like_count   NUMBER(6)        DEFAULT 0          -- 좋아요 수 (기본값 0)
);

-- 🔹 SEQUENCE 생성: 음식 ID 자동 증가 시퀀스
CREATE SEQUENCE FOOD_SEQ
    START WITH 1          -- 시작값
    INCREMENT BY 1        -- 1씩 증가
    NOCACHE               -- 캐시 미사용 (테스트/학습용에 적합)
    NOCYCLE;              -- 순환하지 않음


-- 🔹 샘플 데이터 삽입 (테스트용)
INSERT INTO FOOD_TB (
    food_id, name, category, difficulty, kcal, protein, carb, fat, recipe, image_url
) VALUES (
    FOOD_SEQ.NEXTVAL, '된장찌개', '한식', '보통', 350, 18.5, 22.3, 10.2, '된장을 풀고 두부, 버섯, 애호박 등을 넣어 끓이는 전통 한식 요리입니다.', 'images/soybean_stew.jpg' );

INSERT INTO FOOD_TB (
    food_id, name, category, difficulty, kcal, protein, carb, fat, recipe, image_url
) VALUES (
    FOOD_SEQ.NEXTVAL, '제육볶음', '한식', '보통', 390, 24.3, 9.5, 28.4, '빨간 고추장 양념에 고기를 볶아서 만드는 남성들의 소울 푸드입니다.', 'images/soybean_stew.jpg' );

INSERT INTO FOOD_TB (
    food_id, name, category, difficulty, kcal, protein, carb, fat, recipe, image_url
) VALUES (
    FOOD_SEQ.NEXTVAL, '초밥', '일식', '쉬움', 50, 1.85, 9, 0.36, '하얀 쌀밥 위에 생선의 회를 떠서 올려서 같이 먹는 일본의 전통 요리입니다.', 'images/soybean_stew.jpg' );
    
INSERT INTO FOOD_TB (
    food_id, name, category, difficulty, kcal, protein, carb, fat, recipe, image_url
) VALUES (
    FOOD_SEQ.NEXTVAL, '알리오 올리오 파스타', '양식', '쉬움', 400, 12, 65, 10, '알리오 올리오 파스타는 마늘과 올리브 오일을 핵심 재료로 사용하여 만든  이탈리아 남부 나폴리 지방에서 유래한 전통적인 파스타 요리입니다.', 'images/soybean_stew.jpg' );    

INSERT INTO FOOD_TB (
    food_id, name, category, difficulty, kcal, protein, carb, fat, recipe, image_url
) VALUES (
    FOOD_SEQ.NEXTVAL, '토마토 파스타', '양식', '보통', 348, 12, 55, 10, '토마토를 주재료로 만든 소스에 파스타 면을 버무려 먹는 이탈리아 요리입니다.', 'images/soybean_stew.jpg' );    
    
INSERT INTO FOOD_TB (
    food_id, name, category, difficulty, kcal, protein, carb, fat, recipe, image_url
) VALUES (
    FOOD_SEQ.NEXTVAL, '규동', '일식', '보통', 526, 12, 69, 19, '쇠고기에 양파와 함께 달게 끓인 재료를 그릇에 담은 밥위에 올려 먹는 일본의 덮밥 요리입니다.', 'images/soybean_stew.jpg' );        
    
INSERT INTO FOOD_TB (
    food_id, name, category, difficulty, kcal, protein, carb, fat, recipe, image_url
) VALUES (
    FOOD_SEQ.NEXTVAL, '짜장면', '중식', '보통', 800, 20, 133, 20, '양파, 양배추 등 채소와 돼지고기에 기름으로 튀긴 춘장을 넣어 굵은 국수에 비벼서 먹는 한국식 중국 요리입니다.', 'images/soybean_stew.jpg' );            
    
INSERT INTO FOOD_TB (
    food_id, name, category, difficulty, kcal, protein, carb, fat, recipe, image_url
) VALUES (
    FOOD_SEQ.NEXTVAL, '짬뽕', '중식', '어려움', 700, 30, 100, 20, '해산물 혹은 고기를 비롯한 다양한 채소를 기름에 볶고 난 후, 닭뼈나 돼지뼈로 만든 육수를 넣어 끓이고 삶은 국수를 넣어 먹는 한국식 중국 요리입니다.', 'images/soybean_stew.jpg' );                

COMMIT;
SELECT * FROM FOOD_TB;
```

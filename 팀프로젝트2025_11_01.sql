CREATE TABLE USER_TB (
    id VARCHAR2(30) PRIMARY KEY,
    password VARCHAR2(100) NOT NULL,
    nickname VARCHAR2(50) NOT NULL,
    email VARCHAR2(100) UNIQUE,
    preference VARCHAR2(100),
    allergy CHAR(1),
    join_date DATE DEFAULT SYSDATE
);


INSERT INTO USER_TB (id, password, nickname, email, preference, allergy)
VALUES ('user01', '1234', '영민', 'youngmin@example.com', '비건', 'N');

INSERT INTO USER_TB (id, password, nickname, email, preference, allergy)
VALUES ('user02', 'abcd', '민수', 'minsu@example.com', '고단백', 'Y');

CREATE TABLE FOOD_TB (
    fid NUMBER(6) PRIMARY KEY,
    name VARCHAR2(100) NOT NULL,
    category VARCHAR2(50),
    kcal NUMBER(5),
    protein NUMBER(5,1),
    carb NUMBER(5,1),
    fat NUMBER(5,1),
    recipe CLOB,
    image_url VARCHAR2(200),
    reg_date DATE DEFAULT SYSDATE
);

INSERT INTO FOOD_TB (fid, name, category, kcal, protein, carb, fat, recipe)
VALUES (101, '닭가슴살', '한식', 120, 25, 0, 2, '조리 방법 설명');

INSERT INTO FOOD_TB (fid, name, category, kcal, protein, carb, fat, recipe)
VALUES (102, '비건 샐러드', '양식', 80, 3, 10, 1, '조리 방법 설명');



CREATE TABLE RECOMMEND_TB (
    rec_id NUMBER(8) PRIMARY KEY,
    user_id VARCHAR2(30) NOT NULL,
    food_id NUMBER(6) NOT NULL,
    type VARCHAR2(30) NOT NULL,
    feedback VARCHAR2(200),
    created_at DATE DEFAULT SYSDATE,
    is_favorite CHAR(1) DEFAULT 'N',
    CONSTRAINT fk_recommend_user FOREIGN KEY (user_id) REFERENCES USER_TB(id),
    CONSTRAINT fk_recommend_food FOREIGN KEY (food_id) REFERENCES FOOD_TB(fid)
);

INSERT INTO RECOMMEND_TB (rec_id, user_id, food_id, type, feedback, is_favorite)
VALUES (1, 'user01', 101, 'AI', '단백질 부족', 'N');

INSERT INTO RECOMMEND_TB (rec_id, user_id, food_id, type, feedback, is_favorite)
VALUES (2, 'user02', 102, '랜덤', NULL, 'Y');

CREATE TABLE SEARCH_LOG_TB (
    search_id NUMBER(8) PRIMARY KEY,
    user_id VARCHAR2(30) NOT NULL,
    keyword VARCHAR2(100) NOT NULL,
    filter VARCHAR2(100),
    created_at DATE DEFAULT SYSDATE,
    recent_search CHAR(1) DEFAULT 'Y',
    CONSTRAINT fk_search_user FOREIGN KEY (user_id) REFERENCES USER_TB(id)

);

INSERT INTO SEARCH_LOG_TB (search_id, user_id, keyword, filter, recent_search)
VALUES (1, 'user01', '고단백 닭가슴살', '고단백', 'Y');

INSERT INTO SEARCH_LOG_TB (search_id, user_id, keyword, filter, recent_search)
VALUES (2, 'user02', '비건 샐러드', '비건', 'Y');
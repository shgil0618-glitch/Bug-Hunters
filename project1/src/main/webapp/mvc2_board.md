
■ project001 -  PART002) mvc2  
....... project001  BOARD

1.  MVC1   VS  MVC2
	1) 구성방식 ★ 
	- MVC1 : JSP가  MODEL, VIEW, CONTROLLER 역할을 모두 수행
	- MVC2 :        MODEL(JAVA), VIEW(JSP), CONTROLLER(서블릿) 분리

	2) 유지보수성
	- MVC1 : 낮음 ( JSP에 로직이 포함되어 재사용 어려움) - 간단한 웹
	- MVC2 : 높음 ( MODEL , CONTRLLER의 재사용)       - 대규모

2. MVC2
	1) JAVA, SERVLET 
	2) response 
	3) board

3. 프로젝트
	1) dynamic web project  만들기 : project001              .... pass
	2) ojdbc6.jar  / jstl.jar / standard.jar  파일 셋팅   	  .... pass 
	3) [inc] - header.js / footer.jsp                       .... pass      
	4) mvc2
	▶model 
		1. table + sequence

			| 컬럼명        | 데이터 타입       | 제약 조건       | 설명 |
			|---------------|-------------------|------------------|------|
			| `id`          | `NUMBER`          | `PRIMARY KEY`    | 게시글 고유 ID |
			| `app_user_id` | `NUMBER`          | `NOT NULL`       | 작성자 ID (`appuser` 테이블 참조) |
			| `title`       | `VARCHAR2(200)`   | `NOT NULL`       | 게시글 제목 |
			| `content`     | `CLOB`            | `NOT NULL`       | 게시글 내용 (대용량 텍스트, 최대 4GB) |
			| `pass`        | `VARCHAR2(100)`   | —                | 비회원 삭제용 비밀번호 |
			| `created_at`  | `DATE`            | `DEFAULT SYSDATE`| 작성일 |
			| `hit`         | `NUMBER`          | `DEFAULT 0`      | 조회수 |
			| —             | —                 | `FOREIGN KEY`    | `app_user_id`는 `appuser(app_user_id)` 참조 |
			create sequence post_seq
			SQL> desc post
			Name                                      Null?    Type
			----------------------------------------- -------- ----------------------------
			ID                                        NOT NULL NUMBER
			APP_USER_ID                               NOT NULL NUMBER
			TITLE                                     NOT NULL VARCHAR2(200)
			CONTENT                                   NOT NULL CLOB
			PASS                                               VARCHAR2(100)
			CREATED_AT                                         DATE
			HIT                                                NUMBER


		3. dto

		4. dao

	▶view
		1. list.jsp
		2. write.jsp
		3. detail.jsp
		4. edit.jsp
		5. delete.jsp

	▶controller
		1. frontcontroller
		


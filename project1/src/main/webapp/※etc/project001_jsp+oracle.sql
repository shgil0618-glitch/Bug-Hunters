-- appuser



-- post
--1. table (post) + sequence (post_seq)
--| 컬럼명        | 데이터 타입       | 제약 조건       | 설명 |
--|---------------|-------------------|------------------|------|
--| `id`          | `NUMBER`          | `PRIMARY KEY`    | 게시글 고유 ID |
--| `app_user_id` | `NUMBER`          | `NOT NULL`       | 작성자 ID (`appuser` 테이블 참조) |
--| `title`       | `VARCHAR2(200)`   | `NOT NULL`       | 게시글 제목 |
--| `content`     | `CLOB`            | `NOT NULL`       | 게시글 내용 (대용량 텍스트, 최대 4GB) |
--| `pass`        | `VARCHAR2(100)`   | —                | 비회원 삭제용 비밀번호 |
--| `created_at`  | `DATE`            | `DEFAULT SYSDATE`| 작성일 |
--| `hit`         | `NUMBER`          | `DEFAULT 0`      | 조회수 |
--| —             | —                 | `FOREIGN KEY`    | `app_user_id`는 `appuser(app_user_id)` 참조 |
create sequence  post_seq;
create table  post(
	ID       		NUMBER  NOT NULL , 
	APP_USER_ID     NUMBER  NOT NULL ,
	TITLE           VARCHAR2(200) NOT NULL ,
	CONTENT         CLOB          NOT NULL   ,
	PASS            VARCHAR2(100),
	CREATED_AT      DATE  default sysdate,
	HIT             NUMBER  default 0,
    PRIMARY KEY(ID)
);
  
--1. [글쓰기]글쓰기 sql : 
	insert into post ( id                 , app_user_id ,title , content , pass )  
			  values ( post_seq.nextval   ,          ?  , ?    ,      ?  ,    ? )
              
    insert into post ( id                 , app_user_id ,   title ,  content  ,  pass )  
          values ( post_seq.nextval      ,           1  , 'title' , 'content' , '1111' );
              
--2. [전체보기]전체글가져오기 , appuser테이블에서  email 도 같이 가져오기  sql : 
	SELECT		p.*     ,  u.email
	FROM		post p  join appuser u	on  p.app_user_id= u.app_user_id;

    1. create sequence appuser_seq;
    2. join.jsp 실행해서 회원가입

--3. [상세보기]글번호  해당하는 글가져오기 sql : 
	update post  set  hit=hit+1  where  id=?
	select * from post  where id=?
    
    update post  set  hit=hit+1  where  id=1
	select * from post  where id=1  
    
--4. 글수정하기 sql:
	update post  set   title=?, content=?  where  id=?  and pass=?
	update post  set   title='new-title', content='new-content'  where  id=1  and pass='1111'

--5. 글번호  해당하는 삭제
	delete from  post  where  id=?  and pass=? 
	delete from  post  where  id=1  and pass='1111' 

desc post;
     
     
select * from post;



 
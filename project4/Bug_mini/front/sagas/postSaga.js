import { call, put, takeLatest } from 'redux-saga/effects';
import axios from '../api/axios';
import { message } from 'antd'; // 👈 반드시 임포트 되어있어야 함
import {
  fetchPostsRequest, fetchPostsSuccess, fetchPostsFailure,
  fetchPostRequest, fetchPostSuccess, fetchPostFailure,
  fetchPostsPagedRequest, fetchPostsPagedSuccess, fetchPostsPagedFailure,
  fetchCategoryPostsRequest, fetchCategoryPostsSuccess, fetchCategoryPostsFailure, // ✅ 추가
  searchHashtagRequest, searchHashtagSuccess, searchHashtagFailure,              // ✅ 추가
  fetchLikedPostsRequest, fetchLikedPostsSuccess, fetchLikedPostsFailure,
  fetchMyAndRetweetsRequest, fetchMyAndRetweetsSuccess, fetchMyAndRetweetsFailure, 
  createPostRequest, createPostSuccess, createPostFailure,
  updatePostRequest, updatePostSuccess, updatePostFailure,
  deletePostRequest, deletePostSuccess, deletePostFailure,
} from '../reducers/postReducer';

// 1. 전체 게시글 조회
export function* fetchPosts() {
  try {
    const { data } = yield call(() => axios.get('/api/posts'));
    yield put(fetchPostsSuccess(data));
  } catch (err) {
    yield put(fetchPostsFailure(err.response?.data?.message || err.message));
  }
}

// 2. 단건 게시글 상세 조회
export function* fetchPost(action) {
  try {
    const { data } = yield call(() => axios.get(`/api/posts/${action.payload.postId}`));
    yield put(fetchPostSuccess(data));
  } catch (err) {
    yield put(fetchPostFailure(err.response?.data?.message || err.message));
  }
}

// 3. 전체 게시글 페이징 조회 (수정본)
export function* fetchPostsPaged(action) {
  try {
    const { page, size } = action.payload;
    // ✅ 백엔드 컨트롤러 @GetMapping 경로에 맞춰 "/paged" 삭제
    // 백엔드 파라미터 이름이 page, size이므로 그대로 유지
    const { data } = yield call(() => axios.get(`/api/posts?page=${page}&size=${size}`));
    
    // 리듀서에 데이터와 함께 현재 페이지 번호를 넘겨줌 (초기화 로직을 위해)
    yield put(fetchPostsPagedSuccess({ data, page })); 
  } catch (err) {
    if (err.response?.status === 404 && err.response?.data?.path === '/login') {
      message.error("세션이 만료되었습니다.");
      window.location.href = '/login';
      return;
    }
    yield put(fetchPostsPagedFailure(err.response?.data?.message || err.message));
  }
}

// 4. 카테고리별 검색 (수정)
export function* fetchCategoryPosts(action) {
  try {
    const { category } = action.payload;
    // 백엔드에서 카테고리 검색 시 빈 값이 들어오면 전체를 주는지 확인 필요
    // 안전하게 encodeURIComponent 처리
    const { data } = yield call(() => axios.get(`/api/posts/search/category?category=${encodeURIComponent(category)}`));
    
    // ✅ 중요: 카테고리 검색 성공 시 리듀서에서 기존 posts를 밀어버려야 함
    yield put(fetchCategoryPostsSuccess(data));
  } catch (err) {
    yield put(fetchCategoryPostsFailure(err.response?.data?.message || err.message));
  }
}

// 5. ✅ 해시태그로 검색 (신규 추가)
export function* searchHashtag(action) {
  try {
    const { tag } = action.payload;
    // 백엔드 컨트롤러 엔드포인트: /api/posts/search/hashtag?tag=...
    const { data } = yield call(() => axios.get(`/api/posts/search/hashtag?tag=${encodeURIComponent(tag)}`));
    yield put(searchHashtagSuccess(data));
  } catch (err) {
    yield put(searchHashtagFailure(err.response?.data?.message || err.message));
  }
}

// 6. 좋아요한 게시글 조회
export function* fetchLikedPosts(action) {
  try {
    const { page, size } = action.payload;
    const { data } = yield call(() => axios.get(`/api/posts/liked?page=${page}&size=${size}`));
    yield put(fetchLikedPostsSuccess(data));
  } catch (err) {
    yield put(fetchLikedPostsFailure(err.response?.data?.message || err.message));
  }
}

// 7. 내 쓴 글 + 리트윗 조회 (수정본)
// postSaga.js 내부
// postSaga.js 수정
// postSaga.js
export function* fetchMyAndRetweets(action) {
  try {
    const { page, size } = action.payload; // page는 1
    const response = yield call(() => axios.get(`/api/posts/my-activity`, {
        params: { page, size }
    }));
    yield put(fetchMyAndRetweetsSuccess(response.data));
  } catch (err) {
    yield put(fetchMyAndRetweetsFailure(err.response?.data?.message || err.message));
  }
}

// 8. 게시글 작성 (FormData 활용)
// postSaga.js의 createPost 함수 수정본
// postSaga.js 내의 해당 부분 수정
export function* createPost(action) {
  console.log("Saga 진입! 백엔드로 보낼 데이터:", action.payload); 
  try {
    const { dto, files } = action.payload;
    const formData = new FormData();

    // DTO 데이터 추가
    formData.append('title', dto.title);
    formData.append('category', dto.category);
    formData.append('servingSize', dto.servingSize);
    formData.append('difficulty', dto.difficulty);
    formData.append('description', dto.description || "");
    formData.append('ingredients', dto.ingredients);
    formData.append('instructions', dto.instructions);
    formData.append('content', dto.instructions); 
    
    if (dto.hashtags) {
      formData.append('hashtags', dto.hashtags);
    }

    // 파일 데이터 추가
    if (files && files.length > 0) {
      files.forEach((f) => formData.append('files', f));
    }

    const { data } = yield call(() =>
      axios.post('/api/posts', formData, {
        headers: { 'Content-Type': 'multipart/form-data' },
      })
    );

    console.log("서버 응답 성공:", data);
    yield put(createPostSuccess(data));
  } catch (err) {
    console.error("Saga API 에러:", err.response?.data || err.message);
    yield put(createPostFailure(err.response?.data?.message || err.message));
  }
}
// 9. 게시글 수정 (FormData 활용 - createPost와 로직 통일)
export function* updatePost(action) {
  console.log("수정 Saga 진입! 전송 데이터:", action.payload);
  try {
    const { postId, dto, files } = action.payload;
    const formData = new FormData();

    // DTO 데이터 하나씩 추가 (문자열 변환 처리)
    formData.append('title', dto.title);
    formData.append('category', dto.category);
    formData.append('servingSize', dto.servingSize);
    formData.append('difficulty', dto.difficulty);
    formData.append('description', dto.description || "");
    formData.append('ingredients', dto.ingredients);
    formData.append('instructions', dto.instructions);
    formData.append('content', dto.instructions); // 백엔드 필드 대응

    // ✅ 해시태그 처리: 배열이면 콤마로 연결된 문자열로 변환
    if (dto.hashtags) {
      const hashtagsValue = Array.isArray(dto.hashtags) 
        ? dto.hashtags.join(',') 
        : dto.hashtags;
      formData.append('hashtags', hashtagsValue);
    }

    // 파일 데이터 추가 (새로 업로드한 파일이 있을 때만)
    if (files && files.length > 0) {
      files.forEach(f => {
        // Ant Design Upload의 file 객체에서 실제 File 추출
        const actualFile = f.originFileObj ? f.originFileObj : f;
        formData.append('files', actualFile);
      });
    }

    const { data } = yield call(() =>
      axios.put(`/api/posts/${postId}`, formData, {
        headers: { 'Content-Type': 'multipart/form-data' },
      })
    );
    
    console.log("수정 완료 응답:", data);
    yield put(updatePostSuccess(data));
  // postSaga.js의 updatePost catch 부분
} catch (err) {
    console.error("수정 API 에러:", err.response?.data || err.message);
    const errorMsg = err.response?.status === 404 && err.response?.data?.path === '/login'
        ? "로그인 세션이 만료되었습니다. 다시 로그인해주세요."
        : (err.response?.data?.message || err.message);
        
    yield put(updatePostFailure(errorMsg));
    message.error(errorMsg); // 👈 사용자에게 직접 알림
}
}

// 10. 게시글 삭제
export function* deletePost(action) {
  try {
    yield call(() => axios.delete(`/api/posts/${action.payload.postId}`));
    yield put(deletePostSuccess(action.payload.postId));
    // ✅ 삭제 성공 알림 추가
    // message.success('레시피가 삭제되었습니다.');
  } catch (err) {
    yield put(deletePostFailure(err.response?.data?.message || err.message));
    message.error('삭제에 실패했습니다.');
  }
}

// 감시자 제너레이터
export default function* postSaga() {
  yield takeLatest(fetchPostsRequest.type, fetchPosts);
  yield takeLatest(fetchPostRequest.type, fetchPost);
  yield takeLatest(fetchPostsPagedRequest.type, fetchPostsPaged);
  yield takeLatest(fetchCategoryPostsRequest.type, fetchCategoryPosts); // ✅ 연결
  yield takeLatest(searchHashtagRequest.type, searchHashtag);           // ✅ 연결
  yield takeLatest(fetchLikedPostsRequest.type, fetchLikedPosts);
  yield takeLatest(fetchMyAndRetweetsRequest.type, fetchMyAndRetweets);
  yield takeLatest(createPostRequest.type, createPost);
  yield takeLatest(updatePostRequest.type, updatePost);
  yield takeLatest(deletePostRequest.type, deletePost);
}
import { call, put, takeLatest } from 'redux-saga/effects';
import axios from '../api/axios'; 
import { message } from 'antd'; // 이 줄이 빠져있을 겁니다.
import {
  addRetweetRequest, addRetweetSuccess, addRetweetFailure,
  removeRetweetRequest, removeRetweetSuccess, removeRetweetFailure,
  hasRetweetedRequest, hasRetweetedSuccess, hasRetweetedFailure,
  fetchMyRetweetsRequest, fetchMyRetweetsSuccess, fetchMyRetweetsFailure,
} from '../reducers/retweetReducer';

//  리트윗 추가
// 🚀 리트윗 추가 (수정본)
export function* addRetweet(action) {
  try {
    const { originalPostId } = action.payload;
    const { data } = yield call(() => axios.post(`/api/retweets`, { originalPostId }));
    
    // ✅ 2. 서버에서 준 data 전체를 넘겨야 카운트가 반영됨
    yield put(addRetweetSuccess(data)); 
    message.success("공유되었습니다!");
  } catch (err) {
    const errorMsg = err.response?.data?.message || "이미 공유했거나 본인 글입니다.";
    message.error(errorMsg);
    yield put(addRetweetFailure(errorMsg));
  }
}

//  리트윗 여부 확인
export function* hasRetweeted(action) {
  try {
    const { postId } = action.payload;
    const { data } = yield call(() => axios.get(`/api/retweets/${postId}`));
    yield put(hasRetweetedSuccess({ postId, hasRetweeted: data }));
  } catch (err) {
    yield put(hasRetweetedFailure(err.response?.data?.message || err.message));
  }
}

// 리트윗삭제
// retweetSaga.js 수정본
export function* removeRetweet(action) {
  try {
    const { postId } = action.payload;
    // 이 요청 시 axios 설정에 의해 JWT 토큰이 헤더에 포함되어야 합니다.
    const { data } = yield call(() => axios.delete(`/api/retweets/${postId}`));
    
    yield put(removeRetweetSuccess(data)); 
    message.success("공유를 취소했습니다.");
  } catch (err) {
    // 이제 여기서 500 에러가 나지 않고 정상적으로 처리될 겁니다.
    console.error(err);
  }
}
// 내가 리트윗한 글 목록
export function* fetchMyRetweets(action) {
  try {
    const { userId } = action.payload;
    const { data } = yield call(() => axios.get(`/api/retweets/user/${userId}`)); 
    // 서버에서 [1,2,3,...] 형태로 반환 → {1:true, 2:true,...} 변환
    const retweetedPosts = {};
    data.forEach(postId => { retweetedPosts[postId] = true; });
    yield put(fetchMyRetweetsSuccess(retweetedPosts));
  } catch (err) {
    yield put(fetchMyRetweetsFailure(err.response?.data?.message || err.message));
  }
}

export default function* retweetSaga() {
  yield takeLatest(addRetweetRequest.type, addRetweet);
  yield takeLatest(hasRetweetedRequest.type, hasRetweeted);
  yield takeLatest(removeRetweetRequest.type, removeRetweet);
  yield takeLatest(fetchMyRetweetsRequest.type, fetchMyRetweets);
}

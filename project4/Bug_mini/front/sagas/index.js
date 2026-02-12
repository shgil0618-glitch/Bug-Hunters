import { all, fork } from 'redux-saga/effects';

import authSaga    from './authSaga';
import postSaga    from './postSaga';
import commentSaga from './commentSaga';
import followSaga  from './followSaga';
import likeSaga    from './likeSaga';
import retweetSaga from './retweetSaga';
import materialSaga from './materialSaga';
import chatSaga     from './chatSaga'; // 1. chatSaga 불러오기

export default function* rootSaga() {
  yield all([
    fork(authSaga),
    fork(postSaga),
    fork(commentSaga),
    fork(followSaga),
    fork(likeSaga),
    fork(retweetSaga),
    fork(materialSaga),
    fork(chatSaga), // 2. 여기에 fork로 실행 등록
  ]);
}
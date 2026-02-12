import { all, fork, put, takeLatest, call } from 'redux-saga/effects';
import { loadMaterialsAPI } from '../api/axios'; // 경로 주의
import {
  LOAD_MATERIALS_REQUEST,
  LOAD_MATERIALS_SUCCESS,
  LOAD_MATERIALS_FAILURE,
} from '../reducers/material';

// 1. 워커 사가: 테스트에서 직접 호출하기 위해 앞에 export를 붙입니다.
export function* loadMaterials(action) {
  try {
    // API 호출
    const result = yield call(loadMaterialsAPI, action.data);
    yield put({
      type: LOAD_MATERIALS_SUCCESS,
      data: result.data,
    });
  } catch (err) {
    console.error(err);
    yield put({
      type: LOAD_MATERIALS_FAILURE,
      error: err.response?.data || '데이터를 불러오지 못했습니다.',
    });
  }
}

// 2. 와처 사가
function* watchLoadMaterials() {
  yield takeLatest(LOAD_MATERIALS_REQUEST, loadMaterials);
}

// 3. 루트 사가에 합쳐질 최종 사가
export default function* materialSaga() {
  yield all([
    fork(watchLoadMaterials),
  ]);
}
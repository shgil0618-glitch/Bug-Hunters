import { call, put, takeLatest } from "redux-saga/effects";
import Cookies from "js-cookie"; 
import api from "../api/axios";
import Router from "next/router";
import { message } from "antd";
import {
  signupRequest, signupSuccess, signupFailure,
  loginRequest, loginSuccess, loginFailure,
  refreshTokenRequest, refreshTokenSuccess, refreshTokenFailure,
  logoutRequest, logout, logoutFailure,
  updateNicknameRequest, updateNicknameSuccess, updateNicknameFailure,
  updateProfileImageRequest, updateProfileImageSuccess, updateProfileImageFailure,
} from "../reducers/authReducer";

const signupApi = (formData) => api.post("/auth/signup", formData, { headers: { "Content-Type": "multipart/form-data" } });
const loginApi = (payload) => api.post("/auth/login", payload);
const refreshApi = () => api.post("/auth/refresh");
const logoutApi = () => api.post("/auth/logout");

export function* signup(action) {
  try {
    yield call(signupApi, action.payload);
    yield put(signupSuccess());
    message.success("회원가입 완료!");
  } catch (err) {
    yield put(signupFailure(err.response?.data?.error || err.message));
  }
}

export function* login(action) {
  try {
    const { data } = yield call(loginApi, action.payload);
    const { accessToken, user } = data;
    if (user && accessToken) { 
      if (typeof window !== "undefined") {
        localStorage.setItem("accessToken", accessToken);
        localStorage.setItem("user", JSON.stringify(user)); 
        Cookies.set("accessToken", accessToken);
      }
      yield put(loginSuccess({ user, accessToken }));
      message.success(`${user.nickname}님 환영합니다!`);
      Router.push("/mypage");
    }
  } catch (err) {
    yield put(loginFailure(err.response?.data?.error || err.message));
    message.error("로그인 실패");
  }
}

export function* refresh() {
  try {
    const { data } = yield call(refreshApi);
    const newAccessToken = data?.accessToken || null;
    if (typeof window !== "undefined" && newAccessToken) {
      localStorage.setItem("accessToken", newAccessToken);
      Cookies.set("accessToken", newAccessToken);
    }
    yield put(refreshTokenSuccess({ accessToken: newAccessToken }));
  } catch (err) { 
    console.warn("토큰 갱신 실패: 세션을 유지하며 재시도합니다.");
    yield put(refreshTokenFailure(err.response?.data?.error || err.message));
    // 🚨 주석 처리: 토큰 갱신 실패했다고 해서 바로 로그아웃시키지 않음
    // yield put(logout());  
  }
}

export function* logoutFlow() {
  try {
    yield call(logoutApi); 
  } catch (err) {
    console.error("로그아웃 API 에러:", err);
  } finally {
    if (typeof window !== "undefined") {
      localStorage.removeItem("accessToken");
      localStorage.removeItem("user");
      Cookies.remove("accessToken");
    }
    yield put(logout());
    Router.push("/");
  }
}

// ... 닉네임, 프로필 이미지 수정 Saga는 기존과 동일하게 유지 ...

export default function* authSaga() {
  yield takeLatest(signupRequest.type, signup);
  yield takeLatest(loginRequest.type, login);
  yield takeLatest(refreshTokenRequest.type, refresh);
  yield takeLatest(logoutRequest.type, logoutFlow);
}
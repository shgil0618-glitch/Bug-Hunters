import React, { useEffect } from 'react';
import { useDispatch } from 'react-redux'; // ✅ 추가
import { wrapper } from '../store/configureStore';
import AppLayout from '../components/AppLayout';
import { loginSuccess } from '../reducers/authReducer'; // ✅ 추가
import 'antd/dist/antd.css';
import '../styles/global.css';

function MyApp({ Component, pageProps }) {
  const dispatch = useDispatch();

  useEffect(() => {
    // 1. 브라우저 로컬스토리지에서 로그인 정보 확인
    const user = localStorage.getItem('user');
    const accessToken = localStorage.getItem('accessToken');

    if (user && accessToken) {
      try {
        // 2. 새로고침 등으로 비어버린 리덕스 상태를 로컬스토리지 데이터로 강제 복구
        const userData = JSON.parse(user);
        dispatch(loginSuccess({ 
          user: userData, 
          accessToken 
        }));
        console.log("새로고침: 유저 상태 복구 성공", userData.nickname);
      } catch (e) {
        console.error("유저 정보 복구 중 에러 발생", e);
        localStorage.removeItem('user');
        localStorage.removeItem('accessToken');
      }
    }
  }, [dispatch]);

  return (
    <AppLayout initialUser={pageProps.user}>
      <Component {...pageProps} />
    </AppLayout>
  );
}

export default wrapper.withRedux(MyApp);
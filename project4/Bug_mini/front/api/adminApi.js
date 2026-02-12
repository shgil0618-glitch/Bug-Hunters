import axios from 'axios';

// 1. 공통 설정
const API = axios.create({
    baseURL: 'http://localhost:8484', // 백엔드 주소
    withCredentials: true,             // 쿠키(Refresh Token) 사용 시 필요
});

// 2. 요청을 보내기 직전에 가로채서 토큰을 넣어주는 '인터셉터' 추가
API.interceptors.request.use((config) => {
    const token = localStorage.getItem('accessToken');
    if (token) {
        // 백엔드 Security가 확인할 수 있게 헤더에 Bearer 토큰 추가
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
}, (error) => {
    return Promise.reject(error);
});

// 모든 유저 조회 API
export const fetchAllUsers = () => {
    return API.get('/admin/users');
};

// 유저 상태 변경 (정지/해제)
export const toggleUserStatus = (userId) => {
    return API.patch(`/admin/users/${userId}/status`);
};

// 유저 강제 탈퇴
export const forceDeleteUser = (userId) => {
    return API.delete(`/admin/users/${userId}`);
};
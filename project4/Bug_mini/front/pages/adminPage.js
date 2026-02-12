import React, { useEffect, useState, useCallback } from 'react';
import { useRouter } from 'next/router'; 
import { useSelector } from 'react-redux';
import * as adminApi from '../api/adminApi';
import UserTable from '../components/UserTable'; 

const AdminPage = () => { 
    const reduxUser = useSelector((state) => state.auth?.user || state.user?.user);
    const [users, setUsers] = useState([]); 
    const [loading, setLoading] = useState(false);
    const [isChecking, setIsChecking] = useState(true);
    const router = useRouter();

    const loadUsers = useCallback(async () => {
        try {
            setLoading(true);
            const response = await adminApi.fetchAllUsers();
            
            // 데이터가 HTML 문자열로 왔는지 확인 (시큐리티 로그인 페이지 방지)
            if (typeof response.data === 'string' && response.data.includes("<!DOCTYPE")) {
                console.error("인증 실패: 데이터 대신 로그인 페이지가 수신됨");
                setUsers([]);
                return;
            }

            // 배열 데이터 추출
            const result = response.data;
            if (Array.isArray(result)) {
                setUsers(result);
            } else if (result && typeof result === 'object') {
                setUsers(result.users || result.data || result.content || []);
            }
            
        } catch (error) {
            console.error("데이터 로딩 실패:", error);
            if (error.response?.status === 403) {
                alert("관리자 권한이 없습니다.");
                router.push('/');
            }
        } finally {
            setLoading(false);
        }
    }, [router]);

    useEffect(() => {
        const getStoredUser = () => {
            if (typeof window !== 'undefined') {
                const item = localStorage.getItem('user');
                return item ? JSON.parse(item) : null;
            }
            return null;
        };

        const currentUser = reduxUser || getStoredUser();

        // 관리자 여부 확인
        if (currentUser && (currentUser.role === 'ROLE_ADMIN' || currentUser.role === 'ADMIN')) {
            setIsChecking(false);
            // 초기 데이터 로딩
            loadUsers();
        } else {
            // 권한 없으면 1초 뒤 메인으로
            const timeout = setTimeout(() => {
                alert("관리자 권한이 필요합니다.");
                router.push('/');
            }, 1000);
            return () => clearTimeout(timeout);
        }
    }, [reduxUser, loadUsers, router]);

    const handleToggleStatus = async (userId) => {
        try {
            await adminApi.toggleUserStatus(userId);
            loadUsers(); 
        } catch (error) {
            alert("상태 변경 실패");
        }
    };

    const handleDeleteUser = async (userId) => {
        if (window.confirm("사용자를 강제 탈퇴시키겠습니까?")) {
            try {
                await adminApi.forceDeleteUser(userId);
                loadUsers();
            } catch (error) {
                alert("삭제 처리 실패");
            }
        }
    };

    if (isChecking) return <div style={{ padding: '50px', textAlign: 'center' }}>보안 권한 확인 중...</div>;

    return (
        <div style={{ padding: '30px', maxWidth: '1200px', margin: '0 auto' }}>
            <h1>사용자 관리 시스템 (Admin)</h1>
            <p>전체 사용자: {users?.length || 0}명</p>
            {loading ? <p>데이터 로딩 중...</p> : (
                <UserTable users={users} onToggle={handleToggleStatus} onDelete={handleDeleteUser} />
            )}
        </div>
    );
};

export default AdminPage;
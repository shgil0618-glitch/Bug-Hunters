import React, { useEffect, useState } from 'react';
import axios from 'axios';

const AdminPage = () => {
    const [users, setUsers] = useState([]);
    const accessToken = localStorage.getItem("accessToken"); // 로그인 시 저장한 토큰

    const fetchUsers = async () => {
        try {
            const res = await axios.get("http://localhost:8080/api/admin/users", {
                headers: { Authorization: `Bearer ${accessToken}` }
            });
            setUsers(res.data);
        } catch (err) {
            alert("관리자 권한이 없습니다!");
        }
    };

    useEffect(() => { fetchUsers(); }, []);

    // 정지/해제 토글 버튼 클릭 시
    const onToggleStatus = async (id) => {
        await axios.patch(`http://localhost:8080/api/admin/users/${id}/status`, {}, {
            headers: { Authorization: `Bearer ${accessToken}` }
        });
        fetchUsers(); // 목록 새로고침
    };

    // 강제 탈퇴 버튼 클릭 시
    const onDelete = async (id) => {
        if(window.confirm("정말 강제 탈퇴시키겠습니까?")) {
            await axios.delete(`http://localhost:8080/api/admin/users/${id}`, {
                headers: { Authorization: `Bearer ${accessToken}` }
            });
            fetchUsers();
        }
    };

    return (
        <div style={{ padding: '20px' }}>
            <h2>유저 관리 시스템 (관리자)</h2>
            <table border="1" style={{ width: '100%', borderCollapse: 'collapse' }}>
                <thead>
                    <tr style={{ backgroundColor: '#eee' }}>
                        <th>ID</th><th>이메일</th><th>닉네임</th><th>상태</th><th>동작</th>
                    </tr>
                </thead>
                <tbody>
                    {users.map(user => (
                        <tr key={user.id}>
                            <td>{user.id}</td>
                            <td>{user.email}</td>
                            <td>{user.nickname}</td>
                            <td>{user.deleted ? "🔴 정지됨" : "🟢 정상"}</td>
                            <td>
                                <button onClick={() => onToggleStatus(user.id)}>
                                    {user.deleted ? "정지 해제" : "계정 정지"}
                                </button>
                                <button onClick={() => onDelete(user.id)} style={{ color: 'red', marginLeft: '10px' }}>
                                    강제 탈퇴
                                </button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export default AdminPage;
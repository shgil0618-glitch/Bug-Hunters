import React from 'react';

const UserTable = ({ users, onToggle, onDelete }) => {
    return (
        <table style={{ width: '100%', borderCollapse: 'collapse', marginTop: '20px' }}>
            <thead>
                <tr style={{ backgroundColor: '#f8f9fa', borderBottom: '2px solid #dee2e6' }}>
                    <th style={{ padding: '12px' }}>ID</th>
                    <th style={{ padding: '12px' }}>닉네임</th>
                    <th style={{ padding: '12px' }}>이메일</th>
                    <th style={{ padding: '12px' }}>가입일</th>
                    <th style={{ padding: '12px' }}>상태</th>
                    <th style={{ padding: '12px' }}>관리</th>
                </tr>
            </thead>
            <tbody>
                {/* ✅ users가 배열일 때만 map을 돌리도록 수정 */}
                {Array.isArray(users) && users.length > 0 ? (
                    users.map(user => (
                        <tr key={user.id} style={{ 
                            textAlign: 'center', 
                            borderBottom: '1px solid #eee',
                            backgroundColor: user.deleted ? '#fff5f5' : 'white' // 정지된 유저 강조
                        }}>
                            <td style={{ padding: '10px' }}>{user.id}</td>
                            <td style={{ padding: '10px' }}>{user.nickname}</td>
                            <td style={{ padding: '10px' }}>{user.email}</td>
                            <td style={{ padding: '10px' }}>
                                {user.createdAt ? new Date(user.createdAt).toLocaleDateString() : '-'}
                            </td>
                            <td style={{ padding: '10px' }}>
                                {user.deleted ? 
                                    <span style={{ color: '#e03131', fontWeight: 'bold' }}>정지됨</span> : 
                                    <span style={{ color: '#2f9e44' }}>활동중</span>
                                }
                            </td>
                            <td style={{ padding: '10px' }}>
                                <button 
                                    onClick={() => onToggle(user.id)}
                                    style={{ marginRight: '5px', cursor: 'pointer' }}
                                >
                                    {user.deleted ? '해제' : '정지'}
                                </button>
                                <button 
                                    onClick={() => onDelete(user.id)}
                                    style={{ color: '#e03131', cursor: 'pointer' }}
                                >
                                    강제탈퇴
                                </button>
                            </td>
                        </tr>
                    ))
                ) : (
                    /* ✅ 데이터가 없을 때 보여줄 행 추가 */
                    <tr>
                        <td colSpan="6" style={{ padding: '30px', textAlign: 'center', color: '#888' }}>
                            사용자 데이터가 존재하지 않습니다.
                        </td>
                    </tr>
                )}
            </tbody>
        </table>
    );
};

export default UserTable;
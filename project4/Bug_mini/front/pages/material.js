import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Typography, Divider, Spin, Alert, Empty, Row, Col } from 'antd';
import { LOAD_MATERIALS_REQUEST } from '../reducers/material';
import MaterialList from '../components/MaterialList';

const { Title, Paragraph } = Typography;

const MaterialPage = () => {
    const dispatch = useDispatch();
    
    // 리듀서에서 상태 가져오기
    const { 
        mainMaterials, 
        loadMaterialsLoading, 
        loadMaterialsError 
    } = useSelector((state) => state.material);

    useEffect(() => {
        // 페이지 진입 시 식재료 데이터 요청 (1페이지)
        dispatch({
            type: LOAD_MATERIALS_REQUEST,
            data: 1
        });
    }, [dispatch]);

    return (
        <div style={{ padding: '30px', maxWidth: '1200px', margin: '0 auto' }}>
            {/* 헤더 섹션 */}
            <header style={{ textAlign: 'center', marginBottom: '40px' }}>
                <Title level={2}>🍲 오늘의 추천 식재료</Title>
                <Paragraph style={{ color: '#888' }}>
                    신선한 재료로 건강한 요리를 시작해보세요. 
                    현재 등록된 모든 식재료 리스트입니다.
                </Paragraph>
            </header>

            <Divider />

            {/* 메인 컨텐츠 영역 */}
            <main>
                {/* 로딩 상태 */}
                {loadMaterialsLoading && (
                    <div style={{ textAlign: 'center', padding: '50px' }}>
                        <Spin size="large" tip="신선한 재료를 불러오는 중입니다..." />
                    </div>
                )}

                {/* 에러 상태 */}
                {loadMaterialsError && (
                    <Alert
                        message="데이터 로드 실패"
                        description={loadMaterialsError}
                        type="error"
                        showIcon
                        style={{ marginBottom: '20px' }}
                    />
                )}

                {/* 데이터가 비어있을 때 */}
                {!loadMaterialsLoading && mainMaterials.length === 0 && (
                    <Empty description="등록된 식재료가 없습니다." />
                )}

                {/* 식재료 리스트 컴포넌트 호출 */}
                {!loadMaterialsLoading && mainMaterials.length > 0 && (
                    <MaterialList materials={mainMaterials} />
                )}
            </main>

            {/* 하단 푸터 느낌의 섹션 */}
            <footer style={{ marginTop: '50px', textAlign: 'center', color: '#ccc' }}>
                © 2026 Bug-Hunters Project. All rights reserved.
            </footer>
        </div>
    );
};

export default MaterialPage;
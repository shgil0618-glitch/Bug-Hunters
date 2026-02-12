import React from 'react';
import { Row, Col, Card, Tag, Empty } from 'antd';

const MaterialList = ({ materials }) => {
  // materials가 배열이 아니거나 비어있을 경우를 대비한 방어 로직
  if (!materials || !Array.isArray(materials) || materials.length === 0) {
    return (
      <div style={{ padding: '40px 0' }}>
        <Empty description="등록된 식재료가 없습니다." />
      </div>
    );
  }

  return (
    <div style={{ padding: '20px' }}>
      <Row gutter={[16, 16]}>
        {materials.map((item) => (
          <Col xs={24} sm={12} md={8} lg={6} key={item.materialid}>
            <Card
              hoverable
              cover={
                <img 
                  alt={item.title} 
                  src={item.imageurl || 'https://via.placeholder.com/150'} 
                  style={{ height: 180, objectFit: 'cover' }} 
                />
              }
            >
              <Card.Meta 
                title={item.title} 
                description={
                  <>
                    <Tag color="green">{item.category}</Tag>
                    <div style={{ marginTop: '5px', fontSize: '12px', color: '#999' }}>
                      ID: {item.materialid}
                    </div>
                  </>
                } 
              />
            </Card>
          </Col>
        ))}
      </Row>
    </div>
  );
};

// 반드시 default로 내보내야 index.js에서 중괄호 없이 가져올 수 있습니다.
export default MaterialList;
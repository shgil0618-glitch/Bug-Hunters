import React, { useEffect } from 'react';
import { Modal, Form, Input, Select, Row, Col, InputNumber, Upload } from 'antd';
import { UploadOutlined, EditOutlined } from '@ant-design/icons';

export default function EditPostModal({ 
    visible, 
    editPost, 
    loading, // ✅ 부모로부터 로딩 상태 전달받음
    onCancel, 
    onSubmit, 
    uploadFiles, 
    setUploadFiles 
}) {
    const [form] = Form.useForm();

    // ✅ 데이터 주입 로직
    useEffect(() => {
        if (visible && editPost) {
            // hashtags가 배열인지 문자열인지 체크하여 배열로 변환
            const hashtagArray = Array.isArray(editPost.hashtags) 
                ? editPost.hashtags 
                : (editPost.hashtags ? editPost.hashtags.split(',') : []);

            form.setFieldsValue({
                title: editPost.title,
                category: editPost.category,
                servingSize: editPost.servingSize,
                difficulty: editPost.difficulty,
                description: editPost.description || editPost.content,
                ingredients: editPost.ingredients,
                instructions: editPost.instructions || editPost.content,
                hashtags: hashtagArray,
            });
        }
    }, [visible, editPost, form]);

    const handleFinish = (values) => {
        onSubmit(values);
        // ✅ 성공 시 리셋은 부모의 useEffect(updatePostDone)에서 처리하므로 여기서는 유지
    };

    return (
        <Modal
            title={<span><EditOutlined /> 게시글 수정하기</span>}
            open={visible}
            onCancel={() => {
                form.resetFields(); // 닫을 때 폼 초기화
                onCancel();
            }}
            onOk={() => form.submit()}
            confirmLoading={loading} // ✅ 수정 중일 때 버튼 로딩 표시 (중복 클릭 방지)
            width={700}
            okText="수정완료"
            cancelText="취소"
            destroyOnClose // ✅ 모달이 닫히면 자식 컴포넌트들을 소멸시켜 메모리 누수 방지
            forceRender // ✅ 폼 필드가 즉시 렌더링되도록 설정
        >
            <Form
                form={form}
                layout="vertical"
                onFinish={handleFinish}
                initialValues={{
                    category: "한식",
                    servingSize: 1,
                    difficulty: "보통"
                }}
            >
                {/* 1. 제목 및 카테고리 */}
                <Row gutter={16}>
                    <Col span={16}>
                        <Form.Item
                            label="게시글 제목"
                            name="title"
                            rules={[{ required: true, message: '제목을 입력해주세요!' }]}
                        >
                            <Input placeholder="예: 매콤 달콤 떡볶이" size="large" />
                        </Form.Item>
                    </Col>
                    <Col span={8}>
                        <Form.Item label="카테고리" name="category" rules={[{ required: true }]}>
                            <Select size="large">
                                <Select.Option value="한식">한식</Select.Option>
                                <Select.Option value="일식">일식</Select.Option>
                                <Select.Option value="중식">중식</Select.Option>
                                <Select.Option value="양식">양식</Select.Option>
                                <Select.Option value="디저트">디저트</Select.Option>
                            </Select>
                        </Form.Item>
                    </Col>
                </Row>

                {/* 2. 인원수 및 난이도 */}
                <Row gutter={16}>
                    <Col span={12}>
                        <Form.Item 
                            label="인원수" 
                            name="servingSize" 
                            rules={[{ required: true }]}
                        >
                            <InputNumber min={1} style={{ width: '100%' }} size="large" />
                        </Form.Item>
                    </Col>
                    <Col span={12}>
                        <Form.Item label="만족도" name="difficulty" rules={[{ required: true }]}>
                            <Select size="large">
                                <Select.Option value="쉬움">쉬움</Select.Option>
                                <Select.Option value="보통">보통</Select.Option>
                                <Select.Option value="어려움">어려움</Select.Option>
                            </Select>
                        </Form.Item>
                    </Col>
                </Row>

                {/* 3. 간단 요약 */}
                <Form.Item
                    label="게시글 간단 요약"
                    name="description"
                    rules={[{ required: true }]}
                >
                    <Input placeholder="게시글의 특징을 한 줄로 소개해주세요." />
                </Form.Item>

                {/* 4. 재료 정보 */}
                <Form.Item
                    label="위치"
                    name="ingredients"
                    rules={[{ required: true }]}
                >
                    <Input.TextArea rows={3} placeholder="위치 정보를 입력해주세요." />
                </Form.Item>

                {/* 5. 조리 순서 */}
                <Form.Item
                    label="상세 설명"
                    name="instructions"
                    rules={[{ required: true }]}
                >
                    <Input.TextArea rows={6} placeholder="상세한 설명 적어주세요." />
                </Form.Item>

                {/* 6. 해시태그 */}
                <Form.Item label="해시태그" name="hashtags">
                    <Select mode="tags" style={{ width: "100%" }} placeholder="#태그 입력 후 Enter" />
                </Form.Item>

                {/* 7. 이미지 수정 안내 */}
                <Form.Item label="게시글 이미지 변경 (새 사진 업로드 시 기존 사진 대체)">
                    <Upload 
                        multiple 
                        beforeUpload={() => false} 
                        fileList={uploadFiles}
                        onChange={({ fileList }) => setUploadFiles(fileList)}
                        listType="picture-card"
                        accept="image/*"
                    >
                        {uploadFiles.length < 8 && (
                            <div>
                                <UploadOutlined />
                                <div style={{ marginTop: 8 }}>사진 변경</div>
                            </div>
                        )}
                    </Upload>
                </Form.Item>
            </Form>
        </Modal>
    );
}
import {Button, Card, Col, Flex, Form, Input, notification, Row, Typography} from "antd";
import {useState} from "react";
import {UpdateStudentPasswordAPI} from "services/axios.api.service.js";

const ChangePasswordPage = () => {
    const { Title } = Typography;
    const [form] = Form.useForm();
    const [isLoading, setIsLoading] = useState(false);

    const onFinish = async (values) => {
        setIsLoading(true);

        const {newPassword, confirmNewPassword} = values;
        try {
            if(newPassword === confirmNewPassword) {
                const responseData = await UpdateStudentPasswordAPI(newPassword);
                if(responseData) {
                    form.resetFields();
                    notification.success({
                        message: "Thay đổi mật khẩu thành công!",
                    });

                    setIsLoading(false);
                }
            } else {
                form.resetFields();
                notification.error({
                    message: "Password không trùng nhau!",
                });
                setIsLoading(false);
            }
        } catch (err) {
            form.resetFields();
            notification.error({
                message: "Lỗi ngoài dự tính!",
                description: "Vui lòng đăng xuất rồi thử lại!"
            });
            console.log(err);

            setIsLoading(false);
        }
    };

    return (
        <>
            <Row gutter={[16, 16]}>
                <Col span={24}>
                    <Card>
                        <Flex justify="flex-start" align="center">
                            <Title level={3}>Đổi mật khẩu</Title>
                        </Flex>
                    </Card>
                </Col>

                <Col span={16} className="h-full flex">
                    <Card
                        variant={false}
                        style={{display: 'flex', flexDirection: 'column', height: '100%'}}
                        styles={{
                            body: {
                                flex: 1,
                                overflowY: 'auto',
                                maxHeight: '400px',
                            },
                        }}
                    >

                            <Form
                                name="basic"
                                labelCol={{ span: 7 }}
                                wrapperCol={{ span: 16 }}
                                style={{ maxWidth: 600 }}
                                initialValues={{ remember: true }}
                                onFinish={onFinish}
                                autoComplete="off"
                            >
                                <Form.Item
                                    label="Mật khẩu mới"
                                    name="newPassword"
                                    rules={[{ required: true, message: 'Please input your password!' }]}
                                >
                                    <Input.Password />
                                </Form.Item>

                                <Form.Item
                                    label="Xác nhận mật khẩu mới"
                                    name="confirmNewPassword"
                                    rules={[{ required: true, message: 'Please input your password!' }]}
                                >
                                    <Input.Password />
                                </Form.Item>

                                <Form.Item label={null}>
                                        <Button type="primary" htmlType="submit" loading={isLoading}>
                                            Submit
                                        </Button>
                                </Form.Item>
                            </Form>


                    </Card>
                </Col>

                <Col span={8} className="h-full flex">
                    <Card title="Quy định mật khẩu" variant={false}
                          style={{
                              display: 'flex',
                              flexDirection: 'column',
                              height: '100%'
                          }}
                          styles={{
                              body: {
                                  flex: 1,
                                  overflowY: 'auto',
                                  maxHeight: '400px',
                              },
                          }}
                    >
                        <Row gutter={[8, 8]}>
                            <Col span={24}>
                                <Card>
                                    <p className="text-sm text-gray-500">Mật khẩu mới hợp lệ là mật khẩu chứa từ 8 ký tự trở lên, kết hợp các chữ hoa, chữ thường, số và ký tự đặc biệt</p>
                                </Card>
                            </Col>
                            <Col span={24}>
                                <Card>
                                    <p className="text-sm text-gray-500">Trong trường hợp chưa đăng nhập được vào Microsoft 365 sau khi đổi mật khẩu, vui lòng đợi vài phút để quá trình đồng bộ được hoàn tất.</p>
                                </Card>
                            </Col>
                            <Col span={24}>
                                <Card>
                                    <p className="text-sm text-gray-500">
                                        Vui lòng liên hệ{' '}
                                        <a href="mailto:itsupport@hoasen.edu.vn" className="text-blue-600 hover:underline">
                                            itsupport@hoasen.edu.vn
                                        </a>{' '}
                                        nếu cần hỗ trợ.
                                    </p>
                                </Card>
                            </Col>
                        </Row>
                    </Card>
                </Col>
            </Row>
        </>
    );
}

export default ChangePasswordPage;
import { useState } from 'react';
import type { FormProps } from 'antd';
import { Form, Input, Button, Card } from 'antd';
import { UserOutlined, LockOutlined } from '@ant-design/icons';
import logo from 'assets/logo.png';

type FieldType = {
    username?: string;
    password?: string;
};

const LoginPage = () => {
    const [isSubmit, setIsSubmit] = useState(false);

    const onFinish: FormProps<FieldType>['onFinish'] = (values) => {
        console.log('Success:', values);
    };

    const onFinishFailed: FormProps<FieldType>['onFinishFailed'] = (errorInfo) => {
        console.log('Failed:', errorInfo);
    };

    return (
        <div className="min-h-screen flex items-center justify-center bg-white p-4">
            <Card className="w-full max-w-md p-6 shadow-md rounded-lg">
                {/* Logo Hoa Sen */}
                <div className="flex justify-center mb-6">
                    <img src={logo} alt="Logo Hoa Sen" className="h-12" />
                </div>

                {/* Tiêu đề */}
                <h2 className="text-center text-2xl font-semibold mb-6">Đăng Nhập</h2>

                {/* Form */}
                <Form<FieldType>
                    name="login"
                    layout="vertical"
                    onFinish={onFinish}
                    onFinishFailed={onFinishFailed}
                    autoComplete="off"
                >
                    {/* Username */}
                    <Form.Item<FieldType>
                        name="username"
                        label="Tên đăng nhập"
                        rules={[{ required: true, message: 'Vui lòng nhập tên đăng nhập!' }]}
                    >
                        <Input prefix={<UserOutlined />} placeholder="Tên đăng nhập" />
                    </Form.Item>

                    {/* Password */}
                    <Form.Item<FieldType>
                        name="password"
                        label="Mật khẩu"
                        rules={[{ required: true, message: 'Vui lòng nhập mật khẩu!' }]}
                    >
                        <Input.Password prefix={<LockOutlined />} placeholder="Mật khẩu" />
                    </Form.Item>

                    {/* Button Đăng nhập */}
                    <Form.Item>
                        <Button
                            type="primary"
                            htmlType="submit"
                            className="w-full"
                            style={{ backgroundColor: '#0E2E6E', borderColor: '#0E2E6E' }}
                            loading={isSubmit}
                        >
                            Đăng nhập
                        </Button>
                    </Form.Item>
                </Form>

                {/* Dòng hỗ trợ */}
                <p className="text-center text-sm text-gray-500 mt-2">
                    Vui lòng liên hệ{' '}
                    <a
                        href="mailto:itsupport@hoasen.edu.vn"
                        className="text-blue-600 hover:underline"
                    >
                        itsupport@hoasen.edu.vn
                    </a>{' '}
                    nếu cần hỗ trợ.
                </p>
            </Card>
        </div>
    );
}

export default LoginPage;
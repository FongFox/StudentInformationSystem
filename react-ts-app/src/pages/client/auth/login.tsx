import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import type { FormProps } from 'antd';
import { Form, Input, Button, Card, App } from 'antd';
import { UserOutlined, LockOutlined } from '@ant-design/icons';
import logo from 'assets/logo.png';
import { LoginAPI } from '@/services/api';

type FieldType = {
    username: string;
    password: string;
};

const LoginPage = () => {
    const [isSubmit, setIsSubmit] = useState(false);
    const { notification } = App.useApp();
    const navigate = useNavigate();

    const onFinish: FormProps<FieldType>['onFinish'] = async (values) => {
        setIsSubmit(true);

        const { username, password } = values;
        // console.log(`Check value: ${username} ${password}`);

        const res = await LoginAPI(username, password);
        if (res?.data) {
            localStorage.setItem('access_token', res.data.accessToken);
            notification.success({ message: "Đăng nhập thành công!" });
            navigate("/");
        }
        else {
            notification.error({ message: "Đăng nhập thất bại!" })
        }

        setIsSubmit(false);
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
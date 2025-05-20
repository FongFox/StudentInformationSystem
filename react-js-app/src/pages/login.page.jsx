import {useState} from "react";
import {useNavigate} from "react-router-dom";

import {App, Button, Card, Form, Input} from "antd";
import {LockOutlined, UserOutlined} from "@ant-design/icons";

import logo from 'assets/logo.png';
import {LoginApi} from "services/axios.api.service.js";

export default function LoginPage () {
    const [isLoading, setIsLoading] = useState(false);
    const [student, setStudent] = useState(null);
    const navigate = useNavigate();
    const {notification} = App.useApp();
    let statusCode = null;

    const onFinish = async (values) => {
        setIsLoading(true);
        // Do something in here
        try {
            const response = await LoginApi(values.username, values.password);
            localStorage.setItem("access_token", response.data.accessToken);
            setStudent(response.data.studentProfileResponse);

            statusCode = response.status;
            notification.success({
                message: response.status,
                description: "Đăng nhập thành công!"
            });
        } catch (error) {
            statusCode = error.status;
            notification.error({
                message: error.status,
                description: "Đăng nhập thất bại!"
            });
        } finally {
            setIsLoading(false);
            if (statusCode < 400) {
                navigate("/");
            }
        }

    };

    const onFinishFailed = (errorInfo) => {
        console.log('Failed:', errorInfo);
    };

    return (
        <div className="min-h-screen flex items-center justify-center bg-white p-4">
            <Card className="w-full max-w-md p-6 shadow-md rounded-lg">
                {/* Logo Hoa Sen */}
                <div className="flex justify-center mb-6">
                    <img src={logo} alt="Logo Hoa Sen" className="h-12"/>
                </div>

                {/* Tiêu đề */}
                <h2 className="text-center text-2xl font-semibold mb-6">Đăng Nhập</h2>

                {/* Form */}
                <Form
                    name="login"
                    layout="vertical"
                    onFinish={onFinish}
                    onFinishFailed={onFinishFailed}
                    autoComplete="off"
                >
                    {/* Username */}
                    <Form.Item
                        name="username"
                        label="Tên đăng nhập"
                        rules={[{required: true, message: 'Vui lòng nhập tên đăng nhập!'}]}
                    >
                        <Input prefix={<UserOutlined/>} placeholder="Tên đăng nhập"/>
                    </Form.Item>

                    {/* Password */}
                    <Form.Item
                        name="password"
                        label="Mật khẩu"
                        rules={[{required: true, message: 'Vui lòng nhập mật khẩu!'}]}
                    >
                        <Input.Password prefix={<LockOutlined/>} placeholder="Mật khẩu"/>
                    </Form.Item>

                    {/* Button Đăng nhập */}
                    <Form.Item>
                        <Button
                            type="primary"
                            htmlType="submit"
                            className="w-full"
                            style={{backgroundColor: '#0E2E6E', borderColor: '#0E2E6E'}}
                            loading={isLoading}
                        >
                            Đăng nhập
                        </Button>
                    </Form.Item>
                </Form>

                {/* Dòng hỗ trợ */}
                <p className="text-center text-sm text-gray-500 mt-2">
                    Vui lòng liên hệ{' '}
                    <a href="mailto:itsupport@hoasen.edu.vn" className="text-blue-600 hover:underline">
                        itsupport@hoasen.edu.vn
                    </a>{' '}
                    nếu cần hỗ trợ.
                </p>
            </Card>
        </div>
    );
}
import React from 'react';
import ReactDOM from 'react-dom/client';
import {BrowserRouter, Route, Routes} from "react-router-dom";

import {App as AntdApp, ConfigProvider} from "antd";

import '@/global.css';
import App from "@/app.jsx";
import LoginPage from "pages/login.page.jsx";
import ProtectedRoute from "@/protect.route.jsx";
import HomePage from "pages/home.page.jsx";
import {AppProvider} from "@/app.context.jsx";
import ChangePasswordPage from "pages/change-password.page.jsx";
import GradePage from "pages/grade.page.jsx";


ReactDOM.createRoot(document.getElementById('root')).render(
    <React.StrictMode>
        <ConfigProvider>
            <AntdApp>
                <BrowserRouter>
                    <AppProvider>
                        <Routes>
                            {/* Public routes */}
                            <Route path="/login" element={<LoginPage/>}/>

                            {/* Protected routes */}
                            <Route element={<ProtectedRoute/>}>
                                <Route path="/" element={<App/>}>
                                    <Route index element={<HomePage/>}/>
                                    <Route path="pwd" element={<ChangePasswordPage/>}/>
                                    <Route path="grades" element={<GradePage/>}/>
                                </Route>
                            </Route>

                            {/* Catch-all: nếu không match route nào */}
                            {/*<Route path="*" element={<ErrorPage />} />*/}
                        </Routes>
                    </AppProvider>
                </BrowserRouter>
            </AntdApp>
        </ConfigProvider>
    </React.StrictMode>,
);

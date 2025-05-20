import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import {BrowserRouter} from "react-router-dom";

import '@ant-design/v5-patch-for-react-19';
import {ConfigProvider, App as AntdApp} from "antd";

import '@/global.css';
import App from '@/app.jsx';

createRoot(document.getElementById('root')).render(
  <StrictMode>
      <BrowserRouter>
          <ConfigProvider>
              <AntdApp>
                  <App />
              </AntdApp>
          </ConfigProvider>
      </BrowserRouter>
  </StrictMode>,
);

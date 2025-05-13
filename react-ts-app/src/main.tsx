import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import Layout from './layout.tsx';
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import HomePage from './pages/client/home.tsx';
import ProfilePage from './pages/client/profile.tsx';
import LoginPage from './pages/client/auth/login.tsx';

const router = createBrowserRouter([
  {
    path: "/",
    element: <Layout />,
    children: [
      {
        // path: "/home",
        index: true,
        element: <HomePage />,
      },
      {
        path: "profile",
        element: <ProfilePage />,
      },
      {
        path: "grades",
        element: <div>Student Grade Page!</div>,
      },
      {
        path: "exam",
        element: <div>Student Exam Page!</div>,
      },
      {
        path: "tuition",
        element: <div>Student Tuition Page!</div>,
      },
      {
        path: "photocopy",
        element: <div>Student photocopy account Page!</div>,
      }
    ]
  },
  {
    path: "/login",
    element: <LoginPage />
  }
]);

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    {/* <Layout /> */}
    <RouterProvider router={router} />
  </StrictMode>,
)

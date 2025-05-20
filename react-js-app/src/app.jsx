import {Route, Routes} from "react-router-dom";
import LoginPage from "pages/login.page.jsx";

export default function App() {
  return (
      <Routes>
        <Route index element={<div>Hello world!</div>}/>

        {/* Public Routes: login page */}
        <Route path="login" element={<LoginPage />}/>
      </Routes>
  );
}

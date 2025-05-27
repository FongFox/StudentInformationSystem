import axios from 'services/axios.customize.service';

export const LoginAPI = async (username, password) => {
    const backendURL = "/api/v1/auth/login";
    const requestBody = {username, password};

    return await axios.post(backendURL, requestBody);
}

export const LogoutAPI = async () => {
    const backendURL = "/api/v1/auth/logout";

    return await axios.post(backendURL);
}

export const RefreshAccountAPI = async () => {
    const backendURL = "/api/v1/students/me";

    return await axios.get(backendURL);
}

export const FetchAnnouncementsAPI = async () => {
    const backendURL = "/api/v1/students/me/announcement";

    return await axios.get(backendURL);
}

export const UpdateStudentPasswordAPI = async (newPassword) => {
    const backendURL = "/api/v1/students/me/pwd";
    const requestBody = {newPassword};

    return await axios.patch(backendURL, requestBody);
}

export const FetchGradesAPI = async () => {
    const backendURL = "/api/v1/students/me/grades";

    return await axios.get(backendURL);
}
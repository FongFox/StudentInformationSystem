import axios from 'services/axios.customize.service';

export const LoginAPI = async (username, password) => {
    const backendURL = "/api/v1/auth/login";
    const requestBody = {username, password};

    return await axios.post(backendURL, requestBody);
}

export const RefreshAccountAPI = async () => {
    const backendURL = "/api/v1/students/me";

    return await axios.get(backendURL);
}
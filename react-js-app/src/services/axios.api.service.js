import axios from 'services/axios.customize.service';

export const LoginApi = (username, password) => {
    const backendURL = "/api/v1/auth/login";
    const requestBody = {username, password};

    return axios.post(backendURL, requestBody);
}
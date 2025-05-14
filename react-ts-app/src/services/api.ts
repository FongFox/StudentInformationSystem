import axios from 'services/axios.customize'

export const LoginAPI = (username: string, password: string) => {
    const backendURL = "/api/v1/auth/login";
    return axios.post<IBackendRes<ILogin>>(backendURL, { username, password });
}
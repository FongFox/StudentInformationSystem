import axios from "axios";

// Set config defaults when creating the instance
const instance = axios.create({
    baseURL: import.meta.env.VITE_BACKEND_URL,
    timeout: 30000,
    headers: {delay: 1000},
    withCredentials: true // send cookies automatically
});

// Alter defaults after instance has been created
// instance.defaults.headers.common['Authorization'] = AUTH_TOKEN;

// Add a request interceptor
instance.interceptors.request.use(
    (config) => {
        // Do something before request is sent
        if(config.url.endsWith('/api/v1/auth/login')) {
            localStorage.clear();
            return config;
        }

        const token = localStorage.getItem('access_token');
        if (token) { config.headers.Authorization = token ? `Bearer ${token}` : ''; }

        return config;
    },
    (error) => {
        // Do something with request error
        return Promise.reject(error);
    }
);

// Add a response interceptor
instance.interceptors.response.use(
    (response) => {
        // Any status code that lie within the range of 2xx cause this function to trigger
        // Do something with response data
        // return response;
        return (response && response.data) ? response.data : response;
    },
    (error) => {
        // Any status codes that falls outside the range of 2xx cause this function to trigger
        // Do something with response error
        // return Promise.reject(error);
        return (error.response && error.response.data) ? Promise.reject(error.response.data) : Promise.reject(error);
    }
);

export default instance;
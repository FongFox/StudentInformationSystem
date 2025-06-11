import axios from "axios";

// Set config defaults when creating the instance
const instance = axios.create({
    baseURL: import.meta.env.VITE_BACKEND_URL,
    timeout: 15000
});

// Alter defaults after instance has been created
// instance.defaults.headers.common['Authorization'] = AUTH_TOKEN;

// Add a request interceptor
instance.interceptors.request.use(
    (config) => {
        // Do something before request is sent
        const token = localStorage.getItem('access_token');
        if (localStorage.getItem('access_token')) {
            config.headers.Authorization = token ? `Bearer ${token}` : "";
        }

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
        return (response.data && response.data.data) ?
            response.data.data : response.data;
    },
    (error) => {
        // Any status codes that falls outside the range of 2xx cause this function to trigger
        // Do something with response error
        return (error.response && error.response.data) ?
            error.response.data : Promise.reject(error);
    }
);

export default instance;
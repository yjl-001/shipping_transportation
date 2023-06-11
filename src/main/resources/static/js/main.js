import axios from 'axios';

axios.interceptors.request.use(
    config => {
        if (document.token) {
            config.headers['token'] = document.token;
        }
        console.log(config.headers)
        return config;
    },
    error => {
        console.log(error);
        return Promise.reject(error);
});
import axios from 'axios';

// 创建一个 Axios 实例
const apiClient = axios.create({
    baseURL: 'http://localhost:8080/api/v0',  // 基础URL可以根据你的后端服务调整
    headers: {
        'Content-Type': 'application/json'
    }
});

// 添加请求拦截器
apiClient.interceptors.request.use(
    config => {
        // 在发送请求之前自动为每个请求添加 Authorization 头
        const token = localStorage.getItem('token');
        if (token) {
            config.headers['Authorization'] = `${token}`;
        }
        return config;
    },
    error => {
        // 处理请求错误
        return Promise.reject(error);
    }
);

export default apiClient;

import axios from 'axios';
import router from "@/router";
import {message} from "ant-design-vue";
import {getToken} from "@/utils/auth.ts";
import NProgress from "@/utils/NProgress.ts";

const http = axios.create({
    baseURL: import.meta.env.VITE_APP_API_URL,
    headers: {
        'Content-Type': 'application/json'
    }
})
http.interceptors.request.use(config => {
    NProgress.start()
    const token = getToken()
    token && (config.headers.Authorization = `Bearer ${token}`)
    return config;
})
http.interceptors.response.use(
    response => {
        NProgress.done()
        let {msg, data, code} = response.data;
        if (code === 200) {
            return data;
        } else if (code === 500) {
            message.error(msg)
        } else if (code === 401) {
            message.error("请先登录")
            window.sessionStorage.clear()
            router.push('/login')
        } else if (code === 403) {
            message.error("权限不足")
        } else {
            message.error(`服务异常：${msg}`)
        }
        return Promise.reject(new Error(msg))
    },
    error => {
        NProgress.done()
        if (error.message.includes("401")) {
            message.error("请先登录")
        } else if (error.message.includes("403")) {
            message.error("权限不足")
        } else if (error.message.includes("500")) {
            message.error("服务异常，请稍后再试")
        } else if (error.message.includes('timeout')) {
            message.error('请求超时，请稍后再试')
        } else if (error.message.includes('Network Error')) {
            message.error('网络连接错误，请检查网络连接')
        } else if (error.message.includes('Request failed with status code')) {
            message.error(`服务异常：${error.message}`)
        } else {
            message.error(`网络错误：${error.message}`)
        }
        return Promise.reject(new Error(error.message))
    }
)

export default http;

import axios from 'axios';
import {getToken} from "@/utils/token.ts";
import NProgress from "@/utils/NProgress.ts";
import {message} from "ant-design-vue";
import {useUserStore} from "@/store"

const http = axios.create({
    timeout: 30000,
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
        const {msg, data, code} = response.data;
        if (code === 200) {
            return data
        } else if (code === 500) {
            return Promise.reject(new Error(msg || "服务异常，请稍后再试"))
        } else if (code === 401) {
            useUserStore().logout()
            return Promise.reject(new Error(msg || "请先登录"))
        } else if (code === 403) {
            return Promise.reject(new Error(msg || "权限不足"))
        }
        return Promise.reject(new Error(msg))
    },
    error => {
        NProgress.done()
        const errorMsg = error.response?.data?.msg || error.message
        if (error.message.includes("401")) {
            message.error(errorMsg || "请先登录")
            useUserStore().logout()
            return Promise.reject(errorMsg || "请先登录")
        } else if (error.message.includes("403")) {
            message.error(errorMsg || "权限不足")
            return Promise.reject(errorMsg || "权限不足")
        } else if (error.message.includes("500")) {
            return Promise.reject(errorMsg || "服务异常，请稍后再试")
        } else if (error.message.includes('timeout')) {
            return Promise.reject("请求超时，请稍后再试")
        } else if (error.message.includes('Network Error')) {
            return Promise.reject("网络连接异常，请检查网络连接")
        } else if (error.message.includes('Request failed with status code')) {
            return Promise.reject(new Error(error.message))
        }
        return Promise.reject(new Error(error.message))
    }
)

export default http;

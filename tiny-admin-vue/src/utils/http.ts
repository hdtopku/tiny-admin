import {t} from '@/utils/i18n.ts'
import axios from 'axios'
import {getToken} from '@/utils/token.ts'
import NProgress from '@/utils/NProgress.ts'
import {message} from 'ant-design-vue'
import {useI18nStore, useUserStore} from '@/store'

const http = axios.create({
    timeout: 6000,
    baseURL: import.meta.env.VITE_APP_API_URL,
    headers: {
        'Content-Type': 'application/json',
    },
})
http.interceptors.request.use((config) => {
    NProgress.start()
    const token = getToken()
    token && (config.headers.Authorization = `Bearer ${token}`)
    if (config.url !== '/system/sysI18nRaw/page') {
        config.headers.language = useI18nStore().locale
    } else {
        config.headers.language = 'NoTranslation'
    }
    return config
})
http.interceptors.response.use(
    (response) => {
        NProgress.done()
        const {msg, data, code} = response.data
        if (code === 200) {
            return data
        } else if (code === 500) {
            message.error(t('操作失败，原因：') + msg)
            return Promise.reject(new Error(msg || t('服务异常，请稍后再试')))
        } else if (code === 401) {
            useUserStore().logout()
            return Promise.reject(new Error(msg || t('请先登录')))
        } else if (code === 403) {
            return Promise.reject(new Error(msg || t('权限不足')))
        }
        return Promise.reject(new Error(msg))
    },
    (error) => {
        NProgress.done()
        const errorMsg = error.response?.data?.msg || error.response?.statusText || error.message
        if (error.message.includes('401')) {
            message.error(errorMsg || t('请先登录'))
            useUserStore().logout()
            return Promise.reject(errorMsg || t('请先登录'))
        } else if (error.message.includes('403')) {
            message.error(errorMsg || t('权限不足'))
            return Promise.reject(errorMsg || t('权限不足'))
        } else if (errorMsg.includes('Internal Server Error') || error.message.includes('500')) {
            message.error('服务异常，请稍后再试')
            return Promise.reject(errorMsg || t('服务异常，请稍后再试'))
        } else if (error.message.includes('timeout')) {
            return Promise.reject(t('请求超时，请稍后再试'))
        } else if (error.message.includes('Network Error')) {
            return Promise.reject(t('网络连接异常，请检查网络连接'))
        } else if (errorMsg.includes('Internal Server Error')) {
            return Promise.reject(t('服务异常，请稍后再试'))
        } else if (error.message.includes('Request failed with status code')) {
            return Promise.reject(new Error(error.message))
        }
        return Promise.reject(new Error(error.message))
    }
)

export default http

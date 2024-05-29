import {defineStore} from "pinia";
import {postLogin} from "@/api/auth.ts";
import router from "@/router";
import {setToken} from "@/utils/auth.ts";

export type userType = {
    avatar?: string,
    username?: string,
    nickname?: string,
    role?: Array<string>,
    token?: string,
}
export const useUserStore = defineStore('user', () => {
    const userInfo = ref<userType>({})
    const login = (data: { username: string, password: string }) => {
        return new Promise((resolve) => {
            postLogin(data).then(({token = ''}) => {
                setToken(token)
                resolve(router.push('/'))
            })
        })
    }
    return {
        userInfo,
        login,
        clearUserInfo() {
            userInfo.value = {
                avatar: '',
                username: '',
                nickname: '',
                role: [],
                token: '',
            }
        }
    }
}, {
    persist: {
        storage: localStorage,
    }
})

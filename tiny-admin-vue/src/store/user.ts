import {defineStore} from "pinia";
import {postLogin} from "@/api/auth.ts";
import router from "@/router";
import {setToken} from "@/utils/auth.ts";

export type UserType = {
    avatar?: string,
    username?: string,
    nickname?: string,
    menuTree?: Array<string>,
}
export const useUserStore = defineStore('user', () => {
    const userInfo = ref<UserType>({})
    const login = async (data: { username: string, password: string }) => {
        return postLogin(data).then((res) => {
            setToken(res.token)
            userInfo.value = res.userInfo
            router.push('/')
            return res
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
                menuTree: []
            }
        }
    }
}, {
    persist: {
        storage: localStorage,
    }
})

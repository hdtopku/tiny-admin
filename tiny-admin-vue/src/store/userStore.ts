import {defineStore} from "pinia";
import {postLogin} from "@/api/auth.ts";
import router from "@/router";
import {setToken} from "@/utils/auth.ts";
import {ItemType} from 'ant-design-vue'

const modules = import.meta.glob('../views/**/*.vue')

export type UserType = {
    avatar?: string,
    username?: string,
    nickname?: string,
    menuTree?: any[],
}
export const userStore = defineStore('user', () => {
    const routeList = ref<any>([])
    const userInfo = ref<UserType>({})
    const login = async (data: { username: string, password: string }) => {
        return postLogin(data).then((res) => {
            setToken(res.token)
            userInfo.value = res.userInfo
            router.push('/')
            return res
        })
    }
    const getSidebar = (): ItemType[] => {
        const dfs = (menuTree: any[] | undefined) => {
            return menuTree?.map((item) => {
                const menu: ItemType = {
                    key: item?.key,
                    label: item?.label,
                }
                if (item.children && item.children.length > 0) {
                    menu.children = dfs(item.children)
                }
                return menu
            }) || []
        }
        return dfs(userInfo.value?.menuTree)
    }
    const getRouteList = (): any[] => {
        const routeList: any[] = []
        const dfs = (menuTree: any[] | undefined) => {
            for (let item of menuTree || []) {
                item.children?.length && dfs(item.children)
                if (item.menuType !== 1) continue
                const path = item.component.split('/').filter(i => i.length > 0).join('/')
                routeList.push({
                    path: item.url,
                    name: item.name,
                    component: modules[`../views/${path}.vue`],
                })
            }
        }
        dfs(userInfo.value?.menuTree)
        return routeList
    }
    return {
        userInfo,
        login,
        routeList,
        getSidebar,
        getRouteList,
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
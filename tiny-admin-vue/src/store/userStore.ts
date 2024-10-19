import {defineStore} from "pinia";
import {getSelfInfo, postLogin} from "@/api/system/auth.ts";
import {setToken} from "@/utils/token.ts";
import {ItemType} from 'ant-design-vue';
import {GetIcon} from "@/components/CustomIcon.ts";
import websocketClient from "@/utils/websocket.ts";

const modules = import.meta.glob('../views/**/*.vue')

export type UserType = {
    avatar?: string,
    username?: string,
    nickname?: string,
    email?: string,
    phone?: string,
    menuTree?: any[],
    publicMenuList?: any[]
}
const setMyselfOnlineUrl = '/app/setMyselfOnline'

export const userStore = defineStore('user', () => {
    const routeList = ref<any>([]);
    const userInfo = ref<UserType>({})
    const login = async (data: any) => {
        return postLogin(data).then((res: any) => {
            setToken(res.token)
            userInfo.value = res.userInfo
            websocketClient.publish({
                destination: setMyselfOnlineUrl,
                body: userInfo.value.username
            })
        })
    }
    const refreshUserInfo = async () => {
        return getSelfInfo().then((res: any) => {
            userInfo.value = res
            getSidebar()
            return res
        })
    }
    const getSidebar = (): ItemType[] => {
        const dfs = (menuTree: any[] | undefined) => {
            return menuTree?.filter(item => !item.hidden && item.type === 1).map((item) => {
                const menu: any = {
                    key: item?.url,
                    label: item?.label,
                    icon: () => GetIcon(item?.icon || ''),
                }
                if (item.children && item.children.length > 0) {
                    menu.children = dfs(item.children)
                }
                if (!(menu.children?.length > 0)) {
                    menu.children = null
                }
                return menu
            }) || [];
        }
        return dfs(userInfo.value?.menuTree)
    };
    const getRouteList = (): any[] => {
        const routeList: any[] = []
        const dfs = (menuTree: any[] | undefined) => {
            for (let item of menuTree || []) {
                item.children?.length && dfs(item.children)
                if (item.type === 2) continue
                let path = item.component?.split('/').filter(i => i.length > 0).join('/')
                if (item.unauthorized) {
                    path = '403'
                }
                if (!item.unauthorized && !item.component?.length && !item.redirect?.length && item.children?.length) {
                    item.redirect = item.children[0].url
                }
                let route = {
                    path: item.url,
                    name: item.name,
                    component: modules[`../views/${path}.vue`],
                    meta: {
                        keepAlive: item?.keepAlive ?? true,
                    },
                    redirect: undefined
                }
                if (item.redirect?.length) {
                    route.redirect = item.redirect
                }
                routeList.push(route)
            }
        }
        dfs(userInfo.value?.menuTree)
        return routeList
    }
    const getBtnPermissionSet = () => {
        const permissionSet: Set<string> = new Set();
        const dfs = (menuTree: any[] | undefined) => {
            for (let item of menuTree || []) {
                item.children?.length && dfs(item.children)
                if (item.type === 2 && item.permission?.length) {
                    permissionSet.add(item.permission)
                }
            }
            return permissionSet;
        }
        return dfs(userInfo.value?.menuTree)
    }
    const logout = () => {
        localStorage.removeItem('token')
        localStorage.removeItem('user')
        localStorage.removeItem('menu')
        location.reload()
    }
    return {
        userInfo,
        logout,
        login,
        routeList,
        getSidebar,
        getRouteList,
        refreshUserInfo,
        getBtnPermissionSet,
    };
}, {
    persist: {
        storage: localStorage,
    }
});

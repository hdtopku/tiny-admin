import {defineStore} from "pinia";
import {getSelfInfo, postLogin} from "@/api/auth.ts";
import router from "@/router";
import {setToken} from "@/utils/token.ts";
import {ItemType} from 'ant-design-vue';
import {GetIcon} from "@/components/CustomIcon.ts";

const modules = import.meta.glob('../views/**/*.vue')

export type UserType = {
    avatar?: string,
    username?: string,
    nickname?: string,
    menuTree?: any[],
    publicMenuList?: any[]
}

export const userStore = defineStore('user', () => {
    const routeList = ref<any>([]);
    const userInfo = ref<UserType>({});
    const login = async (data: { username: string; password: string; }) => {
        return postLogin(data).then((res) => {
            setToken(res.token);
            userInfo.value = res.userInfo;
            router.push('/');
            return res;
        });
    };
    const refreshUserInfo = async () => {
        return getSelfInfo().then((res: any) => {
            userInfo.value = res;
            return res;
        });
    };
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
        return  dfs(userInfo.value?.menuTree)
    };
    const getRouteList = (): any[] => {
        const routeList: any[] = [];
        const dfs = (menuTree: any[] | undefined) => {
            for (let item of menuTree || []) {
                item.children?.length && dfs(item.children)
                if (item.type === 2) continue
                let path = item.component?.split('/').filter(i => i.length > 0).join('/')
                if (item.unauthorized) {
                    path = '403'
                }
                routeList.push({
                    path: item.url,
                    name: item.name,
                    component: modules[`../views/${path}.vue`],
                    meta: {
                        keepAlive: item?.keepAlive ?? true,
                    }
                })
            }
        };
        dfs(userInfo.value?.menuTree)
        return routeList;
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
    return {
        userInfo,
        login,
        routeList,
        getSidebar,
        getRouteList,
        refreshUserInfo,
        getBtnPermissionSet,
        clearUserInfo() {
            userInfo.value = {
                avatar: '',
                username: '',
                nickname: '',
                menuTree: []
            };
        }
    };
}, {
    persist: {
        storage: localStorage,
    }
});

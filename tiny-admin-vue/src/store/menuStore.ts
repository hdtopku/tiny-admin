import {defineStore} from 'pinia'
import router from "@/router";
import {useUserStore} from "@/store/index.ts";
import {Ref} from "vue";

export const menuStore = defineStore('menu', () => {
    const sidebarOpenKeys = ref([])
    const sidebarSelectedKeys: Ref<string[]> = ref([])
    const sidebarCollapsed = ref(false)
    const getBreadcrumb = (url: any) => {
        const breadcrumb: any = []
        const menuTree = useUserStore().userInfo.menuTree
        const map: Map<string, any> = new Map()
        let parentId = ''
        const dfs = (url: string, menus: any) => {
            for (let menu of menus) {
                if (!menu.url?.length) continue
                if (menu.url === url) {
                    parentId = menu.parentId
                    breadcrumb.unshift({
                        breadcrumbName: menu.name,
                        path: menu.url
                    })
                }
                if (menu.id?.length) map.set(menu.id, menu)
                if (menu?.children?.length) {
                    dfs(url, menu.children)
                }
            }
        }
        dfs(url, menuTree)
        while (parentId.length) {
            const menu = map.get(parentId)
            breadcrumb.unshift({
                breadcrumbName: menu.name,
                path: menu.url
            })
            parentId = menu.parentId
        }
        return breadcrumb
    }

    const openedTabs: any = ref([])
    const activeTab = ref('')
    let keepTabsOrder = false
    const addTab = (route: any) => {
        activeTab.value = route.path
        sidebarSelectedKeys.value = [route.path]
        if (keepTabsOrder && openedTabs.value.some((tab) => tab.key === route.path)) {
            keepTabsOrder = false
            return
        }
        // remove tab, 把当前路由的tab移到最前面
        const index = openedTabs.value.findIndex((tab) => tab.key === route.path)
        if (index !== -1) {
            openedTabs.value.splice(index, 1)
        }
        // remove oldest tab, tabs 超过 6 个时，把最旧的tab移出
        if (openedTabs.value.length >= 6) {
            openedTabs.value.pop()
        }
        // add tab, 把新路由的tab加到最前面
        openedTabs.value.unshift({
            title: route.name,
            key: route.path,
            content: route.path,
            closable: true,
        })
    }
    const removeTab = (key: string) => {
        const index = openedTabs.value.findIndex((tab) => tab.key === key)
        if (index !== -1) {
            openedTabs.value.splice(index, 1)
        }
        if (activeTab.value === key && openedTabs.value.length > 0) {
            if (index > 0) {
                changeTab(openedTabs.value[index - 1].key)
            } else {
                changeTab(openedTabs.value[0].key)
            }
        }
    }
    const changeTab = (key: string) => {
        if (key == null || key === '' || key === activeTab.value) {
            return
        }
        keepTabsOrder = true
        activeTab.value = key
        router.push(key)
    }
    return {
        sidebarOpenKeys,
        sidebarSelectedKeys,
        sidebarCollapsed,
        openedTabs,
        activeTab,
        addTab,
        removeTab,
        changeTab,
        getBreadcrumb
    }
}, {
    persist: {
        storage: localStorage,
    }
})

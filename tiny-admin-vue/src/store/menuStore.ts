import {defineStore} from 'pinia'
import router from "@/router";

export const menuStore = defineStore('menu', () => {
    const sidebarOpenKeys = ref([])
    const sidebarSelectedKeys = ref([])
    const sidebarCollapsed = ref(false)
    const openedTabs: any = ref([])
    const activeTab = ref('')
    let keepTabsOrder = false
    const addTab = (route: any) => {
        activeTab.value = route.path
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
        changeTab
    }
}, {
    persist: {
        storage: localStorage,
    }
})

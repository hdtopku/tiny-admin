import {defineStore} from 'pinia'
import router from "@/router";

export const menuStore = defineStore('menu', () => {
    const sidebarOpenKeys = ref([])
    const sidebarSelectedKeys = ref([])
    const sidebarCollapsed = ref(false)
    const openedTabs: any = ref([])
    const activeTab = ref('')
    let keepOrder = false
    const addTab = (route: any) => {
        activeTab.value = route.path
        if (keepOrder && openedTabs.value.some((tab) => tab.key === route.path)) {
            keepOrder = false
            return
        }
        removeTab(route.path)
        if (openedTabs.value.length >= 6) {
            openedTabs.value.pop()
        }
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
    }
    const changeTab = (key: string) => {
        keepOrder = true
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

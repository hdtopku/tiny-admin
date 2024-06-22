import { defineStore } from 'pinia'
export const menuStore = defineStore('menu',()=>{
    const openKeys = ref(['1','2'])
    const selectedKeys = ref([])
    const menuCollapsed = ref(false)
    return {
        openKeys,
        selectedKeys,
        menuCollapsed
    }
}, {
    persist: {
        storage: localStorage,
    }
})

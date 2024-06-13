import { defineStore } from 'pinia'
export const menuStore = defineStore('menu',()=>{
    const openKeys = ref(['1','2'])
    const selectedKeys = ref([])
    return {
        openKeys,
        selectedKeys
    }
}, {
    persist: {
        storage: localStorage,
    }
})

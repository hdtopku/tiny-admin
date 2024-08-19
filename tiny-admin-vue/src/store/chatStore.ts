import {defineStore} from "pinia";
import {Ref} from "vue";

export const chatStore = defineStore('chat', () => {
    const onlineUsers: Ref<string[]> = ref([])
    return {
        onlineUsers
    }
})
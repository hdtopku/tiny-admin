import {defineStore} from "pinia";
import {ref, Ref} from "vue";
import {chatHistoryDb, unReadCountDb} from "@/utils/localdb.ts";

export const chatStore = defineStore('chat', () => {
    const onlineUsers: Ref<string[]> = ref([])
    const messageCount = ref(0)
    const addNewMessage = (message) => {
        chatHistoryDb.getItem(message.fromUsername).then((res: any) => {
            const originalMessage = JSON.parse(res || '[]')
            originalMessage.push(message)
            chatHistoryDb.setItem(message.fromUsername, JSON.stringify(originalMessage)).then(() => {
                messageCount.value++
            })
        })
        if (!message.isMine) {
            unReadCountDb.getItem(message.fromUsername).then((res: any) => {
                const count = JSON.parse(res || '0')
                unReadCountDb.setItem(message.fromUsername, JSON.stringify(count + 1))
            })
        }
    }
    return {
        onlineUsers,
        messageCount,
        addNewMessage,
    }
})
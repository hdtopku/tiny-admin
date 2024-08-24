import {defineStore} from "pinia";
import {ref, Ref} from "vue";
import {chatHistoryDb, unReadCountDb} from "@/utils/localdb.ts";

export const chatStore = defineStore('chat', () => {
    const onlineUsers: Ref<string[]> = ref([])
    const messageCount = ref(0)
    const addNewMessage = (message) => {
        const key = message.toUsername+ ':' + message.fromUsername
        chatHistoryDb.getItem(key).then((res: any) => {
            const originalMessage = JSON.parse(res || '[]')
            originalMessage.push(message)
            chatHistoryDb.removeItem(key).then(() => {
                chatHistoryDb.setItem(key, JSON.stringify(originalMessage)).then(() => {
                    messageCount.value++
                })
            })
        })
        if (!message.isMine) {
            unReadCountDb.getItem(key).then((res: any) => {
                const count = JSON.parse(res || '0')
                unReadCountDb.setItem(key, JSON.stringify(count + 1))
            })
        }
    }
    return {
        onlineUsers,
        messageCount,
        addNewMessage,
    }
})
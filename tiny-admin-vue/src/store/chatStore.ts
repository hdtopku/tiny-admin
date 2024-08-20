import {defineStore} from "pinia";
import {ref, Ref} from "vue";
import {getUserPage} from "@/api/user.ts";
import websocketClient from "@/utils/websocket.ts";
import {useUserStore} from "@/store/index.ts";

export const chatStore = defineStore('chat', () => {
    const onlineUsers: Ref<string[]> = ref([])
    const allUsers: Ref<any[]> = ref([])
    getUserPage({pageSize: 1000, pageNum: 1}).then((res: any) => {
        websocketClient.publish({
            destination: '/app/getOnlineUsers',
            body: useUserStore().userInfo.username
        })
        const users: any[] = []
        res.records?.forEach(item => {
            users.push({
                id: item.key,
                username: item.username,
                nickname: item.nickname,
                email: item.email,
                phone: item.phone,
                roles: item.roles,
                isOnline: true,
                chatHistory: []
            })
        })
        allUsers.value = users
    })
    const addNewMessage = (message) => {
        for (let user of allUsers.value) {
            if (user.username === message.fromUsername) {
                user.chatHistory.push({
                    message: message.content,
                    isMine: user.username === message.toUsername
                })
                break
            }
        }
    }
    return {
        onlineUsers,
        allUsers,
        addNewMessage
    }
})

// [
// {
//     "fromUsername": "test",
//     "chatHistory": [
//         {
//             "isMine": false,
//             "content": "你好！"
//         },
//         {
//             "isMine": true,
//             "content": "你好！请问有什么可以帮助你？"
//         }
//     ]
// }
// ]
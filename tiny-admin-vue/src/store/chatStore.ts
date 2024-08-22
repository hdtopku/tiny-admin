import {defineStore} from "pinia";
import {ref, Ref} from "vue";
import {getUserPage} from "@/api/user.ts";
import {useUserStore} from "@/store/index.ts";
import {chatHistoryDb} from "@/utils/localdb.ts";
import websocketClient from "@/utils/websocket.ts";

export const chatStore = defineStore('chat', () => {
    const onlineUsers: Ref<string[]> = ref([])
    const allUsersList: Ref<any> = ref([])
    const usersMap: any = new Map()
    const getChatHistories = () => {
        const chatUsernames: Ref<string[]> = ref([])
        const usersList: any[] = []
        const map = new Map(usersMap)
        chatHistoryDb.keys().then((keys: string[]) => {
            chatUsernames.value = keys
            keys.forEach(key => {
                if (map.has(key)) {
                    usersList.unshift(map.get(key))
                    map.delete(key)
                }
            })
            allUsersList.value = usersList.concat(Array.from(map.values()))
        })
    }
    getUserPage({pageSize: 1000, pageNum: 1}).then((res: any) => {
        // chatHistoryDb.setItem('chatUsers', JSON.stringify(allUsers.value))
        res.records?.forEach(item => {
            usersMap.set(item.username, item)
        })
        websocketClient.publish({
            destination: '/app/getOnlineUsers',
            body: useUserStore().userInfo.username
        })
        getChatHistories()
    })
    const addNewMessage = (message) => {
        chatHistoryDb.getItem(message.fromUsername).then((res: any) => {
            chatHistoryDb.setItem(message.fromUsername, JSON.stringify([...JSON.parse(res || '[]'), message])).then(() => {
                getChatHistories()
            })
        })
    }
    return {
        onlineUsers,
        allUsersList,
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
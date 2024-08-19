import {Client} from "@stomp/stompjs";
import {useChatStore} from '@/store'

const websocketClient = new Client({
    brokerURL: 'ws://localhost:8080/spring-boot-tutorial',
})
const getOnlineUsersTopicUrl = '/topic/onlineUsers'
const getOnlineUsersUrl = '/app/getOnlineUsers'
websocketClient.onConnect = () => {
    websocketClient.subscribe(getOnlineUsersTopicUrl, (users) => {
        useChatStore().onlineUsers = JSON.parse(users.body) || []
    })
    websocketClient.publish({
        destination: getOnlineUsersUrl,
    })
}
websocketClient.onWebSocketError((error) => {
    console.log('WebSocket Error:', error)
})
websocketClient.onDisconnect = () => {
    console.log('WebSocket Disconnected')
}

export default websocketClient
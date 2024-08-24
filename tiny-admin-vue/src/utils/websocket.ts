import {Client} from "@stomp/stompjs";
import {useChatStore, useUserStore} from '@/store'

import { notification } from 'ant-design-vue'

const websocketClient = new Client({
    brokerURL: 'ws://localhost:8080/websocket',
})
const getOnlineUsersTopicUrl = '/topic/onlineUsers'
// const receivePublicMessageTopicUrl = '/topic/receivePublicMessage'
const receivePrivateMessageTopicUrl = '/topic/receivePrivateMessage'
const getOnlineUsersUrl = '/app/getOnlineUsers'
websocketClient.onConnect = () => {
    websocketClient.subscribe(getOnlineUsersTopicUrl, (users) => {
        useChatStore().onlineUsers = JSON.parse(users.body) || []
    })
    websocketClient.subscribe('/user/' + useUserStore().userInfo.username + receivePrivateMessageTopicUrl, (msg) => {
        const m = JSON.parse(msg.body)
        useChatStore().addNewMessage(m)
        const openNotification = () => {
            notification.open({
                message: 'You have a new message from: ' + m.fromUsername,
                description:
                    m.content,
                onClick: () => {
                    console.log('Notification Clicked!');
                },
            });
        };
        openNotification()

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
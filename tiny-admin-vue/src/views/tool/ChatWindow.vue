<template>
  <a-layout has-sider>
    <a-layout-sider
        :style="{ overflow: 'auto', height: '100vh', position: 'fixed',  }"
    >
      <a-menu v-model:selectedKeys="selectedKeys" theme="dark" mode="inline">
        <a-menu-item :icon="() => h(item.icon)" v-for="(item, index) in userList" :key="index">
          <div class="flex justify-between items-center">
            <span class="nav-text">{{ item.name }}</span>
            <div :class="{'bg-green-500': item.isOnline, 'bg-gray-500':!item.isOnline}"
                 class="w-2 h-2 rounded-full flex justify-center items-center"></div>
          </div>
        </a-menu-item>
      </a-menu>
    </a-layout-sider>
    <a-layout :style="{ marginLeft: '200px' }">
      <a-layout-header :style="{ background: '#fff', padding: 0 }"/>
      <a-layout-content :style="{ margin: '24px 16px 120px', overflow: 'initial' }">
        <p class="text-xl">Hi, I am {{ userList[selectedKeys[0]].name }}, happy to chat with you.</p>
        <div v-for="(item, index) in chatHistory.concat(chatHistory)" :key="index">
          <div class="chat-item grid grid-cols-[200px_1fr)]">
            <div :class="{'justify-self-end': item.isMine}" class="chat-content">
              <div :class="{'text-right': item.isMine}" class="chat-name text-sm text-gray-500 my-2">{{
                  item.name
                }}
              </div>
              <div class="chat-message text-md">
                <span class=" text-gray-800 bg-white p-2 rounded-lg">{{ item.message }}</span>
              </div>
            </div>
          </div>
        </div>
      </a-layout-content>
      <div
          class="px-4 py-2 bg-gray-100 fixed bottom-0 right-0 w-[calc(100%_-_400px)] border-t shadow-sm float-right grid grid-cols-[1fr_100px] place-items-center">
        <a-textarea
            v-model:value="currentMessage"
            placeholder="input search text"
            enter-button="Search"
            size="large"
            allow-clear
            @search="handleSendMessage"
        />
        <a-button type="primary" @click="getOnlineUsers">获取在线用户</a-button>
        <a-button type="primary" class="w-full ml-2" @click="handleSendMessage">Send</a-button>
      </div>
    </a-layout>
  </a-layout>
</template>
<script lang="ts" setup>
import {h, Ref, ref} from 'vue';
import {
  AppstoreOutlined,
  BarChartOutlined,
  CloudOutlined,
  ShopOutlined,
  TeamOutlined,
  UploadOutlined,
  UserOutlined,
  VideoCameraOutlined,
} from '@ant-design/icons-vue';
import {Client} from "@stomp/stompjs";

const websocketClient = new Client({
  brokerURL: 'ws://localhost:8080/spring-boot-tutorial',
})
const topicUrl = '/topic/onlineUsers'
const onlineUrl = '/app/online'
const getOnlineUsersUrl = '/app/getOnlineUsers'
const messageTopicUrl = '/topic/messages'
const onlineUsers: Ref<any[]> = ref([])
websocketClient.onConnect = () => {
  websocketClient.subscribe(topicUrl, (users) => {
    onlineUsers.value = JSON.parse(users.body)
    const userSet = new Set(onlineUsers.value.map(item => item.username))
    userList.value.forEach((user) => {
      user.isOnline = userSet.has(user.name);
    })
  })
  websocketClient.subscribe(messageTopicUrl, (message) => {
    const messageObj = JSON.parse(message.body)
    if (messageObj.message?.length)
      chatHistory.value.push(messageObj)
  })
  // for (let user of users) {
  //   login(user)
  // }
}
websocketClient.activate()
websocketClient.onWebSocketError((error) => {
  console.log('WebSocket Error:', error)
})
websocketClient.onDisconnect = () => {
  console.log('WebSocket Disconnected')
}

const users = [{id: '123456', username: 'admin'}, {id: '132412', username: 'test'}, {
  id: '138291',
  username: 'lisi'
}, {id: '810183', username: 'wangwu'}]
const login = (user) => {
  websocketClient.publish({
    destination: onlineUrl,
    body: JSON.stringify(user)
  })
}
const getOnlineUsers = () => {
  websocketClient.publish({
    destination: getOnlineUsersUrl,
  })
}
const currentMessage = ref('')
const handleSendMessage = () => {
  const message = {
    id: users[0].id,
    username: users[0].username,
    message: currentMessage.value,
    action: 'COMMENTED',
    timestamp: null
  }
  websocketClient.publish({
    destination: '/app/chat.sendMessage',
    body: JSON.stringify(message)
  })
  currentMessage.value = ''
}

const selectedKeys = ref<string[]>(['4']);
const chatHistory: Ref<any[]> = ref<any[]>([{
  id: "1",
  name: 'admin',
  message: 'Hello, how can I help you?',
  time: '2022-01-01 12:00:00',
  isMine: false,
}, {
  id: "2",
  name: 'test',
  message: 'I want to ask something.',
  time: '2022-01-01 12:00:00',
  isMine: false,
}, {
  id: "3",
  name: 'test 2',
  message: 'How about this question?',
  time: '2022-01-01 12:00:00',
  isMine: false,
}, {
  id: "4",
  name: 'test 3',
  message: 'What is the price of the product?',
  time: '2022-01-01 12:00:00',
  isMine: true,
}, {
  id: "5",
  name: 'operator',
  message: 'I am sorry, I cannot answer this question.',
  time: '2022-01-01 12:00:00',
  isMine: false,
}, {
  id: "6",
  name: 'developer',
  message: 'I am sorry, I cannot answer this question.',
  time: '2022-01-01 12:00:00',
  isMine: false,
}, {
  id: "7",
  name: 'zhangsan',
  message: 'I am sorry, I cannot answer this question.',
  time: '2022-01-01 12:00:00',
  isMine: false,
}, {
  id: "8",
  name: 'lisi',
  message: 'I am sorry, I cannot answer this question.',
  time: '2022-01-01 12:00:00',
  isMine: false,
}]);
const userList = ref([{
  id: "1",
  name: 'admin',
  icon: UserOutlined,
  isOnline: false,
}, {
  id: "2",
  name: 'test',
  icon: VideoCameraOutlined,
  isOnline: false,
}, {
  id: "3",
  name: 'test 2',
  icon: UploadOutlined,
  isOnline: false,
}, {
  id: "4",
  name: 'test 3',
  icon: BarChartOutlined,
  isOnline: false,
}, {
  id: "5",
  name: 'operator',
  icon: CloudOutlined,
  isOnline: false,
}, {
  id: "6",
  name: 'developer',
  icon: AppstoreOutlined,
  isOnline: false,
}, {
  id: "7",
  name: 'zhangsan',
  icon: TeamOutlined,
}, {
  id: "8",
  name: 'lisi',
  icon: ShopOutlined,
  isOnline: false,
}])
watch(selectedKeys, (val) => {
  console.log(val)
})
</script>
<style scoped>
#components-layout-demo-fixed-sider .logo {
  height: 32px;
  background: rgba(255, 255, 255, 0.2);
  margin: 16px;
}

.site-layout .site-layout-background {
  background: #fff;
}

[data-theme='dark'] .site-layout .site-layout-background {
  background: #141414;
}
</style>

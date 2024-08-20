<template>
  <a-layout has-sider>
    <a-layout-sider
        :style="{ overflow: 'auto', height: '100vh', position: 'fixed',  }"
    >
      <a-menu v-model:selectedKeys="selectedKeys" theme="dark" mode="inline">
        <a-menu-item
            v-for="(item, index) in useChatStore().allUsers" :key="index">
          <div class="flex justify-between items-center">
            <span class="nav-text">{{ item.nickname }}</span>
            <div :class="{'bg-green-500': item.isOnline, 'bg-gray-500':!item.isOnline}"
                 class="w-2 h-2 rounded-full flex justify-center items-center"></div>
          </div>
        </a-menu-item>
      </a-menu>
    </a-layout-sider>
    <a-layout :style="{ marginLeft: '200px' }">
      <a-layout-header :style="{ background: '#fff', padding: 0 }"/>
      <a-layout-content :style="{ margin: '24px 16px 120px', overflow: 'initial' }">
        <div v-for="(item, index) in useChatStore().allUsers[selectedKeys[0]]?.chatHistory" :key="index">
          <div class="chat-item grid grid-cols-[200px_1fr)]">
            <div :class="{'justify-self-end': item.isMine}" class="chat-content">
              <div :class="{'text-right': item.isMine}" class="chat-name text-sm text-gray-500 my-2">{{
                  item.isMine ? 'Me' : useChatStore().allUsers[selectedKeys[0]].username
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
        <a-button type="primary" class="w-full ml-2" @click="handleSendMessage">Send</a-button>
      </div>
    </a-layout>
  </a-layout>
</template>
<script lang="ts" setup>
import {ref} from 'vue';
import websocketClient from "@/utils/websocket.ts";
import {useChatStore, useUserStore} from "@/store";

const getOnlineUsersUrl = '/app/getOnlineUsers'

let onlineUserSet = new Set()
// const sortUsers = (users: any) => {
//   const onlineUsers: any[] = [], offlineUsers: any[] = []
//   for (const user of users) {
//     if (onlineUserSet.has(user.username)) {
//       user.isOnline = true
//       onlineUsers.push(user)
//     } else {
//       user.isOnline = false
//       offlineUsers.push(user)
//     }
//   }
//   useChatStore().allUsers = onlineUsers.concat(offlineUsers)
// }
// getUserPage({pageSize: 1000, pageNum: 1}).then((res: any) => {
//   websocketClient.publish({
//     destination: getOnlineUsersUrl,
//     body: useUserStore().userInfo.username
//   })
//   const users: any[] = []
//   res.records?.forEach(item => {
//     users.push({
//       id: item.key,
//       username: item.username,
//       nickname: item.nickname,
//       email: item.email,
//       phone: item.phone,
//       roles: item.roles,
//       isOnline: onlineUserSet.has(item.username),
//       chatHistory: []
//     })
//   })
// sortUsers(users)
// })

const currentMessage = ref('')

const handleSendMessage = () => {
  const message = {
    fromUsername: useUserStore().userInfo.username,
    toUsername: useChatStore().allUsers[selectedKeys.value[0]].username,
    content: currentMessage.value,
  }
  websocketClient.publish({
    destination: '/app/chat.sendMessage',
    body: JSON.stringify(message)
  })
  currentMessage.value = ''
}

const selectedKeys = ref<number[]>([0]);
watch(selectedKeys, () => {
  console.log(selectedKeys.value)
})

const chatStore = useChatStore()
watch(chatStore, () => {
  onlineUserSet = new Set(chatStore.onlineUsers || [])
  // sortUsers(useChatStore().allUsers)
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

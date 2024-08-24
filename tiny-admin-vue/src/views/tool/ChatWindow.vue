<template>
  <a-layout has-sider>
    <a-layout-sider
        :style="{ overflow: 'auto', height: '100vh', position: 'fixed',  }"
    >
      <a-menu v-model:selectedKeys="selectedKeys" theme="dark" mode="inline">
        <a-menu-item
            v-for="(item, index) in allUsersList" :key="index">
          <div class="grid grid-cols-[130px_1fr] justify-between items-center">
            <div class="flex justify-start items-center">
              <span :class="{'bg-green-500': item.isOnline, 'bg-gray-500':!item.isOnline}"
                    class="w-2 h-2 rounded-full mr-2"></span>
              <span class="nav-text">{{ item.nickname }}</span>
            </div>
            <span v-if="unReadCountMap.get(useUserStore().userInfo.username+':'+item.username)>0"
                  class="bg-red-500 rounded-full text-xs text-white text-center">{{
                unReadCountMap.get(useUserStore().userInfo.username+":"+item.username)
              }}</span>
          </div>
        </a-menu-item>
      </a-menu>
    </a-layout-sider>
    <a-layout :style="{ marginLeft: '200px' }">
      <a-layout-header :style="{ background: '#fff', padding: 0 }"/>
      <a-layout-content :style="{ margin: '24px 16px 120px', overflow: 'initial' }">
        <div v-for="(item, index) in currentChatHistory" :key="index">
          <div class="chat-item grid grid-cols-[200px_1fr)]">
            <div :class="{'justify-self-end': item.isMine}" class="chat-content">
              <div :class="{'text-right': item.isMine}" class="chat-name text-sm text-gray-500 my-2">{{
                  item.isMine ? 'Me' : item.fromUsername
                }}
              </div>
              <div class="chat-message text-md">
                <span class=" text-gray-800 bg-white p-2 rounded-lg">{{ item.content }}</span>
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
import {Ref, ref} from 'vue';
import {useChatStore, useUserStore} from "@/store";
import {chatHistoryDb, unReadCountDb} from "@/utils/localdb.ts";
import websocketClient from "@/utils/websocket.ts";
import {getUserPage} from "@/api/user.ts";

const usersMap: any = new Map()
const allUsersList: Ref<any> = ref([])
const getChatHistories = () => {
  const chatUsernames: Ref<string[]> = ref([])
  const usersList: any[] = []
  const map: any = new Map(usersMap)
  chatHistoryDb.keys().then((keys: string[]) => {
    chatUsernames.value = keys
    keys.forEach(key => {
      if (map.has(key)) {
        usersList.push(map.get(key))
        map.delete(key)
      }
    })
    allUsersList.value = usersList.concat(Array.from(map.values()))
  })
}
getUserPage({pageSize: 1000, pageNum: 1}).then((res: any) => {
  // chatHistoryDb.setItem('chatUsers', JSON.stringify(allUsers.value))
  res.records?.forEach(item => {
    usersMap.set(useUserStore().userInfo.username+":"+item.username, item)
  })
  websocketClient.publish({
    destination: '/app/getOnlineUsers',
    body: useUserStore().userInfo.username
  })
  getChatHistories()
})

const currentMessage = ref('')

const handleSendMessage = () => {
  const message = {
    fromUsername: useUserStore().userInfo.username,
    toUsername: allUsersList.value[selectedKeys.value[0]].username,
    content: currentMessage.value,
  }
  websocketClient.publish({
    destination: '/app/chat.sendMessage',
    body: JSON.stringify(message)
  })
  const localMessage = {
    fromUsername: allUsersList.value[selectedKeys.value[0]].username,
    toUsername: useUserStore().userInfo.username,
    content: currentMessage.value,
    isMine: true,
  }
  useChatStore().addNewMessage(localMessage)
  currentMessage.value = ''
}

const selectedKeys = ref<number[]>([0])
const currentChatHistory: Ref<any[]> = ref<any[]>([])
const unReadCountMap: any = ref(new Map())
const dealMessages = () => {
  chatHistoryDb.getItem(useUserStore().userInfo.username+":"+allUsersList.value[selectedKeys.value[0] || 0]?.username).then((res: any) => {
    currentChatHistory.value = JSON.parse(res || '[]')
  })
  unReadCountDb.keys().then((keys: string[]) => {
    unReadCountMap.value.clear()
    keys.forEach(key => {
      unReadCountMap.value.set(key, unReadCountDb.getItem(key) || 0)
      unReadCountDb.getItem(key).then((res: any) => {
        if (res) {
          unReadCountMap.value.set(key, res)
        }
      })
    })
  })
}
watch(selectedKeys, () => {
  if (!allUsersList.value.length) return
  const username = allUsersList.value[selectedKeys.value[0] || 0]?.username
  if (!username.length) return
  const  key = useUserStore().userInfo.username+":"+username
  unReadCountDb.getItem(key).then((res: any)=>{
    if(res) {
      unReadCountDb.removeItem(key)
    }
  })
  dealMessages()
}, {immediate: true})

const chatStore = useChatStore()
watch(chatStore, () => {
  getChatHistories()
  const username = allUsersList.value[selectedKeys.value[0] || 0]?.username || ''
  if (!username.length) return
  dealMessages()
}, {immediate: true})

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

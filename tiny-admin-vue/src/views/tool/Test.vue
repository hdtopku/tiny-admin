<script lang="ts" setup>
import {Client} from "@stomp/stompjs";
import {Ref} from "vue";
import ChatWindow from "@/views/tool/ChatWindow.vue";

const websocketClient = new Client({
  brokerURL: 'ws://localhost:8080/spring-boot-tutorial',
})
const topicUrl = '/topic/onlineUsers'
const onlineUrl = '/app/online'
const getOnlineUsersUrl = '/app/getOnlineUsers'
const onlineUsers: Ref<any[]> = ref([])
websocketClient.onConnect = () => {
  console.log('WebSocket connected')
  websocketClient.subscribe(topicUrl, (users) => {
    onlineUsers.value = JSON.parse(users.body)
  })
  websocketClient.publish({
    destination: getOnlineUsersUrl,
  })
}
websocketClient.activate()
websocketClient.onWebSocketError((error) => {
  console.log('WebSocket Error:', error)
})
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
const offline = (user)=>{
  websocketClient.publish({
    destination: '/app/offline',
    body: user.id
  })
}

</script>


<template>
  <div class="relative">
<!--    <a-row :gutter="16">-->
<!--      <a-col class="gutter-row text-center" :span="6" v-for="(item, index) in users" :key="index">-->
<!--        <a-card hoverable>-->
<!--          <template #title>-->
<!--            {{ item.username }}-->
<!--          </template>-->
<!--          <template #extra>-->
<!--            <a-button v-if="onlineUsers.find((user) => user.id === item.id)" @click="offline(item)" type="primary">退出</a-button>-->
<!--            <a-button v-else @click="login(item)" type="primary">登录</a-button>-->
<!--          </template>-->
<!--          <a-list size="small" bordered :data-source="onlineUsers">-->
<!--            <template #renderItem="{ item }">-->
<!--              <a-list-item>{{ item.username }}</a-list-item>-->
<!--            </template>-->
<!--            <template #header>-->
<!--              <div>在线用户</div>-->
<!--            </template>-->
<!--          </a-list>-->
<!--        </a-card>-->
<!--      </a-col>-->
<!--    </a-row>-->
    <ChatWindow/>
  </div>
</template>

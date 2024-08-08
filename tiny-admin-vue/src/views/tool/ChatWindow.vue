<template>
  <a-layout has-sider>
    <a-layout-sider
        :style="{ overflow: 'auto', height: '100vh', position: 'fixed',  }"
    >
      <div class="logo"/>
      <a-menu v-model:selectedKeys="selectedKeys" theme="dark" mode="inline">
        <a-menu-item :icon="() => h(item.icon)" v-for="(item, index) in userList" :key="index">
          <span class="nav-text">{{ item.name }}</span>
        </a-menu-item>
      </a-menu>
    </a-layout-sider>
    <a-layout :style="{ marginLeft: '200px' }">
      <a-layout-header :style="{ background: '#fff', padding: 0 }"/>
      <a-layout-content :style="{ margin: '24px 16px 120px', overflow: 'initial' }">
        <p class="text-xl">Hi, I am {{ userList[selectedKeys[0]].name }}, happy to chat with you.</p>
        <div v-for="(item, index) in chatHistory.concat(chatHistory)" :key="index">
          <div class="chat-item grid grid-cols-[200px_1fr)]">
            <div class="chat-content" :class="{'justify-self-end': item.isRight}">
              <div class="chat-name text-sm text-gray-500 my-2" :class="{'text-right': item.isRight}">{{
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
      <div class="px-4 py-2 bg-gray-100 fixed bottom-0 right-0 w-[calc(100%_-_400px)] border-t shadow-sm float-right grid grid-cols-[1fr_100px] place-items-center">
        <a-textarea
            v-model:value="value"
            placeholder="input search text"
            enter-button="Search"
            size="large"
            allow-clear
            @search="onSearch"
        />
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

const selectedKeys = ref<string[]>(['4']);
const chatHistory: Ref<any[]> = ref<any[]>([{
  id: "1",
  name: 'admin',
  message: 'Hello, how can I help you?',
  time: '2022-01-01 12:00:00',
  isRight: false,
}, {
  id: "2",
  name: 'test',
  message: 'I want to ask something.',
  time: '2022-01-01 12:00:00',
  isRight: false,
}, {
  id: "3",
  name: 'test 2',
  message: 'How about this question?',
  time: '2022-01-01 12:00:00',
  isRight: false,
}, {
  id: "4",
  name: 'test 3',
  message: 'What is the price of the product?',
  time: '2022-01-01 12:00:00',
  isRight: true,
}, {
  id: "5",
  name: 'operator',
  message: 'I am sorry, I cannot answer this question.',
  time: '2022-01-01 12:00:00',
  isRight: false,
}, {
  id: "6",
  name: 'developer',
  message: 'I am sorry, I cannot answer this question.',
  time: '2022-01-01 12:00:00',
  isRight: false,
}, {
  id: "7",
  name: 'zhangsan',
  message: 'I am sorry, I cannot answer this question.',
  time: '2022-01-01 12:00:00',
  isRight: false,
}, {
  id: "8",
  name: 'lisi',
  message: 'I am sorry, I cannot answer this question.',
  time: '2022-01-01 12:00:00',
  isRight: false,
}]);
const userList = ref([{
  id: "1",
  name: 'admin',
  icon: UserOutlined,
}, {
  id: "2",
  name: 'test',
  icon: VideoCameraOutlined,
}, {
  id: "3",
  name: 'test 2',
  icon: UploadOutlined,
}, {
  id: "4",
  name: 'test 3',
  icon: BarChartOutlined,
}, {
  id: "5",
  name: 'operator',
  icon: CloudOutlined,
}, {
  id: "6",
  name: 'developer',
  icon: AppstoreOutlined,
}, {
  id: "7",
  name: 'zhangsan',
  icon: TeamOutlined,
}, {
  id: "8",
  name: 'lisi',
  icon: ShopOutlined,
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

import {createApp} from 'vue'
import './style.css'
import App from './App.vue'
import router from './router'
import {createPinia} from 'pinia'
import piniaPluginPersistedState from "pinia-plugin-persistedstate";
import mitt from "mitt";
import permission from "@/directive/permission.ts"
import websocketClient from "@/utils/websocket.ts";
import 'virtual:windi.css'
import i18n from "@/utils/i18n.ts";
import Antd from 'ant-design-vue';

const app = createApp(App)
app.config.globalProperties.$bus = mitt()
const pinia = createPinia()
pinia.use(piniaPluginPersistedState)
app.use(i18n).use(pinia).use(router).use(Antd).mount('#app')
app.directive('permission', {
    mounted(el, binding) {
        permission(el, binding)
    }
})
websocketClient.activate()
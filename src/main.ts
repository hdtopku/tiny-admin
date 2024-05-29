import {createApp} from 'vue'
import './style.css'
import App from './App.vue'
import router from './router'
import 'ant-design-vue/dist/reset.css'
import {createPinia} from 'pinia'
import piniaPluginPersistedState from "pinia-plugin-persistedstate";

const app = createApp(App)
const pinia = createPinia()
pinia.use(piniaPluginPersistedState)
app.use(pinia).use(router).mount('#app')

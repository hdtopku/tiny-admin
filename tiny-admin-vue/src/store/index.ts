import {menuStore} from "@/store/menuStore.ts"
import {userStore} from "@/store/userStore.ts"
import {chatStore} from '@/store/chatStore.ts'
import {i18nStore} from "@/store/i18nStore.ts";

const useMenuStore = () => menuStore()
const useUserStore = () => userStore()
const useChatStore = () => chatStore()
const useI18nStore = () => i18nStore()

export {
    useMenuStore,
    useUserStore,
    useChatStore,
    useI18nStore
}
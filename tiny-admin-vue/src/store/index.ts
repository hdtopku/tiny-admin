import {menuStore} from "@/store/menuStore.ts"
import {userStore} from "@/store/userStore.ts"
import {chatStore} from '@/store/chatStore.ts'

const useMenuStore = () => menuStore()
const useUserStore = () => userStore()
const useChatStore = () => chatStore()

export {
    useMenuStore,
    useUserStore,
    useChatStore,
}
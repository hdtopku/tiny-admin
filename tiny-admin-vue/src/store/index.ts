import {menuStore} from "@/store/menuStore.ts";
import {userStore} from "@/store/userStore.ts";

const useMenuStore = () => menuStore()
const useUserStore = () => userStore()

export {
    useMenuStore,
    useUserStore
}
import {menuStore} from "@/store/menuStore.ts";
import { userStore } from "./userStore";

const useMenuStore = () => menuStore()
const useUserStore = () => userStore()

export {
    useMenuStore,
    useUserStore
}
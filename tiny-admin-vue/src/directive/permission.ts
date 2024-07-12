import {useUserStore} from "@/store";

let permissionSet
const permission = (el: HTMLElement, binding: any) => {
    if (!permissionSet) {
        permissionSet = useUserStore().getBtnPermissionSet()
    }
    const nextSibling: any = el.nextElementSibling
    if (permissionSet.has(binding.value)) {
        nextSibling.parentNode?.removeChild(nextSibling)
    } else {
        el.parentNode?.removeChild(el)
        let showMenu = useUserStore().userInfo.publicMenuList?.some((item: any) => item.unauthorizedStrategy === 1 && item.permission === binding.value)
        if (showMenu) {
            nextSibling.setAttribute("disabled", true)
        } else {
            nextSibling.parentNode?.removeChild(nextSibling)
        }
    }
}

export default permission
import {useUserStore} from "@/store";

let permissionList
const permission=(el, binding)=>{
    if(!permissionList) {
        permissionList = useUserStore().getPermissionList()
    }
    const hasPermission = permissionList.includes(binding.value)
    if(!hasPermission) {
        el.parentNode?.removeChild(el)
    }
}

export default permission
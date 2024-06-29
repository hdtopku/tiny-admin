import http from "@/utils/http.ts";

export const getUserPage = async (data: any) => {
    return http.post("/system/sysUser/page", data)
}

export const saveOrUpdate = async (data: any) => {
    if(data?.key) data.id=data.key
    let {key, ...requestData}: any = {...data}
    return http.post("/system/sysUser/saveOrUpdate", {...requestData})
}

export const updatePassword = async (data: any) => {
    return http.post(`/system/sysUser/updatePassword/${data.id}`, data.password)
}

export const deleteUser = async (id: any) => {
    return http.post(`/system/sysUser/deleteByIds`, [id])
}
export const assignRoles = async (userId, roleIds) => {
    return http.post(`/system/sysUser/assignRoles/${userId}`, roleIds)
}
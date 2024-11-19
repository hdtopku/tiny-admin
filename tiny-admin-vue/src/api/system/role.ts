import http from "@/utils/http.ts"

export const execQuery = async (data: any) => {
    return http.post("/system/sysRole/page", data)
}
export const saveOrUpdate = async (data: any) => {
    return http.post("/system/sysRole/saveOrUpdate", data)
}
export const deleteById = async (id: any) => {
    return http.post("/system/sysRole/deleteByIds" ,[id])
}
export const getRoleList = async () => {
    return http.post("/system/sysRole/list")
}
export const assignMenu = async (roleId: string, menuIds: string[]) => {
    return http.post(`/system/sysRole/assignMenu/${roleId}`, menuIds)
}
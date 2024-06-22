import http from "@/utils/http.ts";

export const getMenuPage = async (data: any) => {
    return http.post("/system/sysMenu/page", data)
}
export const getMenuTree = async () => {
    return http.get("/system/sysMenu/tree")
}
export const saveOrUpdateMenu = async (data: any) => {
    return http.post("/system/sysMenu/saveOrUpdate", data)
}
export const deleteMenuById = async (id: any) => {
    return http.post("/system/sysMenu/deleteByIds",[id])
}
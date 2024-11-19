import http from "@/utils/http.ts";

export const execQuery = async (data: any) => {
    return http.post("/system/sysUser/page", data)
}

export const saveOrUpdate = async (data: any) => {
    return http.post("/system/sysUser/saveOrUpdate", data)
}

export const updatePassword = async (data: any) => {
    return http.post(`/system/sysUser/updatePassword/${data.id}`, data.password)
}

export const deleteById = async (id: any) => {
    return http.post(`/system/sysUser/deleteByIds`, [id])
}
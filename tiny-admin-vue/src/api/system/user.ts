import http from "@/utils/http.ts";

export const getUserPage = async (data: any) => {
    return http.post("/system/sysUser/page", data)
}

export const saveOrUpdateUser = async (data: any) => {
    return http.post("/system/sysUser/saveOrUpdate", data)
}

export const updatePassword = async (data: any) => {
    return http.post(`/system/sysUser/updatePassword/${data.id}`, data.password)
}

export const deleteUserById = async (id: any) => {
    return http.post(`/system/sysUser/deleteByIds`, [id])
}
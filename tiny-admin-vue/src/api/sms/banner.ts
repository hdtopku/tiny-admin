import http from "@/utils/http.ts"

export const saveOrUpdate = async (data: any) => {
    return http.post("/sms/banner/saveOrUpdate", data)
}

export const execQuery = async (data: any) => {
    return http.post("/sms/banner/page", data)
}
export const deleteById = async (id: any) => {
    return http.delete("/sms/banner/" + id)
}
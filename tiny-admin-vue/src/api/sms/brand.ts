import http from "@/utils/http.ts";
export const execQuery = async (data: any) => {
    return http.post("/sms/brand/page", data)
}

export const saveOrUpdate = async (data: any) => {
    return http.post("/sms/brand/saveOrUpdate", data)
}
export const deleteById = async (id: any) => {
    return http.delete("/sms/brand/" + id)
}


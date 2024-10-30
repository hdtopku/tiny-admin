import http from "@/utils/http.ts";

export const getSmsBrandPage = async (data: any) => {
    return http.post("/sms/brand/page", data)
}

export const saveOrUpdateSmsBrand = async (data: any) => {
    return http.post("/sms/brand/saveOrUpdate", data)
}
export const deleteSmsBrandById = async (id: any) => {
    return http.delete("/sms/brand/" + id)
}


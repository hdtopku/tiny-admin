import http from "@/utils/http.ts";

export const getSmsBrandPage = async (data: any) => {
    return http.post("/sms/brand/page", data)
}

export const saveOrUpdateBrand = async (data: any) => {
    return http.post("/sms/brand/saveOrUpdate", data)
}
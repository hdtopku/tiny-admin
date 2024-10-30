import http from "@/utils/http.ts";

export const saveOrUpdateBanner = async (data: any) => {
    return http.post("/sms/banner/saveOrUpdate", data)
}

export const getBannerPage = async (data: any) => {
    return http.post("/sms/banner/page", data)
}
export const deleteBannerById = async (id: any) => {
    return http.delete("/pms/banner/" + id)
}
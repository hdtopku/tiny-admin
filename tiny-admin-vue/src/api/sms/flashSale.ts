import http from "@/utils/http.ts";

export const saveOrUpdateFlashSale = async (data: any) => {
    return http.post("/sms/flashSale/saveOrUpdate", data)
}

export const getFlashSalePage = async (data: any) => {
    return http.post("/sms/flashSale/page", data)
}


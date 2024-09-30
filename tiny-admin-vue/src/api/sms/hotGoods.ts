import http from "@/utils/http.ts";

export const getSmsHotGoodsPage = async (data: any) => {
    return http.post("/sms/hotGoods/page", data)
}

export const saveHotGoods = async (data: any) => {
    return http.post("/sms/hotGoods/save", data)
}

export const updateHotGoods = async (data: any) => {
    return http.post("/sms/hotGoods/update", data)
}
import http from "@/utils/http.ts";

export const getSmsHotGoodsPage = async (data: any) => {
    return http.post("/sms/hotGoods/page", data)
}

export const getAllHotGoodsIds = async () => {
    return http.get("/sms/hotGoods/allGoodsIds")
}

export const deleteSmsHotGoods = (id: any) => {
    return http.delete('/sms/hotGoods/' + id)
}

export const saveHotGoods = async (data: any) => {
    return http.post("/sms/hotGoods/save", data)
}

export const updateHotGoods = async (data: any) => {
    return http.post("/sms/hotGoods/update", data)
}
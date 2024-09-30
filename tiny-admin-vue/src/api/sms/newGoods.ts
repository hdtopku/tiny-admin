import http from "@/utils/http.ts";

export const getSmsNewGoodsPage = async (data: any) => {
    return http.post("/sms/newGoods/page", data)
}

export const saveNewGoods = async (data: any) => {
    return http.post("/sms/newGoods/save", data)
}

export const updateNewGoods = async (data: any) => {
    return http.post("/sms/newGoods/update", data)
}
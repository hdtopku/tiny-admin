import http from "@/utils/http.ts";

export const getSmsNewGoodsPage = async (data: any) => {
    return http.post("/sms/newGoods/page", data)
}

export const saveOrUpdateNewGoods = async (data: any) => {
    return http.post("/sms/newGoods/saveOrUpdate", data)
}
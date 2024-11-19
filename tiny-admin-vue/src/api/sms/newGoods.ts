import http from "@/utils/http.ts";

export const execQuery = async (data: any) => {
    return http.post("/sms/newGoods/page", data)
}

export const getAllNewGoodsIds = async () => {
    return http.get("/sms/newGoods/allGoodsIds")
}

export const deleteById = (id: any) => {
    return http.delete('/sms/newGoods/' + id)
}

export const assignGoods = async (data: any) => {
    return http.post("/sms/newGoods/save", data)
}

export const updateRecord = async (data: any) => {
    return http.post("/sms/newGoods/update", data)
}
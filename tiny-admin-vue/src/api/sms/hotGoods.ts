import http from "@/utils/http.ts"

export const deleteById = (id: any) => {
    return http.delete('/sms/hotGoods/' + id)
}

export const saveOrUpdate = async (data: any) => {
    return http.post("/sms/hotGoods/update", data)
}

export const execQuery = async (data: any) => {
    return http.post("/sms/hotGoods/page", data)
}

export const getAllHotGoodsIds = async () => {
    return http.get("/sms/hotGoods/allGoodsIds")
}

export const saveHotGoods = async (data: any) => {
    return http.post("/sms/hotGoods/save", data)
}
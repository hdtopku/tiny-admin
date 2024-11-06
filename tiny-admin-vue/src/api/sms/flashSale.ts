import http from "@/utils/http.ts"

export const updateFlashSale = async (data: any) => {
    return http.post("/sms/flashSale/saveOrUpdate", data)
}

export const getFlashSalePage = async (data: any) => {
    return http.post("/sms/flashSale/page", data)
}
export const deleteFlashSale = (id: any) => {
    return http.delete('/sms/hotGoods/' + id)
}


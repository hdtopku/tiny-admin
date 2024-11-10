import http from "@/utils/http.ts";

export const assignGoods = (flashId: string, goodsIds: string[]) => {
    return http.post('/sms/flashGoodsRel/assignGoodsIds/' + flashId, goodsIds)
}

export const removeGoods = (flashId: string, goodsIds: string[]) => {
    return http.post('/sms/flashGoodsRel/removeGoodsIds/' + flashId, goodsIds)
}

export const getFlashGoodsIds = async (flashSaleId: any) => {
    return http.get("/sms/flashGoodsRel/getGoodsIds/"+flashSaleId)
}
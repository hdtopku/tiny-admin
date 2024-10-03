import http from "@/utils/http.ts";

export const assignGoods = (flashId: string, goodsIds: string[]) => {
    return http.post('/sms/flashGoodsRel/assignGoodsIds/' + flashId, goodsIds)
}

export const removeGoods = (flashId: string, goodsIds: string[]) => {
    return http.post('/sms/flashGoodsRel/removeGoodsIds/' + flashId, goodsIds)
}
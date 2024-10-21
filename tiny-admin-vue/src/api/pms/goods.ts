import http from "@/utils/http.ts";

export const getGoodsPage = async (data: any) => {
    return http.post("/pms/goods/page", data)
}

export const saveOrUpdateGoods = async (data: any) => {
    return http.post("/pms/goods/saveOrUpdate", data)
}

export const getGoodsListByIds = async (data: any) => {
    return http.post("/pms/goods/listByIds", data)
}

export const deleteGoodsById = async (id: any) => {
    return http.get("/pms/goods/delete/" + id)
}
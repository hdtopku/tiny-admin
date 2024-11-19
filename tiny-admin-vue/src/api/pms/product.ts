import http from "@/utils/http.ts";

export const execQuery = async (data: any) => {
    return http.post("/pms/product/page", data)
}

export const saveOrUpdate = async (data: any) => {
    return http.post("/pms/product/saveOrUpdate", data)
}

export const getGoodsListByIds = async (data: any) => {
    return http.post("/pms/product/listByIds", data)
}
export const deleteById = async (id: any) => {
    return http.get("/pms/product/delete/" + id)
}
import http from "@/utils/http.ts";

export const getGoodsPage = async (data: any) => {
    return http.post("/pms/goods/page", data)
}
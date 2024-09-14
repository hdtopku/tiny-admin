import http from "@/utils/http.ts";

export const getBrandPage = async (data: any) => {
    return http.post("/pms/brand/page", data)
}

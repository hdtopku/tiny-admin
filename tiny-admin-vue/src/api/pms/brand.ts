import http from "@/utils/http.ts";

export const getBrandPage = async (data: any) => {
    return http.post("/pms/brand/page", data)
}
export const getAllBrands = async () => {
    return http.get("/pms/brand/all")
}
export const saveOrUpdateBrand = async (data: any) => {
    return http.post("/pms/brand/saveOrUpdate", data)
}
export const deleteBrandById = async (id: any) => {
    return http.get("/pms/brand/delete/" + id)
}
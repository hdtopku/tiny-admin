import http from "@/utils/http.ts"

export const saveOrUpdate = async (data: any) => {
    return http.post("/sms/flashSale/saveOrUpdate", data)
}

export const execQuery = async (data: any) => {
    return http.post("/sms/flashSale/page", data)
}
export const deleteById = (id: any) => {
    return http.delete('/sms/flashSale/' + id)
}


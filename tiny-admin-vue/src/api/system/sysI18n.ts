import http from "@/utils/http.ts";

export const getI18nPage = async (data: any) => {
    return http.post("/system/sysI18nRaw/page", data)
}

export const saveOrUpdateTranslation = async (data: any) => {
    return http.post("/system/sysI18nTranslation/saveOrUpdate", data)
}
export const saveOrUpdateRawContent = async (data: any) => {
    return http.post("/system/sysI18nRaw/saveOrUpdate", data)
}

export const deleteRawContent = async (id: any) => {
    return http.delete("/system/sysI18nRaw/" + id)
}

export const deleteTranslation = async (id: any) => {
    return http.delete("/system/sysI18nTranslation/" + id)
}
import http from "@/utils/http.ts";

export const getI18nPage = async (data: any) => {
    return http.post("/system/sysI18nRaw/page", data)
}

export const saveOrUpdateTranslation = async (data: any) => {
    return http.post("/system/sysI18nTranslation/saveOrUpdate", data)
}
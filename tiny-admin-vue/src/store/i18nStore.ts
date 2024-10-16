import {defineStore} from "pinia";
import i18n from "@/utils/i18n.ts";
import {useUserStore} from "@/store/index.ts";

export const i18nStore = defineStore('i18n', () => {
    const localeCode = ref('en-US')
    const locale = ref('EN')
    const localeMap = new Map([
        ['EN', 'en-US'],
        ['CN', 'zh-CN'],
        ['JP', 'ja'],
        ['KR', 'ko'],
        ['TC', 'zh-CHT']
    ])
    // 获取浏览器默认语言
    // const getBrowserLang = function () {
    //     let browserLang = navigator.language ? navigator.language : (navigator.languages ? navigator.languages[0] : 'en-US')
    //     let defaultBrowserLang = ''
    //     if (browserLang.toLowerCase() === 'cn' || browserLang.toLowerCase() === 'zh' || browserLang.toLowerCase() === 'zh-cn') {
    //         defaultBrowserLang = 'zh-CN'
    //     } else {
    //         defaultBrowserLang = 'en-US'
    //     }
    //     return defaultBrowserLang
    // }
    const toggleLocale = async (languageCode: string, needFetchInfo = false) => {
        if (!localeMap.has(languageCode)) return
        locale.value = languageCode
        localeCode.value = localeMap.get(languageCode) || 'EN'
        i18n.locale = localeMap.get(languageCode)
        if (needFetchInfo) await useUserStore().refreshUserInfo()
    }
    return {
        locale,
        localeCode,
        toggleLocale
    }
}, {
    persist: {
        storage: localStorage,
    }
})

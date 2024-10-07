// import {defineStore} from "pinia";
// import i18n from "@/utils/i18n.ts";
//
// export const i18nStore = defineStore('i18n', () => {
//     const locale = ref('en-US')
//     // 获取浏览器默认语言
//     // const getBrowserLang = function () {
//     //     let browserLang = navigator.language ? navigator.language : (navigator.languages ? navigator.languages[0] : 'en-US')
//     //     let defaultBrowserLang = ''
//     //     if (browserLang.toLowerCase() === 'cn' || browserLang.toLowerCase() === 'zh' || browserLang.toLowerCase() === 'zh-cn') {
//     //         defaultBrowserLang = 'zh-CN'
//     //     } else {
//     //         defaultBrowserLang = 'en-US'
//     //     }
//     //     return defaultBrowserLang
//     // }
//     const toggleLocale = () => {
//         i18n.locale = locale.value = locale.value === 'zh-CN' ? 'en-US' : 'zh-CN'
//         console.log(i18n.locale, locale.value)
//     }
//     return {
//         i18n,
//         locale,
//         toggleLocale
//     }
// }, {
//     persist: {
//         storage: localStorage,
//         paths: ['locale']
//     }
// })

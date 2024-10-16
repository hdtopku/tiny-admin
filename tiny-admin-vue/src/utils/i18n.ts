import {createI18n} from 'vue-i18n'
import zh from '../../locales/zh-CN.json'
import en from '../../locales/en-US.json'
import cht from '../../locales/zh-CHT.json'
import ja from '../../locales/ja.json'
import ko from '../../locales/ko.json'

const i18n: any = createI18n({
    locale: JSON.parse(localStorage.getItem('i18n') || '{"localeCode":"zh-CN"}').localeCode,
    // 默认语言
    messages: {
        'zh-CN': zh, // 中文
        'en-US': en, // 英文
        'zh-CHT': cht,
        'ja': ja,
        'ko': ko,
    },
})
export const t = i18n.global.t
export default i18n

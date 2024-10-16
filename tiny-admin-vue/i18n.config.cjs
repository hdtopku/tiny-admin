module.exports = {
    input: 'src',
    output: '',
    exclude: ['**/node_modules/**/*'],
    rules: {
        js: {
            caller: '',
            functionName: 't',
            customizeKey: function getCustomizeKey(key, path) {
                return key
            },
            customSlot: function getCustomSlot(slotValue) {
                return `{${slotValue}}`
            },
            importDeclaration: 'import { t } from "@/utils/i18n.ts"',
        },
        ts: {
            caller: '',
            functionName: 't',
            customizeKey: function getCustomizeKey(key, path) {
                return key
            },
            customSlot: function getCustomSlot(slotValue) {
                return `{${slotValue}}`
            },
            importDeclaration: 'import { t } from "@/utils/i18n.ts"',
        },
        cjs: {
            caller: '',
            functionName: 't',
            customizeKey: function getCustomizeKey(key, path) {
                return key
            },
            customSlot: function getCustomSlot(slotValue) {
                return `{${slotValue}}`
            },
            importDeclaration: 'import { t } from "@/utils/i18n.ts"',
        },
        mjs: {
            caller: '',
            functionName: 't',
            customizeKey: function getCustomizeKey(key, path) {
                return key
            },
            customSlot: function getCustomSlot(slotValue) {
                return `{${slotValue}}`
            },
            importDeclaration: 'import { t } from "i18n"',
        },
        jsx: {
            caller: '',
            functionName: 't',
            customizeKey: function getCustomizeKey(key, path) {
                return key
            },
            customSlot: function getCustomSlot(slotValue) {
                return `{${slotValue}}`
            },
            importDeclaration: 'import { t } from "i18n"',
            functionSnippets: '',
        },
        tsx: {
            caller: '',
            functionName: 't',
            customizeKey: function getCustomizeKey(key, path) {
                return key
            },
            customSlot: function getCustomSlot(slotValue) {
                return `{${slotValue}}`
            },
            importDeclaration: 'import { t } from "i18n"',
            functionSnippets: '',
        },
        vue: {
            caller: 'this',
            functionNameInTemplate: '$t',
            functionNameInScript: '$t',
            customizeKey: function getCustomizeKey(key, path) {
                return key
            },
            customSlot: function getCustomSlot(slotValue) {
                return `{${slotValue}}`
            },
            importDeclaration: 'import { t } from "@/utils/i18n.ts"',
            tagOrder: ['template', 'script', 'style'],
        },
    },
    prettier: {semi: false, singleQuote: true},
    incremental: true,
    skipExtract: false,
    localePath: './locales/zh-CN.json',
    localeFileType: 'json',
    excelPath: './locales.xlsx',
    exportExcel: false,
    skipTranslate: false,
    locales: ['en-US', 'zh-CN', 'zh-CHT', 'ko', 'ja'],
    globalRule: {ignoreMethods: []},
    adjustKeyMap: function (allKeyValue, currentFileKeyMap, currentFilePath) {
        return allKeyValue
    },
}

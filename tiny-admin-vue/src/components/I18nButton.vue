<script lang="ts" setup>

import {DownOutlined} from "@ant-design/icons-vue";
import index from "@/views/system/i18n/index.vue";
import {useI18nStore} from "@/store";
import {key} from "localforage";

const languageMap = new Map([['CN', '🇨🇳 中文'], ['EN', '🇺🇸 ENG'],
  ['JP', '🇯🇵 日語'], ['KR', '🇰🇷 한국어'], ['HK', '🇭🇰 繁体']])
const getCurrentLanguage = () => {
  return languageMap.has(useI18nStore().locale) ? languageMap.get(useI18nStore().locale) : '🇨🇳 中文'
}
const handleChangeLanguage = (langCode: string) => {
  useI18nStore().toggleLocale(langCode)
}
</script>

<template>
  <a-dropdown>
    <template #overlay>
      <a-menu>
        <a-menu-item v-for="[langCode, langName] of new Map([...languageMap].filter(([k]) => k !== useI18nStore().locale))"
                     :key="index"
                     @click="handleChangeLanguage(langCode)">
          <a-button :key="key" size="small" type="link">
            {{ langName }}
          </a-button>
        </a-menu-item>
      </a-menu>
    </template>
    <a-button class="group" ghost size="small" type="primary">
      <div class="group-hover:scale-105 duration-300 inline-flex items-center gap-1">
        {{ getCurrentLanguage() }}
        <DownOutlined/>
      </div>
    </a-button>
  </a-dropdown>
</template>

<style scoped>

</style>
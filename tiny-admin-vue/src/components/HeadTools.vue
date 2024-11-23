<template>
  <div class="flex items-center max-sm:gap-2 sm:gap-4 justify-end">
    <a-tooltip>
      <template #title class="hidden sm:block">
        <span>Open Video Introduction</span>
      </template>
      <a-button
          class="group"
          ghost
          shape="circle"
          type="primary"
          @click="openYoutube"
      >
        <div class="inline-flex items-center">
          <VideoCameraOutlined
              class="mt-[3px] text-base group-hover:scale-110 duration-300"
          />
        </div>
      </a-button>
    </a-tooltip>
    <a-tooltip>
      <template #title>
        <span>Open Github</span>
      </template>
      <a-button
          class="group"
          ghost
          shape="circle"
          type="primary"
          @click="openGithub"
      >
        <div class="inline-flex items-center">
          <GithubOutlined
              class="mt-[3px] text-base group-hover:scale-110 duration-300"
          />
        </div>
      </a-button>
    </a-tooltip>
    <a-tooltip>
      <template #title>
        <span>Open Documentation</span>
      </template>
      <a-button @click="openDocumentation" class="group" ghost shape="circle" type="primary">
        <div class="inline-flex items-center">
          <ReadOutlined
              class="mt-[3px] text-base group-hover:scale-110 duration-300"
          />
        </div>
      </a-button>
    </a-tooltip>
    <DarkToggle />
    <!--    <I18nButton />-->
  </div>
</template>

<script lang="ts" setup>
import {GithubOutlined, ReadOutlined, VideoCameraOutlined} from '@ant-design/icons-vue'

const openYoutube = () => {
  window.open('https://youtu.be/0jyElwLyxgk', '_blank')
}
const openGithub = () => {
  window.open('https://github.com/hdtopku/tiny-admin', '_blank')
}
const openDocumentation = () => {
  window.open('https://i7u0jn0r7j0.jp.larksuite.com/wiki/FT6owCCYRiTFj0kRQBKjWJtypac', '_blank')
}
</script>

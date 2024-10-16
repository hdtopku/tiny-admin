<template>
  <span
      :class="{
      'text-indigo-400': !isDark,
      'text-orange-400 hover:text-orange-500': isDark,
    }"
      class="relative inline-flex"
      @click="toggleDark()"
  >
    <a-tooltip>
      <template #title>
        <span>{{ isDark ? $t('切换至白天模式') : $t('切换至夜间模式') }}</span>
      </template>
      <a-button class="group" ghost shape="circle" type="primary">
        <transition-group
            appear
            enter-active-class="animate__animated animate__rotateIn animate__slow"
        >
          <svg
              v-if="!isDark"
              key="sun"
              class="group-hover:scale-105 duration-300 text-indigo-400 inline-flex absolute right-1 bottom-1"
              fill="currentColor"
              height="22px"
              viewBox="0 -960 960 960"
              width="22px"
              xmlns="http://www.w3.org/2000/svg"
          >
            <path
                d="M480-120q-150 0-255-105T120-480q0-150 105-255t255-105q14 0 27.5 1t26.5 3q-41 29-65.5 75.5T444-660q0 90 63 153t153 63q55 0 101-24.5t75-65.5q2 13 3 26.5t1 27.5q0 150-105 255T480-120Zm0-80q88 0 158-48.5T740-375q-20 5-40 8t-40 3q-123 0-209.5-86.5T364-660q0-20 3-40t8-40q-78 32-126.5 102T200-480q0 116 82 198t198 82Zm-10-270Z"
            />
          </svg>
          <svg
              v-else
              key="moon"
              class="group-hover:scale-105 duration-300 text-amber-400 inline-flex absolute right-1 bottom-1"
              fill="currentColor"
              height="22px"
              viewBox="0 -960 960 960"
              width="22px"
              xmlns="http://www.w3.org/2000/svg"
          >
            <path
                d="M440-800v-120h80v120h-80Zm0 760v-120h80v120h-80Zm360-400v-80h120v80H800Zm-760 0v-80h120v80H40Zm708-252-56-56 70-72 58 58-72 70ZM198-140l-58-58 72-70 56 56-70 72Zm564 0-70-72 56-56 72 70-58 58ZM212-692l-72-70 58-58 70 72-56 56Zm268 452q-100 0-170-70t-70-170q0-100 70-170t170-70q100 0 170 70t70 170q0 100-70 170t-170 70Zm0-80q67 0 113.5-46.5T640-480q0-67-46.5-113.5T480-640q-67 0-113.5 46.5T320-480q0 67 46.5 113.5T480-320Zm0-160Z"
            />
          </svg>
        </transition-group>
      </a-button>
    </a-tooltip>
  </span>
</template>
<script lang="ts" setup>
import {useDark, useTitle, useToggle} from '@vueuse/core'

const isDark = useDark({
  selector: 'body',
  attribute: 'color-scheme',
  valueDark: 'dark',
  valueLight: 'light',
  onChanged(dark: boolean) {
    // console.log(localStorage.getItem("vueuse-color-scheme"))
    useTitle(() => (dark ? `🌙Tiny Admin` : `☀️Tiny Admin`))
  },
})
const toggleDark = () => {
  setTimeout(() => {
    useToggle(isDark)()
  }, 300)
}
</script>

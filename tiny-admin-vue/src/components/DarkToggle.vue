<script lang="ts" setup>
import {useDark, useTitle, useToggle} from '@vueuse/core'

const isDark = useDark()
const toggleDark = () => {
  setTimeout(() => {
    document.documentElement.classList.toggle('dark')
    useToggle(isDark)()
  }, 300)
}
watch(isDark, () => {
  useTitle(() => isDark.value ? `🌙Tiny Admin` : `☀️Tiny Admin`)
}, {immediate: true})

</script>

<template>
    <span :class="{'text-indigo-400 bg-gray-700': !isDark, 'text-orange-400 hover:text-orange-500 border-orange-200': isDark}" class="rounded-full cursor-pointer hover:shadow-lg inline-flex items-center justify-center p-1"
          type="text"
          @click="toggleDark()">
      <transition-group appear enter-active-class="animate__animated animate__rotateIn animate__slow"
      >
        <svg v-if="!isDark" key="sun" fill="currentColor" height="24px" viewBox="0 -960 960 960"
             width="24px" xmlns="http://www.w3.org/2000/svg">
          <path
              d="M480-120q-150 0-255-105T120-480q0-150 105-255t255-105q14 0 27.5 1t26.5 3q-41 29-65.5 75.5T444-660q0 90 63 153t153 63q55 0 101-24.5t75-65.5q2 13 3 26.5t1 27.5q0 150-105 255T480-120Zm0-80q88 0 158-48.5T740-375q-20 5-40 8t-40 3q-123 0-209.5-86.5T364-660q0-20 3-40t8-40q-78 32-126.5 102T200-480q0 116 82 198t198 82Zm-10-270Z"/>
        </svg>
        <svg v-else key="moon" fill="currentColor" height="24px" viewBox="0 -960 960 960"
             width="24px" xmlns="http://www.w3.org/2000/svg">
          <path
              d="M440-760v-160h80v160h-80Zm266 110-55-55 112-115 56 57-113 113Zm54 210v-80h160v80H760ZM440-40v-160h80v160h-80ZM254-652 140-763l57-56 113 113-56 54Zm508 512L651-255l54-54 114 110-57 59ZM40-440v-80h160v80H40Zm157 300-56-57 112-112 29 27 29 28-114 114Zm283-100q-100 0-170-70t-70-170q0-100 70-170t170-70q100 0 170 70t70 170q0 100-70 170t-170 70Zm0-80q66 0 113-47t47-113q0-66-47-113t-113-47q-66 0-113 47t-47 113q0 66 47 113t113 47Zm0-160Z"/>
        </svg>
      </transition-group>
    </span>
</template>

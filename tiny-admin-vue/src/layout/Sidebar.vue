<template>
  <a-menu v-model:collapsed="sidebarCollapsed" v-model:open-keys="sidebarOpenKeys"
          v-model:selected-keys="useMenuStore().sidebarSelectedKeys"
          :items="menus" class="overflow-auto h-full " mode="inline"
          :theme="isDark ? 'dark' : 'light'" @click="handleClick">
  </a-menu>
</template>
<script lang="ts" setup>
import {useMenuStore, useUserStore} from "@/store/index.ts"
import {storeToRefs} from 'pinia'
import {useDark} from "@vueuse/core";

const isDark = useDark()

const menus = computed(() => useUserStore().getSidebar())
const handleClick = ({key}) => {
  // router.push(key) // 点击后，页面可能不跳转，所以改为下面这种方式进行跳转
  useMenuStore().changeTab(key)
}
const {sidebarOpenKeys, sidebarCollapsed} = storeToRefs(useMenuStore())

let preOpenKeys
watch(sidebarOpenKeys, (_val, oldVal) => {
  preOpenKeys = oldVal
},)
watch(sidebarCollapsed, (val) => {
  if (!val) sidebarOpenKeys.value = preOpenKeys
},)
</script>
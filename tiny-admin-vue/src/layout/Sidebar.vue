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
import router from "@/router"
import {useDark} from "@vueuse/core";

const isDark = useDark()

const menus = computed(() => useUserStore().getSidebar())
const handleClick = ({key}) => {
  router.push(key)
}
const {sidebarOpenKeys, sidebarCollapsed} = storeToRefs(useMenuStore())

let preOpenKeys
watch(sidebarOpenKeys, (_val, oldVal) => {
  preOpenKeys = oldVal;
},)
watch(sidebarCollapsed, (val) => {
  if (!val) sidebarOpenKeys.value = preOpenKeys
},)
</script>
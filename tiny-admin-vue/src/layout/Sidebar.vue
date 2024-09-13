<template>
  <a-menu v-model:collapsed="sidebarCollapsed" v-model:open-keys="sidebarOpenKeys"
          v-model:selected-keys="useMenuStore().sidebarSelectedKeys"
          :items="menus" class="overflow-auto h-full " mode="inline"
          theme="dark" @click="handleClick">
  </a-menu>
</template>
<script lang="ts" setup>
import {useMenuStore, useUserStore} from "@/store/index.ts";
import {storeToRefs} from 'pinia'

import router from "@/router";
import useGlobal from "@/hooks/useGlobal.ts";

const menus = ref(useUserStore().getSidebar())
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
const {$bus} = useGlobal()
$bus.on('update-user-info', () => {
  menus.value = useUserStore().getSidebar()
  useUserStore().refreshUserInfo()
  // location.reload()
})
</script>
<style scoped>
</style>
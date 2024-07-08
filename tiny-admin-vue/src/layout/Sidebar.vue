<template>
  <a-menu v-model:collapsed="menuCollapsed" v-model:selected-keys="useMenuStore().selectedKeys"
          v-model:open-keys="openKeys"
          @click="handleClick" :items="menus" class="overflow-auto h-full "
          theme="dark" mode="inline">
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
const {openKeys, menuCollapsed} = storeToRefs(useMenuStore())

let preOpenKeys
watch(openKeys, (_val, oldVal) => {
  preOpenKeys = oldVal;
},)
watch(menuCollapsed, (val) => {
  if (!val) openKeys.value = preOpenKeys
},)
const {$bus} = useGlobal()
$bus.on('update-user-info', () => {
  // menus.value = useUserStore().getSidebar()

  location.reload()
})
</script>
<style scoped>
#components-layout-demo-side .logo {
  height: 32px;
  margin: 16px;
  background: rgba(255, 255, 255, 0.3);
}

.site-layout .site-layout-background {
  background: #fff;
}

[data-theme='dark'] .site-layout .site-layout-background {
  background: #141414;
}
</style>
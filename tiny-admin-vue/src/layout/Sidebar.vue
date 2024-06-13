<template>
  <a-menu v-model:selected-keys="useMenuStore().selectedKeys"
          v-model:open-keys="useMenuStore().openKeys"
          @click="handleClick" :items="menus" class="overflow-auto h-full "
          theme="dark" mode="inline">

  </a-menu>
</template>
<script lang="ts" setup>
import {useMenuStore, useUserStore} from "@/store/index.ts";
import router from "@/router";
const menus = useUserStore().getSidebar()
const handleClick = ({key}) => {
  router.push(key)
}
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
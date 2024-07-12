<script setup lang="ts">
import router from "@/router";

let timeout = ref(5)
let interval = setInterval(() => {
  if (timeout.value === 1) {
    clearInterval(interval)
    router.push('/home')
    return
  }
  timeout.value--
}, 1000)

</script>

<template>
  <a-result status="404" sub-title="对不起，你访问的页面不存在！" title="404">
    <template #extra>
      <a-button href="/home" type="primary">回到首页，{{ timeout }}秒后自动跳转</a-button>
    </template>
  </a-result>
</template>

<style scoped>

</style>
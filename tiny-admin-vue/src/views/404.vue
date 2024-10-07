<template>
  <a-result
      :sub-title="$t('对不起，你访问的页面不存在！')"
      status="404"
      title="404"
  >
    <template #extra>
      <a-button type="primary" @click="handleClick"
      >{{ $t('回到首页，') }}{{ timeout }}{{ $t('秒后自动跳转') }}
      </a-button
      >
    </template>
  </a-result>
</template>
<script lang="ts" setup>
import router from '@/router'

let timeout = ref(5)
let interval = setInterval(() => {
  if (timeout.value === 1) {
    clearInterval(interval)
    router.push('/home')
    return
  }
  timeout.value--
}, 1000)
const handleClick = () => {
  clearInterval(interval)
  router.push('/home')
}
</script>

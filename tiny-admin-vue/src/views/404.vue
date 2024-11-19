<template>
  <a-result
      :sub-title="'Sorry, the page you are looking for does not exist.'"
      status="404"
      title="404"
  >
    <template #extra>
      <a-button type="primary" @click="handleClick">
        Return to Home, {{ timeout }} seconds until automatic redirection
      </a-button>
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

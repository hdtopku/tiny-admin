<template>
  <a-result
      :sub-title="'Sorry, you do not have permission to access this page. Please contact the administrator.'"
      status="403"
      title="403"
  >
    <template #extra>
      <a-button type="primary" @click="handleClick">
        Return to Home
      </a-button>
    </template>
  </a-result>
</template>

<script lang="ts" setup>
import router from '@/router'

const handleClick = () => {
  router.push('/home')
}
</script>
